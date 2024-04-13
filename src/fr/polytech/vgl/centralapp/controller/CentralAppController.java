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
	private CompanyService companyService = DAO.getCompanyService();
	
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

		CompanyService cs = DAO.getCompanyService();
	
		if (GiveCompanyView.getlistCompany().isEmpty() == false) {
			for (Company Comp : GiveCompanyView.getlistCompany()) {	
				cs.saveCompany(Comp);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void onObjectReceived(Object receivedObject) {

		System.out.println("Client Central app> Object Receive ");

		if (receivedObject == null)
			return;
	
		ArrayList<Record> records = new ArrayList<>();

		if (receivedObject instanceof Record) {
			records.add((Record) receivedObject);

		} else if (receivedObject instanceof ArrayList) {
			try {
				records.addAll((ArrayList<Record>) receivedObject);

			} catch (Exception exc) {

			}

		} else if (receivedObject instanceof CopyOnWriteArrayList) {
			try {
				records.addAll((CopyOnWriteArrayList<Record>) receivedObject);
//			
			} catch (Exception exc) {
				// return "No company found in the file";
			}
		}

		System.out.println("Client Central app> Records receive :" + records);

//		List<Employee> listEmp = cs.getAllEmployee();
		Employee emp;
		for (Record rec : records) {
			emp = rec.getEmployee();
			
			System.out.println(rec.getEmployee());
			
			
			if( emp.getCompany().equals(company))
			{
				company.addRecord(rec);
				companyService.saveCompany(company);
			}
			else
			{
				emp.addRecord(rec);
				companyService.saveEmployee(emp);
			}
		}
		
		System.out.println("Client Central app> Records added");
	}

	public synchronized CentralApplicationView getView() {
		return view;
	}

	public synchronized void setView(CentralApplicationView view) {
		this.view = view;
	}

}
