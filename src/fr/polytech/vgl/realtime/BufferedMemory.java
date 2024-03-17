package fr.polytech.vgl.realtime;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * BufferedMemory représente un pool d'objets en mémoire tampon pour une utilisation efficace et réutilisable.
 *
 * @param <T> Le type d'objet contenu dans le pool.
 * @since VLH 16/03/24
 */
public class BufferedMemory<T> {

    private int capacity; // Capacité maximale du pool
    private int more; // Capacité supplémentaire à ajouter si nécessaire
    private List<T> garbage; // Liste des objets inutilisés
    private List<T> used; // Liste des objets utilisés
    private Supplier<T> supplier; // Fonction de création personnalisée pour les objets

    /**
     * Constructeur de BufferedMemory.
     *
     * @param capacity Capacité maximale du pool.
     * @param more     Capacité supplémentaire à ajouter si nécessaire.
     * @param supplier Fonction de création personnalisée pour les objets.
     */
    public BufferedMemory(int capacity, int more, Supplier<T> supplier) {
        this.capacity = capacity;
        this.more = more;
        this.used = new ArrayList<>();
        this.garbage = new ArrayList<>();
        this.supplier = supplier;
        fillGarbagePool();
    }

    /**
     * Constructeur de BufferedMemory avec une liste d'objets déjà utilisés.
     *
     * @param capacity Capacité maximale du pool.
     * @param more     Capacité supplémentaire à ajouter si nécessaire.
     * @param supplier Fonction de création personnalisée pour les objets.
     * @param used     Liste des objets déjà utilisés.
     */
    public BufferedMemory(int capacity, int more, Supplier<T> supplier, List<T> used) {
        this.capacity = capacity;
        this.more = more;
        this.used = used;
        this.garbage = new ArrayList<>();
        this.supplier = supplier;
        fillGarbagePool();
    }

    /**
     * Remplit la mémoire tampon avec des objets.
     */
    private void fillGarbagePool() {
        for (int i = used.size(); i < capacity; i++) {
            T obj = createObject();
            if (obj != null) {
                garbage.add(obj);
            }
        }
    }

    /**
     * Remplit la mémoire tampon avec des objets supplémentaires.
     */
    private void fillMore() {
        capacity += more;
        fillGarbagePool();
    }

    /**
     * Crée un nouvel objet en utilisant la fonction de création personnalisée.
     *
     * @return Le nouvel objet créé.
     */
    private T createObject() {
        return supplier.get();
    }

    /**
     * Récupère un objet de la mémoire tampon pour une utilisation.
     *
     * @return L'objet récupéré.
     */
    public T getObject() {
        if (!garbage.isEmpty()) {
            T obj = garbage.remove(0);
            used.add(obj);
            return obj;
        } else {
        	fillMore();
            return getObject();
        }
    }

    /**
     * Détruit un objet et le remet dans la mémoire tampon.
     *
     * @param obj L'objet à détruire.
     */
    public void destroyObject(T obj) {
        if (used.contains(obj)) {
            used.remove(obj);
            garbage.add(obj);
        }
    }

    /**
     * Renvoie la liste des objets utilisés.
     *
     * @return La liste des objets utilisés.
     */
    public List<T> getUsed() {
        return used;
    }

    /**
     * Renvoie la capacité maximale du pool.
     *
     * @return La capacité maximale du pool.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Renvoie la capacité supplémentaire à ajouter si nécessaire.
     *
     * @return La capacité supplémentaire à ajouter.
     */
    public int getMore() {
        return more;
    }

    /**
     * Définit la capacité supplémentaire à ajouter si nécessaire.
     *
     * @param more La capacité supplémentaire à ajouter.
     */
    public void setMore(int more) {
        this.more = more;
    }

    /**
     * Vide complètement la mémoire tampon.
     */
    public void clear() {
        garbage.addAll(used);
        used.clear();
    }

    /**
     * Vérifie si la mémoire tampon est vide.
     *
     * @return true si la mémoire tampon est vide, false sinon.
     */
    public boolean isEmpty() {
        return used.isEmpty();
    }

    /**
     * Vérifie si la mémoire tampon contient un objet spécifique.
     *
     * @param obj L'objet à rechercher.
     * @return true si l'objet est présent, false sinon.
     */
    public boolean contains(T obj) {
        return used.contains(obj);
    }
}
