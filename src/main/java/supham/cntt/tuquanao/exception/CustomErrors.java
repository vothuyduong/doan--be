package supham.cntt.tuquanao.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import supham.cntt.tuquanao.common.GlobalConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrors {

  private int status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GlobalConstants.DATE_TIME_FORMAT)
  private Date timestamp = new Date();

  private String message;

  public CustomErrors(int status, String message) {
    super();
    this.status = status;
    this.message = message;
  }

  public String createJSON() {
    String json = null;
    try {
      ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
      json = objectWriter.writeValueAsString(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return json;
  }
}
