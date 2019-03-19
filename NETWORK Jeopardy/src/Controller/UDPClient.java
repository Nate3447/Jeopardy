package Controller;

import java.io.*;
import java.net.*;

public class UDPClient {
	
	public void startClient() throws Exception {
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		DatagramSocket clientSocket = new DatagramSocket();
		
		InetAddress IPAddress = InetAddress.getByName("hostname");
		
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
	}
	
	public void sendData(String data) {
		
		
		
	}
	
	public void receiveData(String data) {
		
		
		
	}

}
