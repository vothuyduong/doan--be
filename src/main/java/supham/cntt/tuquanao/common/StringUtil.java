package supham.cntt.tuquanao.common;

import java.util.Objects;
import java.util.regex.Pattern;
import supham.cntt.tuquanao.common.GlobalConstants.Number;

public class StringUtil {

  public static boolean isEmpty(String strValue) {
    return Objects.isNull(strValue) || GlobalConstants.Character.TXT_BLANK.equals(strValue.trim());
  }

  public static String conCatString(Object... param) {
    if (Objects.isNull(param)) {
      return GlobalConstants.Character.TXT_BLANK;
    }
    String cast = GlobalConstants.Character.TXT_BLANK;
    for (Object el : param) {
      cast = cast.concat(String.valueOf(el));
    }
    return cast;
  }

  public static String getSubStringIgnoreString(String value, String ignoreString) {
    if (!StringUtil.isEmpty(ignoreString)) {
      int lastIndexSubString = value.length() - (ignoreString.length() + Number.NUMBER_ONE);
      try {
        return value.substring(Number.NUMBER_ZERO, lastIndexSubString);
      } catch (IndexOutOfBoundsException ex) {
        return null;
      }
    }
    return null;
  }

  public static boolean isAlphanumericCharacter(String value) {
    if (StringUtil.isEmpty(value)) {
      return false;
    }
    Pattern pattern = Pattern.compile(GlobalConstants.Character.ALPHANUMBERIC_PATTERN);
    return pattern.matcher(value).matches();
  }
}
