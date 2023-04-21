package supham.cntt.tuquanao.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.common.MessageUtil;
import supham.cntt.tuquanao.common.ResponseEntityUtil;

@ControllerAdvice
public class ResponseExceptionsHandler extends ResponseEntityExceptionHandler implements
    AccessDeniedHandler {

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return responseEntityUtil.generateResponseErrorsBodyRequest(status, errors);
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    String msg = MessageUtil.getMessage(Message.UNAUTHORIZED_CODE);
    this.commonResponseException(response, HttpServletResponse.SC_FORBIDDEN,
        HttpStatus.UNAUTHORIZED.value(), msg);
  }

  @ExceptionHandler(TuQuanAoException.class)
  public void handleSSGErrorException(HttpServletResponse response,
      TuQuanAoException tuQuanAoException) throws IOException {
    this.commonResponseException(response, HttpServletResponse.SC_BAD_REQUEST,
        HttpServletResponse.SC_BAD_REQUEST, tuQuanAoException.getMessage());
  }

  private void commonResponseException(HttpServletResponse response,
      Integer httpServletResponseStatus, Integer httpStatus, String msg) throws IOException {
    response.setStatus(httpServletResponseStatus);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    CustomErrors apiError = new CustomErrors(httpStatus, msg);
    response.getWriter().write(apiError.createJSON());
  }

  @ExceptionHandler(RuntimeException.class)
  public void handleSSGErrorException(HttpServletResponse response,
      RuntimeException runtimeException) throws IOException {
    this.commonResponseException(response, HttpServletResponse.SC_BAD_REQUEST,
        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,runtimeException.getMessage());
  }
}

