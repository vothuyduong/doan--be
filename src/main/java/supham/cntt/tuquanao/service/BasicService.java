package supham.cntt.tuquanao.service;

import java.util.Objects;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.TuquanaoApplication;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.common.JwtUtils;
import supham.cntt.tuquanao.dao.CartRepository;
import supham.cntt.tuquanao.dao.CategoryRepository;
import supham.cntt.tuquanao.dao.CustomerRepository;
import supham.cntt.tuquanao.dao.DetailCartRepository;
import supham.cntt.tuquanao.dao.PictureRepository;
import supham.cntt.tuquanao.dao.PriceRepository;
import supham.cntt.tuquanao.dao.ProductRepository;
import supham.cntt.tuquanao.dao.SizeRepository;
import supham.cntt.tuquanao.dto.UserDetailImpl;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.mapper.CustomerMapper;
import supham.cntt.tuquanao.mapper.ProductNewMapper;

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

  public CustomerMapper customerRegisterDTOToCustomer = Mappers.getMapper(CustomerMapper.class);

  public ProductNewMapper productNewMapper = Mappers.getMapper(ProductNewMapper.class);

  protected UserDetailImpl getUserDetail() throws TuQuanAoException {
    UserDetailImpl userDetail = jwtUtils.getUserDetail();
    if (Objects.isNull(userDetail)) {
      throw new TuQuanAoException(Message.USER_NOT_FOUND, HttpStatus.BAD_REQUEST.value());
    }
    return userDetail;
  }
}
