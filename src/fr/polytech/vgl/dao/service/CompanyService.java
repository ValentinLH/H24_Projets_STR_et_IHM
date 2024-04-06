package fr.polytech.vgl.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.polytech.vgl.dao.CompanyRepositoryItem;
import fr.polytech.vgl.model.Company;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepositoryItem repository;

    public void saveCompany(Company company) {
        repository.save(company);
    }
//    public User getUser(String id) {
//        return repository.findById(id).orElse(null);
//    }
    public List<Company> getAllCompanies(){
        return repository.findAll();
    }
//    public void deleteCompany(Company c) {
//        repository.delete(c);
//    }
}
