package fr.polytech.vgl.centralapp.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import fr.polytech.vgl.centralapp.controller.CompanyListController;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Record;
import fr.polytech.vgl.serialisation.Serialisation;

public class GiveCompanyView extends JFrame {

	JFrame jFrame = new JFrame();

	/*
	 * Bouton permettant de confirmer le choix de l'entreprise
	 */
	private JButton buttonDone = new JButton("Done");

	// private JComboBox<Company> company = new JComboBox<Company>();
	/**
	 * Menu déroulant qui va comprendre le nom des différentes entreprises
	 */
	public static JComboBox<Company> comboBox = new JComboBox<Company>();

	/**
	 * Attribut qui stockera l'entreprise choisi par l'utilisateur
	 */
	public static Company c;

	private CompanyListController controler;

	/*
	 * Permet de créer la première fenêtre comportant le menu déroulant des
	 * entreprises et un bouton pour confirmer notre choix
	 */
	public GiveCompanyView() { // on lui passe le modele qui nous interesse

		super();
		controler = new CompanyListController();

		DefaultComboBoxModel<Company> comboboxmodel = new DefaultComboBoxModel<Company>();
		comboBox = new JComboBox<Company>(comboboxmodel);

		initialize();

		/*
		 * this.setPreferredSize(new Dimension(800,500));
		 * setTitle("Central Application");
		 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); this.pack(); <<<<<<< HEAD
		 * this.setVisible(true);
		 * 
		 * 
		 * 
		 * // Appel du controlleur = lors du clic sur le bouton
		 * //buttonSetCompany.addActionListener(new GiveCompanyNameController(this,
		 * company)); // on lui donne this = la vue ======= this.setVisible(true);
		 */

	}

	/**
	 * Permet de créer la première fenêtre comportant le menu déroulant des
	 * entreprises et un bouton pour confirmer notre choix
	 * 
	 * @param le controler
	 */
	public GiveCompanyView(CompanyListController _controler) {
		super();
		controler = _controler;
		DefaultComboBoxModel<Company> comboboxmodel = new DefaultComboBoxModel<Company>();
		comboBox = new JComboBox<Company>(comboboxmodel);

		initialize();
	}

	/**
	 * permet de récupérer le menu déroulant
	 * 
	 * @return le menu déroulant
	 */
	public JComboBox<Company> getComboBox() {
		return comboBox;
	}

	/**
	 * C'est le corps de la fenêtre Il y a la création du menu déroulant et de ses
	 * actions et la création du bouton "Done" qui permet de confirmer le choix On
	 * stocke l'entreprise choisie dans l'attribut "c"
	 * 
	 * @return l'entreprise choisie par l'utilisateur
	 */
	private Company initialize() {

		
		
		
		
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Initialisation du menu deroulant
		this.add(comboBox);
		comboBox.setBounds(80, 50, 140, 20);

		// Initialisation du bouton
		this.add(buttonDone);
		buttonDone.setBounds(100, 100, 90, 20);

		/*
		try {
			@SuppressWarnings("unchecked")
			List<Company> deSerializeCompany = (List<Company>) Serialisation.DeSerialize("centralAppCompanies.sav");
			// listCompany = deSerialize;
			for (Company comp : deSerializeCompany) {
				comboBox.addItem(comp);
			}

		} catch (Exception e) {
			
		}*/
		
		jFrame.add(buttonDone);
		jFrame.add(comboBox);

		jFrame.setLayout(null);

		jFrame.setResizable(true);
		jFrame.setMinimumSize(new Dimension(300, 200));
		jFrame.setVisible(true);

		buttonDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((Company) comboBox.getSelectedItem() != null) {
					c = (Company) comboBox.getSelectedItem();
					// Pour voir si marche bien
					// System.out.println(c);
				}
				CentralApplicationView view = new CentralApplicationView();
			}

		});

		return c;
	}

	/**
	 * Permet d'ajouter une entreprise dans un menu déroulant
	 * 
	 * @param une entreprise
	 */
	public void addCompany(Company com) {
		comboBox.addItem(com);
	}

	/**
	 * Permet d'avoir la liste des entreprises dans le menu déroulant
	 * 
	 * @return la liste des entreprises
	 */
	public static List<Company> getlistCompany() {
		List<Company> ret = new ArrayList<>();

		for (int i = 0; i < comboBox.getItemCount(); i++) {

			ret.add(comboBox.getItemAt(i));
		}

		return ret;
	}

	/*
	 * public void setButtonDone(CentralApplication view) { CentralApplication(); }
	 */
}
