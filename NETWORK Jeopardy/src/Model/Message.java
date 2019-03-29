package Model;

public class Message {
	
	private String srcAddress;
	private String destAddress;
	private String srcePort;
	private String destPort;
	private String type;
	private String data;
	
	public Message() {
		
	}
	
	public Message(byte[] message){
		
	}
	
	public byte[] getBytes() {
		byte[] message = new byte[1024];
		
		return message;
	}
	
}
