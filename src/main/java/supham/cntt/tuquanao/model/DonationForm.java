package supham.cntt.tuquanao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import supham.cntt.tuquanao.common.GlobalConstants;

@Entity
@Table(name = "donation_form")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationForm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_donation", nullable = false)
  private Integer idDonation;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "phone", nullable = false)
  private String phone;

  @Column(name = "get_address", nullable = false)
  private String getAddress;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = GlobalConstants.DATE_FORMAT)
  @Column(name = "get_time", nullable = false)
  private Date getTime;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "status")
  private String status;

  @Column(name = "id_volunteers")
  private Integer idVolunteers;

  @Column(name = "take_time")
  private Date takeTime;

  @Column(name = "take_picture")
  private String takePicture;
}
