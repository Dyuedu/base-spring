package demo.exception;

public class EmailDuplicateException extends ResourceDuplicateException{

    public EmailDuplicateException(String message, String errors) {
        super(message, errors);
    }

    public EmailDuplicateException() {
        super();
    }
}
