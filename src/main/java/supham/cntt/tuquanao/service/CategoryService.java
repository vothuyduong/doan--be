package supham.cntt.tuquanao.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Category;

@Service
@Slf4j
public class CategoryService extends BasicService {

  public List<Category> listCategory() throws TuQuanAoException {
    List<Category> categories = null;
    try {
      categories = categoryRepository.findAll(Sort.by("idCategory").descending());
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
      throw new TuQuanAoException(Message.SEVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    return categories;
  }

  public Category getDetail(Integer id) {
    Category cat =  categoryRepository.findByIdCategory(id);
    String name = cat.getNameCategory();
    String[] arr = name.split(" - ");
    cat.setNameCategory(arr[1]);
    return cat;
  }

  @Transactional
  public Category save(Category category) throws TuQuanAoException {
    if (category.getNameCategory().equals("")) {
      throw new TuQuanAoException(Message.NAME_CATEGORY_EMPTY, HttpStatus.BAD_REQUEST.value());
    }
    String name = validNameCategory(category.getNameCategory());
    if (categoryRepository.findByNameCategory(name)
        != null) {
      throw new TuQuanAoException(Message.NAME_CATEGORY_EXIST, HttpStatus.BAD_REQUEST.value());
    }
    category.setNameCategory(name);
    return categoryRepository.save(category);
  }

  @Transactional
  public void deleteCategory(Integer idCategory) {
    categoryRepository.deleteByIdCategory(idCategory);
  }

  private String validNameCategory(String nameCategory) {
    String rs = "";
    String[] arr = nameCategory.split("-");
    rs = upcaseString(arr[0]) + " - " + upcaseString(arr[1]);
    return rs;
  }

  private String upcaseString(String aa) {
    return aa.substring(0, 1).toUpperCase() + aa.substring(1);
  }
}
