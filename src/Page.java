

public class Page {
	int vpn; //virt page #
	byte[] data;
	
	public Page(int vpn, String line, int size) {
		this.vpn = vpn;
		this.data = new byte[ size ];
//		System.out.println("line: " + line);
		this.data = line.getBytes();
//		System.out.println("size: " + data.length);
	}
	
	//this method needs to throw an exception
	//her test code is attempting a catch
	//else it fails
	public byte getData( int offset ) throws ArrayIndexOutOfBoundsException {
//		System.out.println("offset: " + offset);
	
	if (offset < 0 || offset > data.length) {
		throw new ArrayIndexOutOfBoundsException("invalid offset");
	} else {
		return this.data[offset];
	}
			
	}
	public String returnData() {
		String line = data.toString();
		return line;
	}
	
	public int dataSize() {
		return data.length;
	}
	

}
