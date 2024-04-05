package fr.polytech.vgl.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import fr.polytech.vgl.model.*;
import fr.polytech.vgl.model.Record;

@SpringBootApplication
@EnableMongoRepositories
public class TestSping implements CommandLineRunner {

	@Autowired
	EmployeeRepository er;

	@Autowired
	ScheduleRepository ff;

	@Autowired
	CompanyRepositoryItem cc;
	
	@Autowired
	DepartmentRepositoryItem dd;
	
	@Autowired
	UpdateRepositoryImpl up;

	public static void main(String[] args) {
		SpringApplication.run(TestSping.class, args);
	}

	@Override
	public void run(String... args) throws Exception {		
		//CRUD COMPANY : 
		
		//createCompany();
		//readCompany();
		//deleteCompany();
		
		//CRUD EMPLOYEE : 
		
		//createEmployeeItems();
		//readEmployee();
		//deleteEmployee();
		
		//CRUD DEPARTEMENT :
		//createDepartment();
		//readDepartment();
		deleteDepartment();
		
		//CRUD SCHEDULE : 
		//createSchesule();
		//readSchedule();
		//deleteSchedule();
		
		//UPDATE with mongoTemplate: 
		//updateTest();
		
	}
	
	//UPDATE : 
	
	void updateTest() {
		
		System.out.println("updateTest Start");
		
		Company com = cc.findByCompanyId(new ObjectId("660c5281160cfe7650b34cad"));
		System.out.println("com = " + com.getCompanyName());
		
		com.setCompanyName("Company n°1");
		
		
		Employee emp = com.getListEmp().get(0);
		emp.setName("Canard");
		emp.setSurname("Dés");
		
		Employee employee = new Employee("Pourquoi","Pas",com,com.getListDpt().get(0));
		Department dep = new Department();
		Record rec = new Record(null,employee);
		
		employee.addRecord(rec);
		com.addRecord(rec);
		
//		com.addEmployee(employee);
//		com.addDepartment(dep);
				
		up.updateCompany(com);
				
		System.out.println("updateTest Start");
		
	}
	
	//READ : 
	
	void readCompany() {
		
		Company company = cc.findByCompanyName("TestComp4");
		
		System.out.println( " company = " + company.toString());
		
		List<Company> test = cc.findAll();
		
		System.out.println("All Company : ");
		cc.findAll().forEach(item -> System.out.println(item.getCompanyName()));
		
		Company com = cc.findByCompanyId(new ObjectId("660dfe9c27a2c35ce23c54f0"));
		
		System.out.println("com = " + com.toString());
		
		List<Company> compa = cc.findCompaniesByEmployee(com.getListEmp().get(0));
		
		System.out.print("compa = ");
		compa.forEach(item -> System.out.println(item.getCompanyName()));
		
		List<Company> compByDpt = cc.findCompaniesByDepartment(com.getListDpt().get(0));
		
		System.out.print("compByDpt = ");
		compa.forEach(item -> System.out.println(item.getCompanyName()));
	}
	
	void readEmployee() {
		
		List<Employee> listEmp = er.findEmployeeByName("Aliceeeeee");
		System.out.println("findEmployeeByName : ");
		listEmp.forEach(emp -> System.out.println(emp.toString()));
		
		Company comp = listEmp.get(0).getCompany();
		Department dep = listEmp.get(0).getDepartement();
		
		listEmp = er.findByCompany(comp);
		System.out.println("findByCompany : ");
		listEmp.forEach(emp -> System.out.println(emp.toString()));
		
		listEmp = er.findByDepartement(dep);
		System.out.println("findByDepartement : ");
		listEmp.forEach(emp -> System.out.println(emp.toString()));
		
		listEmp = er.findEmployeeByNameAndSurname("Max", "Alex");
		System.out.println("findEmployeeByNameAndSurname : ");
		listEmp.forEach(emp -> System.out.println(emp.toString()));
		
		listEmp = er.findEmployeeBySurname("Alex");
		System.out.println("findEmployeeBySurname : ");
		listEmp.forEach(emp -> System.out.println(emp.toString()));
		
		listEmp = er.findAll();
		System.out.println("Employee findAll : ");
		listEmp.forEach(emp -> System.out.println(emp.toString()));
		
		Employee emp = er.findEmployeeById(new ObjectId("660d5b90e9fc8648eaf93a47"));
		System.out.println("findEmployeeById : " + emp.toString());		
	}
	
	void readDepartment() {
		
		
		Department departement = dd.findDepartmentById(new ObjectId("66105333d84f975013605cc0"));
		
		System.out.println("Résulat findDepartmentById : " + departement.getDepartmentName());
		
		List<Department> deptList = dd.findDepartmentByName("Mécanique");
		
		System.out.println("Résulat findOneDepartmentByName : ");
		
		deptList.forEach(dept -> System.out.println(dept.getDepartmentName()));

	}
	
	void readSchedule() {
		
		List<Schedule> listSchedule = ff.findAll();
		System.out.println("Nombre de schedule trouvé : " + listSchedule.size());
		
		Schedule sc = ff.findScheduleById(new ObjectId("660e11f7a311866a41e6b264"));
		System.out.println("Nombre de schedule trouvé : " + sc.toString());
	}
	

	// CREATE
	void createEmployeeItems() {

		Company comp = new Company();
		Department dep = new Department("Infooooooooooeeeeeeee");

		System.out.println("Employee Data creation started...");
		
		er.save(new Employee("Aliceeeeee", "Bobbbbbb", comp, dep));
		er.save(new Employee("Aliceeeeee", "Bobbbbbb", comp, dep));
        er.save(new Employee("Max","Alex",comp,dep));
        er.save(new Employee("Nicolas","Valentin",comp,dep));
        
		System.out.println("Employee Data creation complete...");
	}

	void createSchesule() {
		System.out.println("Schedule Data creation started...");

		ff.save(new Schedule());
		ff.save(new Schedule());
		ff.save(new Schedule());
		ff.save(new Schedule());
		ff.save(new Schedule());
		ff.save(new Schedule());

		System.out.println("Scheule Data creation complete...");
	}

	void createCompany() {

		Company comp = new Company("Company n°6");
		Department dep = new Department("Info6");
		Employee e1 = new Employee("Aliceeeeuu", "Bobeeeeuu", comp, dep);
		Employee e2 = new Employee("AOO", "ALED", comp, dep);
		Record record = new Record(LocalDateTime.now(), e1);
		Record record2 = new Record(LocalDateTime.now(), e2);

		e1.addRecord(record);
		e2.addRecord(record2);

		comp.addDepartment(dep);
		comp.addEmployee(e1);
		dep.addEmployee(e1);
		comp.addEmployee(e2);
		dep.addEmployee(e2);

		comp.addRecord(record);
		comp.addRecord(record2);

		System.out.println("Campany creation started...");

		// cc.save(new Company("Oui"));
		cc.save(comp);

		System.out.println("Campany creation complete...");
	}
	
	void createDepartment() {
		
		Department dep1 = new Department("Info");
		Department dep2 = new Department("Mécanique");
		
		Company comp = new Company();
		dep1.addEmployee(new Employee("Aliceeeeee", "Bobbbbbb", comp, dep1));
		dep1.addEmployee(new Employee("Aliceeeeee", "Bobbbbbb", comp, dep1));
        dep2.addEmployee(new Employee("Max","Alex",comp,dep2));
        dep2.addEmployee(new Employee("Nicolas","Valentin",comp,dep2));
        
        List<Department> list = new ArrayList<Department>();
        
        list.add(dep1);
        list.add(dep2);
        
        System.out.println("Department creation started...");

        dd.saveAll(list);
       
        System.out.println("Department creation started...");
	}
	
	//DELETE : 
	
	void deleteEmployee() {
		
		System.out.println("Employee Data delete started...");
		
		er.deleteById(new ObjectId("660d5c942791d223b5c7163f"));
		
		er.deleteEmployeeByName("Alice");
		
		System.out.println("Employee Data delete complete...");
	}
	
	
	void deleteCompany() {
		
		System.out.println("Campany delete started...");

		cc.deleteById(new ObjectId("660c53f6f418a625d874abb6"));
				
		cc.deleteAll();
		
		System.out.println("Campany delete complete...");

		
	}
	
	void deleteSchedule() {
		
		ff.deleteById(new ObjectId("660e11f7a311866a41e6b269")); //déjà supprime donc ne fait rien
		
		ff.deleteAll();
	}
	
	void deleteDepartment() {
	
		System.out.println("Department delete started...");
		
		dd.deleteById(new ObjectId("661056525dfaef3856ed1c49"));
		dd.deleteAll();
		
		System.out.println("Department delete complete...");
	}
	

	
}