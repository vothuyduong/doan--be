package supham.cntt.tuquanao.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.dto.ProductKskDTO;
import supham.cntt.tuquanao.dto.ProductPriceDTO;
import supham.cntt.tuquanao.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

  Product findByIdProduct(Integer idProduct);

  String QUERY_LIST_NEW = ""
      + "SELECT "
      + "pro.id_product as idProduct, "
      + "pro.name_product as nameProduct, "
      + "IFNULL(MIN(pri.price), 0) as priceMin, "
      + "IFNULL(MAX(pri.price), 0) as priceMax, "
      + "pro.description as description ";

  String CONDITION_LIST_NEW = ""
      + "FROM product pro "
      + "INNER JOIN price pri "
      + "ON pro.id_product = pri.id_product "
      + "GROUP BY pro.id_product "
      + "ORDER BY pro.id_product desc ";

  String CONDITION_LIST_PRODUCT = ""
      + "FROM product pro "
      + "LEFT OUTER JOIN price pri "
      + "ON pro.id_product = pri.id_product "
      + "LEFT OUTER JOIN category ca "
      + "ON pro.id_category = ca.id_category "
      + "LEFT OUTER JOIN size si "
      + "ON pri.id_size = si.id_size "
      + "WHERE (?1 is null OR ca.id_category = ?1) "
      + "AND (?2 is null OR si.id_size = ?2) "
      + "AND (?3 is null OR pro.name_product LIKE %?3%) "
      + "GROUP BY pro.id_product "
      + "ORDER BY pro.id_product desc ";

  String CONDITION_DETAIL = ""
      + "FROM product pro "
      + "LEFT OUTER JOIN price pri "
      + "ON pro.id_product = pri.id_product "
      + "WHERE pro.id_product = ?1 "
      + "GROUP BY pro.id_product ";

  String CONDITION_LQ = ""
      + "FROM product pro "
      + "LEFT OUTER JOIN price pri "
      + "ON pro.id_product = pri.id_product "
      + "WHERE pro.id_category IN "
      + "(SELECT pr.id_category "
      + "FROM product pr "
      + "WHERE pr.id_product = ?1) "
      + "AND pro.id_product != ?1 "
      + "GROUP BY pro.id_product ";

  @Query(value = QUERY_LIST_NEW + CONDITION_LIST_NEW,
      countQuery = "SELECT count(*) " + CONDITION_LIST_NEW, nativeQuery = true
  )
  Page<ProductPriceDTO> getListProductNew(Pageable pageable);

  @Query(value = QUERY_LIST_NEW + CONDITION_LQ,
      countQuery = "SELECT count(*) " + CONDITION_LQ, nativeQuery = true)
  Page<ProductPriceDTO> getListProductLQ(Integer idProduct, Pageable pageable);

  @Query(value = QUERY_LIST_NEW + CONDITION_LIST_PRODUCT,
      countQuery = "SELECT count(*) " + CONDITION_LIST_PRODUCT, nativeQuery = true
  )
  Page<ProductPriceDTO> getListProduct(Integer idCategory, Integer idSize, String keySearch,
      Pageable pageable);

  @Query(value = QUERY_LIST_NEW + CONDITION_DETAIL, nativeQuery = true)
  ProductPriceDTO getDetail(Integer idProduct);

  @Query(value = ""
      + "SELECT new supham.cntt.tuquanao.dto.ProductKskDTO( "
      + "pro.idProduct, "
      + "pro.nameProduct) "
      + "FROM Product pro "
  )
  List<ProductKskDTO> listProductPopup();


  @Query(value = ""
      + "SELECT new supham.cntt.tuquanao.model.Product( "
      + "pro.idProduct, "
      + "pro.nameProduct, "
      + "pro.description, "
      + "pro.idCategory) "
      + "FROM Product pro "
      + "WHERE (?1 IS NULL OR pro.nameProduct LIKE %?1% OR pro.description LIKE %?1%) "
  )
  Page<Product> findAll(String keySearch, Pageable pageable);
}
