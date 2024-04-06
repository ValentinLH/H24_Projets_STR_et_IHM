package fr.polytech.vgl.main;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import fr.polytech.vgl.centralapp.controller.CompanyListController;
import fr.polytech.vgl.dao.*;
import fr.polytech.vgl.dao.repository.CompanyRepository;
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
//					springApplicationContext = SpringApplication.run(SpringApp.class, args);
//
////					CompanyService companyService = springApplicationContext.getBean(CompanyService.class);
//
//					CompanyRepository cc = springApplicationContext.getBean(CompanyRepository.class);

					
//					List<Company> l = companyService.getAllCompanies();
//					l.forEach(item -> System.out.println(item.getCompanyName()));
//					System.out.println(l.size());
					
					
					
//					List<Company> l = cc.findAll();
//					l.forEach(item -> System.out.println(item.getCompanyName()));
//					System.out.println(l.size());

					// GiveCompanyView gv = new GiveCompanyView();
					CompanyListController window = new CompanyListController();

					try {
						
						CompanyRepository cr = DAO.getCompanyRepository();
						List<Company> deSerialize = Serialisation.deserialize("company.sav");
						// listCompany = deSerialize;

						for (Company newcomp : deSerialize) {
							newcomp.setId();
							
							cr.save(newcomp);
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
