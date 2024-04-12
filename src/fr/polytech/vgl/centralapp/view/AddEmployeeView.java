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
	 * Cr�ation des diff�rents textes et labels
	 */
	private JLabel nameLabel = new JLabel("Name : ");
	private JTextField name = new JTextField();
	private JLabel surnameLabel = new JLabel("Surname : ");
	private JTextField surname = new JTextField();

	/*
	 * Cr�ation du menu d�roulant des d�partements
	 */
	private JLabel departmentLabel = new JLabel("Department : ");
	private JComboBox<Department> department;
	DefaultComboBoxModel<Department> modelDepartment = new DefaultComboBoxModel<Department>();

	/*
	 * Bouton de confirmation
	 */
	private JButton ValidationAddEmployee = new JButton("OK");

	/**
	 * Constructeur de la classe. Permet de cr�er une fen�tre qui comporte les
	 * diff�rents �lements On peut renseigner le Nom le pr�nom et le d�partement de
	 * ce nouvel employ� Il y a �galement un bouton permettant de valider toutes les
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
	 * Permet d'avoir le nom de l'employ�
	 * 
	 * @return le nom de l'employ�
	 */
	public JTextField getNameEmployee() {
		return name;
	}

	/**
	 * Permet d'avoir le pr�nom de l'employ�
	 * 
	 * @return le pr�nom de l'employ�
	 */
	public JTextField getSurname() {
		return surname;
	}

	/**
	 * Permet d'avoir le d�partement selectionn�
	 * 
	 * @return le le d�partement selectionn�
	 */
	public Department getDepartment() {
		return (Department) department.getSelectedItem();
	}

	/**
	 * Permet d'avoir le talbeau comportant tous les employ�s
	 * 
	 * @return le talbeau comportant tous les employ�s
	 */
	public JTable getEmployeeTable() {
		return table;
	}

	/**
	 * Permet d'avoir les d�partements du menu d�roulant
	 * 
	 * @return le d�partement
	 */
	public JComboBox<Department> getComboBox() {
		return department;
	}

	/**
	 * Permet d'ajouter un d�partment
	 * 
	 * @param Un departement
	 */
	public void addDepartment(Department dpt) {
		department.addItem(dpt);
	}

}
