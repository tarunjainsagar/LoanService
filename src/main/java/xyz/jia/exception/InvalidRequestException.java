package xyz.jia.exception;

public class InvalidRequestException extends RuntimeException {

        public InvalidRequestException(String msg) {
            super(msg);
        }
}
