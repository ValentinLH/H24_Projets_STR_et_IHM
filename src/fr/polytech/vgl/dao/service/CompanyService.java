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

    //CREATE AND UPDATE : 
    
    public void saveCompany(Company company) {
    	companyRepository.save(company);
    	employeeRepository.saveAll(company.getListEmp());
    	departmentRepository.saveAll(company.getListDpt());
    }
    
    public void saveEmployee(Employee employee) {
    	employeeRepository.save(employee);
    }
    
    public void saveListEmployee(List<Employee> employeeList) {
    	employeeRepository.saveAll(employeeList);
    }
    
    public void saveDepartment(Department department) {
    	departmentRepository.save(department);
    }
    
    public void saveListDepartment(List<Department> departmentList) {
    	departmentRepository.saveAll(departmentList);
    }
    
    //READ : 
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
    
    public List<Employee> getAllEmployee(){
    	return employeeRepository.findAll();
    }
        
    //DELETE : 
        
    public void deleteCompany(Company company) {
        companyRepository.delete(company);
    }
    
    public void deleteEmployee(Employee employee) {
    	employeeRepository.delete(employee);
    }
    
    public void deleteDepartment(Department department) {
    	departmentRepository.delete(department);
    }
}
