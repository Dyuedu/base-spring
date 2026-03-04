package demo.exception;

import org.springframework.http.HttpStatus;

public class ResourceDuplicateException extends ApiErrorException{
    private int code =409;
    private String message = "Resource already exists";
    private HttpStatus status = HttpStatus.CONFLICT;

    public ResourceDuplicateException(String message, String errors) {
        super(409, message, errors);
    }

    public ResourceDuplicateException(){
        super();
    }

}
