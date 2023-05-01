package supham.cntt.tuquanao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceCartDTO {

  private Integer idProduct;

  private String nameProduct;

  private Integer idSize;

  private String nameSize;

  private Double price;

  private Integer quantity;

  private String base64;

  private Double intoMoney;

  public PriceCartDTO(Integer idProduct, String nameProduct, Integer idSize, String nameSize,
      Double price, Integer quantity) {
    this.idProduct = idProduct;
    this.nameProduct = nameProduct;
    this.idSize = idSize;
    this.nameSize = nameSize;
    this.price = price;
    this.quantity = quantity;
  }

  public PriceCartDTO(Integer idProduct, String nameProduct, Integer idSize, String nameSize,
      Double price, Integer quantity, String base64) {
    this.idProduct = idProduct;
    this.nameProduct = nameProduct;
    this.idSize = idSize;
    this.nameSize = nameSize;
    this.price = price;
    this.quantity = quantity;
    this.base64 = base64;
  }
}
