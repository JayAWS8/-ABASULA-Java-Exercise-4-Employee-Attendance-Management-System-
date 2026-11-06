import java.util.Objects;

/**
 * Represents an employee registered in the attendance management system.
 */
public class Employee {
    /** Unique identifier assigned to the employee. */
    private final String employeeId;

    /** Full name of the employee. */
    private final String name;

    /** Department to which the employee belongs. */
    private final String department;

    /**
     * Creates an employee with an ID, name, and department.
     *
     * @param employeeId the employee's unique identifier
     * @param name the employee's name
     * @param department the employee's department
     */
    public Employee(String employeeId, String name, String department) {
        this.employeeId = Objects.requireNonNull(employeeId, "employeeId");
        this.name = Objects.requireNonNull(name, "name");
        this.department = Objects.requireNonNull(department, "department");
    }

    /**
     * Returns the employee's unique identifier.
     *
     * @return the employee ID
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * Returns the employee's name.
     *
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the employee's department.
     *
     * @return the department
     */
    public String getDepartment() {
        return department;
    }
}
