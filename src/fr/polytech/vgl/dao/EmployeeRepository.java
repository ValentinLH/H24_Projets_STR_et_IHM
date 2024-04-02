package fr.polytech.vgl.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.polytech.vgl.model.*;


public interface EmployeeRepository extends MongoRepository<Employee, ObjectId> {
    
	@Query("{name:'?0'}")
	Employee findItemByName(String name);
    
    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<Employee> findAll(String company);
    
    public long count();
}
