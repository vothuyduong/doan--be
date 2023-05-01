package supham.cntt.tuquanao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.common.GlobalConstants.Number;
import supham.cntt.tuquanao.dto.CartDTO;
import supham.cntt.tuquanao.dto.PriceCartDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Cart;
import supham.cntt.tuquanao.model.DetailCart;

@Service
@Slf4j
public class CartService extends BasicService {

  public Integer getQuantityCart() throws TuQuanAoException {
    Cart cart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    if(Objects.isNull(cart)) {
      return Number.NUMBER_ZERO;
    }
    Integer idCart = cart.getIdCart();
    return detailCartRepository.countQuantityProduct(idCart);
  }

  @Transactional
  public void createCart(Integer idProduct, Integer idSize, Integer quantity)
      throws TuQuanAoException {

    if (Objects.isNull(productRepository.findByIdProduct(idProduct))) {
      throw new TuQuanAoException(Message.ID_PRODUCT_INVALID, HttpStatus.BAD_REQUEST.value());
    }
    if (Objects.isNull(sizeRepository.findByIdSize(idSize))) {
      throw new TuQuanAoException(Message.ID_SIZE_INVALID, HttpStatus.BAD_REQUEST.value());
    }
    if (quantity < Number.NUMBER_ONE) {
      throw new TuQuanAoException(Message.CART_QUANTITY_MIN_1, HttpStatus.BAD_REQUEST.value());
    }
    Cart cart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    if (Objects.isNull(cart)) {
      cart = cartRepository.save(new Cart(null, getUserDetail().getIdCustomer()));
    }
    DetailCart detail = detailCartRepository.findByIdCartAndIdSizeAndIdProduct(cart.getIdCart(),
        idSize, idProduct);
    if (Objects.isNull(detail)) {
      detailCartRepository.save(new DetailCart(cart.getIdCart(), idSize, idProduct, quantity));
    } else {
      DetailCart detailUpdate = new DetailCart(detail.getIdCart(), detail.getIdSize(),
          detail.getIdProduct(), detail.getQuantity() + quantity);
      detailCartRepository.save(detailUpdate);
    }
  }

  public List<PriceCartDTO> getDetailCart() throws TuQuanAoException {
    Cart cart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    Integer idCart = cart.getIdCart();
    List<PriceCartDTO> rs = detailCartRepository.getListCart(idCart);
    if (rs.size() > Number.NUMBER_ZERO) {
      rs.forEach(pro -> {
        pro.setBase64(getPicture(pro.getIdProduct()));
      });
    }
    return rs;
  }

  @Transactional
  public void deleteItem(Integer idProduct, Integer idSize) throws TuQuanAoException {
    Cart cart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    Integer idCart = cart.getIdCart();
    DetailCart detailCart = detailCartRepository.findByIdCartAndIdSizeAndIdProduct(idCart, idSize,
        idProduct);
    detailCartRepository.delete(detailCart);
  }

  @Transactional
  public void updateQuantity(CartDTO listPR)
      throws TuQuanAoException {
    Cart cart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    Integer idCart = cart.getIdCart();
    detailCartRepository.deleteAllByIdCart(idCart);
    List<PriceCartDTO> list = listPR.getDetail();
    List<DetailCart> listDetail = new ArrayList<>();
    list.forEach(pri -> {
      DetailCart detail = new DetailCart(idCart, pri.getIdSize(), pri.getIdProduct(), pri.getQuantity());
      listDetail.add(detail);
    });
    detailCartRepository.saveAll(listDetail);
  }
}
