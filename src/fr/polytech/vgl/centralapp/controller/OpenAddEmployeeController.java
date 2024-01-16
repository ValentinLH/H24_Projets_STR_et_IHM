package fr.polytech.vgl.centralapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTable;

import fr.polytech.vgl.centralapp.view.AddEmployeeView;
import fr.polytech.vgl.centralapp.view.GiveCompanyView;
import fr.polytech.vgl.model.Department;

public class OpenAddEmployeeController implements ActionListener {

	private JTable table;

	/**
	 * Constructeur de la classe
	 * 
	 * @param tableau
	 */
	public OpenAddEmployeeController(JTable table) {
		this.table = table;
	}

	/**
	 * Permet d'ouvrir la fenêtre pour ajouter un employée. Permet également de
	 * créer le menu déroulant des départements
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		AddEmployeeView view = new AddEmployeeView(table);
		List<Department> listDpt = GiveCompanyView.c.getListDpt();

		for (Department D : listDpt) {
			view.addDepartment(D);
		}
		/*
		 * for (int i=0; i < listDpt.size(); i++) { view.addDepartment(listDpt.get(i));
		 * }
		 */

	}

}
