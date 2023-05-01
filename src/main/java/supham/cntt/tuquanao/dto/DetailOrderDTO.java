package supham.cntt.tuquanao.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderDTO {

  private String takeCustomer;

  private String takeAddress;

  private String phoneCustomer;

  private String paymentMethod;

  private Double totalMoney;

  private List<PriceCartDTO> detail;
}
