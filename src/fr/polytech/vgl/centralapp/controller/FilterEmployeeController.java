package fr.polytech.vgl.centralapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fr.polytech.vgl.centralapp.view.ModelOfEmployeeTable;


public class FilterEmployeeController  implements ActionListener {

	public FilterEmployeeController(){
		super();
	}
	/**
	 * Classe qui devait permetre de trier les employés. Fonctionnait très bien avant la mise dans les onglets...
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
	
		/*String regex = JOptionPane.showInputDialog("Filter : ");
		 
		TableRowSorter<TableModel> sorter = new ModelOfEmployeeTable().sorter;
        sorter.setRowFilter(RowFilter.regexFilter(regex, 0, 1));*/
		
		
	}
	
}
