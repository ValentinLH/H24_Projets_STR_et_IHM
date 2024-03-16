package fr.polytech.vgl.realtime;

import java.util.ArrayList;
import java.util.List;

public class BufferedMemory<T> {

    private int capacity;
    private int more;
    private List<T> garbage;
    private List<T> used;

    public BufferedMemory(int capacity, int more) {
        this.capacity = capacity;
        this.more = more;
        this.garbage = new ArrayList<>();
        this.used = new ArrayList<>();
        create();
    }

    public BufferedMemory(int capacity, int more, List<T> used) {
        this.capacity = capacity;
        this.more = more;
        this.used = used;
        this.garbage = new ArrayList<>();
        create();
    }

    private void create() {
        for (int i = 0; i < capacity; i++) {
            T obj = createObject();
            garbage.add(obj);
        }
    }

    @SuppressWarnings("unchecked")
	private T createObject() {
        // Create new object of type T
        // You need to implement this method based on your requirements
        // For demonstration, let's assume T has a no-arg constructor
        try {
        	Class<T> clazz = (Class<T>) Class.forName("fr.polytech.vgl.realtime.BufferedMemory");
            // Utilisez getDeclaredConstructor() pour obtenir le constructeur par défaut (sans arguments)
            return clazz.getDeclaredConstructor().newInstance();
        	 //return type.getDeclaredConstructor().newInstance();
        } catch (Exception  e) {
            e.printStackTrace();
        } 
        return null;
    }
    
   

    public T useObject() {
        if (!garbage.isEmpty()) {
            T obj = garbage.remove(0);
            used.add(obj);
            return obj;
        } else {
            // If no objects in garbage, create new object
            T obj = createObject();
            used.add(obj);
            return obj;
        }
    }

    public void destroyObject(T obj) {
        if (used.contains(obj)) {
            used.remove(obj);
            garbage.add(obj);
        }
    }

    public List<T> getUsed() {
        return used;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }
}
