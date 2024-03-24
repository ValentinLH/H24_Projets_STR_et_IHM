package fr.polytech.vgl.rtstest;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import javax.realtime.*;

import fr.polytech.vgl.centralapp.controller.CentralAppController;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.timerecord.controller.TimeRecordControler;

public class TimeExecutionTest {
		
	static Company company = new Company("TestCompany");
	
	public static void main(String[] args) {
		// Création d'un contrôleur

		// Ajout d'une entreprise
		Department department = new Department("Admin");

		// Simuler 100 employés
		for (int i = 1; i <= 100; i++) {
			Employee employee = new Employee("Employee", "n°" + i, company, department);
			company.addEmployee(employee);
		}

		CentralAppController app = new CentralAppController(company);
		
		TimeRecordControler controller = new TimeRecordControler();
		controller.addCompany(company);

		// Simuler des actions simultanées de plusieurs utilisateurs
		
		long startTime = System.nanoTime();
		
		simulateUserActions(controller, 5, 20); // 5 utilisateurs effectuent 20 actions chacun
		
		long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        System.out.println("Execution TOTAL Time pour 100 pointages: " + executionTime + " nanoseconds");
	}
	
	private static void simulateUserActions(TimeRecordControler controller, int numberOfUsers, int actionsPerUser) {

		CountDownLatch latch = new CountDownLatch(numberOfUsers);

		for (int i = 1; i < numberOfUsers + 1; i++) {
						
			int userId = i;			
			new Thread(() -> {
				int addMin = 0;
				List<Employee> employees = company.getListEmp();
				Employee randomEmployee = employees.get(userId);
				try {

					if (randomEmployee != null) {
						long startTime = System.nanoTime();
						 
						controller.sendRecordTest(randomEmployee,
								LocalDateTime.now().plusMinutes(addMin));
						
						long endTime = System.nanoTime();
			            long executionTime = endTime - startTime;
			            System.out.println("Execution Time: " + executionTime + " nanoseconds");
			            
					}
				} catch (Exception e) {
				}
				addMin += 16;

				// Indiquer que le thread a terminé son exécution
				latch.countDown();
			}).start();
		}

		// Attendre la fin de tous les threads
		try {
			// Attendre que tous les threads se terminent
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
