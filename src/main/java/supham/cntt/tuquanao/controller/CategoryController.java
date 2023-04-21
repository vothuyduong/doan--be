package supham.cntt.tuquanao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import supham.cntt.tuquanao.common.ResponseEntityUtil;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Category;
import supham.cntt.tuquanao.service.CategoryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  CategoryService categoryService;

  @GetMapping("")
  ResponseEntity<?> getCatagories() throws TuQuanAoException {
    return responseEntityUtil.generateResponse(HttpStatus.OK, categoryService.listCategory());
  }

  @GetMapping("/info")
  ResponseEntity<?> getDetail(@RequestParam(name = "id") Integer id) {
    return responseEntityUtil.generateResponse(HttpStatus.OK, categoryService.getDetail(id));
  }

  @PostMapping("/save")
  ResponseEntity<?> save(@RequestBody Category category) throws TuQuanAoException {
    categoryService.save(category);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @DeleteMapping("/delete")
  ResponseEntity<?> deleteCategory(@RequestParam int id) {
    categoryService.deleteCategory(id);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }
}
