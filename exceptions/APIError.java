package exceptions;

public class APIError extends Exception {

    private long statusCode;

    public APIError(String message, long statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(long statusCode) {
        this.statusCode = statusCode;
    }

}