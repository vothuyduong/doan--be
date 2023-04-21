package supham.cntt.tuquanao.service;

import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.dto.PriceDTO;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Price;

@Service
public class PriceService extends BasicService {

  public List<PriceDTO> list() {
    return priceRepository.getList();
  }

  public Price getDetail(Integer idProduct, Integer idSize) {
    return priceRepository.findByIdProductAndIdSize(idProduct, idSize);
  }

  @Transactional
  public void save(Price price) throws TuQuanAoException {
    if(Objects.isNull(productRepository.findByIdProduct(price.getIdProduct()))) {
      throw new TuQuanAoException(Message.ID_PRODUCT_INVALID, HttpStatus.BAD_REQUEST.value());
    }
    if(Objects.isNull(sizeRepository.findByIdSize(price.getIdSize()))) {
      throw new TuQuanAoException(Message.ID_SIZE_INVALID, HttpStatus.BAD_REQUEST.value());
    }
    priceRepository.save(price);
  }

  @Transactional
  public void delete(Integer idProduct, Integer idSize) {
    Price pri = getDetail(idProduct, idSize);
    priceRepository.delete(pri);
  }
}
