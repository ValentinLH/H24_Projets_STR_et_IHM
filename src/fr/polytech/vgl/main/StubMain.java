package fr.polytech.vgl.main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;
import fr.polytech.vgl.serialisation.Serialisation;

/**
* @author Lino Touret - Valentin L'Hermite
*
*/

public class StubMain {

	public static void main(String[] args) {

		//System.out.println("Hey");
		
		// Company c1 = new Company("Juanito Futuristique");
		Department d1 = new Department("Administration");


		Company rep = stubCompany();

		for (Employee emp : rep.getListEmp()) {
			// System.out.println(emp);
			emp.sortRecord();
			System.out.println(emp);
		}
		

		for (Department emp : rep.getListDpt()) {
			// System.out.println(emp);
			System.out.println(emp);
		}


		rep.sortRecord();
		for (Record rec : rep.getListRec()) {

			System.out.println(rec);
		}

		rep.getListEmp().get(0).addRecord(LocalDateTime.now());
		//rep.addRecord(null);
		System.out.println("Record of the Day :");
		for (Record rec : rep.recordsOfTheDay()) {

			System.out.println(rec);
		}

		Company sec = miniStubCompany();

		System.out.println("Employé du mois :");
		// System.out.println("Le portefeuile de "+ sec.getListEmp().get(0) + " est de "
		// + sec.getListEmp().get(0).getOvertimePortfolio() + " Minutes");
		System.out.println("Les records ");
		for (Record rec : sec.getListRec()) {

			System.out.println(rec);
			Serialisation.serialize(rec, "company.sav");
			
		}
		sec.getListEmp().get(0).addRecord(LocalDateTime.now().plusDays(1));
		sec.getListEmp().get(0).addRecord(LocalDateTime.now().plusDays(1).plusHours(3));
		
		System.out.println("Le portefeuile  est de " + sec.getListEmp().get(0).getOvertimePortfolio() + " Minutes");
		System.out.println("Serialisation :");
		Record rec =  Serialisation.deserialize("company.sav");
		System.out.println(rec);
		System.out.println(rec.getEmployee());
		
		List<Company> listComp = new ArrayList<>();
		listComp.add(rep);
		
		Company comp2 = stubCompany(); 
		comp2.setCompanyName("The Green Fairy");
		comp2.getListEmp().get(0).setSurname("Bricot");;

		comp2.getListEmp().get(1).setSurname("Pierre");
		
		listComp.add(comp2);
		listComp.add(gigaStubCompany());
		
		Serialisation.serialize(listComp, "timerecord.sav");
		
		List<Record> listRec = new ArrayList<>();
		System.out.println(comp2.getListEmp().get(0).getRecords().get(0));
		listRec.add(comp2.getListEmp().get(0).getRecords().get(0));
		listRec.add(comp2.getListEmp().get(0).getRecords().get(1));
		listRec.add(comp2.getListEmp().get(1).getRecords().get(2));
		listRec.add(comp2.getListEmp().get(2).getRecords().get(0));
		
		Serialisation.serialize(listRec,"newrecords.sav");
		
		Serialisation.serialize(rep,"newcompany.sav");
		
		
		Company c = Serialisation.deserialize("newcompany.sav");
		
		System.out.println(c);

		
	}

	public static Company stubCompany() {
		Company c = new Company("Juanito Futuristics");

		List<LocalDateTime> listDate = new ArrayList<>();
		listDate.add(LocalDateTime.now());
		listDate.add(LocalDateTime.of(2022, 4, 4, 8, 20));
		listDate.add(LocalDateTime.of(2022, 3, 14, 7, 58));
		LocalDateTime date1 = LocalDateTime.now();
		date1 = date1.withHour(8);
		date1 = date1.withMinute(38);
		listDate.add(date1);
		List<Department> listD = new ArrayList<>();
		Department d1 = new Department("Administration");
		Department d2 = new Department("Research and Developement");
		Department d3 = new Department("Production");
		
		listD.add(d1);
		listD.add(d2);
		listD.add(d3);


		Collections.shuffle(listD);
		listD.get(0).addEmployee(new Employee("Judas", "Nanasse"));
		Collections.shuffle(listD);
		listD.get(0).addEmployee(new Employee("Lara", "Clette"));
		Collections.shuffle(listD);
		listD.get(0).addEmployee(new Employee("Sylvain", "Hebon"));
		Collections.shuffle(listD);
		listD.get(0).addEmployee(new Employee("Eve", "Idamant"));
		Collections.shuffle(listD);
		listD.get(0).addEmployee(new Employee("Aubin", "Sahalor"));

		
		
		
		List<Integer> listI = new ArrayList<>();
		listI.add(7);
		listI.add(8);
		listI.add(9);
		listI.add(10);
		

		c.addDepartment(d1);
		c.addDepartment(d2);
		c.addDepartment(d3);


		for (Employee emp : c.getListEmp()) {
			Collections.shuffle(listDate);
			LocalDateTime temp = listDate.get(0);
			for (int i = 0; i < 5; i++) {
				emp.addRecord(temp);
				Collections.shuffle(listI);
				emp.addRecord(temp.plusHours(listI.get(0)));
				temp = temp.plusDays(1);
			}
		}
		
		
		return c;
	}

	public static Company miniStubCompany() {
		Company c = new Company("Juanito Futuristics");

		List<LocalDateTime> listDate = new ArrayList<>();
		listDate.add(LocalDateTime.of(2022, 5, 23, 8, 12));

		List<Department> listD = new ArrayList<>();
		listD.add(new Department("Administration"));
		listD.add(new Department("Research and Developement"));
		listD.add(new Department("Production"));


		Collections.shuffle(listD);
		listD.get(0).addEmployee(new Employee("Judas", "Nanas"));
		
		c.setListDpt(listD);
		
		List<Integer> listI = new ArrayList<>();
		//listI.add(7);
		//listI.add(8);
		listI.add(9);
		//listI.add(10);

		for (Employee emp : c.getListEmp()) {
			Collections.shuffle(listDate);
			LocalDateTime temp = listDate.get(0);
			for (int i = 0; i < 5; i++) {
				emp.addRecord(temp);
				Collections.shuffle(listI);
				emp.addRecord(temp.plusHours(listI.get(0)));
				temp = temp.plusDays(1);
			}
		}

		return c;
	}

	public static Company gigaStubCompany() {
		Company c = new Company("MiniTru");

		List<LocalDateTime> listDate = new ArrayList<>();
		listDate.add(LocalDateTime.of(1984, 8, 6, 8, 12));
		listDate.add(LocalDateTime.of(1984, 10, 8, 8, 20));
		listDate.add(LocalDateTime.of(1984, 2, 13, 7, 58));
		LocalDateTime date1 = LocalDateTime.now();
		date1 = date1.withHour(8);
		date1 = date1.withMinute(38);
		date1.withYear(1984);
		
		listDate.add(date1);
		c.addDepartment(new Department("Archive"));
		c.addDepartment(new Department("Administration"));
		c.addDepartment(new Department("Research and Developement"));
		c.addDepartment(new Department("Production"));
	
		
		for (int i = 0; i < 20; i++)
		{
			Collections.shuffle(c.getListDpt());
			c.getListDpt().get(0).addEmployee(new Employee("Winston", "Smith"));	
		}
		
		
		List<Integer> listI = new ArrayList<>();
		listI.add(7);
		listI.add(8);
		listI.add(9);
		listI.add(10);

		for (Employee emp : c.getListEmp()) {
			Collections.shuffle(listDate);
			LocalDateTime temp = listDate.get(0);
			for (int i = 0; i < 5; i++) {
				emp.addRecord(temp);
				Collections.shuffle(listI);
				emp.addRecord(temp.plusHours(listI.get(0)));
				temp = temp.plusDays(1);
			}
		}

		return c;
	}
}


