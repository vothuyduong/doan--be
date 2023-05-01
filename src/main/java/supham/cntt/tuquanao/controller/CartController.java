package supham.cntt.tuquanao.controller;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import supham.cntt.tuquanao.common.ResponseEntityUtil;
import supham.cntt.tuquanao.dto.CartDTO;
import supham.cntt.tuquanao.dto.PriceCartDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.service.CartService;

@RestController
@CrossOrigin("*")
@RequestMapping("/cart")
public class CartController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  CartService cartService;

  @GetMapping("/count")
  public ResponseEntity<?> countQuantity() throws TuQuanAoException {
    return responseEntityUtil.generateResponse(HttpStatus.OK, cartService.getQuantityCart());
  }

  @PostMapping("/save")
  public ResponseEntity<?> createCart(
      @RequestParam(name = "idProduct") Integer idProduct,
      @RequestParam(name = "idSize") Integer idSize,
      @RequestParam(name = "quantity") Integer quantity) throws TuQuanAoException {
    cartService.createCart(idProduct, idSize, quantity);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @GetMapping("/detail")
  public ResponseEntity<?> getDetailCart() throws TuQuanAoException {
    return responseEntityUtil.generateResponse(HttpStatus.OK, cartService.getDetailCart());
  }

  @DeleteMapping("/delete-item")
  public ResponseEntity<?> deleteItem(@RequestParam(name = "idProduct") Integer idProduct,
      @RequestParam(name = "idSize") Integer idSize)
      throws TuQuanAoException {
    cartService.deleteItem(idProduct, idSize);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @PostMapping("/update-quantity")
  public ResponseEntity<?> updateQuantity(@RequestBody CartDTO detail)
      throws TuQuanAoException {
    cartService.updateQuantity(detail);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }
}
