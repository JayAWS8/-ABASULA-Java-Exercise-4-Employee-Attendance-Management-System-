package exception;

/**
 * Signals an attendance operation that violates the attendance rules.
 */
public class InvalidAttendanceException extends Exception {
    /** Serialization identifier for this exception class. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message the reason the exception was raised
     */
    public InvalidAttendanceException(String message) {
        super(message);
    }
}
