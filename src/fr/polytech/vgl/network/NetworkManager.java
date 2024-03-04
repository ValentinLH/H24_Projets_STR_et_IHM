package fr.polytech.vgl.network;

import javax.realtime.*;

public class NetworkManager {


    private TCPServer server;
    private TCPClient client;
    private RealtimeThread tServer;
    private RealtimeThread tClient;

    public NetworkManager() {
    	  server = new TCPServer(8080);
          client = new TCPClient("localhost", 8081);
          start(); 
    }

    
    public NetworkManager(int serverPort , String clientIp, int clientPort,NetworkObserver observer ) {
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
         
    	 //tServer = TCPOpeningServer();
         //tClient = TCPOpeningClient();	
    }
    
    public void addObserver(NetworkObserver observer) {
    	client.addObserver(observer);
    }

    public void removeObserver(NetworkObserver observer) {
    	client.removeObserver(observer);
    }
    
    
    @Override
    protected void finalize() {
        System.out.print("Destroyed ");
		server.closeServer();
		client.closeClient();
		tServer.interrupt();
		tClient.interrupt();
      }
    
    
    private class ServerTask extends RealtimeThread{
        public void run() {
            try {
                server.setServerConnection();
            } catch (Exception exc) {
                exc.printStackTrace();
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

    
	/**
	 * TCPOpeningServer open the server
	 * @return thread
	 */
	public Thread TCPOpeningServer() {
		Thread t = new Thread(new Runnable() {
			public void run() {

				try {
					server.setServerConnection();
				} catch (Exception exc) {
					//
					exc.printStackTrace();
				}

			}
		});
		t.start();
		return t;
	}



	/**
	 * TCPOpeningClient open the client
	 * @return thread
	 */
	public Thread TCPOpeningClient() {
		Thread t = new Thread(new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					try {

						while (true) {

							// while ()
							client.setSocketConnection();

							Object obj = client.getInputStream().readObject();

						   if (obj != null) {
	                            client.notifyObjectReceived(obj);
	                        }
							
							client.closeClient();

						}

					}

					catch (Exception exc) {
						// System.out.println("Client> Closed");
					}
				}
			}

		});
		t.start();
		return t;
	}
	
    public boolean sendObject(Object obj)
    {
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
    public void setServerPort(int serverPort) {
        server.setPort(serverPort);
    }

    /**
     * Set the IP address for the client.
     *
     * @param clientIp The IP address to set.
     */
    public void setClientIp(String clientIp) {
        client.setIp(clientIp);
    }

    /**
     * Set the port for the client.
     *
     * @param clientPort The port to set.
     */
    public void setClientPort(int clientPort) {
        client.setPort(clientPort);
    }
	
}
