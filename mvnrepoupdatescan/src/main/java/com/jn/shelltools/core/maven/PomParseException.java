package com.jn.shelltools.core.maven;

public class PomParseException extends RuntimeException{
    public PomParseException() {
        super();
    }

    public PomParseException(String message) {
        super(message);
    }

    public PomParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PomParseException(Throwable cause) {
        super(cause);
    }
}
