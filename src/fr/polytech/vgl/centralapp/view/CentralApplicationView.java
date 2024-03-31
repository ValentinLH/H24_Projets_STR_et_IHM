package fr.polytech.vgl.centralapp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fr.polytech.vgl.centralapp.controller.CentralAppController;
import fr.polytech.vgl.centralapp.controller.DelEmployeeController;
import fr.polytech.vgl.centralapp.controller.FilterEmployeeController;
import fr.polytech.vgl.centralapp.controller.OpenAddEmployeeController;
import fr.polytech.vgl.centralapp.controller.SeeCheckEmployeeController;
import fr.polytech.vgl.misc.ModelListener;
import fr.polytech.vgl.model.Company;

public class CentralApplicationView extends JFrame implements ModelListener {

	final static Color[] colors = { Color.decode("#C8DDF2"), Color.decode("#9CB5E1"), Color.decode("#DFF5E9"),
			Color.decode("#F0D0D0") };
	JFrame jFrame = new JFrame();
	
	/*
	 * Conteneur des onglets
	 */
	JTabbedPane onglets = new JTabbedPane();

	/**
	 * Cr�ation de la fen�tre ainsi que du panel des employ�s Cela correspond au
	 * tableau de tous les employ�s avec leurs informations respectives
	 */
	private ModelOfEmployeeTable frame;
	private JTable tableau;
	public TableRowSorter<TableModel> sorter;

	/**
	 * Cr�ation de la fen�tre ainsi que du panel de tous les pointages Cela
	 * correspond au tableau aux pointages de tous les emloy�s
	 */
	private ModelOfCheckPanel frame2;
	private JTable tableau2;
	public TableRowSorter<TableModel> sorter2;

	/**
	 * Cr�ation de la fen�tre ainsi que du panel de tous les pointages du jour Cela
	 * correspond au tableau de tous les pointages du jour
	 */
	private ModelOfDayCheckPanel frame3;
	private JTable tableau3;

	/**
	 * Cr�tion du controler de centralapp
	 */
	private CentralAppController controler;

	/*
	 * Cr�ation des boutons du premier onglets
	 */
	private JButton CheckEmployee;
	private JButton AddAction;
	private JButton RemoveAction;
	private JButton FilterAction;
	/*
	 * Ent�tes des colonnes du premier onglet
	 */
	String[] entetes = { "Surname", "Name", "ID", "Company", "Department", "Presence" };

	/**
	 * Application principale Elle comporte tous les onglets ainsi que l'affichage
	 * des panels Elle affiche �galement la fen�tre principale
	 */
	public CentralApplicationView(CentralAppController controler) { // on lui passe le modele qui nous interesse

		super();
		setTitle("CENTRAL APPLICATION");
		
		this.controler = controler;
		jFrame.setLayout(null);
		jFrame.setResizable(true);
		
		// parenthese initialisation des tableau et onglets
		frame = new ModelOfEmployeeTable(controler.getCompany());
		tableau = new JTable(frame);
		sorter = new TableRowSorter<TableModel>(tableau.getModel());
		
		frame2 = new ModelOfCheckPanel(controler.getCompany());
		tableau2 = new JTable(frame2);
		sorter2 = new TableRowSorter<TableModel>(tableau2.getModel());

		frame3 = new ModelOfDayCheckPanel(controler.getCompany());
		tableau3 = new JTable(frame3);		
		
		
		
		// de retour sur la création de la fenetre
		
		jFrame.setMinimumSize(new Dimension(500, 500));
		jFrame.setSize(1000, 700);
		jFrame.setVisible(true);

		jFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.out.println("Hey");
				controler.closeWindow();
			}
		});

	
		// D�finir la position de conteneur d'onglets
		onglets.setBounds(10, 10, 960, 640);

		// Ajouter les onglets au frame
		jFrame.getContentPane().add(onglets);

		// buttonPanel.add(emp);

		// contenu premier onglet
		JPanel emp = EmployeeTablePanel();
		onglets.addTab("Employee", null, emp, null);

		addSettingsTab();
		// addFileTab();

		// contenu second onglet
		JPanel adc = AllDaysCheckPanel();
		onglets.addTab("All Check", null, adc, null);

		// contenu premier onglet
		JPanel dcp = DaysCheckPanel();
		onglets.addTab("Day's Check", null, dcp, null);

		
		
	}

	/**
	 * Permet d'afficher dans un panel, un tableau de tous les employ�s ainsi que
	 * leurs informations Il y a �galement 4 boutons : Le premier pour ajouter un
	 * employ�, le second pour supprimer un employ�, le troisi�me pour filtrer les
	 * employ�s, et le denier pour afficher les pointages d'un employ� en
	 * particulier Tous ces boutons am�nent � leurs controlers respectis Il y a
	 * �galement quelques lignes qui permettent de trier le tableau
	 * 
	 * @return le panel avec toutes les informations des employ�s ainsi que les 4
	 *         boutons
	 */
	public JPanel EmployeeTablePanel() {
		JPanel emp = new JPanel();
		emp.setLayout(new BorderLayout());

		emp.add(new JScrollPane(tableau), BorderLayout.CENTER);

		AddAction = new JButton("Add Employee");
		// AddAction.setBounds(100, 100, 120, 20);

		RemoveAction = new JButton("Remove Employee");
		// RemoveAction.setBounds(180, 100, 120, 20);

		FilterAction = new JButton("Filter");
		// FilterAction.setBounds(340, 100, 120, 20);

		CheckEmployee = new JButton("Check of the Employee");

		// Cette partie permet de placer les 3 boutons ensemble en bas (SOUTH)
		JPanel boutons = new JPanel();

		boutons.add(AddAction);
		boutons.add(RemoveAction);
		boutons.add(FilterAction);
		boutons.add(CheckEmployee);
		emp.add(boutons, BorderLayout.SOUTH);

		AddAction.addActionListener(new OpenAddEmployeeController(tableau));
		RemoveAction.addActionListener(new DelEmployeeController(tableau));
		CheckEmployee.addActionListener(new SeeCheckEmployeeController(tableau));

		// trier tableau
		tableau.setAutoCreateRowSorter(true);
		tableau.setRowSorter(sorter);
		sorter.setSortsOnUpdates(true);

		FilterAction.addActionListener(new FilterEmployeeController());

		return emp;
	}

	/**
	 * Permet d'afficher un panel avec tous les pointages enregistr�s depuis le
	 * d�part Il appelle juste la vue concern� et renvoit le panel
	 * 
	 * @return Le panel de tous les pointages
	 */
	public JPanel AllDaysCheckPanel() {
		JPanel adc = new JPanel();

		adc.setLayout(new BorderLayout());

		adc.add(new JScrollPane(tableau2), BorderLayout.CENTER);

		// trier tableau
		/*
		 * tableau.setAutoCreateRowSorter(true); tableau.setRowSorter(sorter2);
		 * sorter.setSortsOnUpdates(true);
		 */

		/*
		 * tableau.setRowSorter(sorter2); java.util.List<RowSorter.SortKey> sortList =
		 * new ArrayList<>(2); sortList.add(new RowSorter.SortKey(1,
		 * SortOrder.ASCENDING)); sortList.add(new RowSorter.SortKey(0,
		 * SortOrder.ASCENDING)); sorter2.setSortKeys(sortList);
		 */

		return adc;
	}

	/**
	 * Permet d'afficher un panel avec tous les pointages du jour Il appelle juste
	 * la vue concern� et renvoit le panel
	 * 
	 * @return Le panel des pointages du jour
	 */
	public JPanel DaysCheckPanel() {
		JPanel dcp = new JPanel();

		dcp.setLayout(new BorderLayout());

		dcp.add(new JScrollPane(tableau3), BorderLayout.CENTER);

		// trier tableau
		/*
		 * tableau.setAutoCreateRowSorter(true); tableau.setRowSorter(sorter2);
		 * sorter.setSortsOnUpdates(true);
		 */

		/*
		 * tableau.setRowSorter(sorter2); java.util.List<RowSorter.SortKey> sortList =
		 * new ArrayList<>(2); sortList.add(new RowSorter.SortKey(1,
		 * SortOrder.ASCENDING)); sortList.add(new RowSorter.SortKey(0,
		 * SortOrder.ASCENDING)); sorter2.setSortKeys(sortList);
		 */

		return dcp;
	}

	/**
	 * Permet de r�cup�rer la taille de la colonne
	 * 
	 * @return la taille de la colonne
	 */
	public int getColumnCount() {
		return entetes.length;
	}

	/**
	 * permet de r�cup�rer le nom des colonnes
	 * 
	 * @param columnIndex
	 * @return le nom de chaque colonne
	 */
	public String getColumnName(int columnIndex) {
		return entetes[columnIndex];
	}

	/**
	 * Permet de r�cup�rer le tableau des inforamtions des employ�s
	 * 
	 * @return le tableau des employ�s
	 */
	public JTable getEmployeeTable() {
		return tableau;
	}

	private void addSettingsTab() {
		final JPanel settingsPanel = new JPanel();
		settingsPanel.setBackground(colors[0]);
		onglets.addTab("Settings", null, settingsPanel, null);
		GridBagLayout gbl_settingsPanel = new GridBagLayout();
		gbl_settingsPanel.columnWidths = new int[] { 50, 120, 140, 110, 0, 0 };
		gbl_settingsPanel.rowHeights = new int[] { 40, 60, 40, 40, 50, 0, 0 };
		gbl_settingsPanel.columnWeights = new double[] { 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_settingsPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		settingsPanel.setLayout(gbl_settingsPanel);

		JButton btnSetIp = new JButton("Set Ip");
		btnSetIp.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));

		final JLabel settingLabel = new JLabel("settingLabel");
		settingLabel.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 17));
		GridBagConstraints gbc_settingLabel = new GridBagConstraints();
		gbc_settingLabel.gridwidth = 3;
		gbc_settingLabel.insets = new Insets(0, 0, 5, 5);
		gbc_settingLabel.gridx = 1;
		gbc_settingLabel.gridy = 0;
		settingsPanel.add(settingLabel, gbc_settingLabel);

		JLabel lblNewLabel = new JLabel("IP Address");
		lblNewLabel.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		settingsPanel.add(lblNewLabel, gbc_lblNewLabel);

		final JFormattedTextField textIp = new JFormattedTextField("Localhost");
		GridBagConstraints gbc_textIp = new GridBagConstraints();
		gbc_textIp.insets = new Insets(0, 0, 5, 5);
		gbc_textIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_textIp.gridx = 2;
		gbc_textIp.gridy = 1;
		settingsPanel.add(textIp, gbc_textIp);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 1;
		settingsPanel.add(btnSetIp, gbc_btnNewButton);

		JLabel lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblPort = new GridBagConstraints();
		gbc_lblPort.insets = new Insets(0, 0, 5, 5);
		gbc_lblPort.gridx = 1;
		gbc_lblPort.gridy = 2;
		settingsPanel.add(lblPort, gbc_lblPort);

		final JFormattedTextField textPort = new JFormattedTextField("" + controler.getPort());
		textPort.setFont(new Font("Verdana Pro", Font.PLAIN, 13));
		GridBagConstraints gbc_formattedTextField_1 = new GridBagConstraints();
		gbc_formattedTextField_1.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_1.gridx = 2;
		gbc_formattedTextField_1.gridy = 2;
		settingsPanel.add(textPort, gbc_formattedTextField_1);

		JButton btnSetPort = new JButton("Set Port");
		btnSetPort.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSetPort = new GridBagConstraints();
		gbc_btnSetPort.insets = new Insets(0, 0, 5, 5);
		gbc_btnSetPort.gridx = 3;
		gbc_btnSetPort.gridy = 2;
		settingsPanel.add(btnSetPort, gbc_btnSetPort);

		JLabel lblMyIpAddress = new JLabel("My IP Address :");
		lblMyIpAddress.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMyIpAddress = new GridBagConstraints();
		gbc_lblMyIpAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblMyIpAddress.gridx = 1;
		gbc_lblMyIpAddress.gridy = 3;
		settingsPanel.add(lblMyIpAddress, gbc_lblMyIpAddress);

		JLabel lblIp = new JLabel(controler.getMyIp());
		lblIp.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 2;
		gbc_lblIp.gridy = 3;
		settingsPanel.add(lblIp, gbc_lblIp);

		JLabel lblMyPort = new JLabel("My Port :");
		lblMyPort.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 20));
		GridBagConstraints gbc_lblMyPorts = new GridBagConstraints();
		gbc_lblMyPorts.insets = new Insets(0, 0, 5, 5);
		gbc_lblMyPorts.gridx = 1;
		gbc_lblMyPorts.gridy = 4;
		settingsPanel.add(lblMyPort, gbc_lblMyPorts);

		final JFormattedTextField textMyPort = new JFormattedTextField("" + controler.getMyPort());
		textMyPort.setFont(new Font("Verdana Pro", Font.PLAIN, 13));
		GridBagConstraints gbc_formattedTextField_1_1 = new GridBagConstraints();
		gbc_formattedTextField_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextField_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextField_1_1.gridx = 2;
		gbc_formattedTextField_1_1.gridy = 4;
		settingsPanel.add(textMyPort, gbc_formattedTextField_1_1);

		JButton btnSetMyPort = new JButton("Set My Port");
		btnSetMyPort.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
		GridBagConstraints gbc_btnSetPort_1 = new GridBagConstraints();
		gbc_btnSetPort_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnSetPort_1.gridx = 3;
		gbc_btnSetPort_1.gridy = 4;
		settingsPanel.add(btnSetMyPort, gbc_btnSetPort_1);

		btnSetIp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println();
				String text = textIp.getText();
				String ret = controler.setIp(text);
				if (text.equals(ret)) {
					settingLabel.setText("IP selected");
					settingsPanel.setBackground(colors[2]);
				} else {
					textIp.setText(ret);
					settingLabel.setText("Error IP");
					settingsPanel.setBackground(colors[3]);
				}

			}
		});

		btnSetPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String port = textPort.getText();
				String ret = controler.setPort(port);
				if (port.equals(ret)) {
					settingLabel.setText("Port selected");
					settingsPanel.setBackground(colors[2]);
				} else {
					textPort.setText(ret);
					settingLabel.setText("Error Port");
					settingsPanel.setBackground(colors[3]);
				}
			}
		});

		btnSetMyPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String port = textMyPort.getText();
				String ret = controler.setMyPort(port);
				if (port.equals(ret)) {
					settingLabel.setText("Port selected");
					settingsPanel.setBackground(colors[2]);
				} else {
					textMyPort.setText(ret);
					settingLabel.setText("Error Port");
					settingsPanel.setBackground(colors[3]);
				}
			}
		});
	}

	/*
	 * private void addFileTab() { final JPanel filePanel = new JPanel();
	 * filePanel.setBackground(colors[0]); onglets.addTab("File", null, filePanel,
	 * null); GridBagLayout gbl_filePanel = new GridBagLayout();
	 * gbl_filePanel.columnWidths = new int[] { 50, 138, 160, 0, 0 };
	 * gbl_filePanel.rowHeights = new int[] { 40, 60, 40, 40, 50, 0, 0 };
	 * gbl_filePanel.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0,
	 * Double.MIN_VALUE }; gbl_filePanel.rowWeights = new double[] { 0.0, 1.0, 0.0,
	 * 0.0, 0.0, 1.0, Double.MIN_VALUE }; filePanel.setLayout(gbl_filePanel);
	 * 
	 * JLabel lblNewLabel_1 = new JLabel("File"); lblNewLabel_1.setFont(new
	 * Font("Verdana Pro Cond Light", Font.PLAIN, 20)); GridBagConstraints
	 * gbc_lblNewLabel_1 = new GridBagConstraints(); gbc_lblNewLabel_1.gridwidth =
	 * 2; gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5); gbc_lblNewLabel_1.gridx
	 * = 1; gbc_lblNewLabel_1.gridy = 0; filePanel.add(lblNewLabel_1,
	 * gbc_lblNewLabel_1);
	 * 
	 * final JTextPane textFile = new JTextPane(); textFile.setEnabled(true);
	 * textFile.setEditable(false); GridBagConstraints gbc_textIp_1 = new
	 * GridBagConstraints(); gbc_textIp_1.fill = GridBagConstraints.HORIZONTAL;
	 * gbc_textIp_1.insets = new Insets(0, 0, 5, 5); gbc_textIp_1.gridx = 1;
	 * gbc_textIp_1.gridy = 1; filePanel.add(textFile, gbc_textIp_1);
	 * 
	 * JButton btnSelectCompany = new JButton("Select a Company");
	 * btnSelectCompany.addActionListener(new ActionListener() { public void
	 * actionPerformed(ActionEvent e) { JFileChooser choose = new JFileChooser(new
	 * File("."));
	 * 
	 * choose.setDialogTitle("TimeRecord> Select a Company Serialized File");
	 * choose.setAcceptAllFileFilterUsed(true); FileNameExtensionFilter filter = new
	 * FileNameExtensionFilter(".sav .ser .dat", "sav", "ser", "dat");
	 * choose.addChoosableFileFilter(filter); int res = choose.showOpenDialog(null);
	 * if (res == JFileChooser.APPROVE_OPTION) {
	 * System.out.println(choose.getSelectedFile().getPath());
	 * controler.setFile(choose.getSelectedFile());
	 * textFile.setText(choose.getSelectedFile().getName()); } } });
	 * btnSelectCompany.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
	 * GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
	 * gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5); gbc_btnNewButton_2.gridx
	 * = 2; gbc_btnNewButton_2.gridy = 1; filePanel.add(btnSelectCompany,
	 * gbc_btnNewButton_2);
	 * 
	 * JButton btnAddCompany = new JButton("Add Company");
	 * 
	 * btnAddCompany.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
	 * GridBagConstraints gbc_btnAddCompany = new GridBagConstraints();
	 * gbc_btnAddCompany.insets = new Insets(0, 0, 5, 5); gbc_btnAddCompany.gridx =
	 * 2; gbc_btnAddCompany.gridy = 2; filePanel.add(btnAddCompany,
	 * gbc_btnAddCompany);
	 * 
	 * DefaultComboBoxModel<Company> modelCompany = new
	 * DefaultComboBoxModel<Company>(); final JComboBox<Company> comboBox_2 = new
	 * JComboBox<Company>(modelCompany); GridBagConstraints gbc_comboBox_2 = new
	 * GridBagConstraints(); gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
	 * gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL; gbc_comboBox_2.gridx =
	 * 1; gbc_comboBox_2.gridy = 3; filePanel.add(comboBox_2, gbc_comboBox_2);
	 * 
	 * JButton btnDelCompany = new JButton("Delete Company");
	 * btnDelCompany.setFont(new Font("Verdana Pro Cond Light", Font.PLAIN, 16));
	 * 
	 * GridBagConstraints gbc_btnDelCompany = new GridBagConstraints();
	 * gbc_btnDelCompany.insets = new Insets(0, 0, 5, 5); gbc_btnDelCompany.gridx =
	 * 2; gbc_btnDelCompany.gridy = 3; filePanel.add(btnDelCompany,
	 * gbc_btnDelCompany);
	 * 
	 * final JLabel fileLabel = new JLabel(""); fileLabel.setFont(new
	 * Font("Verdana Pro Cond Light", Font.PLAIN, 17)); GridBagConstraints
	 * gbc_fileLabel = new GridBagConstraints(); gbc_fileLabel.gridwidth = 2;
	 * gbc_fileLabel.insets = new Insets(0, 0, 5, 5); gbc_fileLabel.gridx = 1;
	 * gbc_fileLabel.gridy = 4; filePanel.add(fileLabel, gbc_fileLabel);
	 * 
	 * btnAddCompany.addActionListener(new ActionListener() { public void
	 * actionPerformed(ActionEvent e) { String a = controler.deserialiseCompany();
	 * if (a.equals("No company found in the file")) {
	 * filePanel.setBackground(colors[3]); fileLabel.setText(a); } else if
	 * (a.equals("File not found")) { fileLabel.setText(a);
	 * filePanel.setBackground(colors[3]); } else { fileLabel.setText(a);
	 * filePanel.setBackground(colors[2]); } } });
	 * 
	 * btnDelCompany.addActionListener(new ActionListener() { public void
	 * actionPerformed(ActionEvent arg0) { int ret =
	 * ConfirmDel(((Company)comboBox_2.getSelectedItem()).getCompanyName()); if (ret
	 * == 0) { boolean r =
	 * controler.delCompany((Company)comboBox_2.getSelectedItem()); if (r == true) {
	 * fileLabel.setText("Company Successfully Deleted");
	 * filePanel.setBackground(colors[2]); } else {
	 * fileLabel.setText("Error During Deletion");
	 * filePanel.setBackground(colors[3]); } } else {
	 * fileLabel.setText("Company Not Deleted"); filePanel.setBackground(colors[0]);
	 * } //System.exit(0); } }); }
	 */
	static int ConfirmDel(String companyName) {
		return JOptionPane.showConfirmDialog(null, "Do you really want to delete the Company: " + companyName + " ?",
				"Confirmation", JOptionPane.YES_NO_OPTION);
	}

	@Override
	public void update(Company receivedCompany) {
		
		frame.fireTableDataChanged();
		frame2.fireTableDataChanged();
		frame3.fireTableDataChanged();
		//System.out.println("APP CENTRAL NOTIFY");
	}

}