package fr.polytech.vgl.main;

import java.awt.EventQueue;

import fr.polytech.vgl.timerecord.controller.TimeRecordControler;


/**
* @author Lino Touret - Valentin L'Hermite
*
*/
public class TimeRecordMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TimeRecordMainFrame tr = new TimeRecordMainFrame();
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeRecordControler window = new TimeRecordControler();
					//window.addCompany(CentralAppMain.stubCompany());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	
		
	}

	
	
}
