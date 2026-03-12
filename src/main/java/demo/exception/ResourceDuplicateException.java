package demo.exception;

import org.springframework.http.HttpStatus;

public class ResourceDuplicateException extends ApiErrorException{
    public ResourceDuplicateException(String message, String errors) {
        super(409, message, errors);
    }

    public ResourceDuplicateException(){
        super();
    }

}
