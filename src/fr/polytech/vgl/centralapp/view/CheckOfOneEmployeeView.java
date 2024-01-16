package fr.polytech.vgl.centralapp.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import fr.polytech.vgl.model.Employee;

public class CheckOfOneEmployeeView extends JFrame {

	private ModelOfOneEmployeeCheck frame;
	private JTable tableau;

	String[] entetes = { "Employee", /* "Department", "ID","Date", "Hour" */ "Record" };

	private Employee employee;

	/**
	 * Crée une nouvelle fenêtre affichant tous les pointages d'un employés Exécute
	 * tout ça grâce à la vue ModelOfOneEmployeeCheck
	 * 
	 * @param Un employé
	 */
	public CheckOfOneEmployeeView(Employee emp) {
		this.employee = emp;

		frame = new ModelOfOneEmployeeCheck(emp);
		tableau = new JTable(frame);

		this.setTitle("All Check of " + employee.getName() + " " + employee.getSurname() + " : ");
		this.setSize(600, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		// table.setVisible(true);
		// this.getContentPane().add(table);
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(tableau), BorderLayout.CENTER);

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setVisible(true);
	}

}
