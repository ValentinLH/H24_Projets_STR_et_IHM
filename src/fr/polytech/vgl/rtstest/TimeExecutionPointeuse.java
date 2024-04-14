package fr.polytech.vgl.rtstest;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.timerecord.controller.TimeRecordControler;

public class TimeExecutionPointeuse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				TimeRecordControler controller = new TimeRecordControler();
				List<Company> listcompany = new ArrayList<Company>();

				long startTime = System.nanoTime();
				long moyenne = 0;
				long endTime = 0;
				long executionTime = 0;

				for (int i = 0; i < 100; ++i) {
					Company company = new Company("TestCompany");
					listcompany.add(company);
				}

				startTime = System.nanoTime();

				controller.onObjectReceived(listcompany);

				endTime = System.nanoTime();
				executionTime = endTime - startTime;

				

				for (int i = 0; i < 100; ++i) {
					startTime = System.nanoTime();
					controller.onObjectReceived(listcompany.get(i));
					endTime = System.nanoTime();
					executionTime = endTime - startTime;
					moyenne += executionTime;
				}

				long startTime2 = System.nanoTime();
				for (int i = 0; i < 100; ++i) {
					controller.onObjectReceived(listcompany.get(i));
				}
				long endTime2 = System.nanoTime();
				long executionTime2 = endTime2 - startTime2;
				System.out.println("Temps de traitement de la pointeuse pour  une liste 100 company : " + executionTime
						+ " nanoseconds");

				System.out.println(
						"Temps de traitement de la pointeuse pour 100 company : " + executionTime2 + " nanoseconds");
				System.out.println("Le temps moyen de traitement de la pointeuse pour 1 company est de : "
						+ moyenne / 100 + " nanoseconds");

			}
		});

	}

}
