package fr.polytech.vgl.main;

import java.awt.EventQueue;

import fr.polytech.vgl.timerecord.controller.TimeRecordControler;


import javafx.application.Application;
import fr.polytech.vgl.timerecord.view.TimeRecordTemp;

/**
* @author Lino Touret - Valentin L'Hermite
*
*/
public class TimeRecordMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TimeRecordMainFrame tr = new TimeRecordMainFrame();
		
		Application.launch(TimeRecordTemp.class, args);
		
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
