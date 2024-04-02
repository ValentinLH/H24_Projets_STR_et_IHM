package fr.polytech.vgl.dao;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.polytech.vgl.model.Schedule;

public interface ScheduleRepository extends MongoRepository<Schedule, ObjectId>  {

}
