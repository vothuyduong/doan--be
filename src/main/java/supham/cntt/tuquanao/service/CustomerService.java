package supham.cntt.tuquanao.service;

import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.dto.CustomerLoginDTO;
import supham.cntt.tuquanao.dto.CustomerRegisterDTO;
import supham.cntt.tuquanao.dto.JwtResponse;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Customer;

@Service
@Slf4j
public class CustomerService extends BasicService{

  @Transactional
  public void register(CustomerRegisterDTO registerDTO) throws TuQuanAoException {

    Customer cus = customerRepository.findCustomerByUsername(registerDTO.getUsername());
    if(Objects.nonNull(cus)) {
      throw new TuQuanAoException(Message.USERNAME_EXISTS, HttpStatus.BAD_REQUEST.value());
    }
    try {
      Customer cusNew = customerRegisterDTOToCustomer.customerRegisterDTOToCustomer(registerDTO);
      customerRepository.save(cusNew);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new TuQuanAoException(Message.SEVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }

  public JwtResponse login(CustomerLoginDTO loginDTO) throws TuQuanAoException {
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
    Authentication authentication =
        this.authenticationManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return jwtUtils.getResponse(authentication);
  }
}
