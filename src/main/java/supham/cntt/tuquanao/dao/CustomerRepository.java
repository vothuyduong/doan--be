package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

  Customer findCustomerByUsername(String username);

  Customer save(Customer customer);

  Customer findByIdCustomer(Integer idCustomer);

  @Query(value = ""
      + "SELECT count(cus) "
      + "FROM Customer cus "
  )
  Long countCustomer();
}
