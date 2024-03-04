package fr.polytech.vgl.rtstest;


import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.timerecord.controller.TimeRecordControler;

public class LoadTestTimeRecord {

    public static void main(String[] args) {
        // Création d'un contrôleur
        TimeRecordControler controller = new TimeRecordControler();

        // Ajout d'une entreprise
        Company company = new Company("TestCompany");
        controller.addCompany(company);
        
        Department department = new Department("Admin");
        
        // Simuler 100 employés
        for (int i = 1; i <= 100; i++) {
            Employee employee = new Employee("Employee","n°"+i,company,department);
            company.addEmployee(employee);
        }

        // Simuler des actions simultanées de plusieurs utilisateurs
        simulateUserActions(controller, 10, 20); // 10 utilisateurs effectuent 20 actions chacun
    }

    private static void simulateUserActions(TimeRecordControler controller, int numberOfUsers, int actionsPerUser) {
        for (int i = 1; i <= numberOfUsers; i++) {
            new Thread(() -> {
                for (int j = 1; j <= actionsPerUser; j++) {
                    // Simuler une action, par exemple, envoyer un enregistrement
                    Employee randomEmployee = getRandomEmployee(controller);
                    if (randomEmployee != null) {
                        int result = controller.sendRecord(randomEmployee);
                        System.out.println("User " + Thread.currentThread().getId() + " - Action " + j + ": Result " + result);
                    }

                    // Ajouter une pause entre les actions pour simuler un comportement réaliste
                    sleepRandomMilliseconds(100, 500);
                }
            }).start();
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
