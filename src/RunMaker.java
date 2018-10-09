import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class RunMaker {
	
	
	FileReader fr;
	static BufferedReader br;
	FileWriter fw;
	BufferedWriter bw =  new BufferedWriter(fw);
	static Scanner s;
	static Runtime runtime = Runtime.getRuntime();  //can be used to get memory info, see code at bottom which is commented out
	//can also be used to to give memory limtation command
	
	
	
	public static Run[] PhaseOne(String inputPath, int recordCount, int split) throws IOException{
		
		
		
		String path = "C://Users/Eden/Documents/Workspace/COMP6521EA/src/input_2.txt"; //input path
		FileReader fr = null;  //Initialize file reader
		Run[] phaseOneRuns = null; //return array of runs object

		int index = 0; //where are we writing to in run data array?
		int pageSize = recordCount/split;  //how many records per run object
		int [] data = new int[pageSize]; //Initialize integer array for run object

		
		try {
			fr = new FileReader(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		br = new BufferedReader(fr);
		s  = new Scanner(br);
		phaseOneRuns = new Run[split];  //create array of runs equal to the number of pieces input file will be split into
		
		
		for (int i = 0; i < split; i++){
		
			while (s.hasNextInt() && (pageSize > index)){
			
				data[index] = s.nextInt(); //read data into int array
				index++; 
		
		}
			
			Arrays.sort(data);  //sort data 
			phaseOneRuns[i] = new Run(data, Integer.toString(i)); //create run, passing integer array to constructor
			data = null;  //clear integer array for memory saving purposes (it is saved in run object/file)
			System.gc();  //call garbage collector 
			index = 0; //re-initialize index
		
		}
		
		s.close();
		br.close();   //close readers
		fr.close();
		return phaseOneRuns;  //return array of run objects
		
	}
	
		
	
	
	//function which takes integer runs and returns a merged run 
	public static Run mergeRuns(Run[] inputRuns, int recordsPerRun) throws IOException{   
		
		int[] data = new int[recordsPerRun * inputRuns.length];  //output buffer?
		
		
		for (Run r : inputRuns){
			
			r.readFromPath(500, 0);  //each run gets its data array populated form its source file
				
		}
		
		
		int min = 0, smallest = 0;  //we assume that the first value of the first run is smallest
		
		for (int i = 0; i < recordsPerRun * inputRuns.length; i++){ //for each location in output buffer
			
			for (int y = 0; y < inputRuns.length; y++){ //for each run, check to see if run is empty, if not, assume its the minimum...
				
				if (!inputRuns[y].empty()){   
					smallest = inputRuns[y].peek();
					min = y; 
					break;
				}
					
			}
			
			for (int x = 0; x < inputRuns.length; x++){
	
				if (inputRuns[x].empty()) continue;  //if run is empty --> skip
				
				if (inputRuns[x].peek() < smallest){  //if run value is smaller than current smallest, it is the new smallest
					smallest = inputRuns[x].peek();
					min = x;
				}
				
			}
			
			
			
			if (!inputRuns[min].empty()){  //if current run (minimum) is not empty, return value at top of array and increment pointer
			data[i] = inputRuns[min].pop();  //push into data array of merged run (output buffer)
			}

		}
		
		return new Run(data, "test");  //create merged run
		
	}
	

	
	
	
	
	
	
}


/*Runtime runtime = Runtime.getRuntime();

//runtime.gc();
System.out.println(index);
System.out.println(runtime.freeMemory()/mega + " free");
System.out.println(runtime.totalMemory()/mega + " total");
System.out.println((runtime.totalMemory() - runtime.freeMemory())/mega + " difference");
System.out.println(mega);
*/
