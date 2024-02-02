package fr.polytech.vgl.serialisation;

import java.io.*;
import java.util.List;

/**
 * Serialization is a toolbox to serialize data
 * @author Touret Lino - L'Hermite Valentin
 */
public class Serialisation {

    /**
     * Serialize an object
     * @param obj
     * @param fileName
     * @param <T>
     */
    public static <T> void serialize(T obj, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(obj);
            outputStream.flush();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Deserialize an object
     * @param fileName
     * @param <T>
     * @return T
     */
    @SuppressWarnings("unchecked")
	public static <T> T deserializeObject(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (T) inputStream.readObject();
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
            return null;
        }
    }

    /**
     * Serialize a list of objects
     * @param list
     * @param fileName
     * @param <T>
     */
    public static <T> void serialize(List<T> list, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(list);
            outputStream.flush();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Deserialize a list of objects
     * @param fileName
     * @param <T>
     * @return List<T>
     */
    public static <T> List<T> deserializeList(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
            return null;
        }
    }
    
    public static Object DeSerialize(String fileName) {
		ObjectInputStream inputStream = null;
		Object obj = null;
		try {
			final FileInputStream file = new FileInputStream(fileName);
			inputStream = new ObjectInputStream(file);
			obj = inputStream.readObject();
		
		} catch (java.io.IOException exc) {
			//exc.printStackTrace();
		} catch (ClassNotFoundException exc) {
			//exc.printStackTrace();
		} 
		finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
		return obj;
	}
}
