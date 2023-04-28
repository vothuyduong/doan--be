package supham.cntt.tuquanao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import supham.cntt.tuquanao.common.GlobalConstants;
import supham.cntt.tuquanao.common.GlobalConstants.Character;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationFormDTO {

  @NotBlank(message = "donationForm.fullName.notBlank")
  private String fullName;

  @NotBlank(message = "donationForm.phone.notBlank")
  private String phone;

  @NotBlank(message = "donationForm.getAddress.notBlank")
  private String getAddress;

  @NotNull(message = "donationForm.getTime.notNull")
  private Date getTime;

  private String description = Character.TXT_BLANK;

  @NotBlank(message = "donationForm.status.notBlank")
  private String status;
}
