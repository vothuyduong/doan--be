package supham.cntt.tuquanao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "price")
@IdClass(PriceId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Price {

  @Id
  @Column(name = "id_product", nullable = false)
  private Integer idProduct;

  @Id
  @Column(name = "id_size", nullable = false)
  private Integer idSize;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;
}
