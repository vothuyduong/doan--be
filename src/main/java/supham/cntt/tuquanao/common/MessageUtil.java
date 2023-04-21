package supham.cntt.tuquanao.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.springframework.util.StringUtils;

public class MessageUtil {

  private static final String FIRST_PARAM_PREFIX = "{0}";

  public static String getMessage(String key, Object... param) {
    ResourceBundle rsMessages;
    // Load all message from messages.properties
    rsMessages = ResourceBundle.getBundle(GlobalConstants.PROPERTIES_MESSAGE);

    String message;
    try {
      // Get message from rsMessages
      message = rsMessages.getString(key);
      if (StringUtils.isEmpty(message)) {
        return key;
      }
      // Replace param
      if (param != null && param.length > 0) {
        message = modifyParam(message, param);
      }
    } catch (MissingResourceException e) {
      message = key;
    }
    // return content
    return message;
  }

  private static String modifyParam(String message, Object... param) {
    boolean isFirst = true;
    String cast;
    List<Object> result = new ArrayList<Object>();
    for (Object el : param) {
      cast = String.valueOf(el);
      el = cast.toLowerCase();
      if (isFirst && message.startsWith(FIRST_PARAM_PREFIX)) {
        el = StringUtils.capitalize((String) el);
        isFirst = false;
      }
      result.add(el);
    }
    return MessageFormat.format(message, result.toArray());
  }
}
