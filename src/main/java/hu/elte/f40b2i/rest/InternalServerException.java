package hu.elte.f40b2i.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InternalServerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InternalServerException() {
        super();
    }
    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }
    public InternalServerException(String message) {
        super(message);
    }
    public InternalServerException(Throwable cause) {
        super(cause);
    }
    public InternalServerException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
