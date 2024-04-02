package fr.polytech.vgl.dao;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.polytech.vgl.model.*;
import fr.polytech.vgl.model.Record;

@SpringBootApplication
public class TestSping implements CommandLineRunner {
	
	@Autowired
	EmployeeRepository er;
	
	@Autowired
	ScheduleRepository ff;
	
	@Autowired
	CompanyRepositoryItem cc;
	
	public static void main(String[] args) {
		SpringApplication.run(TestSping.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		//createSchesule();
		createCompany();
	}
	
	//CREATE
    void createEmployeeItems() {
    	
    	Company comp = new Company();
    	Department dep = new Department("Info");
    	
        System.out.println("Data creation started...");
        er.save(new Employee("Alice","Bob",comp,dep));
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
    	
      Company comp = new Company("TestComp2");
  	  Department dep = new Department("Info2");
      Employee e1 = new Employee("Alice","Bob",comp,dep);
      Record record = new Record(LocalDateTime.now(),e1);
      
      e1.addRecord(record);
      
      comp.addDepartment(dep);
      comp.addEmployee(e1);
      dep.addEmployee(e1);
      
      comp.addRecord(record);
      
    	
      System.out.println("Campany creation started...");
          
  	  //cc.save(new Company("Oui"));
  	  cc.save(comp);
        
      System.out.println("Campany creation complete...");
  }
}