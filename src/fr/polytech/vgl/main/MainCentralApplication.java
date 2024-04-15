package fr.polytech.vgl.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import fr.polytech.vgl.centralapp.controller.CompanyListController;
import fr.polytech.vgl.dao.*;
import fr.polytech.vgl.dao.repository.CompanyRepository;
import fr.polytech.vgl.dao.service.CompanyService;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.serialisation.Serialisation;

/**
 * @author Lino Touret - Valentin L'Hermite
 *
 */

public class MainCentralApplication {

	/**
	 * Main permettant de lance la premi�re fen�tre Ce main permet de cr�er
	 * l'entreprise ainsi que les employ�s La premi�re fen�tre correspond au menu
	 * d�roulant et le bouton pour confirmer
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// gv.setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					DAO.start();

					// GiveCompanyView gv = new GiveCompanyView();
					CompanyListController window = new CompanyListController();

					try {
						
						CompanyService cs = DAO.getCompanyService();

						List<Company> deSerialize = cs.getAllCompanies();
						

						for (Company newcomp : deSerialize) {
//							newcomp.setId();
							
//							cs.saveCompany(newcomp);
							window.addCompany(newcomp);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					// window.addCompany(StubMain.stubCompany());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

	}

}
