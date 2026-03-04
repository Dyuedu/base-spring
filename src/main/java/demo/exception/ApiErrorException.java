package demo.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiErrorException extends RuntimeException{
    private int statusCode;
    private String errors;
    public ApiErrorException(String message) {
        super(message);
    }

    public ApiErrorException(int statusCode, String message, String errors) {
        super(message);
        this.statusCode = statusCode;
        this.errors = errors;
    }

    public ApiErrorException() {
        super();
    }

}
