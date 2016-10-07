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
	String tableSizes[]; //just used when we get the initial line of a file to set the number of pages, and the page table size
	
	
	public OS( String filename ) {
		
		parseFile( filename );

	}
	
	//essentially the heavy lifter
	//right now it is a giant method...
	//ideally, we can modularize this so it can call other methods so everything is neater...
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
		
		String initialLine = null;
		try {
			initialLine = inputFile.readLine();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//split into numPages and numBytes
		tableSizes = initialLine.split(" ");
		
		int numPages = Integer.parseInt( tableSizes[0] );
		int numBytes = Integer.parseInt( tableSizes[1] );
		
		//set the size of the array
		pageObj = new Page[numPages];

		//now that we set the size of the pageObj array
		//read through file and...
		//create pageTable mappings
		//insert data into byte array
		
		String reader = null;
		try {
			int counter = 0; //used as the ppn and where we will place the page into the pageObj array 
			while ( ( reader = inputFile.readLine() ) != null) {
				//data will either be a map...or it will be data
				// if = map
				// else = data
				if (isItAMap(reader) ) {
					//if true, then map...
					String[] split = reader.split("\\->");
					pageTable.put( Integer.parseInt( split[0] ),  Integer.parseInt( split[1] ) );

				} else {					
					//if false, then data...
					byte[] data = new byte[numBytes]; 
					data = reader.getBytes();
					pageObj[counter] = new Page(counter, numBytes, data);
					counter++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error reading in lines...");
		}
		


	}
	
	//returns specified page
	public Page getPage(int ppn) {
		return pageObj[ppn];
	}
	
	//uses regex to check if a "->" is present which represents a mapping
	private boolean isItAMap(String reader) {
		//regex
		String strSearch = "d*\\->d*";
		Pattern patSearch = Pattern.compile(strSearch);
		Matcher matSearch = patSearch.matcher(reader);
		return matSearch.find();
	}

	//returns the phys page num based on virt page num
	public int getPPN( int vpn ) {
		return pageTable.get( vpn );
	}
	
	//according to her explanation...
	//the lst 7 bits of the 32 bit virtual address will be used for our virt page num and offset
	//to extract last 4 digits (offset), logical AND with 0b1111 (15 in decimal)
	//to extract the 3 digits that precede the offset, logical AND with 0b1110000 (112 in decimal)
	public byte getDataAtVirtAddress( int virtAddress ) {
//		System.out.println("virtAdd: " + virtAddress);
		//not sure how I am supposed to use this method...
		//extract last 7 bits...
		//2^7-1 = 127
		int extract = virtAddress & 0b1111111;
//		System.out.println("extract: " + extract);
		
		// 2^5 + 2^6 + 2^7
		int vpn = extract & 0b1110000;
		vpn = vpn >> 4;
//		System.out.println("vpn: " + vpn);
		
		//offset is last 4 bits
		int offset = extract & 0b00001111;
//		System.out.println("offset: " + offset);
		
		int ppn = getPPN(vpn);
//		System.out.println("ppn: " + ppn);
		
		Page page = this.getPage(ppn);
		
		return page.getData(offset);
		
	}
	
	//random garbage and test cases...
	//not bothering to clean this up...
	public static void main(String[] args) {
		System.out.println("Data 1");
		OS os1 = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data1.txt");
		System.out.println("\n\n\n\nData 2");
		OS os2 = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data2.txt");
		System.out.println("PPN " + os1.getPPN(2));
		System.out.println("PPN " + os2.getPPN(2));
		
		System.out.println("os1: " + os1.getPage(0).getData(0));
		
		System.out.println("os2: " + os2.getPage(0).getData(0) + "\n");
		
//		OS os1 = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data1.txt");
//		System.out.println(os1.getPPN(2) == 5);
//		System.out.println(pageTable.get(0));
		
//		OS os2 = new OS("/Users/Krirk-Mac/Documents/workspace/OSProj2/src/proj2_data2.txt");
		
		System.out.println(os2.getPage(4).getData(3) == 114);
		System.out.println(os2.getDataAtVirtAddress(45) == 115);
//		System.out.println(os2.getPage(2).getData(13) == 115);
		//45 = 101101 32 + 8 + 4 + 1


	}
	
}
