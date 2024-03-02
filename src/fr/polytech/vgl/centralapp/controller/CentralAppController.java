package fr.polytech.vgl.centralapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.polytech.vgl.centralapp.view.GiveCompanyView;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Record;
import fr.polytech.vgl.network.TCPClient;
import fr.polytech.vgl.network.TCPInfo;
import fr.polytech.vgl.network.TCPServer;
import fr.polytech.vgl.serialisation.Serialisation;


public class CentralAppController {

	public static TCPServer server = new TCPServer(8081);
	public static TCPClient client = new TCPClient("localhost", 8080);
	Company company;
	public static Thread tClient,tServer;
	
	
	public CentralAppController()
	{
		company = GiveCompanyView.c;
		
		tServer = TCPOpeningServer();
		tClient = TCPOpeningClient();
	}
	
	public String setIp(String ip) {
		
		String patternString = "(Localhost)|(^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$)";
		Pattern pattern = Pattern.compile(patternString);
		Matcher m = pattern.matcher(ip);
		// si le motif est trouv�
		if (m.find()) {
			int port = client.getPort();
			
			try {
				client.closeClient();
				client = new TCPClient(ip, port);
				TCPOpeningClient();
			} catch (Exception exc) {
				// nothing
				return "" + client.getIp();
			}
			return ip;
		} else {
			return client.getIp();
		}

	}

	public String setPort(String port) {
		// System.out.println("HEY port:" + port);
		String patternString = "^([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])$";
		Pattern pattern = Pattern.compile(patternString);
		Matcher m = pattern.matcher(port);
		// si le motif est trouv�
		if (m.find()) {
			// System.out.println("motif trouv�");
			String ip = client.getIp();

			if (TCPInfo.available(ip, Integer.parseInt(port)) == true) {
				
				try {

					client.closeClient();

				} catch (Exception exc) {
					// nothing
					// return "" + server.getPort();

				}
				client = new TCPClient(ip, Integer.parseInt(port));

				System.out.println(client.getAddress().toString());
				TCPOpeningClient();
			} else {
				return "" + client.getPort();
			}

			return port;
		} else {
			return "" + client.getPort();
		}
	}

	public String setMyPort(String port) {
		// System.out.println("HEY port:" + port);
		String patternString = "^([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])$";
		Pattern pattern = Pattern.compile(patternString);
		Matcher m = pattern.matcher(port);
		// si le motif est trouv�
		if (m.find()) {
			// System.out.println("motif trouv�");
			String ip = server.getIp();

			if (TCPInfo.available(ip, Integer.parseInt(port)) == true) {
				try {
					server.closeServer();

				} catch (Exception exc) {
					// nothing
					// return "" + server.getPort();

				}
				server = new TCPServer(Integer.parseInt(port));
				TCPOpeningServer();
			} else {

				return "" + server.getPort();
			}

			return port;
		} else {
			return "" + server.getPort();
		}
	}
	
	
	public Thread TCPOpeningServer(final Object obj) {
		Thread t = new Thread(new Runnable() {
			public void run() {

				try {
					server.setServerConnection(obj);
					//while(server.sendObject(obj) == false);
					
				} catch (Exception exc) {
					//
				}

			}
		});
		t.start();
		return t;
	}
	
	public Thread TCPOpeningServer() {
		Thread t = new Thread(new Runnable() {
			public void run() {

				try {
					server.setServerConnection();
					//while(server.sendObject(obj) == false);
					
				} catch (Exception exc) {
					//
				}

			}
		});
		t.start();
		return t;
	}

	public Thread TCPOpeningClient() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while (!Thread.currentThread().isInterrupted())
				{
					try {

						while (true) {

							// while ()
							client.setSocketConnection();
							Object obj= client.getInputStream().readObject();
							
							 
							System.out.println("Client TimeRecord> Object Receive ");
							if (obj != null) {
								// System.out.println(obj.getClass().getName());
								if (obj.getClass().getName().equals("fr.polytech.vgl.model.Record") == true) {
									Record rec = (Record) client.getInputStream().readObject();
									
									//ajouter le rec
									
									System.out.println("Client> Central app Record Receive " + rec);
									// return "Company : " + c.getCompanyName() + " added";
								} else if (obj.getClass().getName().equals("java.util.ArrayList") == true) {
									try {
										@SuppressWarnings("unchecked")
										ArrayList<Record> obj2 = (ArrayList<Record>) obj;
									
										
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
						
							client.closeClient();

						}

					}

					catch (Exception exc) {
						//System.out.println("Client>  Closed");
						//exc.printStackTrace();
					}
				}
				
			}

		});
		t.start();
		return t;
	}
	
	
	public String getMyIp() {
		return server.getIp();
	}

	public int getMyPort() {
		return server.getPort();
	}
	
	public int getPort() {
		return client.getPort();
	}
	
	public String getIp() {
		return client.getIp();
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
		server.closeServer();
		client.closeClient();
		
		tServer.interrupt();
		tClient.interrupt();
	}
	
}
