package supham.cntt.tuquanao.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.exception.TuQuanAoException;
import supham.cntt.tuquanao.model.Size;

@Service
@Slf4j
public class SizeService extends BasicService {

  public List<Size> listSize() throws TuQuanAoException {

    List<Size> list = null;
    try {
      list = sizeRepository.findAll(Sort.by("idSize").descending());
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
      throw new TuQuanAoException(Message.SEVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    return list;
  }

  public Size getDetail(Integer id) throws TuQuanAoException {
    Size size = null;
    try {
      size = sizeRepository.findByIdSize(id);
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
      throw new TuQuanAoException(Message.SEVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    return size;
  }

  @Transactional
  public Size save(Size size) throws TuQuanAoException {
    if(size.getNameSize().equals("")) {
      throw new TuQuanAoException(Message.NAME_SIZE_EMPTY, HttpStatus.BAD_REQUEST.value());
    }
    String name = size.getNameSize().toUpperCase();
    if(sizeRepository.findByNameSize(name) != null) {
      throw new TuQuanAoException(Message.NAME_SIZE_EXIST, HttpStatus.BAD_REQUEST.value());
    }
    size.setNameSize(name);
    return sizeRepository.save(size);
  }

  @Transactional
  public void deleteSize(Integer id) {
    sizeRepository.deleteByIdSize(id);
  }
}
