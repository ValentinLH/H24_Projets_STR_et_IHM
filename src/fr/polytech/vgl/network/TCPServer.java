package fr.polytech.vgl.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

/**
 * TCPServer is a Class for the encapsulation of a TCP server
 * 
 * @version 03/03/24
 */
public class TCPServer extends TCPInfo {

	// TCPBuilder
	public TCPServer(Integer port) {
		updatePort(port);
	}

	private void updatePort(Integer port) {
		try {

			server = new ServerSocket(port);

		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("[-] Error Creating Server Socket");
		}

		try {
			InetAddress localAddress = InetAddress.getLocalHost();
			this.ip = localAddress.getHostAddress();
		} catch (UnknownHostException exc) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// System.out.println("hey");
		}

		this.port = port;
	}

	@Override
	public void setPort(Integer port) {
		updatePort(port);
	}

	// connection

	public void setServerConnection() {
		try {
			while (true) {
				client = server.accept();
				outputStream = new ObjectOutputStream(client.getOutputStream());

				System.out.println("[+] Connection Ok " + client.getInetAddress());
			}
		} catch (IOException exc) {
			System.out.println("[+] Connection NOK ");
			// exc.printStackTrace();
		}
	}

	public void setServerConnection(Object obj) {
		try {
			while (true) {
				client = server.accept();
				outputStream = new ObjectOutputStream(client.getOutputStream());

				System.out.println("[+] Connection Ok " + client.getInetAddress());
				outputStream.writeObject(obj);
				break;
			}

			while (true) {
				client = server.accept();
				outputStream = new ObjectOutputStream(client.getOutputStream());

				System.out.println("[+] Connection Ok " + client.getInetAddress());
			}
		} catch (IOException e) {
			System.out.println("[-] Connection NOK ");
			// e.printStackTrace();
		}
	}

	public void closeServer() {
		try {

			if (outputStream != null) {
				outputStream.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (server != null) {
				server.close();
			}
			System.out.println("[+] Server Closed");
		} catch (IOException exc) {
			System.out.println("[-] Error Closing Server");
			// e.printStackTrace();
		} catch (Exception exc) {
			//
		}
	}

	public boolean sendObject(Object obj) {
		try {
			outputStream.writeObject(obj);
		} catch (Exception exc) {
			System.out.println("[-] Error Sending");
			return false;
			// exc.printStackTrace();
			// return false;
		}
		return true;
	}
}