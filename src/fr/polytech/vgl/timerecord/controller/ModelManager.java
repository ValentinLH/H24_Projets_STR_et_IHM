package fr.polytech.vgl.timerecord.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Department;
import fr.polytech.vgl.model.Employee;
import fr.polytech.vgl.model.Record;

public class ModelManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient List<ObserverModel> modelObservers = new ArrayList<>();;
	
	public ModelManager() {}
	
	public void addModelObservers(ObserverModel om) {
		modelObservers.add(om);
	}
	
	public void removeModelObservers(ObserverModel om) {
		modelObservers.remove(om);
	}
	
	public void onNotifyEmployeeReceived(Employee receivedEmployee){
        for (ObserverModel observer : modelObservers) {
            observer.onEmployeeReceived(receivedEmployee);
        }
	}
	
	public void onNotifyDepartementReceived(Department receivedDepartment){
        for (ObserverModel observer : modelObservers) {
            observer.onDepartementReceived(receivedDepartment);
        }
	}
	
	public void onNotifyCompanyReceived(Company receivedCompany){
        for (ObserverModel observer : modelObservers) {
            observer.onCompanyReceived(receivedCompany);
        }
	}
	
	public void onNotifyRecordReceived(Record receivedRecord){
        for (ObserverModel observer : modelObservers) {
            observer.onRecordReceived(receivedRecord);
        }
	}
}
