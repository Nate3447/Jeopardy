package Controller;

import java.util.ArrayList;

import Model.*;

public class Main {
	
	public static void main(String[] args) throws Exception {

		UDPClient client;
		UDPServer server;
		
		
		boolean isServer = true;
		
		if (isServer) {
			server = new UDPServer();
			server.startServer();
			
		} else {
			
			client = new UDPClient();
			
		}
		
	}

}
