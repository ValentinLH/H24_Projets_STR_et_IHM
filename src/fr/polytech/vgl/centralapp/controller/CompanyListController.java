package fr.polytech.vgl.centralapp.controller;

import java.util.ArrayList;
import java.util.List;

import fr.polytech.vgl.centralapp.view.CentralApplicationView;
import fr.polytech.vgl.centralapp.view.GiveCompanyView;
import fr.polytech.vgl.dao.DAO;
import fr.polytech.vgl.dao.service.CompanyService;
import fr.polytech.vgl.model.Company;

public class CompanyListController {

	/**
	 * La vue de la premi�re fen�tre
	 */
	private GiveCompanyView view;

	/**
	 * Une liste d'entreprise
	 */
	private List<Company> listCompany;

	/**
	 * Constructeur de la classe
	 */
	public CompanyListController() {
		listCompany = new ArrayList<>();
		view = new GiveCompanyView(this);
		// addCompany()
		// view.
	}

	/**
	 * Permet de r�cup�rer la liste des entreprise
	 * 
	 * @return la liste des entreprises
	 */
	public List<Company> getListCompany() {
		return listCompany;
	}

	/**
	 * Permet d'instancier la liste des entreprises
	 * 
	 * @param listCompany La liste des entreprises
	 */
	public void setListCompany(List<Company> listCompany) {
		this.listCompany = listCompany;
	}

	/**
	 * Permet d'ajouer un entreprise
	 * 
	 * @param une entreprise
	 */
	public void addCompany(Company company) {
		// view.comboBox.addCompany(company);
		// view.comboBox_1.addCompany(company);
		// view.addEmployee(company.getListEmp().get(0));

		/*
		 * for (Company com : company.getListEmp()) { view.addEmployee(emp); }
		 */
		if (listCompany.contains(company) == false) {
			view.addCompany(company);
			listCompany.add(company);
		}

	}

	public void selected(Company company) {
		// TODO Auto-generated method stub
		CentralAppController centralController = new CentralAppController(company);
	}
	
	public synchronized void closeWindow() {

		CompanyService cs = DAO.getCompanyService();
	
		if (listCompany.isEmpty() == false) {
			for (Company Comp : listCompany) {
				cs.saveCompany(Comp);
			}
		}
	}
}
