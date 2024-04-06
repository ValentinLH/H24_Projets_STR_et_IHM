package fr.polytech.vgl.dao.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.UpdateResult;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;

@Component
public class UpdateRepositoryImpl implements UpdateRepository {

	
	@Autowired
	MongoTemplate mongoTemplate;
	
	
	@Override
	public void updateEmployeeInCompany(Employee employee) {
        Query query = new Query(Criteria.where("employee").is(employee));
        Update update = new Update();
        update.set("employee", employee);
        
        UpdateResult result = mongoTemplate.updateFirst(query, update, Company.class);
        
        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");
		
	}

	@Override
	public void updateAllEmployeeInCompany(List<Employee> employeeList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCompany(Company company) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDepartmentInCompany(Department department) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRecordInCompany(Record record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAllRecordsInCompany(List<Record> recordList) {
		// TODO Auto-generated method stub
		
	}

}
