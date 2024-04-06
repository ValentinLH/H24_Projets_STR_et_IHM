package fr.polytech.vgl.model;

/*public enum Department {
	RD, Adminstration, Production

}
*/
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *  Department represent the department of a company
 * @author Touret Lino - L'Hermite Valentin
 *
 */

@Document("department")
public class  Department implements java.io.Serializable {
	
	@Id
	private ObjectId id; // Utilisation de ObjectId comme type pour l'identifiant
	
	private static final long serialVersionUID = 1L;
	private static int listId = 0; 
	private int departementId;
    private String departmentName;
    
    @DBRef(lazy = true)
    private List<Employee> listEmp;
    
    
    
    
    /**
	 * @param id the id to set
	 */
	public void setId() {
		this.id = new ObjectId();
	}

	public Department() {
		super();
	}

	public  Department(String name) {
    	this.id = new ObjectId();
        this.departmentName = name;
        departementId = listId;
        listId++;
        listEmp = new ArrayList<>();
    }
   
    @Override
    public String toString(){
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
    
	public void addEmployee(Employee emp)
	{
		listEmp.add(emp);
	}
	
	public void delEmployee(Employee emp)
	{
		try {
			emp.getCompany().delEmployee(emp);
			emp.setDepartement(null);
			listEmp.remove(emp);
		}
		catch (Exception exc)
		{
			//nothing here to del
		}
	}
	
	public void delEmployee(int index)
	{
		try {
			
			listEmp.get(index).setDepartement(null);
			listEmp.remove(index);
		}
		catch (Exception exc)
		{
			//nothing here to del
		}
	}

    
}