package demo.exception;

public class CodeDuplicateException extends ResourceDuplicateException{
    public CodeDuplicateException(String message, String errors) {
        super(message, errors);
    }

    public CodeDuplicateException() {
        super();
    }
}
