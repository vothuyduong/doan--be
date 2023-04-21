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
import supham.cntt.tuquanao.model.Size;
import supham.cntt.tuquanao.service.SizeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/size")
public class SizeController {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Autowired
  SizeService sizeService;

  @GetMapping("")
  public ResponseEntity<Object> getList() throws TuQuanAoException {
    return responseEntityUtil.generateResponse(HttpStatus.OK, sizeService.listSize());
  }

  @GetMapping("/info")
  public ResponseEntity<Object> getDetail(@RequestParam(name = "idSize") Integer idSize)
      throws TuQuanAoException {
    return responseEntityUtil.generateResponse(HttpStatus.OK, sizeService.getDetail(idSize));
  }

  @PostMapping("/save")
  public ResponseEntity<Object> save(@RequestBody Size size) throws TuQuanAoException {
    sizeService.save(size);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Object> deleteSize(@RequestParam(name = "idSize") Integer idSize) {
    sizeService.deleteSize(idSize);
    return responseEntityUtil.generateResponse(HttpStatus.OK);
  }
}
