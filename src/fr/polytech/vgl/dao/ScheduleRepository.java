package fr.polytech.vgl.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.polytech.vgl.model.Schedule;


public interface ScheduleRepository extends MongoRepository<Schedule, ObjectId>  {

	//Read : 
	@Query("{'id' : ?0}")
	Schedule findScheduleById(ObjectId id);
	
	List<Schedule> findAll();
	
	//Delete : 
	 void deleteAll();
	 
	 @Query(value = "{'id' : ?0}")
	 void deleteScheduleyById(ObjectId id);
	 
	 
	 
}
