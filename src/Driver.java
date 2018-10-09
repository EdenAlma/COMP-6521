import java.io.IOException;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		String path = "C://Users/Eden/Documents/Workspace/COMP6521EA/src/input_2.txt";  //path for input file, no header processing yet...
		//just file with tab integers


		Run[] test = null; //array of run objects
		
		
		try {
			
			test = RunMaker.PhaseOne(path, 1000, 2);  //PhaseOne method which reads sorts and writes individual pages
			//path = input source, 2nd parameter = number of records to read, 3rd = how many pieces to split data into
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Run r = RunMaker.mergeRuns(test, 500);   //mergeRuns function takes array of runs and merges them into single run
			//test is the array of runs generated in phase one
			//2nd parameter is the number of records to take from each run
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
				
		
		
	}

}
