
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
	
	
	private boolean exists( int offset ) {
		if (this.data.length == 0 || offset <= 0) {
			return false;
		} else {
			return true;
		}
	}
	
	//this method needs to throw an exception
	//her test code is attempting a catch
	//else it fails
	public byte getData( int offset ) {
		if ( exists( offset ) ) {
			return this.getByte( offset );	
		} else {
			System.out.println("Invalid Offset...");
			byte nullByte = (Byte) null;
			return nullByte;
		}
		 
	}
}
