package supham.cntt.tuquanao.security;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import supham.cntt.tuquanao.common.GlobalConstants;
import supham.cntt.tuquanao.common.JwtUtils;
import supham.cntt.tuquanao.common.ResponseEntityUtil;
import supham.cntt.tuquanao.dto.UserDetailImpl;
import supham.cntt.tuquanao.service.UserDetailServiceImpl;

@Slf4j
public class JwtAuthenticateFilter extends OncePerRequestFilter {

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserDetailServiceImpl userDetailService;

  @Autowired
  ResponseEntityUtil responseEntityUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String token = this.parseJwt(request);
      if (Objects.nonNull(token) && this.jwtUtils.validateToken(token)) {
        String username = this.jwtUtils.getUserNameFromToken(token);
        UserDetailImpl userDetail =
            (UserDetailImpl) this.userDetailService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
      responseEntityUtil.generateResponseErrorsJWTException(response, e.getMessage());
      return;
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
    // remove token prefix from authorization header
    if (Objects.nonNull(headerAuth) && headerAuth.startsWith(
        GlobalConstants.Character.TOKEN_PREFIX)) {
      return headerAuth.substring(GlobalConstants.Character.TOKEN_PREFIX.length(),
          headerAuth.length());
    }
    return null;
  }
}
