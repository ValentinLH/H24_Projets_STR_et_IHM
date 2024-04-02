package fr.polytech.vgl.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.polytech.vgl.model.Department;


public interface DepartmentRepositoryItem extends MongoRepository<Department, ObjectId> {

}
