package fr.polytech.vgl.centralapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;

import fr.polytech.vgl.centralapp.view.AddEmployeeView;
import fr.polytech.vgl.centralapp.view.GiveCompanyView;
import fr.polytech.vgl.centralapp.view.ModelOfEmployeeTable;
import fr.polytech.vgl.dao.DAO;
import fr.polytech.vgl.dao.service.CompanyService;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.serialisation.Serialisation;

public class ValidationAddEmployeeController implements ActionListener{

	private JFrame addEmployeeFrame;
	private JTable table;
	
	/**
	 * Constructeur de la classe
	 * @param Prend une fen�tre/view en param�tre
	 */
	public ValidationAddEmployeeController(AddEmployeeView frame)
	{
		CompanyService cs = DAO.getCompanyService();

		this.addEmployeeFrame = frame;
		this.table = ((AddEmployeeView)addEmployeeFrame).getEmployeeTable();
		
		//Pour serialisation
		try {
			@SuppressWarnings("unchecked")
			List<Employee> deSerialize = (List<Employee>) Serialisation.deserialize("timerecord.sav");
			// listCompany = deSerialize;

			for (Employee newemp : deSerialize) {
				GiveCompanyView.company.addEmployee(newemp);
			}
		} catch (Exception e) {
			System.out.println("Heys");
		}
	}
	
	/**
	 * Permet d'ajouter un employ� dans le tableau et de le s�rialiser
	 */
	public void actionPerformed(ActionEvent event)
	{
		CompanyService cs = DAO.getCompanyService();

		String nameEmp = ((AddEmployeeView)addEmployeeFrame).getNameEmployee().getText();
		String surnameEmp = ((AddEmployeeView)addEmployeeFrame).getSurname().getText();
		Department departmentEmp = ((AddEmployeeView)addEmployeeFrame).getDepartment();
		
		//En attendant d'avoir bien r�gl� le probl�me des entreprises
		Company c1 = GiveCompanyView.company;
		
		Employee e1 = new Employee(nameEmp, surnameEmp,c1, departmentEmp);
		
		((ModelOfEmployeeTable)table.getModel()).addRow(e1);
		
		cs.saveCompany(c1);
	}
	
	/*public void closeWindow()
	{

		Serialisation.SerializeListCompany(listCompany, "Employee.sav");
	}*/
	
}
