package fr.polytech.vgl.centralapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;

import fr.polytech.vgl.centralapp.view.AddEmployeeView;
import fr.polytech.vgl.centralapp.view.GiveCompanyView;
import fr.polytech.vgl.centralapp.view.ModelOfEmployeeTable;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.serialisation.Serialisation;

public class ValidationAddEmployeeController implements ActionListener{

	private JFrame addEmployeeFrame;
	private JTable table;
	
	/**
	 * Constructeur de la classe
	 * @param Prend une fenêtre/view en paramètre
	 */
	public ValidationAddEmployeeController(AddEmployeeView frame)
	{
		this.addEmployeeFrame = frame;
		this.table = ((AddEmployeeView)addEmployeeFrame).getEmployeeTable();
		
		//Pour serialisation
		try {
			@SuppressWarnings("unchecked")
			List<Employee> deSerialize = (List<Employee>) Serialisation.DeSerialize("timerecord.sav");
			// listCompany = deSerialize;

			for (Employee newemp : deSerialize) {
				GiveCompanyView.c.addEmployee(newemp);
			}
		} catch (Exception e) {
			System.out.println("Heys");
		}
	}
	
	/**
	 * Permet d'ajouter un employé dans le tableau et de le sérialiser
	 */
	public void actionPerformed(ActionEvent event)
	{
		String nameEmp = ((AddEmployeeView)addEmployeeFrame).getNameEmployee().getText();
		String surnameEmp = ((AddEmployeeView)addEmployeeFrame).getSurname().getText();
		Department departmentEmp = ((AddEmployeeView)addEmployeeFrame).getDepartment();
		
		//En attendant d'avoir bien réglé le problème des entreprises
		Company c1 = GiveCompanyView.c;
		
		((ModelOfEmployeeTable)table.getModel()).addRow(new Employee(nameEmp, surnameEmp,c1, departmentEmp));
	}
	
	/*public void closeWindow()
	{

		Serialisation.SerializeListCompany(listCompany, "Employee.sav");
	}*/
	
}
