package fr.polytech.vgl.misc;

import fr.polytech.vgl.model.Company;

import javax.realtime.*;


public interface ModelListener {
	void update(Company receivedCompany);
			
	default void asyncNotify(Company receivedCompany) {		
		
		try {
			RealtimeThread rt2 = new RealtimeThread() {
				@Override
				public void run() {
					update(receivedCompany);
				}
			};
			rt2.run();
		}catch(Exception e) {
			System.out.println(e);
		};
	};
}
