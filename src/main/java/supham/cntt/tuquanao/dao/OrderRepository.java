package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

  void deleteByIdOrder(Integer idOrder);
}
