package fr.polytech.vgl.dao.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.polytech.vgl.model.*;


public interface EmployeeRepository extends MongoRepository<Employee, ObjectId> {
    
	@Query("{name:'?0'}")
	List<Employee> findEmployeeByName(String name);
	
	@Query("{surname:'?0'}")
	List<Employee> findEmployeeBySurname(String surname);
	
	@Query("{name:'?0', surname '?0'}")
	List<Employee> findEmployeeByNameAndSurname(String name, String surname);
    
	List<Employee> findByCompany(Company company);
	
	List<Employee> findByDepartement(Department department);
    
    public long count();
}
