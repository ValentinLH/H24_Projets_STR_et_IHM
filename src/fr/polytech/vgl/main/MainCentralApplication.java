package fr.polytech.vgl.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.polytech.vgl.centralapp.controller.CompanyListController;
import fr.polytech.vgl.dao.service.CompanyService;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.serialisation.Serialisation;

/**
 * @author Lino Touret - Valentin L'Hermite
 *
 */

@Component
public class MainCentralApplication {

	@Autowired
	static CompanyService companyService;
	
	/**
	 * Main permettant de lance la premi�re fen�tre Ce main permet de cr�er
	 * l'entreprise ainsi que les employ�s La premi�re fen�tre correspond au menu
	 * d�roulant et le bouton pour confirmer
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		
		List<Company> l = companyService.getAllCompanies();
		l.forEach(item -> System.out.println(item.getCompanyName()));
		
		
		// gv.setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// GiveCompanyView gv = new GiveCompanyView();
					CompanyListController window = new CompanyListController();
					
					try {
						List<Company> deSerialize = Serialisation.deserialize("company.sav");
						// listCompany = deSerialize;

						for (Company newcomp : deSerialize) {
							window.addCompany(newcomp);
						}
					} catch (Exception e) {
						
					}
					
					//window.addCompany(StubMain.stubCompany());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
