package fr.polytech.vgl.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 *  TCPClient is a Class for the encapsulation of a TCP client
 *  @version 03/03/24
 */
public class TCPClient extends TCPInfo
{
	
	private List<NetworkObserver> observers = new ArrayList<>();    
	
    //builders
    public TCPClient() 
    {
    	client = null;
        outputStream = null;
        inputStream = null;
        port = 0;
        address = null;
    } 
    
    public TCPClient(String address,Integer port)
    {
    	updateAddress( address,port); 
    }
        
    public void updateAddress(String address,Integer port)
    {
    	try {
    		this.address = new InetSocketAddress(address, port);
    		this.port = port;
    		this.ip = address;
    		
    	}
    	catch (Exception exc)
    	{
    		this.address = new InetSocketAddress(this.ip, this.port);
    	}
        
    }
    
	@Override
	public void setIp(String ip) {
		 updateAddress(ip,this.port);
	}
	
	@Override
	public void setPort(Integer port) {
		 updateAddress(this.ip,port);
	}
	
    public void setSocketConnection() 
    {
        try 
        {
        	client = new Socket(address.getHostName(), address.getPort());
            outputStream = new ObjectOutputStream(client.getOutputStream());
            inputStream = new ObjectInputStream(client.getInputStream());
            client.setSoTimeout(0);
        } 
        catch (IOException exc) 
        {
        	//exc.printStackTrace();
        	notifyNetworkError(exc);
        }
    }
    
    public void closeClient() 
    {
    	try 
        {
    		if(outputStream != null)
    		{
        		outputStream.close();
    		}
    		if(inputStream != null)
    		{
                inputStream.close();
    		}
    		if(client != null)
    		{
    			client.close();
    		}
    		System.out.println("[+] Client Closed");
            
        } 
        catch (IOException exc) 
        {
        	System.out.println("[-] Error Closing Client");
        	//exc.printStackTrace();
        	notifyNetworkError(exc);
        }
    	catch (Exception exc)
    	{
    		notifyNetworkError(exc);
    	}
    }


    public void addObserver(NetworkObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(NetworkObserver observer) {
        observers.remove(observer);
    }

    public void notifyObjectReceived(Object receivedObject) {
        for (NetworkObserver observer : observers) {
            observer.onObjectReceived(receivedObject);
        }
    }

    public void notifyNetworkError(Exception e) {
        for (NetworkObserver observer : observers) {
        	 observer.onNetworkError(e);
        }
    }
}

