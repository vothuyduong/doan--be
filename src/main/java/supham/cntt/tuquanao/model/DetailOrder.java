package supham.cntt.tuquanao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detail_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_detail")
  private Integer idDetail;

  @Column(name = "id_order", nullable = false)
  private Integer idOrder;

  @Column(name = "name_product", nullable = false)
  private String nameProduct;

  @Column(name = "name_size", nullable = false)
  private String nameSize;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "into_money", nullable = false)
  private Double intoMoney;

  @Column(name = "id_product", nullable = false)
  private Integer idProduct;

  @Column(name = "id_size", nullable = false)
  private Integer idSize;
}
