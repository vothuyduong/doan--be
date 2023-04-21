package supham.cntt.tuquanao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

  private Integer idProduct;

  private String nameProduct;

  private String description;

  private Integer idCategory;

  private String nameCategory;

  private String base64;

  public ProductResponseDTO(Integer idProduct, String nameProduct, String description,
      String nameCategory, String base64) {
    this.idProduct = idProduct;
    this.nameProduct = nameProduct;
    this.description = description;
    this.nameCategory = nameCategory;
    this.base64 = base64;
  }

  public ProductResponseDTO(Integer idProduct, String nameProduct, String description,
      Integer idCategory, String base64) {
    this.idProduct = idProduct;
    this.nameProduct = nameProduct;
    this.description = description;
    this.idCategory = idCategory;
    this.base64 = base64;
  }
}
