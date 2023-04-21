package supham.cntt.tuquanao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDTO {

  private Integer idProduct;

  private String nameProduct;

  private Integer idSize;

  private String nameSize;

  private Double price;

  private Integer quantity;
}
