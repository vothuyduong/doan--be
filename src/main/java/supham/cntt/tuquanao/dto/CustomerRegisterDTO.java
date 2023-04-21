package supham.cntt.tuquanao.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterDTO {

  private Integer idCustomer;

  @NotEmpty(message = "name.empty")
  private String name;

  private String address;

  private String phone;

  @NotEmpty(message = "username.empty")
  @Size(min = 2, max = 15)
  private String username;

  @NotEmpty(message = "password.empty")
  @Size(min = 4, max = 20)
  private String password;
}
