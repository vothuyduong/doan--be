package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import supham.cntt.tuquanao.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Integer> {

  Picture findByIdProduct(Integer idProduct);
}
