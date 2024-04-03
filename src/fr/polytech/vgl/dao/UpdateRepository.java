package fr.polytech.vgl.dao;

import java.util.List;

import fr.polytech.vgl.model.*;
import fr.polytech.vgl.model.Record;

public interface UpdateRepository {

	public void updateEmployeeInCompany(Employee employee);
	
	public void updateAllEmployeeInCompany(List<Employee> employeeList);
	
	public void updateCompany(Company company);
	
	public void updateDepartmentInCompany(Department department);
	
	public void updateRecordInCompany(Record record);
	
	public void updateAllRecordsInCompany(List<Record> recordList);
}
