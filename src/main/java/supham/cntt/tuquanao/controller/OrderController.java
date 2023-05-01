package supham.cntt.tuquanao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supham.cntt.tuquanao.common.ResponseEntityUtil;
import supham.cntt.tuquanao.dto.DetailOrderDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.service.OrderService;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  OrderService orderService;

  @GetMapping("")
  ResponseEntity<?> getList() throws TuQuanAoException {
    return responseEntityUtil.generateResponse(HttpStatus.OK, orderService.listProduct());
  }

  @PostMapping("/save")
  ResponseEntity<?> save(@RequestBody DetailOrderDTO detailOrderDTO) throws TuQuanAoException {
    orderService.save(detailOrderDTO);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }
}
