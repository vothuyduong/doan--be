package supham.cntt.tuquanao.service;

import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.common.GlobalConstants.Number;
import supham.cntt.tuquanao.dto.PriceDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Cart;
import supham.cntt.tuquanao.model.DetailCart;

@Service
@Slf4j
public class CartService extends BasicService {

  @Transactional
  public void createCart(Integer idProduct, Integer idSize, Integer quantity) throws TuQuanAoException {

    if(Objects.isNull(productRepository.findByIdProduct(idProduct))) {
      throw new TuQuanAoException(Message.ID_PRODUCT_INVALID, HttpStatus.BAD_REQUEST.value());
    }
    if(Objects.isNull(sizeRepository.findByIdSize(idSize))) {
      throw new TuQuanAoException(Message.ID_SIZE_INVALID, HttpStatus.BAD_REQUEST.value());
    }
    if(quantity < Number.NUMBER_ONE) {
      throw new TuQuanAoException(Message.CART_QUANTITY_MIN_1, HttpStatus.BAD_REQUEST.value());
    }
    Cart cart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    if(Objects.isNull(cart)) {
      cart = cartRepository.save(new Cart(null, getUserDetail().getIdCustomer()));
    }
    DetailCart detail = detailCartRepository.findByIdCartAndIdSizeAndIdProduct(cart.getIdCart(), idSize, idProduct);
    if(Objects.isNull(detail)) {
      detailCartRepository.save(new DetailCart(cart.getIdCart(), idSize, idProduct, quantity));
    } else {
      DetailCart detailUpdate = new DetailCart(detail.getIdCart(), detail.getIdSize(), detail.getIdProduct(), detail.getQuantity() + quantity);
      detailCartRepository.save(detailUpdate);
    }
  }

  public List<PriceDTO> getDetailCart() throws TuQuanAoException {
    Integer idCart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer()).getIdCart();
    return detailCartRepository.getListCart(idCart);
  }

  @Transactional
  public void deleteItem(Integer idProduct, Integer idSize) throws TuQuanAoException {
    Integer idCart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer()).getIdCart();
    DetailCart detailCart = detailCartRepository.findByIdCartAndIdSizeAndIdProduct(idCart, idSize, idProduct);
    detailCartRepository.delete(detailCart);
  }

  @Transactional
  public void deleteAll() throws TuQuanAoException {
    Integer idCart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer()).getIdCart();
    detailCartRepository.deleteDetailCartsByIdCart(idCart);
  }

  @Transactional
  public void updateQuantity(Integer idProduct, Integer idSize, Integer quantity) {

  }
}
