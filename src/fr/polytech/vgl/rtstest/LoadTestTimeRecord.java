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
		simulateUserActions(controller, 5, 20); // 10 utilisateurs effectuent 20 actions chacun

		System.out.println(
				"Nombre total de pointage reçu par l'application central : " + app.getCompany().getListRec().size());

	}

	private static void simulateRandomUserActions(TimeRecordControler controller, int numberOfUsers,
			int actionsPerUser) {
		AtomicInteger totalGoodResult = new AtomicInteger(0);
		AtomicInteger totalBadResult = new AtomicInteger(0);

		CountDownLatch latch = new CountDownLatch(numberOfUsers);

		for (int i = 1; i < numberOfUsers + 1; i++) {
			new Thread(() -> {
				int addMin = 0;
				int goodResult = 0;
				int badResult = 0;

				for (int j = 1; j <= actionsPerUser; j++) {
					try {
						Employee randomEmployee = getRandomEmployee(controller);
						if (randomEmployee != null) {

							int result = controller.sendRecordTest(randomEmployee,
									LocalDateTime.now().plusMinutes(addMin));

							System.out.println(
									"User " + Thread.currentThread().getId() + " - Action " + j + ": Result " + result);

							if (result == 1) {
								goodResult++;
							} else {
								badResult++;
							}

						}
					} catch (Exception e) {
						// e.printStackTrace();

					}
					addMin += 16;
					sleepRandomMilliseconds(100, 500);
				}

				// Ajouter les résultats du thread aux totaux
				totalGoodResult.addAndGet(goodResult);
				totalBadResult.addAndGet(badResult);

				// Indiquer que le thread a terminé son exécution
				latch.countDown();
			}).start();
		}
	}

	private static void simulateUserActions(TimeRecordControler controller, int numberOfUsers, int actionsPerUser) {
		AtomicInteger totalGoodResult = new AtomicInteger(0);
		AtomicInteger totalBadResult = new AtomicInteger(0);

		CountDownLatch latch = new CountDownLatch(numberOfUsers);

		for (int i = 1; i < numberOfUsers + 1; i++) {
			int userId = i;

			new Thread(() -> {
				int addMin = 0;
				int goodResult = 0;
				int badResult = 0;
				List<Employee> employees = company.getListEmp();
				Employee randomEmployee = employees.get(userId);
				for (int j = 1; j <= actionsPerUser; j++) {
					try {

						if (randomEmployee != null) {

							int result = controller.sendRecordTest(randomEmployee,
									LocalDateTime.now().plusMinutes(addMin));

							System.out.println(
									"User " + Thread.currentThread().getId() + " - Action " + j + ": Result " + result);

							if (result == 1) {
								goodResult++;
							} else {
								badResult++;
							}

						}
					} catch (Exception e) {
					}
					addMin += 16;
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
			System.out.println("Nombre total d'envoie réussi: " + totalGoodResult.get());
			System.out.println("Nombre total d'envoie échoué " + totalBadResult.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static Employee getRandomEmployee(TimeRecordControler controller) {

		List<Employee> employees = company.getListEmp();
		if (!employees.isEmpty()) {
			int randomIndex = new Random().nextInt(employees.size());

			return employees.get(randomIndex);
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
