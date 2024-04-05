package fr.polytech.vgl.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.client.result.UpdateResult;

import fr.polytech.vgl.model.Company;

@Component
public class UpdateRepositoryImpl implements UpdateRepository {
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void updateCompany(Company company) {
		Query query = new Query(Criteria.where("id").is(company.getId()));
		Update update = new Update();
        update.set("companyName", company.getCompanyName());
        update.set("listDpt", company.getListDpt());
        update.set("listEmp", company.getListEmp());
        update.set("listRec", company.getListRec());
        
        UpdateResult result = mongoTemplate.updateFirst(query, update, Company.class);
        
        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");		
	}
}
