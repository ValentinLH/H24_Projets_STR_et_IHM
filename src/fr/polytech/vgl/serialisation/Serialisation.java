package fr.polytech.vgl.serialisation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import fr.polytech.vgl.model.Company;
import fr.polytech.vgl.model.Record;

/**
 * Serialisation is an tool box to serialize data
 * @author Touret Lino - L'Hermite Valentin
 *
 */
public class Serialisation {

	/**
	 * Serialize a company
	 * @param company
	 * @param fileName
	 */
	public static void SerializeCompany(Company company, String fileName) {
		ObjectOutputStream outputStream = null;

		try {
			FileOutputStream file = new FileOutputStream(fileName);
			outputStream = new ObjectOutputStream(file);
			outputStream.writeObject(company);
			outputStream.flush();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
	}
	
	/**
	 * Serialize a list of company
	 * @param listCompany
	 * @param fileName
	 */
	public static void SerializeListCompany(List<Company> listCompany, String fileName) {
		ObjectOutputStream outputStream = null;

		try {
			FileOutputStream file = new FileOutputStream(fileName);
			outputStream = new ObjectOutputStream(file);
			outputStream.writeObject(listCompany);
			outputStream.flush();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
	}

	/**
	 * DeSerializeCompany
	 * @param fileName
	 * @return Company
	 */
	public static Company DeSerializeCompany(String fileName) {
		ObjectInputStream inputStream = null;
		Company company = null;
		try {
			final FileInputStream file = new FileInputStream(fileName);
			inputStream = new ObjectInputStream(file);
			company = (Company) inputStream.readObject();
		
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
		return company;
	}

	public static void SerializeRecord(Record record, String fileName) {
		ObjectOutputStream outputStream = null;

		try {
			final FileOutputStream file = new FileOutputStream(fileName);
			outputStream = new ObjectOutputStream(file);
			outputStream.writeObject(record);
			outputStream.flush();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
	}
	
	public static void SerializeObject(Object obj, String fileName) {
		ObjectOutputStream outputStream = null;

		try {
			final FileOutputStream file = new FileOutputStream(fileName);
			outputStream = new ObjectOutputStream(file);
			outputStream.writeObject(obj);
			outputStream.flush();
		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
	}

	public static Record DeSerializeRecord(String fileName) {
		ObjectInputStream inputStream = null;
		Record record = null;
		try {
			final FileInputStream fichier = new FileInputStream(fileName);
			inputStream = new ObjectInputStream(fichier);
			record = (Record) inputStream.readObject();

		} catch (java.io.IOException exc) {
			exc.printStackTrace();
		} catch (ClassNotFoundException exc) {
			exc.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException exc) {
				exc.printStackTrace();
			}
		}
		return record;
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
