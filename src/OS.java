import java.util.HashMap;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OS {
	int numPages;
	int numBytes;
	Page [] pageObj = new Page[numPages]; //OS: Bullet 1
	static HashMap <Integer, Integer> pageTable = new HashMap<Integer,Integer>(); //OS: Bullet 2
	static HashMap <Integer, Integer> keyLookup = new HashMap<Integer,Integer>(); //OS: Bullet 2
	String [] tableSizes;
	
	public OS(String filename){ //OS: Bullet 5
		List <String> table = parseFile(filename);
		populatePageTable(table);
		populatePageObjects(table);
	}
	
	//returns the phys page num based on virt page num
	public int getPPN(int vpn){ //OS: Bullet 3
		return pageTable.get(vpn);
		
	}
	
	public Page getPage(int ppn) throws Exception{
		if (ppn >= pageObj.length) {
			throw new Exception("invalid offset");
		} else {
			return pageObj[ppn];
		}
	}
	
	public static double numOfBits(int bitLength) {
		double exponent = 0;
		while ( true ) {
			if ( Math.pow(2, exponent) < bitLength ) {
				exponent++;
				
			} else {
				return exponent;
			}
		}
	}
	
	public byte getDataAtVirtAddress(int virtAddress) throws Exception { //OS: Bullet 4
	
		//determine size of virtAddress
		
		double vaLen, offsetLen, vpnLen; //bits necessary
		vpnLen = numOfBits(numPages);
		offsetLen = numOfBits(numBytes);
		vaLen = vpnLen + offsetLen;
//		System.out.println("virtAddress: " + virtAddress);
//		System.out.println("vaLen: " + vaLen);
//		System.out.println("vpnLen: " + vpnLen);
//		System.out.println("offsetLen: " + offsetLen);


//		System.out.println("virtAdd: " + virtAddress);
		//not sure how I am supposed to use this method...
		//extract last 7 bits...
		//2^7-1 = 127
		int logic4Extract = (int) Math.pow(2, vaLen)-1;
//		System.out.println(logic4Extract);
		int extract = virtAddress & logic4Extract;
//		System.out.println("extract: " + extract);
		
		// 2^5 + 2^6 + 2^7
		int logic4VPN = (int) Math.pow(2, vpnLen)-1;
		System.out.println(logic4VPN);
//		System.out.println("extract " + extract);
		int tempExtract = extract;
		tempExtract = tempExtract >> (int) offsetLen;
//		System.out.println("tempExtract " + tempExtract);
		int vpn = tempExtract & logic4VPN;
		
		System.out.println("vpn: " + vpn);
		
		//offset is last 4 bits
		int logic4Offset = (int) Math.pow(2, offsetLen)-1;
//		System.out.println(logic4Offset);
		int offset = extract & logic4Offset;
//		System.out.println("offset: " + offset);
		
		int ppn = getPPN(vpn);
//		System.out.println("ppn: " + ppn);
		
		Page page = null;
		
		

		
		
		
		
		
		try {
			page = this.getPage(ppn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		try {
			return page.getData(offset);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (Byte) null;

		
	}
	
	public List<String> parseFile(String filename){
		List<String> lines = null;
		try{
			lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
//			System.out.println("Printing file...\n");
//			for (String line:lines){
//				System.out.println(line);
//			}
//		System.out.println("\nEnd of file...\n");
		
		}catch (IOException e){
			e.printStackTrace();
		}
		return lines;
	}
	
	public void populatePageTable(List<String> pageData){
		
		for (String line:pageData /*int i = 0; i < pageData.size()-1; i++*/){
//			String line = pageData.get(i);
			if(line == pageData.get(0)){
				tableSizes = line.split(" ");
				numPages = Integer.parseInt( tableSizes[0] );
				numBytes = Integer.parseInt( tableSizes[1] );
				//initialize the physMem (pageObj array)
				pageObj = new Page[numPages];
//				System.out.println("pages: " + numPages);
//				System.out.println("offset: " + numBytes);
//				continue;	
			}
				
			if(isItAMap(line)){
				//if true, then map...
				String[] split = line.split("\\->");
				pageTable.put( Integer.parseInt( split[0] ),  Integer.parseInt( split[1] ) );
				keyLookup.put( Integer.parseInt( split[1] ),  Integer.parseInt( split[0] ) );
			} 
			
			
		}
//		System.out.println("\nPage Table\n");
//		for (int key : pageTable.keySet()) {
//		    System.out.println(key + " " + pageTable.get(key));
//		}
//		System.out.println("\nkey table\n");
//		for (int value : keyLookup.keySet()) {
//			System.out.println(value + " " + keyLookup.get(value));
//		}
	}
	public void populatePageObjects(List<String> pageData) {
		int ppn = 0;
//		System.out.println("size: " + pageData + "\n");
		
		for (String line : pageData) {
//			System.out.println(line);
			
			if ( isItString(line) ){						
//					if false, then data...
//					System.out.println("Physical page: " + ppn);
					int vpn = keyLookup.get(ppn);
//					System.out.println("\nppn: " + ppn + "\nvpn: " + vpn);
					
					
//					System.out.println("count: " + ppn);
					pageObj[ppn] = new Page(vpn, line, numBytes);
					ppn++;
	
					
			}

		}
	}

	//uses regex to check if a "->" is present which represents a mapping
	private boolean isItAMap(String reader) {
		//regex
		String strSearch = "d*\\->d*";
		Pattern patSearch = Pattern.compile(strSearch);
		Matcher matSearch = patSearch.matcher(reader);
		return matSearch.find();
	}
	
	//uses regex to check if the string contains just letters
	private boolean isItString(String reader) {
		String strSearch = "[a-z]";
		Pattern patSearch = Pattern.compile(strSearch);
		Matcher matSearch = patSearch.matcher(reader);
//		System.out.println("is it a string? " + matSearch.find() + "\n");
		return matSearch.find();
	}
	

	public static void main(String [] arg){
	
//		OS test1 = new OS("proj2_data2.txt");
//		System.out.println(test1.getPPN(111));
		/*
		 * Part 1/1a
		 */
//		OS os = new OS("proj2_data1.txt");
		
		System.out.println("Part 1/1a");
		//test 1
//		System.out.println("\nTest 1");
//		System.out.println(os.getPPN(2) == 5); 
//		
//		//test 2
//		System.out.println("\nTest 2");
//		System.out.println(os.getPage(0).getData(11) == 115);
//
//		//test 3
//		System.out.println("\nTest 3");
//		System.out.println(os.getDataAtVirtAddress(0) == 110);
//		
//		//test 4
//		System.out.println("\nTest 4");
//		System.out.println(os.getDataAtVirtAddress(5) == 99);

		//test 5
//		System.out.println("\nTest 5");
//		System.out.println(os.getDataAtVirtAddress(21) == 115);
//		int ppn = os.getPPN(1);
//		
//		System.out.println(os.getPage(ppn).getData(5) == 115);
		OS os = new OS("proj2_data3.txt");
//		try {
//			os.getPage(15).getData(100);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			os.getPage(4).getData(-99);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
