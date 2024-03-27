package fr.polytech.vgl.rtstest;

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
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.timerecord.controller.TimeRecordControler;
import fr.polytech.vgl.model.Record;

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
		
				
		
		//TimeRecordControler controller = new TimeRecordControler();
		//controller.addCompany(company);

		// Calcul des temps de traitement pour 100 pointages
		List<Record> recordslist = new ArrayList<Record>();
		int add =0;
		
		long startTime = 0;
		long moyenne = 0;
		
		//Creation de la liste : 
		for(int i = 1; i<=100;i++) {
			Record record  = new Record(LocalDateTime.now().plusMinutes(add),company.getListEmp().get(i));
			add+=16;
			recordslist.add(record);	
		}
		
		
		//Calcul de la moyenne de traitement pour 1 pointage
		for(int i =0; i<100;i++) {
			long startTime2 = System.nanoTime();
			app.onObjectReceived(recordslist.get(i));
			long endTime2 = System.nanoTime();
	        long executionTime2 = endTime2 - startTime2;
	        moyenne+=executionTime2;
		}
		
		//Calcul du temps de traitement pour 100 pointages
		startTime = System.nanoTime();
		for(int i =0; i<100;i++) {
			app.onObjectReceived(recordslist.get(i));
		}
		long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        
        
        System.out.println("Temps de traitement de l'app central pour 100 pointages: " + executionTime + " nanoseconds");
        System.out.println("Le temps moyen de traitement de l'app central pour 1 pointage est de : " + moyenne/100 + " nanoseconds");
        
        //Calcul du temps de traitement pour une ArrayList de 100 pointage
        
        startTime = System.nanoTime();
        
        app.onObjectReceived(recordslist);
        
        endTime = System.nanoTime();
        executionTime = endTime - startTime;
        
        System.out.println("Temps de traitement de l'app central pour 100 pointages: " + executionTime + " nanoseconds");        

	}
}
