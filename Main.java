import exception.EmployeeAlreadyExistsException;
import exception.EmployeeNotFoundException;
import exception.InvalidAttendanceException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Demonstrates employee registration and attendance management.
 */
public class Main {
    /** Formatter used to display attendance timestamps. */
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Runs the attendance management demonstration.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        AttendanceManager attendanceManager = new AttendanceManager();

        System.out.println("Test 1: Register employees");
        registerEmployee(attendanceManager, new Employee(
                "E001", "Riri Williams", "IT"));
        registerEmployee(attendanceManager, new Employee(
                "E002", "Nick Fury", "HR"));

        System.out.println("\nTest 2: Prevent duplicate employee registration");
        registerEmployee(attendanceManager, new Employee(
                "E001", "Thor Son of Odin", "Electrical"));

        System.out.println("\nTest 3: Find an existing employee");
        displayEmployee(attendanceManager, "E001");

        System.out.println("\nTest 4: Handle an unregistered employee");
        displayEmployee(attendanceManager, "E999");

        System.out.println("\nTest 5: Record an employee's time-in");
        recordTimeIn(attendanceManager, "E001");

        System.out.println("\nTest 6: Prevent multiple active attendance records");
        recordTimeIn(attendanceManager, "E001");

        System.out.println("\nTest 7: Prevent time-out without an active record");
        recordTimeOut(attendanceManager, "E002");

        System.out.println("\nTest 8: Record an employee's time-out");
        recordTimeOut(attendanceManager, "E001");

        System.out.println("\nTest 9: Allow a new attendance record after time-out");
        recordTimeIn(attendanceManager, "E001");

        System.out.println("\nTest 10: Display completed attendance records");
        displayCompletedRecords(attendanceManager, "E001");
    }

    /**
     * Registers an employee and reports success or a duplicate ID.
     *
     * @param attendanceManager the manager handling registration
     * @param employee the employee to register
     */
    private static void registerEmployee(
            AttendanceManager attendanceManager, Employee employee) {
        System.out.println("Employee details:");
        System.out.println("  Employee ID: " + employee.getEmployeeId());
        System.out.println("  Name: " + employee.getName());
        System.out.println("  Department: " + employee.getDepartment());
        try {
            attendanceManager.registerEmployee(employee);
            System.out.println("Registration Successful.");
        } catch (EmployeeAlreadyExistsException exception) {
            System.out.println("Registration Failed: " + exception.getMessage());
        }
    }

    /**
     * Finds and displays an employee, reporting an unknown ID.
     *
     * @param attendanceManager the manager to search
     * @param employeeId the ID to search for
     */
    private static void displayEmployee(
            AttendanceManager attendanceManager, String employeeId) {
        try {
            Employee employee = attendanceManager.findEmployeeById(employeeId);
            System.out.println("Employee found: " + getEmployeeLabel(employee)
                    + ", Department: " + employee.getDepartment() + ".");
        } catch (EmployeeNotFoundException exception) {
            System.out.println("Search Failed for "
                    + getEmployeeLabel(attendanceManager, employeeId) + ": "
                    + exception.getMessage());
        }
    }

    /**
     * Records and reports a time-in attempt.
     *
     * @param attendanceManager the manager handling attendance
     * @param employeeId the employee ID to record
     */
    private static void recordTimeIn(
            AttendanceManager attendanceManager, String employeeId) {
        String employeeLabel = getEmployeeLabel(attendanceManager, employeeId);
        try {
            attendanceManager.timeIn(employeeId);
            System.out.println("Time-in recorded for " + employeeLabel + ".");
        } catch (EmployeeNotFoundException | InvalidAttendanceException exception) {
            System.out.println("Time-in Failed for " + employeeLabel + ": "
                    + exception.getMessage());
        }
    }

    /**
     * Records and reports a time-out attempt.
     *
     * @param attendanceManager the manager handling attendance
     * @param employeeId the employee ID to record
     */
    private static void recordTimeOut(
            AttendanceManager attendanceManager, String employeeId) {
        String employeeLabel = getEmployeeLabel(attendanceManager, employeeId);
        try {
            attendanceManager.timeOut(employeeId);
            System.out.println("Time-out recorded for " + employeeLabel + ".");
        } catch (EmployeeNotFoundException | InvalidAttendanceException exception) {
            System.out.println("Time-out failed for " + employeeLabel + ": "
                    + exception.getMessage());
        }
    }

    /**
     * Retrieves and displays all completed attendance records for an employee.
     *
     * @param attendanceManager the manager providing attendance records
     * @param employeeId the employee ID whose records are displayed
     */
    private static void displayCompletedRecords(
            AttendanceManager attendanceManager, String employeeId) {
        try {
            List<AttendanceRecord> records =
                    attendanceManager.getCompletedAttendanceRecords(employeeId);
            String employeeLabel = getEmployeeLabel(attendanceManager, employeeId);
            System.out.println("Completed attendance records for "
                    + employeeLabel + ":");
            for (AttendanceRecord record : records) {
                System.out.println("Employee: " + employeeLabel + " | Time-in: "
                        + record.getTimeIn().format(DATE_TIME_FORMATTER)
                        + ", Time-out: "
                        + record.getTimeOut().format(DATE_TIME_FORMATTER));
            }
        } catch (EmployeeNotFoundException exception) {
            System.out.println("Could not retrieve attendance records: "
                    + exception.getMessage());
        }
    }

    /**
     * Returns an employee's name and employee ID for display.
     *
     * @param employee the employee to describe
     * @return the employee's name and ID
     */
    private static String getEmployeeLabel(Employee employee) {
        return employee.getName() + " (Employee ID: "
                + employee.getEmployeeId() + ")";
    }

    /**
     * Finds an employee's display label, or identifies an unregistered ID.
     *
     * @param attendanceManager the manager used to find the employee
     * @param employeeId the ID to look up
     * @return the employee's name and ID, or an unregistered-employee label
     */
    private static String getEmployeeLabel(
            AttendanceManager attendanceManager, String employeeId) {
        try {
            return getEmployeeLabel(attendanceManager.findEmployeeById(employeeId));
        } catch (EmployeeNotFoundException exception) {
            return "Unregistered employee (Employee ID: " + employeeId + ")";
        }
    }
}
