package supham.cntt.tuquanao.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import supham.cntt.tuquanao.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

  Category findByIdCategory(Integer id);

  void deleteByIdCategory(Integer idCategory);

  Category findByNameCategory(String name);
}
