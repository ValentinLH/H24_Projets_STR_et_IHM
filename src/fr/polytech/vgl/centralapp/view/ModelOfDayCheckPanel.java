package fr.polytech.vgl.centralapp.view;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;


/**
 * ModelOfDayCheckPanel is a view of a table 
 * 
 * @version VLH 09/03/24
 */
public class ModelOfDayCheckPanel extends AbstractTableModel {

	/**
	 * Nom de l'entreprise choisie par l'utilisateur
	 */
	private Company company;

	/*
	 * Une liste d'employee
	 */
	private List<Employee> listemployee = new ArrayList<Employee>();

	String[] entetes = { "Employee", /* "Department", "ID","Date", "Hour" */ "Record" };

	/**
	 * Constructeur de la classe
	 * 
	 * @param Une liste d'employ�
	 */
	public ModelOfDayCheckPanel(Company company) {
		super();
		this.company = company;
		//this.listemployee = listemp;
	}

	/**
	 * Permet d'avoir le nombre de colonne
	 */
	public int getColumnCount() {
		return entetes.length;
	}

	/*
	 * Permet d'avoir le nombre de lignes Ce nombre est comtp� � partir des
	 * pointages du jour, par employ�
	 */
	public int getRowCount() {
		/*int compteur = 0;
		for (int i = 0; i < listemployee.size(); i++) {
			compteur = compteur + getListRecordofDay(listemployee.get(i)).size();
		}
		return compteur;*/
		return company.recordsOfTheDay().size();
	}
	
	
	/**
	 * Permet de cr�er le tableau des pointages du jour et de stocker les valeurs au
	 * bon endroit C'est une m�thode sp�ciale de TableModel qui renvoit un objet
	 * dans la case [row][col] Elle s'arr�te quand le nombre de ligne a �t� atteint
	 * 
	 * @param lignes
	 * @param colonne
	 * @return l'object � mettre dans la bonne case
	 */
	public Object getValueAt(int row, int col) {
		
		switch (col) {
		case 0:
			return company.recordsOfTheDay().get(row).getEmployee();
	
		case 1:
			
			return company.recordsOfTheDay().get(row).getRecord();

		default:
			return null;
		}
		
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

	/**
	 * Permet de stocker uniquement les pointages du jour dans une nouvelle liste de
	 * pointage
	 * 
	 * @param employee
	 * @return la liste des pointages du jour de l'employ�
	 */
	public List<Record> getListRecordofDay(Employee employee) {
		List<Record> records = employee.getRecords();
		List<Record> recordsOfDay = new ArrayList<Record>();
		int compteur = 0;

		for (int i = 0; i < records.size(); i++) {
			if (records.get(i).getRecord().getDayOfYear() == LocalDateTime.now().getDayOfYear()
					&& records.get(i).getRecord().getYear() == LocalDateTime.now().getYear()) {
				recordsOfDay.add(records.get(i));

			}
		}

		return recordsOfDay;
	}
}
