package orgchart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {

    private final Employee dir = employee("DIR", 1000);
    private final Employee mgr1 = employee("MGR_1", 100);
    private final Employee mgr2 = employee("MGR_2", 100);
    private final Employee mgr3 = employee("MGR_2", 100);
    private final Employee ic1 = employee("ic_1", 10);
    private final Employee ic2 = employee("ic_2", 10);
    private final Employee ic3 = employee("ic_3", 10);
    private final Employee ic4 = employee("ic_4", 10);


    /**
     *                 DIR
     *        MGR_1           MGR_2
     *    MGR_3   IC_1     IC_2    IC_3
     * IC_4
     */
    @BeforeEach
    void setup() {
        dir.addReports(mgr1, mgr2);
        mgr1.addReports(mgr3, ic1);
        mgr2.addReports(ic2, ic3);
        mgr3.addReports(ic4);
    }

    @Test
    void test_orgCost_IsCorrect() {
        assertEquals(1340, dir.getOrgCost());
        assertEquals(220, mgr1.getOrgCost());
        assertEquals(120, mgr2.getOrgCost());
        assertEquals(110, mgr3.getOrgCost());
        assertEquals(10, ic1.getOrgCost());
        assertEquals(10, ic2.getOrgCost());
        assertEquals(10, ic3.getOrgCost());
        assertEquals(10, ic4.getOrgCost());
    }

    @Test
    void test_orgDepth_IsCorrect() {
        assertEquals(3, dir.orgDepth());
        assertEquals(2, mgr1.orgDepth());
        assertEquals(1, mgr2.orgDepth());
        assertEquals(1, mgr3.orgDepth());
        assertEquals(0, ic1.orgDepth());
        assertEquals(0, ic2.orgDepth());
        assertEquals(0, ic3.orgDepth());
        assertEquals(0, ic4.orgDepth());
    }

    @Test
    void test_orgCost_EmployeeRemoval_ShouldDecreasesOrgCost() {
        mgr2.removeReports(ic3);

        // These should change
        assertEquals(110, mgr2.getOrgCost());
        assertEquals(1330, dir.getOrgCost());

        // This shouldnt
        assertEquals(220, mgr1.getOrgCost());

    }

    @Test
    void test_orgCost_EmployeeAddition_ShouldIncreaseSalary() {

        Employee ic5 = employee("IC_5", 10);
        mgr3.addReports(ic5);

        // These should change
        assertEquals(120, mgr3.getOrgCost());
        assertEquals(230, mgr1.getOrgCost());
        assertEquals(1350, dir.getOrgCost());

        // This shouldn't
        assertEquals(120, mgr2.getOrgCost());

    }

    private Employee employee(String name, double salary) {
        return new Employee(name, salary);
    }
}
