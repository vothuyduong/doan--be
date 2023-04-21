package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

  Cart findByIdCustomer(Integer idCustomer);
}
