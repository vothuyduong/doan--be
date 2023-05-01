package supham.cntt.tuquanao.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Number;
import supham.cntt.tuquanao.dto.DetailOrderDTO;
import supham.cntt.tuquanao.dto.PriceCartDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Cart;
import supham.cntt.tuquanao.model.Customer;
import supham.cntt.tuquanao.model.DetailOrder;
import supham.cntt.tuquanao.model.Orders;

@Service
@Slf4j
public class OrderService extends BasicService{

  public DetailOrderDTO listProduct() throws TuQuanAoException {
    Cart cart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    Integer idCart = cart.getIdCart();
    List<PriceCartDTO> rs = detailCartRepository.getListCart(idCart);
    AtomicReference<Double> sum = new AtomicReference<>(0d);
    if (rs.size() > Number.NUMBER_ZERO) {
      rs.forEach(pro -> {
        Double intoMoney = pro.getPrice() * pro.getQuantity();
        pro.setIntoMoney(intoMoney);
        pro.setBase64(getPicture(pro.getIdProduct()));
        sum.set(sum.get() + intoMoney);
      });
    }

    Customer customer = customerRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    DetailOrderDTO detail = new DetailOrderDTO();
    detail.setTakeCustomer(customer.getName());
    detail.setTakeAddress(customer.getAddress());
    detail.setPhoneCustomer(customer.getPhone());
    detail.setTotalMoney(sum.get());
    detail.setDetail(rs);
    return detail;
  }

  @Transactional
  public void save(DetailOrderDTO detailOrderDTO) throws TuQuanAoException {
    Orders order = new Orders(getUserDetail().getIdCustomer(),
        detailOrderDTO.getTakeCustomer(), detailOrderDTO.getTakeAddress(),
        detailOrderDTO.getPhoneCustomer(), detailOrderDTO.getPaymentMethod(), detailOrderDTO.getTotalMoney(), new java.util.Date());
    Orders orderNew = orderRepository.save(order);
    List<PriceCartDTO> detail = detailOrderDTO.getDetail();
    detail.forEach(de -> {
      DetailOrder detailOrder = new DetailOrder();
      detailOrder.setIdOrder(orderNew.getIdOrder());
      detailOrder.setNameProduct(de.getNameProduct());
      detailOrder.setNameSize(de.getNameSize());
      detailOrder.setPrice(de.getPrice());
      detailOrder.setQuantity(de.getQuantity());
      detailOrder.setIntoMoney(de.getIntoMoney());
      detailOrder.setIdProduct(de.getIdProduct());
      detailOrder.setIdSize(de.getIdSize());
      detailOrderRepository.save(detailOrder);
    });
    Cart cart = cartRepository.findByIdCustomer(getUserDetail().getIdCustomer());
    Integer idCart = cart.getIdCart();
    detailCartRepository.deleteAllByIdCart(idCart);
    cartRepository.delete(cart);
  }
}
