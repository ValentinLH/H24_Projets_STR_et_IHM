package fr.polytech.vgl.unittest;

import static org.junit.Assert.*;


import org.junit.Test;

import fr.polytech.vgl.model.*;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalTime;
import java.time.DayOfWeek;

/**
 *  ScheduleTest is a Junit Test Class for Schedule
 *  @since 02/03/24
 *  VLH
 */
public class ScheduleTest {

    @Test
    public void testGetSchedule() {
        Schedule schedule = new Schedule();
        Map<DayOfWeek, LocalTime[]> result = schedule.getSchedule();

        assertNotNull(result);
        assertEquals(5, result.size());
        assertTrue(result.containsKey(DayOfWeek.MONDAY));
        assertTrue(result.containsKey(DayOfWeek.TUESDAY));
        assertTrue(result.containsKey(DayOfWeek.WEDNESDAY));
        assertTrue(result.containsKey(DayOfWeek.THURSDAY));
        assertTrue(result.containsKey(DayOfWeek.FRIDAY));
    }

    @Test
    public void testSetSchedule() {
        Schedule schedule = new Schedule();
        Map<DayOfWeek, LocalTime[]> newSchedule = new HashMap<>();
        newSchedule.put(DayOfWeek.MONDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(18, 0)});
        newSchedule.put(DayOfWeek.TUESDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(18, 0)});
        newSchedule.put(DayOfWeek.WEDNESDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(18, 0)});
        newSchedule.put(DayOfWeek.THURSDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(18, 0)});
        newSchedule.put(DayOfWeek.FRIDAY, new LocalTime[]{LocalTime.of(9, 0), LocalTime.of(18, 0)});

        schedule.setSchedule(newSchedule);
        Map<DayOfWeek, LocalTime[]> result = schedule.getSchedule();

        assertNotNull(result);
        assertEquals(newSchedule, result);
    }


    @Test
    public void testGetDayHours() {
        Schedule schedule = new Schedule();
        LocalTime[] result = schedule.getDayHours(DayOfWeek.MONDAY);

        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals(LocalTime.of(8, 30), result[0]);
        assertEquals(LocalTime.of(17, 0), result[1]);
    }

    @Test
    public void testSetDayHours() {
        Schedule schedule = new Schedule();
        LocalTime[] newHours = {LocalTime.of(9, 0), LocalTime.of(18, 0)};

        schedule.setDayHours(DayOfWeek.MONDAY, newHours);
        LocalTime[] result = schedule.getDayHours(DayOfWeek.MONDAY);

        assertNotNull(result);
        assertEquals(newHours, result);
    }

    @Test
    public void testGetWorkingTime() {
        Schedule schedule = new Schedule();
        int result = schedule.getWorkingTime(DayOfWeek.MONDAY);

        assertNotNull(result);
        assertEquals(8, result);
    }
}
