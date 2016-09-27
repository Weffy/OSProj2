import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OS {

	static HashMap pageTable; 
	Page[] pageObj; //memory
	String tableSizes[];
	
	
	public OS( String filename ) {
		
		parseFile( filename );

	}
	
	private void parseFile(String filename) {
		BufferedReader inputFile = null;
		//open filename and setup BufferedReader...
		
		try {
			inputFile = new BufferedReader( new FileReader( filename ) );
		} catch (FileNotFoundException e1) {
			System.out.println("File does not exist...");
		}
		
		//if we get here, then the file is good.
		//need to read in initial line to get numPages & numBytes
		//set the size of the pageObj and data arrays
		
		String initialLine = inputFile.readLine();
		tableSizes = initialLine.split(" ");
		int numPages = Integer.parseInt( tableSizes[0] );
		int numBytes = Integer.parseInt( tableSizes[1] );
		
		pageObj = new Page[numPages];
		String reader = null;
		while ( ( reader = inputFile.readLine() ) != null) {
//			pageObj[]
		}
		
		
		
		
		
		
		
		//if we get here, then data should be in a list of strings called listData...
		//now we need to convert strings to a Byte array
//		byte[] byteData = new byte[ listData.size() ];
//		for (byte data1 : byteData) {
//			
//		}
//		
//		return byteData;

	}

	
	
	
	
	
	
	
	private static int getPPN( int vpn ) {
		return pageTable.get( vpn );
	}
	
	private static byte getDataAtVirtAddress( int virtAddress ) {
		
		return (Byte) null;
	}
	
	public static void main(String[] args) {
		
		
	}
	
}
