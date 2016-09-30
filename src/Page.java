
public class Page {
	int pageNum;
	byte[] data;
	
	public Page(int counter, int numBytes, byte[] data) {
		this.pageNum = counter;
		this.data = new byte[ numBytes ];
//		System.out.println( "this data: " + new String(this.data) );
//		System.out.println( "In Page class: " + new String(data) );
//		System.out.println( "data: " + new String(data) );
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
	
	
	public byte getData( int offset ) {
		if ( exists() ) {
			return this.getByte( offset );	
		} else {
			System.out.println("Invalid Offset...");
			byte nullByte = (Byte) null;
			return nullByte;
		}
		 
	}
}
