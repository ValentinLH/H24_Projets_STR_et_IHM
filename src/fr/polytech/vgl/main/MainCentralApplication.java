package fr.polytech.vgl.main;

import java.awt.EventQueue;

import fr.polytech.vgl.centralapp.controller.CompanyListController;

/**
 * @author Lino Touret - Valentin L'Hermite
 *
 */

public class MainCentralApplication {

	/**
	 * Main permattant de lance la premi�re fen�tre Ce main permet de cr�er
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
					// GiveCompanyView gv = new GiveCompanyView();
					CompanyListController window = new CompanyListController();
					window.addCompany(CentralAppMain.stubCompany());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
