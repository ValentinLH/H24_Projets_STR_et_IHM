package fr.polytech.vgl.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  Company represent the company
 * @author Touret Lino - L'Hermite Valentin
 */

public class Company implements java.io.Serializable {
	
	private static final long serialVersionUID = 8140981971700902890L;
	
	private String companyName; 
	private List<Employee> listEmp;
	private List<Record> listRec;
	private List<Department> listDpt;
	
	/**
	 * Default Constructor
	 */
	public Company() {
		companyName = "Not Defined";
		this.listEmp = new ArrayList<>();
		this.listRec = new ArrayList<>();
		this.listDpt =new ArrayList<>(); 
	}
	
	/**
	 * Comfort Constructor
	 * @param _companyName
	 */
	public Company(String _companyName) {
		companyName = _companyName;
		this.listEmp = new ArrayList<>();
		this.listRec = new ArrayList<>();
		this.listDpt =new ArrayList<>(); 
	}

	/**
	 * Comfort Constructor even more comfortable
	 * @param _companyName
	 * @param listEmp
	 * @param listRec
	 */
	public Company(String _companyName, List<Employee> listEmp, List<Record> listRec) {
		super();
		companyName = _companyName;
		this.listEmp = listEmp;
		this.listRec = listRec;
		this.listDpt =new ArrayList<>(); 
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
		return listRec;
	}

	public void setListRec(List<Record> listRec) {
		this.listRec = listRec;
	}
	
	/**
	 * addEmployee
	 * @param emp
	 */
	public void addEmployee(Employee emp)
	{
		if(listEmp.contains(emp) == false)
		{
			listEmp.add(emp);
			emp.setCompany(this);
			addDepartment(emp.getDepartement());
		}
		
	}
	
	/**
	 * delEmployee
	 * @param emp
	 */
	public void delEmployee(Employee emp)
	{
		try {
			listEmp.remove(emp);
		}
		catch (Exception exc)
		{
			//nothing here to del
		}
	}
	
	/**
	 * delEmployee
	 * @param index
	 */
	public void delEmployee(int index)
	{
		try {
			listEmp.remove(index);
		}
		catch (Exception exc)
		{
			//nothing here to del
		}
	}
	
	/**
	 * addRecord
	 * @param rec
	 */
	public void addRecord(Record rec)
	{
		if(listRec.contains(rec) == false)
		{
			
			listRec.add(rec);
		}
	}
	
	/**
	 * addRecord
	 * @param emp
	 * @param date
	 */
	public void addRecord(Employee emp, LocalDateTime date)
	{
		Record rec = emp.addRecord(date);
		if(listRec.contains(rec) == false)
		{
			listRec.add(rec);
		}
	}
	
	/**
	 * sortRecord sort the list of record
	 */
	public void sortRecord()
	{
		java.util.Collections.sort(listRec);
	}
	
	/**
	 * recordsOfTheDay
	 * @return List of record of the day
	 */
	public List<Record> recordsOfTheDay()
	{
		List<Record> listR = new ArrayList<>();
		for (Record rec : listRec)
		{
			if(rec.getRecord().toLocalDate().equals(LocalDate.now()))
			{
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
	 * @param Dpt
	 */
	public void addDepartment(Department Dpt) {
		if(listDpt.contains(Dpt) == false)
		{
			//System.out.println("Heu : " +Dpt);
			listDpt.add(Dpt);
		}
		if (listDpt.contains(null) == true)
		{
			listDpt.remove(null);
		}
	}
	
	/**
	 * delDepartment
	 * @param Dpt
	 */
	public void delDepartment(Department Dpt) {
		if(listDpt.contains(Dpt) == false)
		{
			listDpt.remove(Dpt);
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(listEmp, listRec,listDpt);
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
	 * @param rec
	 */
	public void delRecord(Record rec)
	{
		try {
			listRec.remove(rec);
		}
		catch (Exception exc)
		{
			//nothing here to del
		}
	}
	
	/**
	 * delRecord
	 * @param index
	 */
	public void delRecord(int index)
	{
		try {
			listRec.remove(index);
		}
		catch (Exception exc)
		{
			//nothing here to del
		}
	}

	@Override
	public String toString() {
		return  companyName ;
	}
	
	public String extendedToString() {
		return "Company [companyName=" + companyName + ", listEmp=" + listEmp + ", listRec=" + listRec + "]";
	}

	public List<Record> AllRecord(){
		List<Record> allrecord = new ArrayList<>();
		//for (Employee emp : listEmp) {
			for (Record rec : listRec)
			{
					allrecord.add(rec);
			}
		//}
		
		return allrecord;
	}
	

	
}
