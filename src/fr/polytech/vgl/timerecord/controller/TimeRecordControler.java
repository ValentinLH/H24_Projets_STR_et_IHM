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
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;
import fr.polytech.vgl.network.NetworkManager;
import fr.polytech.vgl.network.NetworkObserver;
import fr.polytech.vgl.network.TCPClient;
import fr.polytech.vgl.network.TCPServer;
import fr.polytech.vgl.network.TCPInfo;
import fr.polytech.vgl.serialisation.Serialisation;
import fr.polytech.vgl.timerecord.view.TimeRecordMainFrame;
import fr.polytech.vgl.timerecord.controller.ObserverModel;
import fr.polytech.vgl.timerecord.controller.ModelManager;

/**
 * Main Controller Class of the TimeRecorder
 * 
 * @author Touret Lino - L'Hermite Valentin
 * @version VLH 06/03/24
 *
 */

public class TimeRecordControler implements NetworkObserver {

	private TimeRecordMainFrame view;
	private List<Company> listCompany;
	private List<Record> recordsBuffer;

	private NetworkManager networkManager;
	private File file;

	private Map<Employee, LocalDateTime> antiSpam;
	
	private ModelManager Mm;
	/**
	 * TimeRecordControler()
	 * 
	 * @return A proper generated TimeRecordControler
	 * @param void
	 */
	public TimeRecordControler() {
		networkManager = new NetworkManager(this);

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
		
		Mm = new ModelManager();
		addModelObservers(view);

		sendRecordBuffer();

	}

	/**
	 * getListCompany
	 * 
	 * @return listCompany
	 */
	public List<Company> getListCompany() {
		return listCompany;
	}

	/**
	 * setListCompany
	 * 
	 * @param listCompany
	 */
	public void setListCompany(List<Company> listCompany) {
		
		for(Company comp : listCompany){
			comp.setModelManager(Mm);
		}
		
		this.listCompany = listCompany;
	}

	/**
	 * addCompany
	 * 
	 * @param company
	 */
	public void addCompany(Company company) {
		// view.comboBox.addCompany(company);
		// view.comboBox_1.addCompany(company);
		// view.addEmployee(company.getListEmp().get(0));
		boolean contain = false;
		
		company.setModelManager(Mm);
		
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
	 * 
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
	 * 
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

		if (recordsBuffer.contains(newRecord) == false) {
			recordsBuffer.add(newRecord);
		}
		sendRecordBuffer();

		if (recordsBuffer.isEmpty() == true) {
			return 1;
		} else {
			return 0;
		}

	}

	/**
	 * setIp
	 * 
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
		networkManager.setClientIp(ip);
		return networkManager.getClientIp();
	}

	/**
	 * setPort
	 * 
	 * @param port
	 * @return state of the port
	 */
	public String setPort(String port) {
		networkManager.setClientPort(port);
		return "" + networkManager.getClientPort();

	}

	/**
	 * setMyPort
	 * 
	 * @param port
	 * @return myPort
	 */

	public String setMyPort(String port) {
		networkManager.setServerPort(port);
		return "" + networkManager.getServerPort();

	}

	/**
	 * sendRecordTest
	 * 
	 * @param employee
	 * @param date
	 * @return state of the sending
	 */
	public int sendRecordTest(Employee employee, LocalDateTime date) {
		Record newRecord = new Record(date, employee);
		// view.comboBox.getSelectedItem().toString();

		if (recordsBuffer.contains(newRecord) == false) {
			recordsBuffer.add(newRecord);
		}
		sendRecordBuffer();

		if (recordsBuffer.isEmpty() == true) {
			return 1;
		} else {
			return 0;
		}

		// view.comboBox.
		// System.out.println(newRecord);
	}

	/**
	 * getFile
	 * 
	 * @return file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * setFile
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * deserialiseCompany
	 * 
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

	/**
	 * sendRecordBuffer send the Records of the buffer
	 */
	public void sendRecordBuffer() {

		if (networkManager.sendObject(recordsBuffer)) {
			System.out.println("Record  Sended : " + recordsBuffer);
			recordsBuffer.clear();
		} else {
			System.out.println("Record Not Sended : " + recordsBuffer);
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
	}

	@Override
	public void onObjectReceived(Object receivedObject) {
		// TODO Auto-generated method stub
		System.out.println("Client TimeRecord> Object Receive ");
		if (receivedObject != null) {
			// System.out.println(obj.getClass().getName());
			if (receivedObject.getClass().getName().equals("fr.polytech.vgl.model.Company") == true) {
				Company c = (Company) receivedObject;
				addCompany(c);

				System.out.println("Client TimeRecord> Company added ");
				// return "Company : " + c.getCompanyName() + " added";
			} else if (receivedObject.getClass().getName().equals("java.util.ArrayList") == true) {
				try {
					ArrayList<Company> obj2 = (ArrayList<Company>) receivedObject;
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

	}
	
	
	public void addModelObservers(ObserverModel om) {
		Mm.addModelObservers(om);
	}
	
	public void removeModelObservers(ObserverModel om) {
		Mm.removeModelObservers(om);
	}
	
}
