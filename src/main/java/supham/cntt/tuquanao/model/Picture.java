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
@Table(name = "picture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_picture", nullable = false)
  private Integer idPicture;

  @Column(name = "name_picture", nullable = false)
  private String namePicture;

  @Column(name = "uri", nullable = false)
  private String uri;

  @Column(name = "id_product", nullable = false)
  private Integer idProduct;
}
