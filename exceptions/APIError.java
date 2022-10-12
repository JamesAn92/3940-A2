package exceptions;

public class APIError extends Exception {

    private final int DEFAULT_STATUS_CODE = 500; 
    private long statusCode;

    public APIError(String message, long statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public APIError(String message) {
        super(message);
        this.statusCode = DEFAULT_STATUS_CODE;
    }

    public long getStatusCode() {
        return statusCode;
    }
}