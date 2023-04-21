package supham.cntt.tuquanao.exception;

public class TuQuanAoException extends Exception{

  private static final long serialVersionUID = 1L;

  private Integer status;

  public TuQuanAoException(String message) {
    super(message);
  }

  public TuQuanAoException(String message, Integer status) {
    super(message);
    this.status = status;
  }

  public TuQuanAoException(String message, Throwable ex) {
    super(message, ex);
  }

  public TuQuanAoException(Throwable ex) {
    super(ex);
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
