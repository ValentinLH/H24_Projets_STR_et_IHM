package fr.polytech.vgl.centralapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTable;

import fr.polytech.vgl.centralapp.view.CheckOfOneEmployeeView;
import fr.polytech.vgl.centralapp.view.ModelOfOneEmployeeCheck;
import fr.polytech.vgl.model.Employee;

public class SeeCheckEmployeeController implements ActionListener {

	private JTable table;
	private JPanel panel;
	private Employee emp;
	/*
	 * Création de la fenêtre comportant le panel
	 */
	private ModelOfOneEmployeeCheck frame = new ModelOfOneEmployeeCheck();
	private JTable tableau = new JTable(frame);

	/**
	 * Constructeur de la classe
	 * 
	 * @param tableau
	 */
	public SeeCheckEmployeeController(JTable table) {

		this.table = table;
		// this.table = new JTable (EmployeePanel);
	}

	/**
	 * Permet de voir les pointages d'un employee. Il faut tout d'abord sélectionner
	 * la ligne eet ensuite le controller emmène faire la vue correspondante
	 */
	public void actionPerformed(ActionEvent event) {
		int selected = table.getSelectedRow();
		if (selected >= 0) {
			new Thread(new Runnable() {
				public void run() {
					int selected = table.getSelectedRow();
					int id = (int) table.getValueAt(selected, 2);

					emp = Employee.getById(id);
					// System.out.print(emp.getRecords());
					// panel = EmployeeCheck(emp);
					CheckOfOneEmployeeView view = new CheckOfOneEmployeeView(emp);
				}
			}).start();

		}
	}

}
