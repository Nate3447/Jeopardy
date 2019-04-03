package Controller;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import Model.Jeopardy;

public class UDPClient {
	
	private static final int BUFFER_SIZE = 1024;
	private static final String END_CODE = "11203447";
	private static final int TIMEOUT = 1000;
	
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
		socket.setSoTimeout(TIMEOUT);
		
		Scanner scan = new Scanner(System.in);
		System.out.println("What is your name? ");
		String name = scan.nextLine();
		System.out.println("Finding a game for player: " + name + "...\n");
		joinGame(name);
		
		
		boolean canRespond = false;
		boolean gameOver = false;
		String message = null;
		
		
		while(!gameOver) {
			
			DatagramPacket packet = receiveData();
			message = getPacketMessage(packet);
			if(message.equals(END_CODE)){
				gameOver = true;
			} else {
				System.out.println(message);
				canRespond = isWaiting(packet);
				if(canRespond) {
					String input = scan.nextLine();
					sendData(input);
				} 
			}
		}
	}
	
	public void joinGame(String name) throws Exception {
		sendData(name);
		System.out.println("Host reached... ");
		
	}
	
	public String readPacket(DatagramPacket packet) {
		String message = new String(packet.getData()).trim();
		return message;
	}
	
	public String getPacketMessage(DatagramPacket packet) {
		String message = readPacket(packet);
		
		// return substring of message
		message = message.substring(1);
		
		return message.trim();
	}
	
	public boolean isWaiting(DatagramPacket packet) {
		boolean isWaiting = false;
		String message = readPacket(packet);
		
		// Get first char of message
		message = message.substring(0, 1);
		if(message.equals("1")){
			isWaiting = true;
		}
		
		return isWaiting;
	}
	
	public void sendData(String data) throws Exception {
		sendData = new byte[BUFFER_SIZE];
		receiveData = new byte[BUFFER_SIZE];
		sendData = data.getBytes();
		boolean timedOut = true;
		DatagramPacket packet = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);

		while(timedOut) {
			try {
				socket.send(packet);
				
				// STOP AND WAIT
				DatagramPacket ack = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(ack);
				timedOut = false;
			} catch(SocketTimeoutException exception) {
				System.out.println("Packet send timed out. Resending... ");
			}
		}
	}
	
	public DatagramPacket receiveData() throws Exception {
		receiveData = new byte[BUFFER_SIZE];
		boolean timedOut = true;
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);

		while(timedOut) {
			try {
				socket.receive(packet);
				timedOut = false;
			} catch(SocketTimeoutException exception) {
				
			}
		} 
		return packet;
	}

}
