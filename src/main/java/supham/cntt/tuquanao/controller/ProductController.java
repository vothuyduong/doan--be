package supham.cntt.tuquanao.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import supham.cntt.tuquanao.common.ResponseEntityUtil;
import supham.cntt.tuquanao.dto.PageDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.service.ProductService;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  ProductService productService;

  @GetMapping("")
  public ResponseEntity<?> getList(
      @RequestParam(name = "pageCurrent", required = false, defaultValue = "1") Integer pageCurrent,
      @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
      @RequestParam(name = "keySearch", required = false) String keySearch,
      @RequestParam(name = "orderBy", required = false) String orderBy,
      @RequestParam(name = "sortType", required = false) String sortType) {
    PageDTO rs = productService.getList(pageCurrent, pageSize, keySearch, orderBy, sortType);
    return responseEntityUtil.generateResponse(HttpStatus.OK, rs.getData(), rs.getCount(), pageSize,
        rs.getTotalPage(), pageCurrent);
  }

  @GetMapping("/ksk")
  public ResponseEntity<?> getListKsk() {
    return responseEntityUtil.generateResponse(HttpStatus.OK, productService.listProduct());
  }

  @GetMapping("/list-new")
  public ResponseEntity<?> getListNew() {
    return responseEntityUtil.generateResponse(HttpStatus.OK, productService.getListNew());
  }

  @GetMapping("/list-lq")
  public ResponseEntity<?> getListLQ(@RequestParam(name = "idProduct") Integer idProduct) {
    return responseEntityUtil.generateResponse(HttpStatus.OK, productService.getListLQ(idProduct));
  }

  @GetMapping("/list-pro")
  public ResponseEntity<?> getListProduct(
      @RequestParam(name = "idCategory", required = false) Integer idCategory,
      @RequestParam(name = "idSize", required = false) Integer idSize,
      @RequestParam(name = "keySearch", required = false) String keySearch,
      @RequestParam(name = "pageCurrent", required = false, defaultValue = "1") Integer pageCurrent,
      @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
    PageDTO rs = productService.getListProduct(idCategory, idSize, keySearch, pageCurrent,
        pageSize);
    return responseEntityUtil.generateResponse(HttpStatus.OK, rs.getData(), rs.getCount(), pageSize,
        rs.getTotalPage(), pageCurrent);
  }

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestParam(name = "file", required = false) MultipartFile file,
      @RequestParam(name = "idProduct", required = false) Integer idProduct,
      @RequestParam(name = "nameProduct") String nameProduct,
      @RequestParam(name = "description", required = false) String description,
      @RequestParam(name = "idCategory") Integer idCategory) throws TuQuanAoException, IOException {
    productService.saveProduct(file, idProduct, nameProduct, description, idCategory);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @GetMapping("/info")
  public ResponseEntity<?> getDetail(@RequestParam(name = "idProduct") Integer idProduct)
      throws IOException {
    return responseEntityUtil.generateResponse(HttpStatus.OK, productService.getDetail(idProduct));
  }

  @GetMapping("/detail")
  public ResponseEntity<?> getDetailProduct(@RequestParam(name = "idProduct") Integer idProduct) {
    return responseEntityUtil.generateResponse(HttpStatus.OK,
        productService.getDetailProduct(idProduct));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<?> delete(@RequestParam(name = "idProduct") Integer idProduct)
      throws TuQuanAoException {
    productService.deleteProduct(idProduct);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }
}
