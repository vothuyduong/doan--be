package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.model.Size;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {

  Size findByIdSize(Integer idSize);

  void deleteByIdSize(Integer id);

  Size findByNameSize(String toUpperCase);
}
