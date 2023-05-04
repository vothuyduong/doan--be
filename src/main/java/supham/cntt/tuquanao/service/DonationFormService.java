package supham.cntt.tuquanao.service;

import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.dto.DonationFormDTO;
import supham.cntt.tuquanao.model.DonationForm;

@Service
@Slf4j
public class DonationFormService extends BasicService{

  @Transactional
  public void saveDonationForm(DonationFormDTO requestDTO) {
    DonationForm form = new DonationForm();
    form.setFullName(requestDTO.getFullName());
    form.setPhone(requestDTO.getPhone());
    form.setGetTime(requestDTO.getGetTime());
    form.setGetAddress(requestDTO.getGetAddress());
    form.setDescription(requestDTO.getDescription());
    form.setStatus(requestDTO.getStatus());
    donationFormRepository.save(form);
  }

  public Long countDona() {
    return donationFormRepository.countDonationForm();
  }
}
