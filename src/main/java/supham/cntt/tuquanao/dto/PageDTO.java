package supham.cntt.tuquanao.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@NotEmpty
@AllArgsConstructor
public class PageDTO {

  private Object data;

  private Integer totalPage = 0;

  private Long count = 0L;
}
