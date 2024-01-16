package fr.polytech.vgl.centralapp.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;

public class ModelOfEmployeeTable extends AbstractTableModel {

	/**
	 * Nom de l'entreprise choisie par l'utilisateur
	 */
	private Company company = GiveCompanyView.c;

	/*
	 * Une liste d'employee
	 */
	private List<Employee> employee = new ArrayList<Employee>();

	/**
	 * Liste comprenant les noms des entreprises
	 */
	private final List<Company> companyname = new ArrayList<Company>();

	String[] entetes = { "Surname", "Name", "ID", "Company", "Department", "Presence" };

	/**
	 * Constructeur de la classe
	 */
	public ModelOfEmployeeTable() {

		super();
		employee = company.getListEmp();

	}

	/**
	 * Permet de récupérer le nombre de colonne
	 */
	public int getColumnCount() {
		return entetes.length;
	}

	/**
	 * Permet de récupérer le nombre de ligne
	 */
	public int getRowCount() {
		return company.getListEmp().size();
	}

	/**
	 * Permet de créer le tableau de tous les employés et de stocker les valeurs au
	 * bon endroit C'est une méthode spéciale de TableModel qui renvoit un objet
	 * dans la case [row][col] Elle s'arrête quand le nombre de ligne a été atteint
	 * 
	 * @param lignes
	 * @param colonne
	 * @return l'object à mettre dans la bonne case
	 */
	public Object getValueAt(int row, int col) {
		switch (col) {
		case 0:
			return company.getListEmp().get(row).getName();
		case 1:
			return company.getListEmp().get(row).getSurname();
		case 2:
			return company.getListEmp().get(row).getId();
		case 3:
			return company.getListEmp().get(row).getCompany();
		case 4:
			return company.getListEmp().get(row).getDepartement();
		default:
			return null;
		}
	}

	/**
	 * Permet de récuperer le nom des colonnes
	 */
	public String getColumnName(int col) {
		return this.entetes[col];
	}

	/**
	 * Permet de savoir si les celulles sont modifiables
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	/**
	 * Permet de supprimer une ligne
	 * 
	 * @param la position de la ligne à supprimer
	 */
	public void removeRow(int position) {
		if (position >= 0) {
			company.getListEmp().remove(position);
			this.fireTableRowsDeleted(position, position);
		}
	}

	/**
	 * Permet d'ajouter une ligne
	 * 
	 * @param L'employé à ajouter
	 */
	public void addRow(Employee newEmployee) {
		this.fireTableRowsInserted(company.getListEmp().size() - 1, company.getListEmp().size() - 1);
	}

	/**
	 * Permet de modifier une ligne. La méthode n'est pas utilisée car nous n'avaons
	 * pas eu le temps
	 * 
	 * @param selection
	 * @param name
	 * @param surname
	 * @param department
	 */
	public void modifyRow(int selection, String name, String surname, Department department) {
		company.getListEmp().get(selection).setName(name);
		company.getListEmp().get(selection).setSurname(surname);
		company.getListEmp().get(selection).setDepartement(department);
		this.fireTableDataChanged();
	}

	/**
	 * Permet d'instancier les attributs
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (aValue != null) {
			Employee people = employee.get(rowIndex);

			switch (columnIndex) {
			case 0:
				people.setSurname((String) aValue);
				break;
			case 1:
				people.setName((String) aValue);
				break;
			case 2:
				people.setCompany((Company) aValue);
				break;
			case 3:
				people.setDepartement((Department) aValue);
				break;
			}
		}
	}

	@Override
	public Class getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 2:
			return Company.class;
		/*
		 * case 3: return Boolean.class; case 4 : return Sport.class;
		 */
		default:
			return Object.class;
		}
	}

}
