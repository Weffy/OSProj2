import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OS {

	static HashMap<Integer, Integer> pageTable = new HashMap<Integer, Integer>();
	static Page[] pageObj; //memory
	String tableSizes[];
	
	
	public OS( String filename ) {
		
		parseFile( filename );

	}
	
	private void parseFile(String filename) {
		BufferedReader inputFile = null;
		//open filename and setup BufferedReader...
		
		try {
			inputFile = new BufferedReader( new FileReader( filename ) );
//			System.out.println("File exists...");
		} catch (FileNotFoundException e1) {
			System.out.println("File does not exist...");
		}
		
		//if we get here, then the file is good.
		//need to read in initial line to get numPages & numBytes
		//set the size of the pageObj and data arrays
		
		String initialLine = null;
		try {
			initialLine = inputFile.readLine();
			System.out.println(initialLine);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//split into numPages and numBytes
		tableSizes = initialLine.split(" ");
		int numPages = Integer.parseInt( tableSizes[0] );
		int numBytes = Integer.parseInt( tableSizes[1] );
		System.out.println("numPages: " + numPages);
		System.out.println("numBytes: " + numBytes);
		//set the size of the array
		pageObj = new Page[numPages];
		String reader = null;
		System.out.println(pageObj.length);
		//now that we set the size of the pageObj array
		//read through file and...
		//create pageTable mappings
		//insert data into byte array
		

		
		
		try {
			while ( ( reader = inputFile.readLine() ) != null) {
//				System.out.println("Enter while loop");
				int counter = 0;
				if (isItAMap(reader) ) {
					//if true, then this is data...handle accordingly
					System.out.println("if fired...");
					String[] split = reader.split("\\->");
					pageTable.put( Integer.parseInt( split[0] ),  Integer.parseInt( split[1] ) );
					System.out.println(Integer.parseInt( split[0]));
					System.out.println(Integer.parseInt( split[1]));
				} else {
					//if false, then not data, handle accordingly...
					byte[] data = new byte[numBytes]; 
					data = reader.getBytes();
					//need to use getPPN to figure out where to place the page!!!
					pageObj[counter] = new Page(counter, numBytes, data);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading in lines...");
		}
		


	}

	
	
	private boolean isItAMap(String reader) {
		//regex
		System.out.println("is it data?");
		String strSearch = "d*\\->d*";
		Pattern patSearch = Pattern.compile(strSearch);
		Matcher matSearch = patSearch.matcher(reader);
		return matSearch.find();
	}

	private static int getPPN( int vpn ) {
		return pageTable.get( vpn );
	}
	
	private static byte getDataAtVirtAddress( int virtAddress ) {
		
		return (Byte) null;
	}
	
	public static void main(String[] args) {
		
		OS os = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data1.txt");

	}
	
}
