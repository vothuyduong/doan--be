package supham.cntt.tuquanao.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import supham.cntt.tuquanao.common.GlobalConstants;
import supham.cntt.tuquanao.common.GlobalConstants.Character;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.common.GlobalConstants.Number;
import supham.cntt.tuquanao.dto.PageDTO;
import supham.cntt.tuquanao.dto.ProductKskDTO;
import supham.cntt.tuquanao.dto.ProductNew;
import supham.cntt.tuquanao.dto.ProductPriceDTO;
import supham.cntt.tuquanao.dto.ProductResponseDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Picture;
import supham.cntt.tuquanao.model.Product;

@Service
@Slf4j
public class ProductService extends BasicService {

  public PageDTO getList(Integer pageCurrent, Integer pageSize, String keySearch, String orderBy,
      String sortType) {
    Sort sort = Sort.by("idProduct").descending();
    if (!GlobalConstants.isEmpty(orderBy) && !GlobalConstants.isEmpty(sortType)) {
      if (sortType.equalsIgnoreCase("asc") || sortType.equalsIgnoreCase("desc")) {
        sort = Sort.by(Direction.fromString(sortType), orderBy);
      }
    }
    if (Objects.nonNull(keySearch) && keySearch.equals(Character.TXT_BLANK)) {
      keySearch = null;
    }
    Pageable pageable = PageRequest.of(pageCurrent - 1, pageSize, sort);
    Page<Product> productList = null;
    List<Product> products = null;
    try {
      productList = productRepository.findAll(keySearch, pageable);
      products = productList.getContent();
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    List<ProductResponseDTO> productResponses = new ArrayList<>();
    products.forEach(pro -> {
      ProductResponseDTO responseDTO = new ProductResponseDTO();
      responseDTO.setIdProduct(pro.getIdProduct());
      responseDTO.setNameProduct(pro.getNameProduct());
      responseDTO.setDescription(pro.getDescription());
      responseDTO.setNameCategory(
          categoryRepository.findByIdCategory(pro.getIdCategory()).getNameCategory());
      String encodedString = getPicture(pro.getIdProduct());
      responseDTO.setBase64(encodedString);
      productResponses.add(responseDTO);
    });
    return new PageDTO(productResponses, productList.getTotalPages(),
        productList.getTotalElements());
  }

  public List<ProductNew> getListNew() {
    Pageable pageable = PageRequest.of(Number.NUMBER_ZERO, Number.NUMBER_EIGHT);
    List<ProductPriceDTO> list = productRepository.getListProductNew(pageable).getContent();
    List<ProductNew> result = new ArrayList<>();
    list.forEach(pro -> {
      ProductNew productNew = productNewMapper.productPriceDTOToProductNew(pro);
      productNew.setBase64(getPicture(productNew.getIdProduct()));
      result.add(productNew);
    });
    return result;
  }

  public List<ProductNew> getListLQ(Integer idProduct) {
    Pageable pageable = PageRequest.of(Number.NUMBER_ZERO, Number.NUMBER_FOUR);
    List<ProductPriceDTO> list = productRepository.getListProductLQ(idProduct, pageable).getContent();
    List<ProductNew> result = new ArrayList<>();
    list.forEach(pro -> {
      ProductNew productNew = productNewMapper.productPriceDTOToProductNew(pro);
      productNew.setBase64(getPicture(productNew.getIdProduct()));
      result.add(productNew);
    });
    return result;
  }

  public List<ProductKskDTO> listProduct() {
    return productRepository.listProductPopup();
  }

  public PageDTO getListProduct(Integer idCategory, Integer idSize, String keySearch,
      Integer pageCurrent, Integer pageSize) {
    if (Objects.nonNull(keySearch) && keySearch.equals(Character.TXT_BLANK)) {
      keySearch = null;
    }
    Pageable pageable = PageRequest.of(pageCurrent - Number.NUMBER_ONE, pageSize);
    Page<ProductPriceDTO> listPro = productRepository.getListProduct(idCategory, idSize, keySearch,
        pageable);
    List<ProductPriceDTO> list = listPro.getContent();
    List<ProductNew> result = new ArrayList<>();
    list.forEach(pro -> {
      ProductNew productNew = productNewMapper.productPriceDTOToProductNew(pro);
      productNew.setBase64(getPicture(productNew.getIdProduct()));
      result.add(productNew);
    });
    return new PageDTO(result, listPro.getTotalPages(), listPro.getTotalElements());
  }

  public ProductNew getDetailProduct(Integer idProduct) {
    ProductPriceDTO pro = productRepository.getDetail(idProduct);
    ProductNew rs = productNewMapper.productPriceDTOToProductNew(pro);
    if(Objects.nonNull(pictureRepository.findByIdProduct(idProduct))) {
      rs.setBase64(getPicture(idProduct));
    }
    return rs;
  }

  public ProductResponseDTO getDetail(Integer idProduct) throws IOException {
    Product product = productRepository.findByIdProduct(idProduct);
    ProductResponseDTO responseDTO = new ProductResponseDTO();
    responseDTO.setIdProduct(product.getIdProduct());
    responseDTO.setNameProduct(product.getNameProduct());
    responseDTO.setDescription(product.getDescription());
    responseDTO.setIdCategory(product.getIdCategory());
    String encodedString = getPicture(idProduct);
    responseDTO.setBase64(encodedString);
    return responseDTO;
  }

  @Transactional
  public void saveProduct(MultipartFile file, Integer idProduct, String nameProduct,
      String description, Integer idCategory) throws TuQuanAoException, IOException {

    if (nameProduct == "") {
      throw new TuQuanAoException(Message.NAME_PRODUCT_EMPTY, HttpStatus.BAD_REQUEST.value());
    }
    if (Objects.isNull(categoryRepository.findByIdCategory(idCategory))) {
      throw new TuQuanAoException(Message.ID_CATEGORY_INVALID, HttpStatus.BAD_REQUEST.value());
    }
    Product productNew = productRepository.save(
        new Product(idProduct, nameProduct, description, idCategory));

    if (Objects.nonNull(file) && (!file.getOriginalFilename().equals(""))) {
      Picture pictureCheck = pictureRepository.findByIdProduct(productNew.getIdProduct());
      if (Objects.nonNull(pictureCheck)) {
        Path path = Paths.get(pictureCheck.getUri() + "/" + pictureCheck.getNamePicture());
        try {
          Files.delete(path);
          pictureRepository.delete(pictureCheck);
        } catch (NoSuchFileException ex) {
          System.out.println("No such file");
        } catch (DirectoryNotEmptyException ex) {
          System.out.println("Directory is not...");
        } catch (IOException ex) {
          System.out.println(ex);
        }
      }
      Path uploadDir = Paths.get("./uploads/");
      if (!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);
      }
      String fileName = new Date().getTime() + "." + getFileExtension(file.getOriginalFilename());
      try {
        byte[] bytes = file.getBytes();
        Path targetLocation = Paths.get("./uploads/" + fileName);
        Files.write(targetLocation, bytes);
      } catch (IOException ex) {
        throw new TuQuanAoException(Message.NOT_SAVE_FILE, HttpStatus.BAD_REQUEST.value());
      }
      String uri = Paths.get("./uploads/").toString();
      Picture pictureNew = pictureRepository.save(
          new Picture(null, fileName, uri, productNew.getIdProduct()));
    }
  }

  @Transactional
  public void deleteProduct(Integer idProduct) throws TuQuanAoException {
    Product product = productRepository.findByIdProduct(idProduct);
    if (Objects.isNull(product)) {
      throw new TuQuanAoException(Message.ID_PRODUCT_INVALID, HttpStatus.BAD_REQUEST.value());
    }
    Picture picture = pictureRepository.findByIdProduct(idProduct);
    productRepository.delete(product);
    if (Objects.nonNull(picture)) {
      Path path = Paths.get(picture.getUri() + "/" + picture.getNamePicture());
      try {
        Files.delete(path);
      } catch (NoSuchFileException ex) {
        System.out.println("No such file");
      } catch (DirectoryNotEmptyException ex) {
        System.out.println("Directory is not...");
      } catch (IOException ex) {
        System.out.println(ex);
      }
      pictureRepository.delete(picture);
    }
  }

  private String getFileExtension(String fileName) {
    if (fileName == null) {
      return null;
    }
    String[] fileNameParts = fileName.split("\\.");
    return fileNameParts[fileNameParts.length - 1];
  }

  private String getPicture(Integer idProduct) {
    Picture picture = pictureRepository.findByIdProduct(idProduct);
    String encodedString = "";
    if (Objects.nonNull(picture)) {
      Path filePath = Paths.get(picture.getUri() + "/" + picture.getNamePicture());
      byte[] fileContent = new byte[0];
      try {
        fileContent = FileUtils.readFileToByteArray(new File(filePath.toUri()));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      encodedString = Base64.getEncoder().encodeToString(fileContent);
    }
    return encodedString;
  }
}
