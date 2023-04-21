package supham.cntt.tuquanao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductNew {

  private Integer idProduct;

  private String nameProduct;

  private Double priceMin;

  private Double priceMax;

  private String description;

  private String base64;
}
