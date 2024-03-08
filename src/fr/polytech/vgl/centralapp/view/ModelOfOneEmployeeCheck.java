package fr.polytech.vgl.centralapp.view;

import javax.swing.table.AbstractTableModel;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Employee;

public class ModelOfOneEmployeeCheck extends AbstractTableModel {

	/**
	 * Nom de l'entreprise choisie par l'utilisateur
	 */
	private Company company = GiveCompanyView.company;

	/**
	 * Employee
	 */
	private Employee employee;

	String[] entetes = { "Employee", /* "Department", "ID","Date", "Hour" */ "Record" };

	/**
	 * Constructeur de la classe avec un employee
	 * 
	 * @param emp
	 */
	public ModelOfOneEmployeeCheck(Employee emp) {
		super();
		this.employee = emp;
	}

	/**
	 * Constructeur de la classe
	 */
	public ModelOfOneEmployeeCheck() {
		super();
	}

	/**
	 * Permet d'avoir le nombre de colonnes
	 */
	public int getColumnCount() {
		return entetes.length;
	}

	/**
	 * Permet d'avoir le nombre de lignes
	 */
	public int getRowCount() {
		return employee.getRecords().size();
	}

	/**
	 * Permet de cr�er le tableau de les pointages d'un employee et de stocker les
	 * valeurs au bon endroit C'est une m�thode sp�ciale de TableModel qui renvoit
	 * un objet dans la case [row][col] Elle s'arr�te quand le nombre de ligne a �t�
	 * atteint
	 * 
	 * @param lignes
	 * @param colonne
	 * @return l'object � mettre dans la bonne case
	 */
	public Object getValueAt(int row, int col) {

		for (int i = 0; i < employee.getRecords().size(); i++) {
			switch (col) {
			case 0:
				return employee.getRecords().get(row).getEmployee();

			case 1:
				return employee.getRecords().get(row).getRecord();

			default:
				return null;
			}
		}
		return null;

	}

	/**
	 * Permet d'avoir le nom des colonnes
	 */
	public String getColumnName(int col) {
		return this.entetes[col];
	}

	/**
	 * Permet de savoir si les cellules sont modifiables
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
