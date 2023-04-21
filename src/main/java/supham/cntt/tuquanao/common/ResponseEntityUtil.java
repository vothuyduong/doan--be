package supham.cntt.tuquanao.common;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import supham.cntt.tuquanao.exception.CustomErrors;

@Component
public class ResponseEntityUtil {

  private enum RESPONSE_PROPERTIES {
    TIMESTAMP("timestamp"),
    STATUS("status"),
    MESSAGES("messages"),
    COUNT("count"),
    DATA("data"),
    LIMIT("limit"),
    TOTAL_PAGES("total_pages"),
    CURRENT_PAGE("current_page"),
    ERRORS_BODY_REQUEST("errors");

    String value;

    RESPONSE_PROPERTIES(String value) {
      this.value = value;
    }

    public String getValue() {
      return this.value;
    }
  }

  @Autowired
  ServletContext servletContext;

  public ResponseEntity<Object> generateResponse(HttpStatus status) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(RESPONSE_PROPERTIES.TIMESTAMP.getValue(), new Date());
    body.put(RESPONSE_PROPERTIES.STATUS.getValue(), status.value());
    return new ResponseEntity<>(body, status);
  }

  public ResponseEntity<Object> generateResponse(
      HttpStatus status, Object data) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(RESPONSE_PROPERTIES.TIMESTAMP.getValue(), new Date());
    body.put(RESPONSE_PROPERTIES.STATUS.getValue(), status.value());
    body.put(RESPONSE_PROPERTIES.DATA.getValue(), data);
    return new ResponseEntity<>(body, status);
  }

  public ResponseEntity<Object> generateResponse(
      HttpStatus status, String messageCode, Object data) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(RESPONSE_PROPERTIES.TIMESTAMP.getValue(), new Date());
    body.put(RESPONSE_PROPERTIES.STATUS.getValue(), status.value());

    // get message by message code
    String msg = MessageUtil.getMessage(messageCode);
    body.put(RESPONSE_PROPERTIES.MESSAGES.getValue(), msg);

    body.put(RESPONSE_PROPERTIES.DATA.getValue(), data);
    return new ResponseEntity<>(body, status);
  }

  public ResponseEntity<Object> generateResponseErrorsBodyRequest(
      HttpStatus status, Map<String, String> errors) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(RESPONSE_PROPERTIES.TIMESTAMP.getValue(), new Date());
    body.put(RESPONSE_PROPERTIES.STATUS.getValue(), status.value());
    body.put(RESPONSE_PROPERTIES.ERRORS_BODY_REQUEST.getValue(), errors);
    return new ResponseEntity<>(body, status);
  }

  public void generateResponseErrorsJWTException(HttpServletResponse response,
      String msg) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    CustomErrors apiError = new CustomErrors(HttpStatus.UNAUTHORIZED.value(), msg);
    response.getWriter().write(apiError.createJSON());
  }

  public ResponseEntity<Object> generateResponse(
      HttpStatus status, Object data, long count, int limit, int totalPages, int currentPage) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put(RESPONSE_PROPERTIES.TIMESTAMP.getValue(), new Date());
    body.put(RESPONSE_PROPERTIES.STATUS.getValue(), status.value());
    body.put(RESPONSE_PROPERTIES.LIMIT.getValue(), limit);
    body.put(RESPONSE_PROPERTIES.COUNT.getValue(), count);
    body.put(RESPONSE_PROPERTIES.TOTAL_PAGES.getValue(), totalPages);
    body.put(RESPONSE_PROPERTIES.CURRENT_PAGE.getValue(), currentPage);
    body.put(RESPONSE_PROPERTIES.DATA.getValue(), data);
    return new ResponseEntity<>(body, status);
  }
}
