package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.model.DetailOrder;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {

}
