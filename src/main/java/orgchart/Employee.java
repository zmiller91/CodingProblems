package orgchart;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    public final String name;
    public final List<Employee> reports;

    public Employee boss;
    public final double salary;
    public double orgCost;

    public Employee(String name, double salary) {
        this.name = name;
        reports = new ArrayList<>();
        this.salary = salary;
        this.orgCost = this.salary;
    }

    public void addReports(Employee... reports) {

        double orgCostIncrease = 0;
        for (Employee e : reports) {
            e.boss = this;
            this.reports.add(e);
            orgCostIncrease += e.orgCost;
        }

        updateOrgCost(orgCostIncrease);
    }

    public void removeReports(Employee... reports) {

        double orgCostDecrease = 0;
        for (Employee e : reports) {
            e.boss = null;
            this.reports.remove(e);
            orgCostDecrease -= e.orgCost;
        }

        updateOrgCost(orgCostDecrease);
    }

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

    private void updateOrgCost(double adjustment) {
        this.orgCost += adjustment;
        if (this.boss != null) {
            this.boss.updateOrgCost(adjustment);
        }
    }
}
