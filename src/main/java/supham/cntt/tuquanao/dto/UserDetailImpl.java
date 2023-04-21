package supham.cntt.tuquanao.dto;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import supham.cntt.tuquanao.model.Customer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailImpl implements UserDetails {

  private Integer idCustomer;

  private String name;

  private String address;

  private String phone;

  private String username;

  private String password;

  public static UserDetails buil(Customer customer) {
    return new UserDetailImpl(
        customer.getIdCustomer(),
        customer.getName(),
        customer.getAddress(),
        customer.getPhone(),
        customer.getUsername(),
        customer.getPassword());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
