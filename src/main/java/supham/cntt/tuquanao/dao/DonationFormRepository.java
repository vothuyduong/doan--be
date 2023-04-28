package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.model.DonationForm;

@Repository
public interface DonationFormRepository extends JpaRepository<DonationForm, Integer> {

}
