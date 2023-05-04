package supham.cntt.tuquanao.controller;

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
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Price;
import supham.cntt.tuquanao.service.PriceService;

@RestController
@CrossOrigin("*")
@RequestMapping("/price")
public class PriceController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  PriceService priceService;

  @GetMapping("")
  ResponseEntity<?> getAll() {
    return responseEntityUtil.generateResponse(HttpStatus.OK, priceService.list());
  }

  @GetMapping("/info")
  ResponseEntity<?> getDetail(@RequestParam(name = "idProduct") Integer idProduct, @RequestParam(name = "idSize") Integer idSize) {
    return responseEntityUtil.generateResponse(HttpStatus.OK, priceService.getDetail(idProduct, idSize));
  }

  @PostMapping("/save")
  ResponseEntity<?> save(@RequestBody Price price) throws TuQuanAoException {
    priceService.save(price);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @DeleteMapping("/delete")
  ResponseEntity<?> delete(@RequestParam(name = "idProduct") Integer idProduct, @RequestParam(name = "idSize") Integer idSize) {
    priceService.delete(idProduct, idSize);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @GetMapping("/count")
  ResponseEntity<?> countQuantity() {
    return responseEntityUtil.generateResponse(HttpStatus.OK, priceService.countPro());
  }
}
