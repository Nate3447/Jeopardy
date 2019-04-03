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
		
		boolean canRespond = false;
		boolean gameOver = false;
		boolean youWon = false;
		
		
		
		while(!gameOver) {
			
			DatagramPacket packet = receiveData();
			System.out.println("PRINT DATA HERE");
			
			if(canRespond) {
				String input = scan.nextLine();
				sendData(input);
			} 
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
	
	public void hitBuzzer() throws Exception {
		
	}
	
	public String readPacket(DatagramPacket packet) {
		String data = new String(packet.getData()).trim();
		return data;
	}
	
	public String getPacketMessage(String data) {
		String message = null;
		
		return message;
	}
	
	public int getPacketType(String data) {
		int type = 0;
		
		return type;
	}
	
	public void canReply(String message) {
		
	}
	
	public void sendData(String data) throws Exception {
		sendData = new byte[BUFFER_SIZE];
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
