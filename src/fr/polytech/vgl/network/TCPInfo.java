package fr.polytech.vgl.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPInfo {
	
	protected ServerSocket server;
	protected Socket client;
	protected Integer port = 8080;
	protected String ip = "Localhost";
	
	protected InetSocketAddress address;
	
	protected ObjectOutputStream outputStream;
	protected ObjectInputStream inputStream;
	
	
	public static boolean available(String ip, int port) {
	    try (Socket ignored = new Socket(ip, port)) {
	        return false;
	    } catch (IOException ignored) {
	        return true;
	    }
	}
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		
		
		this.ip = ip;
	}
	
	public ServerSocket getServer() {
		return server;
	}
	public void setServer(ServerSocket server) {
		this.server = server;
	}
	public Socket getClient() {
		return client;
	}
	public void setClient(Socket client) {
		this.client = client;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public InetSocketAddress getAddress() {
		return address;
	}
	public void setAddress(InetSocketAddress address) {
		this.address = address;
	}
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	public void setOutputStream(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}
	public ObjectInputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	
	   

}