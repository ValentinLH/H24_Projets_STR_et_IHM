package fr.polytech.vgl.network;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.realtime.*;

public class NetworkManager {

	private static final String patternIP = "(Localhost)|(^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$)";
	private static final String patternPort = "^([0-9]|[0-9][0-9]|[0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])$";

	private Pattern pattern;
	private Matcher matcher;

	private TCPServer server;
	private TCPClient client;
	private RealtimeThread tServer;
	private RealtimeThread tClient;

	public NetworkManager() {
		server = new TCPServer(8080);
		client = new TCPClient("localhost", 8081);
		start();
	}

	public NetworkManager(int serverPort, String clientIp, int clientPort, NetworkObserver observer) {
		server = new TCPServer(serverPort);
		client = new TCPClient(clientIp, clientPort);

		addObserver(observer);

		start();
	}

	public NetworkManager(NetworkObserver observer) {
		server = new TCPServer(8080);
		client = new TCPClient("localhost", 8081);

		addObserver(observer);

		start();
	}

	public void start() {
		tServer = new ServerTask();
		tClient = new ClientTask();
		tServer.start();
		tClient.start();

		// tServer = TCPOpeningServer();
		// tClient = TCPOpeningClient();
	}

	public void addObserver(NetworkObserver observer) {
		client.addObserver(observer);
	}

	public void removeObserver(NetworkObserver observer) {
		client.removeObserver(observer);
	}

	private class ServerTask extends RealtimeThread {
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					server.setServerConnection();
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		}
	}

	private class ClientTask extends RealtimeThread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					while (true) {
						client.setSocketConnection();

						Object obj = client.getInputStream().readObject();
						System.out.println(client.getIp() + ":" + client.getPort());

						if (obj != null) {
							client.notifyObjectReceived(obj);
						}

						client.closeClient();
					}
				} catch (Exception exc) {
					// Handle the exception
				}
			}
		}
	}

	public boolean sendObject(Object obj) {
		System.out.println(client.getIp() + ":" + client.getPort());
		return server.sendObject(obj);
	}

	public String getServerIp() {
		return server.getIp();
	}

	public int getServerPort() {
		return server.getPort();
	}

	public int getClientPort() {
		return client.getPort();
	}

	public String getClientIp() {
		return client.getIp();
	}

	/**
	 * Set the IP address for the server.
	 *
	 * @param serverIp The IP address to set.
	 */
	public void setServerIp(String serverIp) {
		server.setIp(serverIp);
	}

	/**
	 * Set the port for the server.
	 *
	 * @param serverPort The port to set.
	 */
	public void setServerPort(String serverPort) {
		pattern = Pattern.compile(patternPort);
		matcher = pattern.matcher(serverPort);
		// si le motif est trouv�
		if (matcher.find()) {
			// System.out.println("motif trouv�");
			if (TCPInfo.available(getClientIp(), Integer.parseInt(serverPort)) == true) {
				server.setPort(Integer.parseInt(serverPort));
			}
		}
		restartServer();
	}

	/**
	 * Set the IP address for the client.
	 *
	 * @param clientIp The IP address to set.
	 */
	public void setClientIp(String clientIp) {

		pattern = Pattern.compile(patternIP);
		matcher = pattern.matcher(clientIp);

		// si le motif est trouv�
		if (matcher.find()) {

			try {
				client.setIp(clientIp);
				restartClient();

			} catch (Exception exc) {
				// nothing
				exc.printStackTrace();
			}
		}

	}

	/**
	 * Set the port for the client.
	 *
	 * @param clientPort The port to set.
	 */
	public void setClientPort(String clientPort) {

		pattern = Pattern.compile(patternPort);
		matcher = pattern.matcher(clientPort);
		// si le motif est trouv�
		if (matcher.find()) {
			// System.out.println("motif trouv�");
			if (TCPInfo.available(getClientIp(), Integer.parseInt(clientPort)) == true) {
				client.setPort(Integer.parseInt(clientPort));
			}
		}
		restartClient();
	}

	private void restartClient() {
		RealtimeThread tTemp = new ClientTask();

		tTemp.start();

		client.closeClient();
		tClient.interrupt();

		tClient = tTemp;
	}

	private void restartServer() {
		RealtimeThread tTemp = new ServerTask();

		tTemp.start();

		server.closeServer();
		tClient.interrupt();

		tClient = tTemp;
	}

}
