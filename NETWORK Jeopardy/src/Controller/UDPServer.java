package Controller;

import java.io.*;
import java.net.*;

public class UDPServer {
	
	public void startServer() throws Exception {
		
		DatagramSocket serverSocket = new DatagramSocket(9876);
		
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1014];
		
		while(true) {
			
			// RECEIVE DATA
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			
			String sentence = new String(receivePacket.getData());
			InetAddress IPAddress = receivePacket.getAddress();
			
			int port = receivePacket.getPort();
			
			// DO STUFF TO DATA
			
			String capitalizedSentence = sentence.toUpperCase();
			
			// SEND DATA
			
			sendData = capitalizedSentence.getBytes();
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			
			serverSocket.send(sendPacket);
		}
		
	}
	
	
}
