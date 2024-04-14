package fr.polytech.vgl.rtstest;

import java.awt.EventQueue;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import javax.realtime.*;

import fr.polytech.vgl.centralapp.controller.CentralAppController;
import fr.polytech.vgl.dao.DAO;
import fr.polytech.vgl.dao.service.CompanyService;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.timerecord.controller.TimeRecordControler;
import fr.polytech.vgl.model.Record;

public class TimeExecutionTest {

	static Company company;

	public static void main(String[] args) {
		// Création d'un contrôleur

		// Ajout d'une entreprise
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				CompanyService cs = DAO.getCompanyService();

				company = cs.findFirstByCompanyName("TestCompany");

				for (Employee emp : company.getListEmp())
					emp.getRecords().clear();

				cs.saveCompany(company);

				CentralAppController app = new CentralAppController(company);

				// TimeRecordControler controller = new TimeRecordControler();
				// controller.addCompany(company);

				// Calcul des temps de traitement pour 100 pointages
				List<Record> recordslist = new ArrayList<Record>();
				int add = 0;

				long startTime = 0;
				long moyenne = 0;

				// Creation de la liste :
				for (int i = 0; i < 100; i++) {
					Record record = new Record(LocalDateTime.now().plusMinutes(add), company.getListEmp().get(i));
					add += 16;
					recordslist.add(record);
				}

				// Calcul de la moyenne de traitement pour 1 pointage
				for (int i = 0; i < 100; i++) {
					long startTime2 = System.nanoTime();
					app.onObjectReceived(recordslist.get(i));
					long endTime2 = System.nanoTime();
					long executionTime2 = endTime2 - startTime2;
					moyenne += executionTime2;
				}

				// Calcul du temps de traitement pour 100 pointages
				startTime = System.nanoTime();
				for (int i = 0; i < 100; i++) {
					app.onObjectReceived(recordslist.get(i));
				}
				long endTime = System.nanoTime();
				long executionTime = endTime - startTime;

				// Calcul du temps de traitement pour une ArrayList de 100 pointage

				long startTimeList = System.nanoTime();

				app.onObjectReceived(recordslist);

				long endTimeList = System.nanoTime();
				long executionTimeList = endTimeList - startTimeList;

				System.out.println(
						"Temps de traitement de l'app central pour 100 pointages: " + executionTime + " nanoseconds");
				System.out.println("Le temps moyen de traitement de l'app central pour 1 pointage est de : "
						+ moyenne / 100 + " nanoseconds");

				System.out.println("Temps de traitement de l'app central pour 100 pointages: " + executionTimeList
						+ " nanoseconds");

				app.closeWindow();

			}
		});
	}
}
