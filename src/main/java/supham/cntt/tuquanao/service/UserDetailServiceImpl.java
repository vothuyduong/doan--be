package supham.cntt.tuquanao.service;

import java.util.Objects;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.common.MessageUtil;
import supham.cntt.tuquanao.dto.UserDetailImpl;
import supham.cntt.tuquanao.model.Customer;

@Service
public class UserDetailServiceImpl extends BasicService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Customer customer = this.customerRepository.findCustomerByUsername(username);
    if (Objects.isNull(customer)) {
      throw new UsernameNotFoundException(MessageUtil.getMessage(Message.UNAUTHORIZED_CODE));
    }
    return UserDetailImpl.buil(customer);
  }
}
