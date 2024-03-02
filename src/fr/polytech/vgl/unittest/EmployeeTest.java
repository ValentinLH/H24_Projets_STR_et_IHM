package fr.polytech.vgl.unittest;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import fr.polytech.vgl.model.*;
import fr.polytech.vgl.model.Record;

/**
 *  EmployeeTest is a Junit Test Class for Employee 
 *  @since 02/03/24
 *  VLH 
 * getOvertimePortfolio est tester dans OvertimePortfolioTest
 */

public class EmployeeTest {

    private Company company;
    private Department department;

    @Before
    public void setUp() {
        company = new Company("Le Bateau Ivre");
        department = new Department("Peche");
    }

    @Test
    public void testAddRecord() {
        Employee employee = new Employee("Claire", "Delunne", company, department);
        company.addEmployee(employee);

        LocalDateTime date = LocalDateTime.now();
        employee.addRecord(date);

        Record record = employee.getRecords().get(0);
        assertNotNull(record);
        assertEquals(employee, record.getEmployee());
    }


    @Test
    public void testAddEmployee() {
        Employee employee1 = new Employee("Yvon", "Tremblay", company, department);
        Employee employee2 = new Employee("Mélusine", "Enperte", company, department);

        company.addEmployee(employee1);
        company.addEmployee(employee2);

        assertTrue(company.getListEmp().contains(employee1));
        assertTrue(company.getListEmp().contains(employee2));
        assertEquals(company, employee1.getCompany());
        assertEquals(company, employee2.getCompany());
    }

    @Test
    public void testDelEmployee() {
        Employee employee = new Employee("Yvon", "Tremblay", company, department);
        company.addEmployee(employee);

        company.delEmployee(employee);
        	
        System.out.println(employee.getCompany());
        
        assertFalse(company.getListEmp().contains(employee));
        assertNull(employee.getCompany());
    }

    @Test
    public void testSetCompany() {
        Employee employee = new Employee("Yvon", "Tremblay", company, department);
        company.addEmployee(employee);

        Company newCompany = new Company("Les Gens Bons");
        employee.setCompany(newCompany);

        assertFalse(company.getListEmp().contains(employee));
        assertTrue(newCompany.getListEmp().contains(employee));
        assertEquals(newCompany, employee.getCompany());
    }
}
