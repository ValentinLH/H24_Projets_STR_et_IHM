package fr.polytech.vgl.centralapp.view;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import fr.polytech.vgl.centralapp.controller.ValidationAddEmployeeController;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;

public class AddEmployeeView extends JFrame {

	private JTable table;

	/*
	 * Création des différents textes et labels
	 */
	private JLabel nameLabel = new JLabel("Name : ");
	private JTextField name = new JTextField();
	private JLabel surnameLabel = new JLabel("Surname : ");
	private JTextField surname = new JTextField();

	/*
	 * Création du menu déroulant des départements
	 */
	private JLabel departmentLabel = new JLabel("Department : ");
	private JComboBox<Department> department;
	DefaultComboBoxModel<Department> modelDepartment = new DefaultComboBoxModel<Department>();

	/*
	 * Bouton de confirmation
	 */
	private JButton ValidationAddEmployee = new JButton("OK");

	/**
	 * Constructeur de la classe. Permet de créer une fenêtre qui comporte les
	 * différents élements On peut renseigner le Nom le prénom et le département de
	 * ce nouvel employé Il y a également un bouton permettant de valider toutes les
	 * informations et d'envoyer ensuite au bon controler
	 * 
	 * @param table
	 */
	public AddEmployeeView(JTable table) {
		this.table = table;

		this.setTitle("Add an employee");
		this.setSize(600, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(null);

		// name
		this.add(nameLabel);
		this.add(name);
		nameLabel.setBounds(200, 70, 200, 25);
		name.setBounds(200, 100, 200, 25);

		// surname
		this.add(surnameLabel);
		this.add(surname);
		surnameLabel.setBounds(200, 170, 200, 25);
		surname.setBounds(200, 200, 200, 25);

		// department
		department = new JComboBox<Department>(modelDepartment);

		// GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		this.add(departmentLabel);
		this.add(department);
		departmentLabel.setBounds(200, 270, 200, 25);
		department.setBounds(200, 300, 200, 25);

		// ok button
		ValidationAddEmployee.addActionListener(new ValidationAddEmployeeController(this));
		this.add(ValidationAddEmployee);
		ValidationAddEmployee.setBounds(250, 380, 100, 30);

		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Permet d'avoir le nom de l'employé
	 * 
	 * @return le nom de l'employé
	 */
	public JTextField getNameEmployee() {
		return name;
	}

	/**
	 * Permet d'avoir le prénom de l'employé
	 * 
	 * @return le prénom de l'employé
	 */
	public JTextField getSurname() {
		return surname;
	}

	/**
	 * Permet d'avoir le département selectionné
	 * 
	 * @return le le département selectionné
	 */
	public Department getDepartment() {
		return (Department) department.getSelectedItem();
	}

	/**
	 * Permet d'avoir le talbeau comportant tous les employés
	 * 
	 * @return le talbeau comportant tous les employés
	 */
	public JTable getEmployeeTable() {
		return table;
	}

	/**
	 * Permet d'avoir les départements du menu déroulant
	 * 
	 * @return le département
	 */
	public JComboBox<Department> getComboBox() {
		return department;
	}

	/**
	 * Permet d'ajouter un départment
	 * 
	 * @param Un departement
	 */
	public void addDepartment(Department dpt) {
		department.addItem(dpt);
	}

}
