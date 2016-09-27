
public class Page {
	int pageNum;
	static byte[] data;
	
	public Page( int pageNum, int size ) {
		this.pageNum = pageNum;
		data = new byte[ size ];
	}
	
	private static boolean exists() {
		if (data.length == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	private static byte getData( int offset ) {
		if ( exists() ) {
			return data[ offset ];	
		} else {
			System.out.println("Invalid Offset...");
			byte nullByte = (Byte) null;
			return nullByte;
		}
		 
	}
}
