package supham.cntt.tuquanao.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import supham.cntt.tuquanao.common.ResponseEntityUtil;
import supham.cntt.tuquanao.dto.DonationFormDTO;
import supham.cntt.tuquanao.service.DonationFormService;

@RestController
@CrossOrigin("*")
@RequestMapping("/donation-form")
public class DonationFormController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  DonationFormService donationFormService;

  @PostMapping("/save")
  public ResponseEntity<?> saveInfo(@Valid @RequestBody DonationFormDTO requestDTO) {
    donationFormService.saveDonationForm(requestDTO);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @GetMapping("/count")
  public ResponseEntity<?> countDonation() {
    return responseEntityUtil.generateResponse(HttpStatus.OK, donationFormService.countDona());
  }
}
