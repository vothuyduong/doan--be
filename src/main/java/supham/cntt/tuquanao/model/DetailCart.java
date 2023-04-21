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
@Table(name = "detail_cart")
@IdClass(DetailCartId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailCart {

  @Id
  @Column(name = "id_cart", nullable = false)
  private Integer idCart;

  @Id
  @Column(name = "id_size", nullable = false)
  private Integer idSize;

  @Id
  @Column(name = "id_product", nullable = false)
  private Integer idProduct;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;
}
