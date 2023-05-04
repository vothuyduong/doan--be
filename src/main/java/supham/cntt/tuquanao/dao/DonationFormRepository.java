package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.model.DonationForm;

@Repository
public interface DonationFormRepository extends JpaRepository<DonationForm, Integer> {

  @Query(value = ""
      + "SELECT count(don) "
      + "FROM DonationForm don "
  )
  Long countDonationForm();
}
