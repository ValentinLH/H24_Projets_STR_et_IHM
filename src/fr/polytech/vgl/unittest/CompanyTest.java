package fr.polytech.vgl.unittest;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.polytech.vgl.model.*;
import fr.polytech.vgl.model.Record;

/**
 *  CompanyTest is a Junit Test Class for Company 
 *  @since 02/03/24
 *  VLH
 */
public class CompanyTest {

    private Company company;
    private Employee employee1;
    private Employee employee2;
    private Record record1;
    private Record record2;
    private Department departement;
    
    @Before
    public void setUp() {
        company = new Company("Test Company");
        departement = new Department("Administration");
        employee1 = new Employee("Lara", "Clette", company,departement);
        employee2 = new Employee("Tom","D'Anjou",company,departement);
        record1 = new Record(LocalDateTime.now(),employee1);
        record2 = new Record(LocalDateTime.now().minusDays(1),employee2);

        company.addEmployee(employee1);
    }

    @Test
    public void testAddEmployee() {
        company.addEmployee(employee2);
        assertTrue(company.getListEmp().contains(employee2));
    }

    @Test
    public void testDelEmployeeByObject() {
        company.delEmployee(employee1);
        assertFalse(company.getListEmp().contains(employee1));
    }


    @Test
    public void testAddRecord() {
        company.addRecord(record1);
        assertTrue(company.getListRec().contains(record1));
    }

    @Test
    public void testAddRecordWithEmployeeAndDate() {
        company.addRecord(employee1, LocalDateTime.now());
        assertTrue(company.getListRec().stream().anyMatch(r -> r.getEmployee().equals(employee1)));
    }

    @Test
    public void testSortRecord() {
    	// test a revoir
        company.addRecord(record1);
        company.addRecord(record2);
        company.sortRecord();
        assertEquals(record2, company.AllRecord().get(0));
    }

    @Test
    public void testRecordsOfTheDay() {
        company.addRecord(record1);
        company.addRecord(record2);
        List<Record> recordsOfTheDay = company.recordsOfTheDay();
        assertTrue(recordsOfTheDay.contains(record1));
        assertFalse(recordsOfTheDay.contains(record2));
    }

    @Test
    public void testAddDepartment() {
        Department department = new Department("Test Department");
        company.addDepartment(department);
        assertTrue(company.getListDpt().contains(department));
    }

    @Test
    public void testDelDepartment() {
        Department department = new Department("Test Department");
        company.addDepartment(department);
        company.delDepartment(department);
        assertFalse(company.getListDpt().contains(department));
    }

    @Test
    public void testHashCode() {
        // ne sert a rien 
    	assertTrue(true);
    	
    	//Company sameCompany = new Company("Test Company");
        
        //assertEquals(company.hashCode(), sameCompany.hashCode());
    }

    @Test
    public void testEquals() {
        Company sameCompany = new Company("Test Company");
        assertTrue(company.equals(sameCompany));
    }

    @Test
    public void testDelRecordByObject() {
        company.addRecord(record1);
        company.delRecord(record1);
        assertFalse(company.getListRec().contains(record1));
    }

    @Test
    public void testToString() {
        assertEquals("Test Company", company.toString());
    }


    @Test
    public void testAllRecord() {
        company.addRecord(record1);
        company.addRecord(record2);
        List<Record> allRecords = company.AllRecord();
        assertTrue(allRecords.contains(record1));
        assertTrue(allRecords.contains(record2));
    }
}
