package supham.cntt.tuquanao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supham.cntt.tuquanao.common.ResponseEntityUtil;
import supham.cntt.tuquanao.service.CustomerService;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  CustomerService customerService;

  @GetMapping("/count")
  public ResponseEntity<?> count() {
    return responseEntityUtil.generateResponse(HttpStatus.OK, customerService.countCustomer());
  }
}
