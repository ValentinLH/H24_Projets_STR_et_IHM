package fr.polytech.vgl.unittest;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

import fr.polytech.vgl.model.*;
import fr.polytech.vgl.model.Record;

/**
 *  RecordTest is a Junit Test Class for Record 
 *  @since 02/03/24
 *  VLH
 */
public class RecordTest {

    @Test
    public void testRecordCreationWithEmployee() {
        LocalDateTime dateTime = roundToNearestQuarterHour(LocalDateTime.now());
        Employee employee = new Employee("John", "Doe", null, null);

        Record record = new Record(dateTime, employee);

        assertEquals(dateTime, record.getRecord());
        assertEquals(employee, record.getEmployee());
        assertTrue(employee.getRecords().contains(record));
    }

    @Test
    public void testRecordCreationWithoutEmployee() {
        LocalDateTime dateTime = roundToNearestQuarterHour(LocalDateTime.now());

        Record record = new Record(dateTime);

        assertEquals(dateTime, record.getRecord());
        assertNull(record.getEmployee());
    }

    @Test
    public void testSetRecord() {
        LocalDateTime dateTime1 = roundToNearestQuarterHour(LocalDateTime.now());
        LocalDateTime dateTime2 = roundToNearestQuarterHour(roundToNearestQuarterHour(LocalDateTime.now()).plusHours(1).plusMinutes(30));
        Record record = new Record(dateTime1);

        record.setRecord(dateTime2);

        assertEquals(dateTime2, record.getRecord());
    }

    @Test
    public void testSetEmployee() {
        LocalDateTime dateTime = LocalDateTime.now();
        Employee employee1 = new Employee("John", "Doe", null, null);
        Employee employee2 = new Employee("Jane", "Doe", null, null);
        Record record = new Record(dateTime, employee1);

        record.setEmployee(employee2);

        assertFalse(employee1.getRecords().contains(record));
        assertTrue(employee2.getRecords().contains(record));
        assertEquals(employee2, record.getEmployee());
    }

    @Test
    public void testSetMinutes() {
        LocalDateTime dateTime = LocalDateTime.now();
        Record record = new Record(dateTime);

        record.setMinutes(37);

        assertEquals(30, record.getRecord().getMinute());
    }

    @Test
    public void testComputeMinutes() {
        assertEquals(45, Record.computeMinutes(42));
        assertEquals(15, Record.computeMinutes(12));
    }

    @Test
    public void testSetHours() {
        LocalDateTime dateTime = LocalDateTime.now();
        Record record = new Record(dateTime);

        record.setHours(19);

        assertEquals(19, record.getRecord().getHour());
    }

    @Test
    public void testGetRounded() {
        assertEquals(15, Record.getRounded());
    }
    
    private  LocalDateTime roundToNearestQuarterHour(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        int roundedMinutes = (((minutes + 7) / 15) * 15)%60; // Arrondir à la tranche de 15 minutes la plus proche
        return dateTime.withMinute(roundedMinutes).withSecond(0).withNano(0);
    }
    
    
}
