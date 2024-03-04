package fr.polytech.vgl.centralapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.polytech.vgl.centralapp.view.GiveCompanyView;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Record;
import fr.polytech.vgl.network.NetworkManager;
import fr.polytech.vgl.network.NetworkObserver;
import fr.polytech.vgl.network.TCPClient;
import fr.polytech.vgl.network.TCPInfo;
import fr.polytech.vgl.network.TCPServer;
import fr.polytech.vgl.serialisation.Serialisation;


public class CentralAppController implements NetworkObserver {


	Company company;
	private NetworkManager networkManager;

	
	public CentralAppController()
	{
		networkManager = new NetworkManager(8081,"localhost", 8080,this);
		
		company = GiveCompanyView.c;
		

	}
	
	public String setIp(String ip) {
		
		String patternString = "(Localhost)|(^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$)";
		Pattern pattern = Pattern.compile(patternString);
		Matcher m = pattern.matcher(ip);
		// si le motif est trouv�
		if (m.find()) {
			int port = networkManager.getClientPort();
			
			try {
				networkManager.setClientIp(ip);
			
			} catch (Exception exc) {
				// nothing
				
			}
		} 
		return networkManager.getClientIp();
		

	}
	
	public String setPort(String port) {
		// System.out.println("HEY port:" + port);
		String patternString = "^([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])$";
		Pattern pattern = Pattern.compile(patternString);
		Matcher m = pattern.matcher(port);
		// si le motif est trouv�
		if (m.find()) {
			// System.out.println("motif trouv�");
			String ip = networkManager.getClientIp();

			if (TCPInfo.available(ip, Integer.parseInt(port)) == true) {

				networkManager.setClientPort( Integer.parseInt(port));
				
			} 
		}
		return "" +networkManager.getClientPort();
		
	}

	public String setMyPort(String port) {
		// System.out.println("HEY port:" + port);
		String patternString = "^([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])$";
		Pattern pattern = Pattern.compile(patternString);
		Matcher m = pattern.matcher(port);
		// si le motif est trouv�
		if (m.find()) {
			// System.out.println("motif trouv�");
			String ip = networkManager.getServerIp();

			if (TCPInfo.available(ip, Integer.parseInt(port)) == true) {
				networkManager.setServerPort(Integer.parseInt(port));
				
			}
		}
		return "" + networkManager.getServerPort();
		
	}
	
	
	
	public String getMyIp() {
		return networkManager.getServerIp();
	}

	public int getMyPort() {
		return networkManager.getServerPort();
	}

	public int getPort() {
		return networkManager.getClientPort();
	}

	public String getIp() {
		return networkManager.getClientIp();
	}
	
	public void closeWindow()
	{
		//sendRecordBuffer();
	
		if (GiveCompanyView.getlistCompany().isEmpty() == false) {
			// System.out.println("Hey "+recordsBuffer.get(0));
			List<Company> listC = new ArrayList<>();  
			for (Company Comp : GiveCompanyView.getlistCompany())
			{
				if (listC.contains(Comp) == false)
				{
					listC.add(Comp);
				}
			}
			Serialisation.serialize(listC, "centralAppCompanies.sav");
		}
		
	}

	@Override
	public void onObjectReceived(Object receivedObject) {
		// TODO Auto-generated method stub
		System.out.println("Client TimeRecord> Object Receive ");
		if (receivedObject != null) {
			// System.out.println(obj.getClass().getName());
			if (receivedObject.getClass().getName().equals("fr.polytech.vgl.model.Record") == true) {
				Record rec = (Record) receivedObject;
				
				//ajouter le rec
				
				System.out.println("Client> Central app Record Receive " + rec);
				// return "Company : " + c.getCompanyName() + " added";
			} else if (receivedObject.getClass().getName().equals("java.util.ArrayList") == true) {
				try {
					@SuppressWarnings("unchecked")
					ArrayList<Record> obj2 = (ArrayList<Record>) receivedObject;
				
					
					for (Record rec : obj2)
					{
						if (rec.getEmployee().getCompany().equals(company) == true )
						{
							if ( company.getListEmp().contains(rec.getEmployee()) == true )
							{
								company.addRecord(rec);
								System.out.println("CA> Record  Added");
							}
							else
							{
								company.addEmployee(rec.getEmployee());
								company.addRecord(rec);
								System.out.println("CA> Record and Employee  Added");
							}
						}
						else
						{
							if (GiveCompanyView.getlistCompany().contains(rec.getEmployee().getCompany()) ==false ) {
								GiveCompanyView.comboBox.addItem(rec.getEmployee().getCompany());
							}
							else {
								int i = GiveCompanyView.getlistCompany().indexOf(rec.getEmployee().getCompany());
								GiveCompanyView.getlistCompany().get(i).addRecord(rec);
							}
							
							System.out.println("CA> Company Added");
						}
					}
					
					System.out.println("Client> Central app Record Receive " + obj2);
				} catch (Exception exc) {
					// return "No company found in the file";
				}
			}

		}
	}
	
}
