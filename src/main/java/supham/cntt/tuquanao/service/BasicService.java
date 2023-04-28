package supham.cntt.tuquanao.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Objects;
import org.apache.commons.io.FileUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.common.JwtUtils;
import supham.cntt.tuquanao.dao.CartRepository;
import supham.cntt.tuquanao.dao.CategoryRepository;
import supham.cntt.tuquanao.dao.CustomerRepository;
import supham.cntt.tuquanao.dao.DetailCartRepository;
import supham.cntt.tuquanao.dao.DonationFormRepository;
import supham.cntt.tuquanao.dao.PictureRepository;
import supham.cntt.tuquanao.dao.PriceRepository;
import supham.cntt.tuquanao.dao.ProductRepository;
import supham.cntt.tuquanao.dao.SizeRepository;
import supham.cntt.tuquanao.dto.UserDetailImpl;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.mapper.CustomerMapper;
import supham.cntt.tuquanao.mapper.ProductNewMapper;
import supham.cntt.tuquanao.model.Picture;

@Service
public class BasicService {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  SizeRepository sizeRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  PictureRepository pictureRepository;

  @Autowired
  PriceRepository priceRepository;

  @Autowired
  CartRepository cartRepository;

  @Autowired
  DetailCartRepository detailCartRepository;

  @Autowired
  DonationFormRepository donationFormRepository;

  public CustomerMapper customerRegisterDTOToCustomer = Mappers.getMapper(CustomerMapper.class);

  public ProductNewMapper productNewMapper = Mappers.getMapper(ProductNewMapper.class);

  protected UserDetailImpl getUserDetail() throws TuQuanAoException {
    UserDetailImpl userDetail = jwtUtils.getUserDetail();
    if (Objects.isNull(userDetail)) {
      throw new TuQuanAoException(Message.USER_NOT_FOUND, HttpStatus.BAD_REQUEST.value());
    }
    return userDetail;
  }

  String getPicture(Integer idProduct) {
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
