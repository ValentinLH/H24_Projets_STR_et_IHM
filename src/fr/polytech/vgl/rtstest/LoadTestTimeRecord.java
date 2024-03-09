package fr.polytech.vgl.rtstest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import fr.polytech.vgl.centralapp.controller.CentralAppController;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.timerecord.controller.TimeRecordControler;

public class LoadTestTimeRecord {

	public static void main(String[] args) {
		// Création d'un contrôleur

		
		
		// Ajout d'une entreprise
		Company company = new Company("TestCompany");
	
		
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
		simulateUserActions(controller, 10, 20); // 10 utilisateurs effectuent 20 actions chacun
		
		

		System.out.println("Total record receive : " + app.getCompany().AllRecord().size());
		
	}

	private static void simulateUserActions(TimeRecordControler controller, int numberOfUsers, int actionsPerUser) {
		AtomicInteger totalGoodResult = new AtomicInteger(0);
		AtomicInteger totalBadResult = new AtomicInteger(0);

		CountDownLatch latch = new CountDownLatch(numberOfUsers);
		
		for (int i = 1; i <= numberOfUsers; i++) {
			new Thread(() -> {
				int addMin = 0;
				int goodResult = 0;
				int badResult = 0;

				for (int j = 1; j <= actionsPerUser; j++) {
					Employee randomEmployee = getRandomEmployee(controller);
					if (randomEmployee != null) {
						int result = controller.sendRecordTest(randomEmployee, LocalDateTime.now().plusMinutes(addMin));
						System.out.println(
								"User " + Thread.currentThread().getId() + " - Action " + j + ": Result " + result);

						if (result == 1) {
							goodResult++;
						} else {
							badResult++;
						}
					}

					addMin += 15;
					sleepRandomMilliseconds(100, 500);
				}

				// Ajouter les résultats du thread aux totaux
				totalGoodResult.addAndGet(goodResult);
				totalBadResult.addAndGet(badResult);
				
				// Indiquer que le thread a terminé son exécution
	            latch.countDown();
			}).start();
		}

		// Attendre la fin de tous les threads
	    try {
	        // Attendre que tous les threads se terminent
	        latch.await();
			// Afficher les totaux
			System.out.println("Total Good Results: " + totalGoodResult.get());
			System.out.println("Total Bad Results: " + totalBadResult.get());
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }



	}

	private static Employee getRandomEmployee(TimeRecordControler controller) {
		List<Company> companies = controller.getListCompany();
		if (!companies.isEmpty()) {
			Company randomCompany = companies.get(0);
			List<Employee> employees = randomCompany.getListEmp();
			if (!employees.isEmpty()) {
				int randomIndex = new Random().nextInt(employees.size());
				return employees.get(randomIndex);
			}
		}
		return null;
	}

	private static void sleepRandomMilliseconds(int min, int max) {
		try {
			Thread.sleep(ThreadLocalRandom.current().nextInt(min, max + 1));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
