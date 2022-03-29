package orgchart;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee of a company.
 */
public class Employee {

    /**
     * Name of employee.
     */
    private String name;

    /**
     * List of direct reports.
     */
    private List<Employee> reports;

    /**
     * This employee's supervisor.
     */
    private Employee boss;

    /**
     * This employee's salary.
     */
    private double salary;

    /**
     * Total cost of this employee's organization.
     */
    private double orgCost;

    /**
     * Public constructor used to create an employee.
     *
     * @param name of the employee
     * @param salary of the employee
     */
    public Employee(String name, double salary) {
        this.name = name;
        reports = new ArrayList<>();
        this.salary = salary;
        this.orgCost = this.salary;
    }

    /**
     * Adds direct reports that report to this employee.
     *
     * @param reports to add
     */
    public void addReports(Employee... reports) {

        double orgCostIncrease = 0;
        for (Employee e : reports) {
            e.boss = this;
            this.reports.add(e);
            orgCostIncrease += e.orgCost;
        }

        updateOrgCost(orgCostIncrease);
    }

    /**
     * Removes direct reports from reporting to this employee.
     *
     * @param reports to remove
     */
    public void removeReports(Employee... reports) {

        double orgCostDecrease = 0;
        for (Employee e : reports) {
            e.boss = null;
            this.reports.remove(e);
            orgCostDecrease -= e.orgCost;
        }

        updateOrgCost(orgCostDecrease);
    }

    /**
     * Calculates the distance between this employee and the lowest-level employee.
     *
     * @return depth of this employee's org chart
     */
    public int orgDepth() {

        if (this.reports.isEmpty()) {
            return 0;
        }

        int maxDepth = 0;
        for (Employee r : this.reports) {
            maxDepth = Math.max(maxDepth, r.orgDepth());
        }

        return maxDepth + 1;
    }

    /**
     * Get the salary of this employee.
     *
     * @return employee's salary
     */
    public double getSalary() {
        return this.salary;
    }

    /**
     * Get the total cost of this employee's organization.
     *
     * @return total organization cost
     */
    public double getOrgCost() {
        return this.orgCost;
    }

    private void updateOrgCost(double adjustment) {
        this.orgCost += adjustment;
        if (this.boss != null) {
            this.boss.updateOrgCost(adjustment);
        }
    }
}
