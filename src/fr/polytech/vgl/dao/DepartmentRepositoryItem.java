package fr.polytech.vgl.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;


public interface DepartmentRepositoryItem extends MongoRepository<Department, ObjectId> {

	@Query("{departmentName:'?0'}")
	Department findDepartmentByName(String departmentName);

}
