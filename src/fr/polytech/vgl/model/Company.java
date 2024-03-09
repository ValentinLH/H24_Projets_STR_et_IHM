package fr.polytech.vgl.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Company represent the company
 * 
 * @author Touret Lino - L'Hermite Valentin
 */

public class Company implements java.io.Serializable {

	private static final long serialVersionUID = 8140981971700902890L;

	private String companyName;
	private List<Employee> listEmp;
	private List<Department> listDpt;

	/**
	 * Default Constructor
	 */
	public Company() {
		companyName = "Not Defined";
		this.listEmp = new ArrayList<>();
		this.listDpt = new ArrayList<>();
	}

	/**
	 * Comfort Constructor
	 * 
	 * @param _companyName
	 */
	public Company(String _companyName) {
		companyName = _companyName;
		this.listEmp = new ArrayList<>();
		this.listDpt = new ArrayList<>();
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
		this.listEmp = listEmp;
		this.listDpt = new ArrayList<>();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<Employee> getListEmp() {
		return listEmp;
	}

	public void setListEmp(List<Employee> listEmp) {
		this.listEmp = listEmp;
	}

	public List<Record> getListRec() {
		return listEmp.stream().flatMap(employee -> employee.getRecords().stream()).collect(Collectors.toList());
	}

	@Deprecated
	public void setListRec(List<Record> listRec) {
		System.out.println("Il n'est plus possible d'ajouter une liste brute de record a company");
		// this.listRec = listRec;
	}

	/**
	 * addEmployee
	 * 
	 * @param emp
	 */
	public void addEmployee(Employee emp) {
		if (listEmp.contains(emp) == false) {
			listEmp.add(emp);
			emp.setCompany(this);
			addDepartment(emp.getDepartement());
		}

	}

	/**
	 * delEmployee
	 * 
	 * @param emp
	 */
	public void delEmployee(Employee emp) {
		try {
			listEmp.remove(emp);
		} catch (Exception exc) {
			// nothing here to del
		}
	}

	/**
	 * delEmployee
	 * 
	 * @param index
	 */
	public void delEmployee(int index) {
		try {
			listEmp.remove(index);
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

		if (listEmp.contains(rec.getEmployee())) {

			Employee foundEmployee = listEmp.get(listEmp.indexOf(rec.getEmployee()));

			foundEmployee.addRecord(rec);

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
	}

	/**
	 * sortRecord sort the list of record
	 */
	public void sortRecord() {
		List<Record> listRec = listEmp.stream().flatMap(employee -> employee.getRecords().stream())
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
		List<Record> listRec = listEmp.stream().flatMap(employee -> employee.getRecords().stream())
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
		this.listDpt = listDpt;
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
		}
		if (listDpt.contains(null) == true) {
			listDpt.remove(null);
		}
	}

	/**
	 * delDepartment
	 * 
	 * @param Dpt
	 */
	public void delDepartment(Department Dpt) {
		if (listDpt.contains(Dpt)) {
			listDpt.remove(Dpt);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(listEmp, listDpt);
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
			if (listEmp.contains(rec.getEmployee())) {

				Employee foundEmployee = listEmp.get(listEmp.indexOf(rec.getEmployee()));

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
		return "Company [companyName=" + companyName + ", listEmp=" + listEmp + "]";
	}

	public List<Record> AllRecord() {
		List<Record> listRec = listEmp.stream().flatMap(employee -> employee.getRecords().stream())
				.collect(Collectors.toList());
		java.util.Collections.sort(listRec);
		return listRec;
	}

}
