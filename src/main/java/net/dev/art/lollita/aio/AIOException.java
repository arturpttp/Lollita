package net.dev.art.lollita.aio;

public class AIOException extends RuntimeException {

    private static final long serialVersionUID = 0L;

    public AIOException(String message) {
        super(message);
    }

    public AIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public AIOException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

}
