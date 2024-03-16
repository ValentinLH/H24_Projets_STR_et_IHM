package fr.polytech.vgl.realtime;

import java.util.ArrayList;
import java.util.List;

public class BufferedMemory<T> {

    private int capacity;
    private int more;
    private List<T> garbage;
    private List<T> used;
    private Class<T> clazz;

    public BufferedMemory(int capacity, int more, Class<T> clazz) {
        this.capacity = capacity;
        this.more = more;
        this.garbage = new ArrayList<>();
        this.used = new ArrayList<>();
        this.clazz = clazz;
        fillGarbagePool(clazz);
    }

    public BufferedMemory(int capacity, int more, List<T> used, Class<T> clazz) {
        this.capacity = capacity;
        this.more = more;
        this.used = used;
        this.garbage = new ArrayList<>();
        this.clazz = clazz;
        fillGarbagePool(clazz);
    }

    private void fillGarbagePool(Class<T> clazz) {
        for (int i = 0; i < capacity; i++) {
            T obj = createObject(clazz);
            if (obj != null) {
                garbage.add(obj);
            }
        }
    }

    private T createObject(Class<T> clazz) {
        try {
            // Utilisez getDeclaredConstructor() pour obtenir le constructeur par défaut (sans arguments)
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
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
            T obj = createObject(clazz);
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
