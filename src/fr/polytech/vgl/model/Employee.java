package fr.polytech.vgl.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

import fr.polytech.vgl.centralapp.view.GiveCompanyView;

/**
 * Employee represents an employee of a company
 * 
 * This version is adapted for real-time systems.
 * 
 * Note: Further adjustments might in the other classe be necessary based on
 * specific RTSJ requirements.
 * 
 * @author Touret Lino - L'Hermite Valentin
 * @since 02/03/24 
 * VLH
 * @version VLH 09/03/24
 */
public class Employee implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private static int id_auto = 0;
	private String name;
	private String surname;
	private int id;
	private Schedule schedule;
	private Integer overtimePortfolio;

	private Department departement;
	private List<Record> records;
	
	
    public Employee(String _name, String _surname, Company _company, Department _departement) {
        name = _name;
        surname = _surname;
        id = id_auto;
        id_auto++;
        setDepartement(_departement);
        // Using CopyOnWriteArrayList for thread safety
        records = new CopyOnWriteArrayList<>();
        schedule = new Schedule();
        overtimePortfolio = 0;
    }

    public Employee(String _name, String _surname, int _id, Company _company, Department _departement,
            List<Record> _records) {
        name = _name;
        surname = _surname;
        id = _id;
        setCompany(_company);
        setDepartement(_departement);
        // Using CopyOnWriteArrayList for thread safety
        records = new CopyOnWriteArrayList<>(_records);
        overtimePortfolio = 0;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getId() {
		return id;
	}

	public static Employee getById(int idEmployee) {
		for (Employee E : GiveCompanyView.company.getListEmp()) {
			if (E.getId() == idEmployee) {
				return E;
			}
		}
		System.out.println("There is a problem. There is no employee with the id " + idEmployee + "\n");
		return null;
	}

	public Company getCompany() {
		return departement.getCompany();
	}

	public void setCompany(Company company) {
		System.out.println("Employee set company n'existe plus, la charge est au département");

	}

	public Department getDepartement() {
		return departement;
	}

	public void setDepartement(Department departement) {
		this.departement = departement;
	}

    // Add synchronization for thread safety
    public synchronized Record addRecord(LocalDateTime date) {
        Record rec = new Record(date, this);
        if (!records.contains(rec)) {
            records.add(rec);
        }
        return rec;
    }


	public List<Record> getRecords() {
		return records;
	}

	public Record getRecordIndex(int i) {
		return records.get(i);
	}

    public synchronized void addRecord(Record record) {
        if (record.getEmployee() != null) {
            record.getEmployee().delRecord(record);
            record.setEmployee(this);
        }

        if (!records.contains(record)) {
            records.add(record);
        }

 
    }

	public void delRecord(Record record) {
		records.remove(record);
	}

	public void delRecord(int index) {
		records.remove(index);
	}

	@Override
	public String toString() {
		// return name + " " + surname+ " [ID" + id+"]" ;
		return name + " " + surname + " - " + departement + " of " + (getCompany() != null ? getCompany().getCompanyName() : null) + "";
	}

	public String ExtendedtoString() {
		// sortRecord();
		return "Employee [name=" + name + ", surname=" + surname + ", id=" + id + ", company="
				+ (getCompany() != null ? getCompany().getCompanyName() : null)+ ", departement=" + departement + ", records=" + records + "]";
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public void sortRecord() {
		java.util.Collections.sort(records);
	}

	public void setOvertimePortfolio(Integer overtimePortfolio) {
		this.overtimePortfolio = Record.computeMinutes(overtimePortfolio.intValue());
	}

	public synchronized Integer getOvertimePortfolio() {
	    sortRecord();
	    Integer overtimePortfolio = this.overtimePortfolio;

	    int minus = 0;
	    if (records.size() % 2 == 1) {
	        minus = 1;
	    }

	    for (int i = 0; i < records.size() - 1 - minus; i++) {
	        if (records.get(i).getRecord().toLocalDate()
	                .compareTo(records.get(i + 1).getRecord().toLocalDate()) == 0) {
	            try {
	                // Synchronize access to schedule for thread safety
	                synchronized (schedule) {
	                    Integer workingTime = schedule.getWorkingTime(
	                            records.get(i).getRecord().toLocalDate().getDayOfWeek());

	                    if (records.get(i).getRecord().toLocalTime()
	                            .compareTo(records.get(i + 1).getRecord().toLocalTime()) < 0) {
	                        LocalTime localtime = records.get(i + 1).getRecord().toLocalTime()
	                                .minusHours(records.get(i).getRecord().toLocalTime().getHour());
	                        localtime = localtime.minusMinutes(records.get(i).getRecord().toLocalTime().getMinute());

	                        Integer morningH = records.get(i).getRecord().getHour() * 60
	                                + records.get(i).getRecord().getMinute();
	                        Integer eveningH = records.get(i + 1).getRecord().getHour() * 60
	                                + records.get(i + 1).getRecord().getMinute();
	                        overtimePortfolio = overtimePortfolio + ((eveningH - morningH) - workingTime);
	                    }
	                }
	            } catch (Exception e) {
	                // Handle exceptions in a real-time-friendly manner
	                // For example, log the exception or take appropriate actions
	            }
	        }
	    }
	    return overtimePortfolio;
	}


	@Override
	public int hashCode() {
		return Objects.hash((getCompany() != null ? getCompany().getCompanyName() : null), departement.getDepartmentName(), id, name, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals((getCompany() != null ? getCompany().getCompanyName() : null), (other.getCompany() != null ? other.getCompany().getCompanyName() : null))
				&& Objects.equals(departement, other.departement) && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname);
	}

}
