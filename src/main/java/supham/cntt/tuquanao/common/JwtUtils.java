package supham.cntt.tuquanao.common;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import supham.cntt.tuquanao.common.GlobalConstants.Message;
import supham.cntt.tuquanao.dto.JwtResponse;
import supham.cntt.tuquanao.dto.UserDetailImpl;
import supham.cntt.tuquanao.exception.TuQuanAoException;

@Component
@Slf4j
public class JwtUtils {

  @Value("${supham.secret}")
  private String secretKey;

  @Value(("${supham.expiredTime}"))
  private Integer expiredTime;

  public String generateJwtToken(UserDetailImpl userPrincipal) throws TuQuanAoException {
    return Jwts.builder()
        .setSubject(userPrincipal.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + this.expiredTime))
        .signWith(SignatureAlgorithm.HS512, this.secretKey)
        .compact();
  }

  public JwtResponse getResponse(Authentication authentication) throws TuQuanAoException {
    UserDetailImpl userPrincipal = (UserDetailImpl) authentication.getPrincipal();
    return new JwtResponse(
        userPrincipal.getUsername(),
        generateJwtToken(userPrincipal)
    );
  }

  public boolean validateToken(String token) throws TuQuanAoException {
    try {
      Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
      throw new TuQuanAoException(MessageUtil.getMessage(Message.UNAUTHORIZED_CODE));
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      throw new TuQuanAoException(MessageUtil.getMessage(Message.UNAUTHORIZED_CODE));
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
      throw new TuQuanAoException(MessageUtil.getMessage(Message.EXPIRED));
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
      throw new TuQuanAoException(MessageUtil.getMessage(Message.UNAUTHORIZED_CODE));
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
      throw new TuQuanAoException(MessageUtil.getMessage(Message.UNAUTHORIZED_CODE));
    }
  }

  public String getUserNameFromToken(String token) {
    return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public UserDetailImpl getUserDetail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailImpl userPrincipal = (UserDetailImpl) authentication.getPrincipal();
    if (Objects.isNull(userPrincipal)) {
      return null;
    }
    return userPrincipal;
  }
}
