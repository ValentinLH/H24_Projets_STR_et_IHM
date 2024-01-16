package fr.polytech.vgl.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient extends TCPInfo
{
	
        
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
        
    /*
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
	*/
    
	
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
        }
    	catch (Exception exc)
    	{
    		
    	}
    }
}