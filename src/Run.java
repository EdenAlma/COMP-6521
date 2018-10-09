import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


//Run object which helps track and manage data and identify run



public class Run {
	
	public static String outputDirectory = "C://Users/Eden/Documents/Workspace/COMP6521EA/src"; 
	//directory where runs are read and written too after phase one
	
	private String id; //name of run
	private int pointer; //pointer in data array
	private int offset; //location where data is read from in file
	private int pass; //which pass is the run created in
	private String path; //where file is stored
	private boolean merged; //flag to indicate that this file has been completely merged
	public int[] data; //array which temporarily holds data (acts as stack)
	public int recordsInRun; //total number of records in file
	
	
	
	/**
	 * 
	 * @param input array of sorted values // to be written to disc
	 */
	public Run(int[] input, String id){    //constructor for run object, takes in array of ints and id string to identify and name file
		
		System.out.println("Creating run:" + id );  //message to help with debugging, to be removed
		this.pointer = 0;  //values which points to position of data array
		this.id = id;  //name of the object
		this.path = outputDirectory + "/" + id + ".txt";  //location of file
		this.data = input;  //setting data input array for run
		
		try {
			writeToPath();   //function which writes data to file
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("writing of files failed");
		}
		
		
		
	}
	
	
	public Run(Run[] runsToMerge){  //constructor not used at the moment
		
	}
	
	
	public void writeToPath() throws IOException{
		
		File file = new File (this.path);  //create file to write data to
		
		if (!file.exists()){
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw, (32 + (6 * this.data.length))); //size of buffer is equal to size of integer array and tabs
		
		for (int x : this.data){
			
			bw.write((Integer.toString(x) + '\t'));  //write data to file
			
		}
		
		this.data = null;  //clear data array for memory usage
		bw.close(); //close writer
		fw.close();
		System.gc();  //run garbage collector to get memory back
		
		
	}
	
	
	//function which reads data from run file to array, needed for merge function to work
	//recordsToRead, number of records to put into data array of run
	//readOffset where to read from in input file 10 bytes per int since int = 8 bytes and '\t' is 2 bytes
	public void readFromPath(int recordsToRead, int readOffset) throws IOException{  
		
		int index = 0;  //location to write into array
		this.data = new int[recordsToRead];  //creatae data array to hold data from file
		
		FileReader fr  = null;
		try {
			fr = new FileReader(this.path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BufferedReader br = new BufferedReader(fr);
		Scanner s  = new Scanner(br);
		
			
		while (s.hasNextInt() && (recordsToRead > index)){
			
			this.data[index] = s.nextInt();  //read file into array
			index++;
		}
		
		
		fr.close();   //cleared reader objects
		br.close();
		s.close();
		
		System.gc(); //call garbage collector to free memory
		
		
	}
	
	
	public int peek(){  //checks the value of where we are in the run data array
		return this.data[this.pointer];
	}
	
	public int pop(){  //gets value from where pointer is pointing and increments value
		System.out.println("Run " + this.id + " pops: " + this.pointer);  //output for debugging 
		int top = this.data[this.pointer];
		this.pointer++;
		return top;
	}
	
	public boolean empty(){
		
		if (this.pointer == (this.data.length)){  //checks if pointer haas went through entire array already
		return true;
		
		}
		
		return false;
	}
	
	

}
