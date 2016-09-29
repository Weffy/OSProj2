
public class Page {
	int pageNum;
	byte[] data;
	
	public Page(int counter, int numBytes, byte[] data) {
		this.pageNum = counter;
		data = new byte[ numBytes ];
		this.data = data;
	}

	private byte getByte( int offset ) {
		return this.data[offset];
	}
	
	private boolean exists() {
		if (this.data.length == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	
	private byte getData( int offset ) {
		if ( exists() ) {
			return this.getByte( offset );	
		} else {
			System.out.println("Invalid Offset...");
			byte nullByte = (Byte) null;
			return nullByte;
		}
		 
	}
}
