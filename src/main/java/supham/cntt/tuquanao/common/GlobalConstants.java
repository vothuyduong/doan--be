package supham.cntt.tuquanao.common;

import java.util.Objects;

public class GlobalConstants {

  public static final String DATE_FORMAT = "MM/dd/yyyy";
  public static final String DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
  public static final String PROPERTIES_MESSAGE = "messages";

  public static boolean isEmpty(String strValue) {
    return Objects.isNull(strValue) || GlobalConstants.Character.TXT_BLANK.equals(strValue.trim());
  }

  public static class Character {

    public static final String ALPHANUMBERIC_PATTERN = "^[ A-Za-z0-9]*$";
    public static final String TXT_BLANK = "";
    public static final String TOKEN_PREFIX = "Bearer ";
  }

  public static class Number {

    public static final Integer NUMBER_ZERO = 0;
    public static final Integer NUMBER_ONE = 1;
    public static final Integer NUMBER_FOUR = 4;
    public static final Integer NUMBER_EIGHT = 8;
  }

  public static class Message {

    public static final String UNAUTHORIZED_CODE = "unauthorized";
    public static final String EXPIRED = "expired";
    public static final String SEVER_ERROR = "sever.error";
    public static final String USER_NOT_FOUND = "user.not.found";

    //customer
    public static final String USERNAME_EXISTS = "username.exists";

    //category
    public static final String ID_CATEGORY_INVALID = "id.category.invalid";
    public static final String NAME_CATEGORY_EMPTY = "name.category.empty";
    public static final String NAME_CATEGORY_EXIST = "name.category.exist";

    //size
    public static final String NAME_SIZE_EMPTY = "name.size.empty";
    public static final String NAME_SIZE_EXIST = "name.size.exist";

    //image
    public static final String NOT_SAVE_FILE = "not.save.file";

    //product
    public static final String ID_PRODUCT_INVALID = "id.product.invalid";
    public static final String NAME_PRODUCT_EMPTY = "name.product.empty";

    //price
    public static final String ID_SIZE_INVALID = "id.size.invalid";

    //cart
    public static final String CART_QUANTITY_MIN_1 = "cart.quantity.min.1";

    //order
    public static final String QUANTITY_PRODUCT_NOT_ENOUGH = "quantity.product.not.enough";
  }

  public static class GenerateJWT {

    public static final String JWT_NAME = "name";
  }
}
