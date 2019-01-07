package yuriy.weiss.iq.puzzler;

public class PuzzlerException extends RuntimeException {

    public PuzzlerException( String message ) {
        super( message );
    }

    public PuzzlerException( String message, Throwable t ) {
        super( message, t );
    }
}
