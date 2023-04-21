package supham.cntt.tuquanao.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import supham.cntt.tuquanao.common.ResponseEntityUtil;
import supham.cntt.tuquanao.dto.CustomerLoginDTO;
import supham.cntt.tuquanao.dto.CustomerRegisterDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.service.CustomerService;

@RestController
@CrossOrigin("*")
public class LoginController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  CustomerService customerService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody CustomerRegisterDTO registerDTO)
      throws TuQuanAoException {
    customerService.register(registerDTO);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody CustomerLoginDTO loginDTO)
      throws TuQuanAoException {
    return responseEntityUtil.generateResponse(HttpStatus.OK, customerService.login(loginDTO));
  }
}
