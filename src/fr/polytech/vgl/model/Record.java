package fr.polytech.vgl.model;


import java.time.LocalDateTime;
import java.util.Objects;
import java.time.format.DateTimeFormatter;

/**
 *  Record represent a record of an employee
 * @author Touret Lino - L'Hermite Valentin
 *
 */
public class Record implements Comparable<Record>,java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int rounded = 15;
	private LocalDateTime record;
	private Employee employee;
	
	
	public Record(LocalDateTime record, Employee emp) {
		super();
		this.record = record ;
		setHoursMinutes(record.getHour(),record.getMinute());
		this.employee = emp;
		emp.addRecord(this);
	}
	public Record(LocalDateTime record) {
		super();
		this.record = record;
		setHoursMinutes(record.getHour(),record.getMinute());
		employee= null;
	}
	public LocalDateTime getRecord() {
		return record;
	}
	public void setRecord(LocalDateTime record) {
		this.record = record;
		setHoursMinutes(record.getHour(),record.getMinute());
	}
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String formatDateTime = record.format(formatter);

		return "Record [record=" + formatDateTime + ", Employee id=" + employee.getId() + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(employee, record);
	}
	@Override
	public int compareTo(Record other) {
		// TODO Auto-generated method stub
		return getRecord().compareTo(other.getRecord());
	}
	
	
	 void setHoursMinutes(int hh, int mm)
	    {
	        setHours(hh);
	        setMinutes(mm);
	    }
	
	public void setMinutes(int i) {
		if (i%rounded >= (rounded+1)/2 )
		{
			i = ((i/rounded)+1) * rounded;
		}
		else
		{
			i = (i/rounded) * rounded;
		}
		if (i == 60)
		{
			setHours(record.getHour()+1);
			i = 0;
		}
		
		if (0 <= i && i < 60)  {
			record = record.withMinute(i);
            
        }
        else {
            throw new IllegalArgumentException("Minutes invalide");
        }
	}
	
	public static int computeMinutes(int i) {
		if (i%rounded >= (rounded+1)/2 )
		{
			i = ((i/rounded)+1) * rounded;
		}
		else
		{
			i = (i/rounded) * rounded;
		}
		
		return i;
	}
	
	
	public void setHours(int i) {
		 if (0 <= i && i < 24)  {
			 	record = record.withHour(i);
	        }
	        else {
	            //throw new IllegalArgumentException("Heures invalide");
	        	
	        	if (i>0) 
	        	{
	        		record = record.withHour(i%24);
	        	}
	        	else
	        	{
	        		record = record.withHour(24 + i%24);
	        	
	        	}
	        }
	}
	
	public static int getRounded() {
		return rounded;
	}
	
	
}
