package fr.polytech.vgl.dao;

import java.time.LocalDateTime;
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
public class TestSpring implements CommandLineRunner {

	@Autowired
	EmployeeRepository er;

	@Autowired
	ScheduleRepository ff;

	@Autowired
	CompanyRepositoryItem cc;

	public static void main(String[] args) {
		SpringApplication.run(TestSpring.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		readCompany();
		
	}
	
	//READ : 
	
	void readCompany() {
		//Company company = cc.findByCompanyName("TestComp4");
		
		//company.toString();
		
		List<Company> test = cc.findAll();
		
		cc.findAll().forEach(item -> System.out.println(item.getCompanyName()));
		//Company com = cc.findByCompanyId(new ObjectId("660c546dce06cb6b7a762215"));
		
		//com.toString();
		
//		Optional<Company> c = cc.findById(new ObjectId("660d5b90e9fc8648eaf93a47"));
//		
//		c.toString();
//		
	}
	
	
	List<Company> getCompanies() {
	
		
		List<Company> companies = cc.findAll();
		return companies;
			
	}

	// CREATE
	void createEmployeeItems() {

		Company comp = new Company();
		Department dep = new Department("Infooooooooooeeeeeeee");

		System.out.println("Data creation started...");
		er.save(new Employee("Aliceeeeee", "Bobbbbbb", comp, dep));
//        er.save(new Employee("Max","Alex",comp,dep));
//        er.save(new Employee("Nico","Val",comp,dep));
//        EmployeeRepository.save(new Employee("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
//        EmployeeRepository.save(new Employee("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
//        EmployeeRepository.save(new Employee("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
//        EmployeeRepository.save(new Employee("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));
		System.out.println("Data creation complete...");
	}

	void createSchesule() {
		System.out.println("Schedule Data creation started...");

		ff.save(new Schedule());

		System.out.println("Scheule Data creation complete...");
	}

	void createCompany() {

		Company comp = new Company("TestComp5");
		Department dep = new Department("Info5");
		Employee e1 = new Employee("Aliceeeeuu", "Bobeeeeuu", comp, dep);
		Record record = new Record(LocalDateTime.now(), e1);

		e1.addRecord(record);

		comp.addDepartment(dep);
		comp.addEmployee(e1);
		dep.addEmployee(e1);

		comp.addRecord(record);

		System.out.println("Campany creation started...");

		// cc.save(new Company("Oui"));
		cc.save(comp);

		System.out.println("Campany creation complete...");
	}
	

	
}