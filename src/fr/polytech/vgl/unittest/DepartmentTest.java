package fr.polytech.vgl.unittest;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import fr.polytech.vgl.model.*;
/**
 *  DepartmentTest is a Junit Test Class for Department 
 *  @since 02/03/24
 *  VLH 
 */

public class DepartmentTest {


    private Company company;

    @Before
    public void setUp() {
        company = new Company("La Mie Journée");
    }

    @Test
    public void testAddEmployee() {
        Department department = new Department("Paris-Brest");
        Employee employee = new Employee("Laure","Édubois", company, department);

        department.addEmployee(employee);

        assertTrue(department.getListEmp().contains(employee));
        assertEquals(department, employee.getDepartement());
    }

    @Test
    public void testDelEmployee() {
        Department department = new Department("Eclair au café");
        Employee employee = new Employee("Laure","Édubois", company, department);
        department.addEmployee(employee);

        department.delEmployee(employee);

        assertFalse(department.getListEmp().contains(employee));
        assertNull(employee.getDepartement());
    }

    @Test
    public void testDelEmployeeByIndex() {
        Department department = new Department("La Brioche Vendéenne");
        Employee employee = new Employee("Laure","Édubois", company, department);
        department.addEmployee(employee);

        department.delEmployee(0);

        assertFalse(department.getListEmp().contains(employee));
        assertNull(employee.getDepartement());
    }

    @Test
    public void testSetDepartmentName() {
        Department department = new Department("Le Chou ... de Bruxelles");
        assertEquals("Le Chou ... de Bruxelles", department.getDepartmentName());

        department.setDepartmentName("Chouquette");
        assertEquals("Chouquette", department.getDepartmentName());
    }

    @Test
    public void testGetDepartementId() {
        Department department1 = new Department("La Madeleine de Proust");
        Department department2 = new Department("A la recherche du Pain Perdu");

        assertNotEquals(department1.getDepartementId(), department2.getDepartementId());
    }
}
