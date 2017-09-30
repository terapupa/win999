package guidezup.win999.sys.DAO;

public class OperationException extends Exception {
    public static final int SOME_PROBLEM_CODE = 1000;

    private int code = 0;
    private String message = null;

    public OperationException() {
        super();
    }

    public OperationException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
