package supham.cntt.tuquanao.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.common.MessageUtil;
import supham.cntt.tuquanao.exception.CustomErrors;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    // Get message
    String msg = MessageUtil.getMessage(Message.UNAUTHORIZED_CODE);
    CustomErrors apiError = new CustomErrors(HttpStatus.UNAUTHORIZED.value(), msg);
    response.getWriter().write(apiError.createJSON());
  }
}
