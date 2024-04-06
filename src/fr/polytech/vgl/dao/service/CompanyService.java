package fr.polytech.vgl.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.polytech.vgl.dao.repository.*;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;    

    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveCompany(Company company) {
    	companyRepository.save(company);
    	for (Employee emp : company.getListEmp())
    		employeeRepository.save(emp);
    	for (Department dep : company.getListDpt())
    		departmentRepository.save(dep);
    }
//    public User getUser(String id) {
//        return repository.findById(id).orElse(null);
//    }
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
//    public void deleteCompany(Company c) {
//        repository.delete(c);
//    }
}
