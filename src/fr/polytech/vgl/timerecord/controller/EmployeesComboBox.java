package fr.polytech.vgl.timerecord.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Employee;
/*
public class EmployeesComboBox extends JComboBox {
	private List<Employee> listEmployee;
	
	
	public EmployeesComboBox() {
		super();
		setEditable(true);
		listEmployee = new ArrayList<>();
		
	}

	public EmployeesComboBox(List<Employee> listEmployee) {
		super();
		setEditable(true);
		
		this.listEmployee = new ArrayList<>();
		
		for(Employee emp : listEmployee)
		{
			if (this.listEmployee.contains(emp) ==false) {
				this.listEmployee.add(emp);
			}
		}
	
		setEmployeesOnCombo();
	}
	
		

	public List<Employee> getListEmployee() {
		return listEmployee;
	}

	public void setListEmployee(List<Employee> listEmployee) {
		this.listEmployee = listEmployee;
	}

	public void setEmployeesOnCombo()
	{	
		setModel(new DefaultComboBoxModel(listEmployee.toArray()));		
	}
	
	public void addCompany(Company company)
	{
		for(Employee emp : company.getListEmp())
		{
			if (listEmployee.contains(emp) ==false) {
				listEmployee.add(emp);
			}
		}
		setEmployeesOnCombo();
		//
	}
	
}
*/
