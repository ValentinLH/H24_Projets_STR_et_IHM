package fr.polytech.vgl.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.polytech.vgl.model.Company;


public interface CompanyRepositoryItem extends MongoRepository<Company, ObjectId> {

//	void delete(Company c);

//     @Query("{companyName: '?#{#companyName2}'}")
//     Company findByCompanyName(String companyName2);
//    
//	  @Query("{ 'id' : ?#{#id} }")
//     Company findByCompanyId(ObjectId id);
    
//    List<Company> findBylistEmp(ObjectId employeeId);
//    
//    List<Company> findBylistDpt(ObjectId departmentId);
   
//    public long count();
	
//	List<Company> findAll();
}
