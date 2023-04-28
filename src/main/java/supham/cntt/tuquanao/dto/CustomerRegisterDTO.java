package supham.cntt.tuquanao.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterDTO {

  private Integer idCustomer;

  @NotBlank(message = "name.blank")
  private String name;

  private String address;

  private String phone;

  @NotBlank(message = "username.blank")
  @Size(min = 2, max = 30)
  private String username;

  @NotBlank(message = "password.blank")
  @Size(min = 4, max = 50)
  private String password;
}
