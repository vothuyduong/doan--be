package supham.cntt.tuquanao.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.dto.PriceCartDTO;
import supham.cntt.tuquanao.model.DetailCart;

@Repository
public interface DetailCartRepository extends JpaRepository<DetailCart, Integer> {

  DetailCart findByIdCartAndIdSizeAndIdProduct(Integer idCart, Integer idSize, Integer idProduct);

  @Query(value = ""
      + "SELECT new supham.cntt.tuquanao.dto.PriceCartDTO( "
      + "detail.idProduct, "
      + "pro.nameProduct, "
      + "detail.idSize, "
      + "si.nameSize, "
      + "pri.price, "
      + "detail.quantity) "
      + "FROM DetailCart detail "
      + "INNER JOIN Product pro "
      + "ON detail.idProduct = pro.idProduct "
      + "INNER JOIN Size si "
      + "ON detail.idSize = si.idSize "
      + "INNER JOIN Price pri "
      + "ON detail.idSize = pri.idSize AND detail.idProduct = pri.idProduct "
      + "WHERE detail.idCart = ?1 "
  )
  List<PriceCartDTO> getListCart(Integer idCart);

  void deleteAllByIdCart(Integer idCart);

  @Query(value = ""
      + "SELECT sum(de.quantity) "
      + "FROM DetailCart de "
      + "WHERE de.idCart = ?1 "
      + "GROUP BY de.idCart "
  )
  Integer countQuantityProduct(Integer idCart);
}
