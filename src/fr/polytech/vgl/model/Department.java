package fr.polytech.vgl.model;

/*public enum Department {
	RD, Adminstration, Production

}
*/
import java.util.ArrayList;
import java.util.List;

/**
 * Department represent the department of a company
 * 
 * @author Touret Lino - L'Hermite Valentin
 * @version VLH 09/03/24
 */
public class Department implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private static int listId = 0;
	private int departementId;
	private String departmentName;

	private Company company;
	private List<Employee> listEmp;

	public Department(String name) {
		this.departmentName = name;
		departementId = listId;
		listId++;
		listEmp = new ArrayList<>();
	}

	@Override
	public String toString() {
		return departmentName;
	}

	public int getDepartementId() {
		return departementId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List<Employee> getListEmp() {
		return listEmp;
	}

	public void setEListEmp(List<Employee> employee) {
		this.listEmp = employee;
	}

	public void addEmployee(Employee emp) {
		listEmp.add(emp);
	}

	public void delEmployee(Employee emp) {
		try {
			emp.setDepartement(null);
			listEmp.remove(emp);
		} catch (Exception exc) {
			// nothing here to del
		}
	}

	public void delEmployee(int index) {
		try {

			listEmp.get(index).setDepartement(null);
			listEmp.remove(index);
		} catch (Exception exc) {
			// nothing here to del
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}