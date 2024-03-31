package fr.polytech.vgl.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

import fr.polytech.vgl.misc.ModelListener;

/**
 * Company represent the company
 * 
 * @author Touret Lino - L'Hermite Valentin
 * @version VLH 09/03/24
 */

public class Company implements java.io.Serializable {

	private static final long serialVersionUID = 8140981971700902890L;

	private ObjectId id; // Utilisation de ObjectId comme type pour l'identifiant
	private String companyName;
	private List<Department> listDpt;

	private transient List<ModelListener> modelObservers;

	/**
	 * Default Constructor
	 */
	public Company() {
		companyName = "Not Defined";
		this.listDpt = new ArrayList<>();
		this.modelObservers = new ArrayList<>();

	}

	/**
	 * Comfort Constructor
	 * 
	 * @param _companyName
	 */
	public Company(String _companyName) {
		companyName = _companyName;
		this.listDpt = new ArrayList<>();
		this.modelObservers = new ArrayList<>();

	}

	/**
	 * Comfort Constructor even more comfortable
	 * 
	 * @param _companyName
	 * @param listEmp
	 * @param listRec
	 */
	public Company(String _companyName, List<Employee> listEmp) {
		super();
		companyName = _companyName;
		this.listDpt = new ArrayList<>();
		this.modelObservers = new ArrayList<>();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
		NotifyObserverModel(this);
	}

	public List<Employee> getListEmp() {
		return listDpt.stream().flatMap(department -> department.getListEmp().stream()).collect(Collectors.toList());
	}

	public void setListEmp(List<Employee> listEmp) {
		System.out.println("Il n'est plus possible d'ajouter une liste brute d'employee a company");
		// this.listEmp = listEmp;
		NotifyObserverModel(this);
	}

	public List<Record> getListRec() {
		return getListEmp().stream().flatMap(employee -> employee.getRecords().stream()).collect(Collectors.toList());
	}

	@Deprecated
	public void setListRec(List<Record> listRec) {
		NotifyObserverModel(this);

		System.out.println("Il n'est plus possible d'ajouter une liste brute de record a company");
		// this.listRec = listRec;

	}

	/**
	 * addEmployee
	 * 
	 * @param emp
	 */
	public void addEmployee(Employee emp) {

		if (emp.getDepartement() != null) {
			addDepartment(emp.getDepartement());
			NotifyObserverModel(this);
		}
		
	}

	/**
	 * delEmployee
	 * 
	 * @param emp
	 */
	public void delEmployee(Employee emp) {
		if (listDpt.contains(emp.getDepartement())) {
			// System.out.println("Heu : " +Dpt);
			emp.getDepartement().delEmployee(emp);
			NotifyObserverModel(this);
		}

	}

	/**
	 * delEmployee
	 * 
	 * @param index
	 */
	public void delEmployee(int index) {
		System.out.println("Il n'est plus possible delEmployee avec un index");

		try {
			// listEmp.remove(index);
		} catch (Exception exc) {
			// nothing here to del
		}
	}

	/**
	 * addRecord
	 * 
	 * @param rec
	 */

	public void addRecord(Record rec) {

		if (getListEmp().contains(rec.getEmployee())) {

			Employee foundEmployee = getListEmp().get(getListEmp().indexOf(rec.getEmployee()));

			foundEmployee.addRecord(rec);

			NotifyObserverModel(this);

		}
	}

	/**
	 * addRecord
	 * 
	 * @param emp
	 * @param date
	 */

	public void addRecord(Employee emp, LocalDateTime date) {
		emp.addRecord(date);
		NotifyObserverModel(this);

	}

	/**
	 * sortRecord sort the list of record
	 */
	public void sortRecord() {
		List<Record> listRec = getListEmp().stream().flatMap(employee -> employee.getRecords().stream())
				.collect(Collectors.toList());
		java.util.Collections.sort(listRec);
	}

	/**
	 * recordsOfTheDay
	 * 
	 * @return List of record of the day
	 */
	public List<Record> recordsOfTheDay() {
		List<Record> listR = new ArrayList<>();
		List<Record> listRec = getListEmp().stream().flatMap(employee -> employee.getRecords().stream())
				.collect(Collectors.toList());
		for (Record rec : listRec) {
			if (rec.getRecord().toLocalDate().equals(LocalDate.now())) {
				listR.add(rec);
			}
		}

		return listR;
	}

	public List<Department> getListDpt() {
		return listDpt;
	}

	public void setListDpt(List<Department> listDpt) {
		this.listDpt = new ArrayList<>();
		listDpt.forEach(dpt-> addDepartment(dpt));
	}

	/**
	 * addDepartment
	 * 
	 * @param Dpt
	 */
	public void addDepartment(Department Dpt) {
		if (listDpt.contains(Dpt) == false) {
			// System.out.println("Heu : " +Dpt);
			listDpt.add(Dpt);
			Dpt.setCompany(this);
		}
		NotifyObserverModel(this);
	}

	/**
	 * delDepartment
	 * 
	 * @param Dpt
	 */
	public void delDepartment(Department Dpt) {
		if (listDpt.contains(Dpt)) {
			listDpt.remove(Dpt);
			Dpt.setCompany(null);
			NotifyObserverModel(this);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(listDpt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(companyName, other.companyName);
	}

	/**
	 * delRecord
	 * 
	 * @param rec
	 */
	public void delRecord(Record rec) {

		try {
			if (getListEmp().contains(rec.getEmployee())) {

				Employee foundEmployee = getListEmp().get(getListEmp().indexOf(rec.getEmployee()));

				foundEmployee.delRecord(rec);

			}
		} catch (Exception exc) {
			// nothing here to del
		}
	}

	@Override
	public String toString() {
		return companyName;
	}

	public String extendedToString() {
		return "Company [companyName=" + companyName + "]";
	}

	public List<Record> AllRecord() {
		List<Record> listRec = getListEmp().stream().flatMap(employee -> employee.getRecords().stream()).collect(Collectors.toList());
		java.util.Collections.sort(listRec);
		return listRec;
	}

	// Gestion des observers
	public void addModelObservers(ModelListener om) {
		if (modelObservers == null) {
			this.modelObservers = new ArrayList<>();
		}

		if (om != null && modelObservers.contains(om) == false)
			modelObservers.add(om);
	}

	public void removeModelObservers(ModelListener om) {
		if (modelObservers == null) {
			this.modelObservers = new ArrayList<>();
			return;
		}

		if (om != null && modelObservers.contains(om) == true)
			modelObservers.remove(om);
	}

	public void NotifyObserverModel(Company receivedCompany) {

		if (modelObservers == null) {
			this.modelObservers = new ArrayList<>();
		} else {
			for (ModelListener observer : modelObservers) {
				observer.asyncNotify(receivedCompany);
			}
		}
	}

}
