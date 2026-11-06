package exceptions;

/**
 * Signals that an employee ID is not registered in the system.
 */
public class EmployeeNotFoundException extends Exception {
    /** Serialization identifier for this exception class. */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an exception with a descriptive message.
     *
     * @param message the reason the exception was raised
     */
    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
