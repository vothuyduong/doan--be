package supham.cntt.tuquanao.model;

import java.util.Date;
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
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_order")
  private Integer idOrder;

  @Column(name = "id_customer", nullable = false)
  private Integer idCustomer;

  @Column(name = "take_customer", nullable = false)
  private String takeCustomer;

  @Column(name = "take_address", nullable = false)
  private String takeAddress;

  @Column(name = "phone_customer", nullable = false)
  private String phoneCustomer;

  @Column(name = "payment_method", nullable = false)
  private String paymentMethod;

  @Column(name = "id_volunteers")
  private Integer idVolunteers;

  @Column(name = "take_time")
  private Date takeTime;

  @Column(name = "take_picture")
  private String takePicture;

  @Column(name = "status")
  private String status;

  @Column(name = "total_money", nullable = false)
  private Double totalMoney;

  @Column(name = "date_create", nullable = false)
  private Date dateCreate;
}
