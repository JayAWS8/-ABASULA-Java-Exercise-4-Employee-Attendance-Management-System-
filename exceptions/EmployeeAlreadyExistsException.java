package exceptions;

/**
 * Signals an attempt to register an employee ID that is already in use.
 */
public class EmployeeAlreadyExistsException extends Exception {
    /** Serialization identifier for this exception class. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message the reason the exception was raised
     */
    public EmployeeAlreadyExistsException(String message) {
        super(message);
    }
}
