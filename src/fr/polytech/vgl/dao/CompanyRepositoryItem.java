package fr.polytech.vgl.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;


public interface CompanyRepositoryItem extends MongoRepository<Company, ObjectId> {

	//Read
	 @Query("{companyName: ?0}")
	 Company findByCompanyName(String companyName2);
	
	 @Query("{'id' : ?0}")
	 Company findByCompanyId(ObjectId id);
	 
	 @Query(value = "{'listEmp' : ?0}")
	 List<Company> findCompaniesByEmployee(@Param("employee") Employee employee);
	 
	 @Query(value = "{'listDpt' : ?0}")
	 List<Company> findCompaniesByDepartment(@Param("department") Department departemnt);
	 
	 List<Company> findAll();
       
	 public long count();
}
