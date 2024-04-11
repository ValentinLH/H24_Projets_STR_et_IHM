package fr.polytech.vgl.centralapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.bson.types.ObjectId;

import fr.polytech.vgl.centralapp.view.GiveCompanyView;
import fr.polytech.vgl.centralapp.view.ModelOfEmployeeTable;
import fr.polytech.vgl.dao.DAO;
import fr.polytech.vgl.dao.service.CompanyService;
import fr.polytech.vgl.model.Employee;

public class DelEmployeeController implements ActionListener {

	// private EmployeeTableModelView EmployeePanel = new EmployeeTableModelView();
	private JTable table;

	/*
	 * public DelEmployeeController(EmployeeTableModelView Panel) {
	 * this.EmployeePanel = Panel;
	 * 
	 * this.table = ((EmployeeTableModelView) EmployeePanel).getEmployeeTable();
	 * //this.table = new JTable (EmployeePanel); }
	 */
	
	/**
	 * constructeur de la classe
	 * 
	 * @param Tableau
	 */
	public DelEmployeeController(JTable table) {

		this.table = table;
		// this.table = new JTable (EmployeePanel);
	}

	/**
	 * Active le controller. Il prend en compte la ligne cliqu�e et permet ensuite
	 * de la supprimer
	 */
	public void actionPerformed(ActionEvent event) {
		int selected = table.getSelectedRow();
		if (selected >= 0) {
			int message = ConfirmDel();
			if (message == 0) {
				new Thread(new Runnable() {
					public void run() {
						int selected = table.getSelectedRow();

						((ModelOfEmployeeTable) table.getModel()).removeRow(selected);
						
						CompanyService cs = DAO.getCompanyService();
						cs.saveCompany(GiveCompanyView.company);
					}
				}).start();
			}
		}
	}

	/**
	 * Message de confirmation
	 * 
	 * @return une bo�te message avec deux chois
	 */
	static int ConfirmDel() {
		return JOptionPane.showConfirmDialog(null, "Do you really want to remove this employee ?", "Confirmation ?",
				JOptionPane.YES_NO_OPTION);
	}
}
