package fr.polytech.vgl.realtime;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BufferedMemory<T> {

    private int capacity;
    private int more;
    private List<T> garbage;
    private List<T> used;
    private Supplier<T> supplier; // Fonction de création personnalisée

    public BufferedMemory(int capacity, int more, Supplier<T> supplier) {
        this.capacity = capacity;
        this.more = more;
        this.used = new ArrayList<>();
        this.garbage = new ArrayList<>();
        this.supplier = supplier;
        fillGarbagePool();
    }

    private void fillGarbagePool() {
        for (int i = 0; i < capacity; i++) {
            T obj = createObject();
            if (obj != null) {
                garbage.add(obj);
            }
        }
    }

    private T createObject() {
        return supplier.get(); // Utilise la fonction de création personnalisée
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



