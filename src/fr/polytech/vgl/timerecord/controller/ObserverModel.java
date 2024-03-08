package fr.polytech.vgl.timerecord.controller;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;
import fr.polytech.vgl.model.Department;

public interface ObserverModel {
	void onEmployeeReceived(Employee receivedEmployee);
	void onDepartementReceived(Department receivedDepartment);
	void onCompanyReceived(Company receivedCompany);
	void onRecordReceived(Record receivedRecord);
}
