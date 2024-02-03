package fr.polytech.vgl.timerecord.controller;

import java.io.File;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;
import fr.polytech.vgl.network.TCPClient;
import fr.polytech.vgl.network.TCPServer;
import fr.polytech.vgl.network.TCPInfo;
import fr.polytech.vgl.serialisation.Serialisation;
import fr.polytech.vgl.timerecord.view.TimeRecordMainFrame;

/**
 *  Main Controller Class of the TimeRecorder
 * @author Touret Lino - L'Hermite Valentin
 *
 *
 */

public class TimeRecordControler {

	private TimeRecordMainFrame view;
	private List<Company> listCompany;
	private List<Record> recordsBuffer;

	public static TCPServer server = new TCPServer(8080);
	public static TCPClient client = new TCPClient("localhost", 8081);
	public static Thread tClient, tServer;

	private File file;

	private Map<Employee, LocalDateTime> antiSpam;

	/**
	 * TimeRecordControler()
	 * @return A proper generated TimeRecordControler
	 * @param void
	 */
	public TimeRecordControler() {
		listCompany = new ArrayList<>();
		view = new TimeRecordMainFrame(this);
		file = null;
		antiSpam = new HashMap<>();

		try {
			List<Company> deSerialize = Serialisation.deserialize("timerecord.sav");
			// listCompany = deSerialize;

			for (Company newcomp : deSerialize) {
				addCompany(newcomp);
			}
		} catch (Exception e) {
			listCompany = new ArrayList<>();
		}

		recordsBuffer = new ArrayList<>();
		try {
			List<Record> deSerializeRec = Serialisation.deserialize("records.sav");
			// listCompany = deSerialize;
			for (Record rec : deSerializeRec) {
				recordsBuffer.add(rec);
			}

		} catch (Exception e) {
			recordsBuffer = new ArrayList<>();
		}

		// addCompany()
		// view.
		tServer = TCPOpeningServer();
		tClient = TCPOpeningClient();

		sendRecordBuffer();

	}

	/**
	 * getListCompany
	 * @return listCompany
	 */
	public List<Company> getListCompany() {
		return listCompany;
	}
	
	/**
	 * setListCompany
	 * @param listCompany
	 */
	public void setListCompany(List<Company> listCompany) {
		this.listCompany = listCompany;
	}

	/**
	 * addCompany
	 * @param company
	 */
	public void addCompany(Company company) {
		// view.comboBox.addCompany(company);
		// view.comboBox_1.addCompany(company);
		// view.addEmployee(company.getListEmp().get(0));
		boolean contain = false;
		for (Company com : listCompany) {
			if (com.getCompanyName().equals(company.getCompanyName()) == true) {
				contain = true;
			}

		}

		if (contain == false) {
			for (Employee emp : company.getListEmp()) {
				view.addEmployee(emp);
			}
			view.addCompany(company);
			listCompany.add(company);
		}

	}

	/**
	 * delCompany
	 * @param company
	 * @return false if it was not deleted else true 
	 */
	public boolean delCompany(Company company) {
		if (listCompany.contains(company) == true) {
			for (Employee emp : company.getListEmp()) {
				view.delEmployee(emp);
			}
			view.delCompany(company);
			listCompany.remove(company);
		} else {
			return false;
		}

		return true;
	}

	/**
	 * sendRecord send the record to the central application
	 * @param employee
	 * @return state of the sending
	 */
	public int sendRecord(Employee employee) {

		// view.comboBox.getSelectedItem().toString();

		// Anti Spamming
		if (antiSpam.containsKey(employee) == true) {
			if (antiSpam.get(employee).compareTo(LocalDateTime.now()) >= 0) {
				return -1;
			} else {
				antiSpam.remove(employee);
			}
		}

		Record newRecord = new Record(LocalDateTime.now(), employee);

		antiSpam.put(employee, LocalDateTime.now().plusMinutes(Record.getRounded()));
		
		if(recordsBuffer.contains(newRecord) ==false)
		{
			recordsBuffer.add(newRecord);
		}
		sendRecordBuffer();
		
		if (recordsBuffer.isEmpty() == true) {
			return 1;
		} else {
			return 0;
		}
		
		/*
		if (server.sendObject(newRecord) == true) {

			
			return 1;
		} else {
			return 0;
		}
		*/
		// System.out.println(newRecord);

	}

	/**
	 * setIp
	 * @param ip
	 * @return
	 */
	public String setIp(String ip) {
		// System.out.println("HEY ip:" + ip);
		// String patternString =
		// "^(25[0�5]|2[0�4][0�9]|[01]?[0�9][0�9]?).(25[0�5]|2[0�4][0�9]|[01]?[0�9][0�9]?).(25[0�5]|2[0�4][0�9]|[01]?[0�9][0�9]?).(25[0�5]|2[0�4][0�9]|[01]?[0�9][0�9]?)$";
		/*
		 * String patternString =
		 * "(Localhost)| ^([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
		 * "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
		 * "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\." +
		 * "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])$";
		 */
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
				return client.getIp();
			}
			return ip;
		} else {
			return client.getIp();
		}

	}
	
/**
 * setPort
 * @param port
 * @return state of the port
 */
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
	/**
	 * setMyPort
	 * @param port
	 * @return myPort
	 */

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

	/**
	 * sendRecordTest 
	 * @param employee
	 * @param date
	 * @return state of the sending
	 */
	public int sendRecordTest(Employee employee, LocalDateTime date) {
		Record newRecord = new Record(date, employee);
		// view.comboBox.getSelectedItem().toString();
		
		if(recordsBuffer.contains(newRecord) ==false)
		{
			recordsBuffer.add(newRecord);
		}
		sendRecordBuffer();
		
		if (recordsBuffer.isEmpty() == true) {
			return 1;
		} else {
			return 0;
		}
		
		// view.comboBox.
		//System.out.println(newRecord);
	}

	/**
	 * getFile
	 * @return file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * setFile
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * deserialiseCompany
	 * @return state of the serialisation
	 */
	@SuppressWarnings("unchecked")
	public String deserialiseCompany() {
		if (file != null) {
			Object obj = Serialisation.deserialize(file.getAbsolutePath());
			if (obj != null) {
				// System.out.println(obj.getClass().getName());
				if (obj.getClass().getName().equals("fr.polytech.vgl.model.Company") == true) {
					Company c = (Company) obj;
					addCompany(c);
					return "Company : " + c.getCompanyName() + " added";
				} else if (obj.getClass().getName().equals("java.util.ArrayList") == true) {
					try {
						ArrayList<Company> obj2 = (ArrayList<Company>) obj;
						List<Company> listc = obj2;
						for (Company comp : listc) {
							addCompany(comp);
						}
						return "Companies has been insered";
					} catch (Exception exc) {
						return "No company found in the file";
					}
				}

			} else {
				return "No company found in the file";
			}
		}

		return "File not found";
	}

	/**
	 * TCPOpeningServer open the server
	 * @return thread
	 */
	public Thread TCPOpeningServer() {
		Thread t = new Thread(new Runnable() {
			public void run() {

				try {
					server.setServerConnection();
				} catch (Exception exc) {
					//
				}

			}
		});
		t.start();
		return t;
	}

	/**
	 * TCPOpeningClient open the client
	 * @return thread
	 */
	public Thread TCPOpeningClient() {
		Thread t = new Thread(new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					try {

						while (true) {

							// while ()
							client.setSocketConnection();

							Object obj = client.getInputStream().readObject();

							System.out.println("Client TimeRecord> Object Receive ");
							if (obj != null) {
								// System.out.println(obj.getClass().getName());
								if (obj.getClass().getName().equals("fr.polytech.vgl.model.Company") == true) {
									Company c = (Company) obj;
									addCompany(c);

									System.out.println("Client TimeRecord> Company added ");
									// return "Company : " + c.getCompanyName() + " added";
								} else if (obj.getClass().getName().equals("java.util.ArrayList") == true) {
									try {
										ArrayList<Company> obj2 = (ArrayList<Company>) obj;
										List<Company> listc = obj2;
										for (Company comp : listc) {
											addCompany(comp);
										}
										// return "Companies has been insered";

										System.out.println("Companies has been insered");
									} catch (Exception exc) {
										// return "No company found in the file";
									}
								}

							}

							client.closeClient();

						}

					}

					catch (Exception exc) {
						// System.out.println("Client> Closed");
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

	/**
	 * sendRecordBuffer send the Records of the buffer 
	 */
	public void sendRecordBuffer() {

		if (server.sendObject(recordsBuffer) == false) {
			System.out.println("Record Not Sended : " + recordsBuffer);
		} else {
			System.out.println("Record  Sended : " + recordsBuffer);
			recordsBuffer.clear();
		}
	}

	/**
	 * closeWindow close the window properly
	 */
	public void closeWindow() {

		sendRecordBuffer();
		if (recordsBuffer.isEmpty() == false) {
			// System.out.println("Hey "+recordsBuffer.get(0));
			Serialisation.serialize(recordsBuffer, "records.sav");
		}

		Serialisation.serialize(listCompany, "timerecord.sav");
		server.closeServer();
		client.closeClient();
		tServer.interrupt();
		tClient.interrupt();

	}
}
