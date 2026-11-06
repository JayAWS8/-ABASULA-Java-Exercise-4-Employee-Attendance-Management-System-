import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an employee's time-in and time-out transaction.
 */
public class AttendanceRecord {
    /** ID of the employee associated with this attendance record. */
    private final String employeeId;

    /** Date and time when the employee started attendance. */
    private final LocalDateTime timeIn;

    /** Date and time when the employee ended attendance, or {@code null} if active. */
    private LocalDateTime timeOut;

    /**
     * Creates an attendance record with no time-out value.
     *
     * @param employeeId the ID of the employee
     * @param timeIn the date and time the employee started attendance
     */
    public AttendanceRecord(String employeeId, LocalDateTime timeIn) {
        this.employeeId = Objects.requireNonNull(employeeId, "employeeId");
        this.timeIn = Objects.requireNonNull(timeIn, "timeIn");
        this.timeOut = null;
    }

    /**
     * Returns the ID of the employee associated with this record.
     *
     * @return the employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Returns the time-in date and time.
     *
     * @return the time-in value
     */
    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    /**
     * Returns the time-out date and time, or {@code null} if attendance is active.
     *
     * @return the time-out value, or {@code null}
     */
    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    /**
     * Sets the time-out date and time.
     *
     * @param timeOut the time-out value
     */
    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }
}
