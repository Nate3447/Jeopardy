package Controller;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import Model.Jeopardy;

public class UDPClient {
	
	private static final int BUFFER_SIZE = 1024;
	
	private InetAddress serverAddress;
	private int serverPort;
	//private InetAddress clientAddress;
	private int clientPort;
	private Jeopardy game;
	private byte[] sendData;
	private byte[] receiveData;
	DatagramSocket socket;
	
	public UDPClient(String address, int serverPort, int clientPort) throws Exception {
		serverAddress = InetAddress.getByName(address);
		//serverAddress = InetAddress.getLocalHost();
		this.serverPort = serverPort;
		this.clientPort = clientPort;
	}
	
	public void run() throws Exception {				

		//clientAddress = InetAddress.getLocalHost();
		socket = new DatagramSocket(clientPort);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("What is your name? ");
		String name = scan.nextLine();
		System.out.println("Finding a game for player: " + name + "...\n");
		joinGame(name);
		
		
		boolean gameOver = false;
		boolean youWon = false;
		
		
		
		while(!gameOver) {
			String input = scan.nextLine();
			
			
		}
		
		if(youWon) {
			System.out.println("You won!");
		} else {
			System.out.println("You lost!");
		}
	}
	
	public void joinGame(String name) throws Exception {
		sendData(name);
		System.out.println("Host reached... ");
		DatagramPacket packet = receiveData();
		System.out.println(new String(packet.getData()).trim());
	}
	
	public void sendData(String data) throws Exception {
		sendData = new byte[1024];
		sendData = data.getBytes();
		DatagramPacket packet = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
		socket.send(packet);
		
	}
	
	public DatagramPacket receiveData() throws Exception {
		receiveData = new byte[BUFFER_SIZE];
		
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
		socket.receive(packet);
		
		return packet;
	}

}
