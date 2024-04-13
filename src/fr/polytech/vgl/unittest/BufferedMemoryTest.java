package fr.polytech.vgl.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

import fr.polytech.vgl.realtime.BufferedMemory;

public class BufferedMemoryTest {

    private BufferedMemory<String> bufferedMemory;

    @Before
    public void setUp() {
        bufferedMemory = new BufferedMemory<>(5, 2, new Supplier<String>() {
            @Override
            public String get() {
                return "New String";
            }
        });
    }

    @Test
    public void testGetObject() {
        List<String> retrievedStrings = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            retrievedStrings.add(bufferedMemory.getObject());
        }
        assertEquals(7, retrievedStrings.size());
    }

    @Test
    public void testDestroyObject() {
        String obj = bufferedMemory.getObject();
        assertTrue(bufferedMemory.contains(obj));
        bufferedMemory.destroyObject(obj);
        assertFalse(bufferedMemory.contains(obj));
    }

    @Test
    public void testClear() {
        for (int i = 0; i < 3; i++) {
            bufferedMemory.getObject();
        }
        assertFalse(bufferedMemory.isEmpty());
        bufferedMemory.clear();
        assertTrue(bufferedMemory.isEmpty());
    }

    @Test
    public void testContains() {
        String obj = bufferedMemory.getObject();
        assertTrue(bufferedMemory.contains(obj));
    }
}
