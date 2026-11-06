import exceptions.EmployeeAlreadyExistsException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidAttendanceException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Manages employee registration and attendance transactions.
 */
public class AttendanceManager {
    /** Employees registered in the system. */
    private final List<Employee> employees;

    /** Attendance records created by the system. */
    private final List<AttendanceRecord> attendanceRecords;

    /**
     * Creates an attendance manager with empty employee and attendance lists.
     */
    public AttendanceManager() {
        employees = new ArrayList<>();
        attendanceRecords = new ArrayList<>();
    }

    /**
     * Registers an employee if the employee ID is not already registered.
     *
     * @param employee the employee to register
     * @throws EmployeeAlreadyExistsException if the ID is already registered
     */
    public void registerEmployee(Employee employee)
            throws EmployeeAlreadyExistsException {
        Objects.requireNonNull(employee, "employee");
        boolean alreadyExists = employees.stream()
                .anyMatch(existing -> existing.getEmployeeId()
                        .equals(employee.getEmployeeId()));
        if (alreadyExists) {
            throw new EmployeeAlreadyExistsException(
                    "Employee ID " + employee.getEmployeeId()
                            + " is already registered.");
        }
        employees.add(employee);
    }

    /**
     * Finds a registered employee by ID.
     *
     * @param employeeId the employee ID to find
     * @return the matching employee
     * @throws EmployeeNotFoundException if the ID is not registered
     */
    public Employee findEmployeeById(String employeeId)
            throws EmployeeNotFoundException {
        return employees.stream()
                .filter(employee -> employee.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee ID " + employeeId + " was not found."));
    }

    /**
     * Records a time-in for an employee with no active attendance record.
     *
     * @param employeeId the employee ID to record
     * @throws EmployeeNotFoundException if the ID is not registered
     * @throws InvalidAttendanceException if the employee already has an active record
     */
    public void timeIn(String employeeId)
            throws EmployeeNotFoundException, InvalidAttendanceException {
        findEmployeeById(employeeId);
        boolean hasActiveRecord = attendanceRecords.stream()
                .anyMatch(record -> record.getEmployeeId().equals(employeeId)
                        && record.getTimeOut() == null);
        if (hasActiveRecord) {
            throw new InvalidAttendanceException(
                    "Employee ID " + employeeId
                            + " already has an active attendance record.");
        }
        attendanceRecords.add(new AttendanceRecord(employeeId, LocalDateTime.now()));
    }

    /**
     * Records a time-out for an employee with an active attendance record.
     *
     * @param employeeId the employee ID to record
     * @throws EmployeeNotFoundException if the ID is not registered
     * @throws InvalidAttendanceException if no active record exists
     */
    public void timeOut(String employeeId)
            throws EmployeeNotFoundException, InvalidAttendanceException {
        findEmployeeById(employeeId);
        AttendanceRecord activeRecord = attendanceRecords.stream()
                .filter(record -> record.getEmployeeId().equals(employeeId)
                        && record.getTimeOut() == null)
                .findFirst()
                .orElseThrow(() -> new InvalidAttendanceException(
                        "Employee ID " + employeeId
                                + " does not have an active attendance record."));
        activeRecord.setTimeOut(LocalDateTime.now());
    }

    /**
     * Returns completed attendance records for a registered employee.
     *
     * @param employeeId the employee ID whose completed records are requested
     * @return completed records associated with the employee
     * @throws EmployeeNotFoundException if the ID is not registered
     */
    public List<AttendanceRecord> getCompletedAttendanceRecords(String employeeId)
            throws EmployeeNotFoundException {
        findEmployeeById(employeeId);
        return attendanceRecords.stream()
                .filter(record -> record.getEmployeeId().equals(employeeId)
                        && record.getTimeOut() != null)
                .collect(Collectors.toList());
    }
}
