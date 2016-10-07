import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Page {
	int pageNum;
	byte[] data;
	
	public Page(int counter, int numBytes, byte[] data) {
		this.pageNum = counter;
		this.data = new byte[ numBytes ];
		this.data = data;
	}

	private byte getByte( int offset ) {
		return this.data[offset];
	}
	
	
	public boolean exists( int offset ) {
		if (this.data.length == 0 || offset < 0) {
			return false;
		} else {
			return true;
		}
	}
	
	//this method needs to throw an exception
	//her test code is attempting a catch
	//else it fails
	public byte getData( int offset ) throws Exception {
		
		try {
			return this.getByte( offset );	
		} catch (Exception e1) {
//			System.out.println("invalid offset");
//			throw new Exception("transction: " + transNbr); 
//			throw new Exception("transction: " + transNbr, E); 

//			System.out.println("offset: " + offset + " throwing exception...");
			throw new Exception("invalid offset");
//			byte nullByte = (Byte) null;
//			return nullByte;
		}
		

	}
}
