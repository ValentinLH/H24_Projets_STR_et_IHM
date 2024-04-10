package fr.polytech.vgl.model;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;



/**
*  Schedule represent the schedule of an employee
 * @author Touret Lino - L'Hermite Valentin
 */

@Document("schedule")
public class Schedule implements java.io.Serializable {

	/**
	 * 
	 */
	@Id
	private ObjectId id ; // Utilisation de ObjectId comme type pour l'identifiant
	
	private static final long serialVersionUID = 1L;
	private static int generic_day_start[] = {8,30};
	private static int generic_day_end[] = {17,0};
	Map<DayOfWeek, LocalTime[]> schedule;


	public Schedule (){
		this.schedule = new HashMap<DayOfWeek, LocalTime[]>();
		schedule.put(DayOfWeek.MONDAY, new LocalTime[]{LocalTime.of(generic_day_start[0], generic_day_start[1]),LocalTime.of(generic_day_end[0], generic_day_end[1])});
		schedule.put(DayOfWeek.TUESDAY, new LocalTime[]{LocalTime.of(generic_day_start[0], generic_day_start[1]),LocalTime.of(generic_day_end[0], generic_day_end[1])});
		schedule.put(DayOfWeek.WEDNESDAY, new LocalTime[]{LocalTime.of(generic_day_start[0], generic_day_start[1]),LocalTime.of(generic_day_end[0], generic_day_end[1])});
		schedule.put(DayOfWeek.THURSDAY, new LocalTime[]{LocalTime.of(generic_day_start[0], generic_day_start[1]),LocalTime.of(generic_day_end[0], generic_day_end[1])});
		schedule.put(DayOfWeek.FRIDAY, new LocalTime[]{LocalTime.of(generic_day_start[0], generic_day_start[1]),LocalTime.of(generic_day_end[0], generic_day_end[1])});
		
	}


	public Map<DayOfWeek, LocalTime[]> getSchedule() {
		return schedule;
	}


	public void setSchedule(Map<DayOfWeek, LocalTime[]> schedule) {
		this.schedule = schedule;
	}
	
	public LocalTime[] getDayHours(DayOfWeek day)
	{
		return schedule.get(day);
	}
	
	public void setDayHours(DayOfWeek day, LocalTime[] hours) {
		schedule.replace(day, hours);
	}
	
	public Integer getWorkingTime(DayOfWeek day)
	{
		Integer i = 0;
		Integer morningH  = schedule.get(day)[0].getHour()*60 + schedule.get(day)[0].getMinute(); 
		Integer eveningH  = schedule.get(day)[1].getHour()*60 + schedule.get(day)[1].getMinute(); 
		i = eveningH - morningH;
		return i;
	}


	public void setId() {
		id = new ObjectId();
		
	}
}
