package fr.polytech.vgl.unittest;

import fr.polytech.vgl.model.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

import static org.junit.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OvertimePortfolioTest {
	

	
	@Test
	public void testHoursNegative() {
		Company c = new Company("Juanito Futuristics");
		Department d = new Department("Administration");
		
		Employee emptest = new Employee("Judas", "Nanas", c, d);
		
		LocalDateTime temp = LocalDateTime.of(2022, 5, 23, 8, 12);
		for (int i = 0; i < 5; i++) {
			emptest.addRecord(temp);
			emptest.addRecord(temp.plusHours(7));
			temp = temp.plusDays(1);
			
		}
		
		assertEquals(Integer.valueOf(-450), emptest.getOvertimePortfolio());
		
		
	}
	
	@Test
	public void testDoubleAppel() {
		Company c = new Company("Juanito Futuristics");
		Department d = new Department("Administration");
		
		Employee emptest = new Employee("Judas", "Nanas", c, d);
		
		LocalDateTime temp = LocalDateTime.of(2022, 5, 23, 8, 12);
		for (int i = 0; i < 5; i++) {
			emptest.addRecord(temp);
			emptest.addRecord(temp.plusHours(7));
			temp = temp.plusDays(1);
			
		}
		emptest.getOvertimePortfolio();
		
		assertEquals(Integer.valueOf(-450), emptest.getOvertimePortfolio());
		
		
	}
	
	@Test
	public void testHoursPositive() {
		Company c = new Company("Juanito Futuristics");
		Department d = new Department("Administration");
		
		Employee emptest = new Employee("Judas", "Nanas", c, d);
		
		LocalDateTime temp = LocalDateTime.of(2022, 5, 23, 8, 12);
		for (int i = 0; i < 5; i++) {
			emptest.addRecord(temp);
			emptest.addRecord(temp.plusHours(10));
			temp = temp.plusDays(1);
			
		}
		
		assertEquals(Integer.valueOf(450), emptest.getOvertimePortfolio());
			
	}
	
	@Test
	public void testHoursNul() {
		Company c = new Company("Juanito Futuristics");
		Department d = new Department("Administration");
		
		Employee emptest = new Employee("Judas", "Nanas", c, d);
		
		LocalDateTime temp = LocalDateTime.of(2022, 5, 23, 8, 12);
		LocalDateTime temp2;
		for (int i = 0; i < 5; i++) {
			emptest.addRecord(temp);
			temp2 = temp.plusHours(8);
			temp2 = temp2.plusMinutes(30);
			emptest.addRecord(temp2);
			temp = temp.plusDays(1);
			
		}
		
		assertEquals(Integer.valueOf(0), emptest.getOvertimePortfolio());
		
		
	}
	
	@Test
	public void testHoursSet() {
		Company c = new Company("Juanito Futuristics");
		Department d = new Department("Administration");
		
		Employee emptest = new Employee("Judas", "Nanas", c, d);
		
		emptest.setOvertimePortfolio(Integer.valueOf(10));
		
		LocalDateTime temp = LocalDateTime.of(2022, 5, 23, 8, 12);
		LocalDateTime temp2;
		for (int i = 0; i < 5; i++) {
			emptest.addRecord(temp);
			temp2 = temp.plusHours(8);
			temp2 = temp2.plusMinutes(30);
			emptest.addRecord(temp2);
			temp = temp.plusDays(1);
			
		}
		
		assertEquals(Integer.valueOf(15), emptest.getOvertimePortfolio());
		
		
	}
	

}
