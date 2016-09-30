import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OS {

	static HashMap<Integer, Integer> pageTable = new HashMap<Integer, Integer>(); //(vpn, ppn)
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
			
			System.out.println("File exists...");
		} catch (FileNotFoundException e1) {
			System.out.println("File does not exist...");
		}
		
		//if we get here, then the file is good.
		//need to read in initial line to get numPages & numBytes
		//set the size of the pageObj and data arrays
		
		String initialLine = null;
		try {
			initialLine = inputFile.readLine();
			System.out.println("Initial Line: " + initialLine);
//			System.out.println(initialLine);
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
		System.out.println("pageObj array length: " + pageObj.length);
		//now that we set the size of the pageObj array
		//read through file and...
		//create pageTable mappings
		//insert data into byte array
		

		
		
		try {
			int counter = 0;
			while ( ( reader = inputFile.readLine() ) != null) {
//				System.out.println("Enter while loop");
				System.out.println("\nreader: " + reader);
				if (isItAMap(reader) ) {
					//if true, then this is data...handle accordingly
					System.out.println("it IS a map!\n");
					String[] split = reader.split("\\->");
					pageTable.put( Integer.parseInt( split[0] ),  Integer.parseInt( split[1] ) );
					System.out.println("virt add: " + Integer.parseInt( split[0]));
					System.out.println("phys add: " + Integer.parseInt( split[1]));
				} else {
					System.out.println("We have data!\n");					
					System.out.println("pageObj aka phys memory: " + counter + "\n");
					
					//if false, then not data, handle accordingly...
					byte[] data = new byte[numBytes]; 
//					System.out.println("Reader: " + reader);
//					System.out.println( new String(data) );
					data = reader.getBytes();
//					System.out.println("Reader: " + reader);
					System.out.println( "data: " + new String(data) );
					pageObj[counter] = new Page(counter, numBytes, data);
					counter++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading in lines...");
		}
		


	}

	public Page getPage(int ppn) {
		return pageObj[ppn];
	}
	
	private boolean isItAMap(String reader) {
		//regex
//		System.out.println("is it data?");
		String strSearch = "d*\\->d*";
		Pattern patSearch = Pattern.compile(strSearch);
		Matcher matSearch = patSearch.matcher(reader);
		return matSearch.find();
	}

	public int getPPN( int vpn ) {
		return pageTable.get( vpn );
	}
	
	public static byte getDataAtVirtAddress( int virtAddress ) {
		//not sure how I am supposed to use this method...
		return (Byte) null;
	}
	
	public static void main(String[] args) {
		System.out.println("Data 1");
		OS os1 = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data1.txt");
		System.out.println("\n\n\n\nData 2");
		OS os2 = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data2.txt");
//		System.out.println("PPN " + os1.getPPN(2));
//		System.out.println("PPN " + os2.getPPN(2));
//		System.out.println(os1.getPage(0).getData(0));
//		
//		OS os = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data2.txt");
//		System.out.println(os.getPage(4).getData(3) == 114);
		
//		OS os = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data1.txt");
//		System.out.println(os.getPPN(2) == 5);
//		System.out.println(pageTable.get(0));
	}
	
}
