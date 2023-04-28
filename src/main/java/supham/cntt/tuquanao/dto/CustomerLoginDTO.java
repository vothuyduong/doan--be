package supham.cntt.tuquanao.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerLoginDTO {

  @NotBlank(message = "username.empty")
  @Size(min = 2, max = 30)
  private String username;

  @NotBlank(message = "password.empty")
  @Size(min = 4, max = 50)
  private String password;
}
