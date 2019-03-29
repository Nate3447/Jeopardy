package Controller;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPClient {
	
	public void startClient() throws Exception {
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		
		DatagramSocket clientSocket = new DatagramSocket();
		
		InetAddress IPAddress = InetAddress.getLocalHost();
		
		Scanner scanner = new Scanner(System.in);
		
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		boolean gameOver = false;
		boolean youWon = false;
		
		System.out.println("Joining game...\n");
		
		while(!gameOver) {
			String input = scanner.nextLine();
			
			
		}
		
		if(youWon) {
			System.out.println("You won!");
		} else {
			System.out.println("You lost!");
		}
	}
	
	public void sendData(String data) {
		
		
		
	}
	
	public void receiveData(String data) {
		
		
		
	}

}
