package fr.polytech.vgl.centralapp.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;

public class ModelOfCheckPanel extends AbstractTableModel {

	/**
	 * Nom de l'entreprise choisie par l'utilisateur
	 */
	private Company company = GiveCompanyView.c;

	/*
	 * Une liste d'employee
	 */
	private List<Employee> listemployee = new ArrayList<Employee>();

	String[] entetes = { "Employee", /* "Department", "ID","Date", "Hour" */ "Record" };

	/**
	 * Cosntructeur de la classe
	 * 
	 * @param Liste d'employee
	 */
	public ModelOfCheckPanel(List<Employee> listemp) {
		super();
		this.listemployee = listemp;
	}

	/**
	 * Permet d'avoir le nombre de colonne
	 */
	public int getColumnCount() {
		return entetes.length;
	}

	/**
	 * Permet d'avoir le nombre de lignes. On utilise pour ça une double boucle for
	 * pour avoir la nombre de pointage pour chaque employé Donc avoir finalement le
	 * nombre total de pointage
	 */
	public int getRowCount() {
		/*
		 * int compteur = 0;
		 * 
		 * for (int i = 0; i < company.getListEmp().size(); i++) {
		 * 
		 * for (int j = 0; j < listemployee.get(i).getRecords().size(); j++) {
		 * compteur++; } }
		 * 
		 * return compteur;
		 */
		//System.out.print(company.AllRecord().size() + " ");
		return company.AllRecord().size();
	}

	/**
	 * Permet de créer le tableau de tous les pointages et de stocker les valeurs au
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
			return company.AllRecord().get(row).getEmployee();
		case 1:
			return company.AllRecord().get(row).getRecord();

		default:
			return null;
		}

	}

	
	/**
	 * ça a été un test non concluant pour stocker les bonnes valeurs mais la
	 * méthode étant déjà codé spécifiquement, cela ne marchait pas
	 * 
	 * @param lignes
	 * @param colonne
	 * @return l'object à mettre dans la bonne case
	 */
	public Object getValueAtTest(int row, int col) {

		// Object tableau[][] = new Object[][2];

		/* for (int i = 0; i < listemployee.size(); i++) */ {
			for (Employee emp : listemployee) {
				/* for (Record rec : getListRecord(employee.get(i))) */
				for (Record rec : getListRecord(emp)) {

					switch (col) {
					case 0:
						return emp;

					case 1:
						// return employee.get(i).getRecords().get(row).getRecord();
						return rec.getRecord();

					default:
						return null;
					}
				}
			}
		}
		return null;

	}

	/**
	 * ça a été un test non concluant pour stocker les bonnes valeurs mais la
	 * méthode étant déjà codé spécifiquement, cela ne marchait partiellement
	 * 
	 * @param lignes
	 * @param colonne
	 * @return l'object à mettre dans la bonne case
	 */
	public Object getValueAtTest2(int row, int col) {

		switch (col) {
		case 0:
			return listemployee.get(row % listemployee.size());
		case 1:
			return listemployee.get(row % listemployee.size()).getRecords()
					.get(row % listemployee.get(row % listemployee.size()).getRecords().size()).getRecord();

		default:
			return null;
		}

	}

	/**
	 * Permet de récupérer le nom des colonnes
	 */
	public String getColumnName(int col) {
		return this.entetes[col];
	}

	/**
	 * Permet de préciser si les cellules sont modifiables
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	/**
	 * Méthode permtant de récupérer une liste de pointage rapidement d'un employé
	 * 
	 * @param employee
	 * @return la liste des pointage de cet employé
	 */
	public List<Record> getListRecord(Employee employee) {
		List<Record> records = employee.getRecords();
		return records;
	}
}
