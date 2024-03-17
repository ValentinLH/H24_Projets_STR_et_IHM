package fr.polytech.vgl.timerecord.controller;

import fr.polytech.vgl.model.Company;

import javax.realtime.*;


public interface ObserverModel {
	void Update(Company receivedCompany);
			
	default void AsyncNotify(Company receivedCompany) {		
		
		try {
			RealtimeThread rt2 = new RealtimeThread() {
				@Override
				public void run() {
					Update(receivedCompany);
				}
			};
			rt2.run();
		}catch(Exception e) {
			System.out.println(e);
		};
	};
}
