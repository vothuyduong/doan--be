package supham.cntt.tuquanao.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.dto.PriceDTO;
import supham.cntt.tuquanao.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Integer> {

  @Query(value = ""
      + "SELECT new supham.cntt.tuquanao.dto.PriceDTO("
      + "pro.idProduct, "
      + "pro.nameProduct, "
      + "si.idSize, "
      + "si.nameSize, "
      + "pri.price, "
      + "pri.quantity) "
      + "FROM Price pri "
      + "INNER JOIN Product pro ON pri.idProduct = pro.idProduct "
      + "INNER JOIN Size si ON pri.idSize = si.idSize"
  )
  List<PriceDTO> getList();

  Price findByIdProductAndIdSize(Integer idProduct, Integer idSize);
}
