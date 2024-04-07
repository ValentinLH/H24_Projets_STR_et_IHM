package fr.polytech.vgl.centralapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.polytech.vgl.centralapp.view.CentralApplicationView;
import fr.polytech.vgl.centralapp.view.GiveCompanyView;
import fr.polytech.vgl.dao.DAO;
import fr.polytech.vgl.dao.repository.CompanyRepository;
import fr.polytech.vgl.dao.repository.EmployeeRepository;
import fr.polytech.vgl.dao.service.CompanyService;
import fr.polytech.vgl.main.MainCentralApplication;
import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;
import fr.polytech.vgl.network.NetworkManager;
import fr.polytech.vgl.network.NetworkObserver;
import fr.polytech.vgl.realtime.BufferedMemory;
import fr.polytech.vgl.serialisation.Serialisation;

/**
 * CentralAppController is the main controller of the Central Application
 * 
 * @author Touret Lino - L'Hermite Valentin
 * @version VLH 09/03/24
 */

public class CentralAppController implements NetworkObserver {

	private Company company;
	private CentralApplicationView view;
	private NetworkManager networkManager;

	public CentralAppController(Company company) {
		// Création d'un gestionnaire de réseau avec les ports et l'observateur
		// spécifiés
		// Pour simplifier, on suppose ici une approche à thread unique
		// (single-threaded)
		this.networkManager = new NetworkManager(8081, "localhost", 8080, this);

		// Initialisation de l'objet Company associé à ce contrôleur
		this.company = company;

		// Création de la vue associée à ce contrôleur
		this.setView(new CentralApplicationView(this));

		this.company.addModelObservers(view);

	}

	public synchronized Company getCompany() {
		return company;
	}

	public synchronized void setCompany(Company company) {
		company.addModelObservers(view);
		this.company = company;
	}

	public synchronized String setIp(String ip) {
		networkManager.setClientIp(ip);
		return networkManager.getClientIp();

	}

	public synchronized String setPort(String port) {
		networkManager.setClientPort(port);
		return "" + networkManager.getClientPort();

	}

	public synchronized String setMyPort(String port) {
		networkManager.setServerPort(port);
		return "" + networkManager.getServerPort();

	}

	public synchronized String getMyIp() {
		return networkManager.getServerIp();
	}

	public synchronized int getMyPort() {
		return networkManager.getServerPort();
	}

	public synchronized int getPort() {
		return networkManager.getClientPort();
	}

	public synchronized String getIp() {
		return networkManager.getClientIp();
	}

	public synchronized void closeWindow() {
		// sendRecordBuffer();

		if (GiveCompanyView.getlistCompany().isEmpty() == false) {
			// System.out.println("Hey "+recordsBuffer.get(0));
			List<Company> listC = new ArrayList<>();
			for (Company Comp : GiveCompanyView.getlistCompany()) {
				if (listC.contains(Comp) == false) {
					// Comp.addModelObservers(this.getView());
					listC.add(Comp);
				}
			}
			Serialisation.serialize(listC, "centralAppCompanies.sav");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void onObjectReceived(Object receivedObject) {

		System.out.println("Client Central app> Object Receive ");
		
		if (receivedObject == null)
			return;

		CompanyService cs = DAO.getCompanyService();

//		List<Company> l = cc.findAll();
//		l.forEach(item -> System.out.println(item.getCompanyName()));
//		System.out.println(l.size());

		ArrayList<Record> records = new ArrayList<>();

//		BufferedMemory<Record> bufferedMemory = new BufferedMemory(10, 5, () -> new Record(null));

	

		// System.out.println(obj.getClass().getName());
		if (receivedObject instanceof Record) {
			records.add((Record) receivedObject);

		} else if (receivedObject instanceof ArrayList) {
			try {
				records.addAll((ArrayList<Record>) receivedObject);
//				ArrayList<Record> records = ;

			} catch (Exception exc) {
				// return "No company found in the file";
			}

		} else if (receivedObject instanceof CopyOnWriteArrayList) {
			try {
				records.addAll((CopyOnWriteArrayList<Record>) receivedObject);
//				ArrayList<Record> records = ;

			} catch (Exception exc) {
				// return "No company found in the file";
			}
		}

		System.out.println("Client Central app> Records receive :" + records);
		EmployeeRepository empRepo = DAO.getEmployeeRepository();
		Optional<Employee> emp;
		for (Record rec : records) {
			emp = empRepo.findById(rec.getEmployee().getId());

			if (emp.isPresent()) {
				emp.get().addRecord(rec);
				empRepo.save(emp.get());
				
				if(company.getListEmp().contains(emp.get()))
					company.addRecord(rec);
			}
		}
//			if (rec.getEmployee().getCompany().equals(company) == true) {
//				
//				
//				
//				if (company.getListEmp().contains(rec.getEmployee()) == true) {
//					company.addRecord(rec);
//				} else {
//					company.addEmployee(rec.getEmployee());
//					company.addRecord(rec);
//				}
//			} else {
//				if (GiveCompanyView.getlistCompany().contains(rec.getEmployee().getCompany()) == false) {
//					GiveCompanyView.comboBox.addItem(rec.getEmployee().getCompany());
//				} else {
//					int i = GiveCompanyView.getlistCompany().indexOf(rec.getEmployee().getCompany());
//					GiveCompanyView.getlistCompany().get(i).addRecord(rec);
//				}
//
//			}
//		}

		company.notifyAll();
		System.out.println("Client Central app> Records added");
	}

	public synchronized CentralApplicationView getView() {
		return view;
	}

	public synchronized void setView(CentralApplicationView view) {
		this.view = view;
	}

}
