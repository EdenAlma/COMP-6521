package PhaseOne;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class PhaseOne {
	public long total_records = 0;
	public int memory_limit = 0;
	public String folder_destination;
	public String file_name_starts_with;
	public String path = "";
	public int total_sub_files = 0;
	public int limit_buffer = 0;
	
	public PhaseOne(String path, String folder_destination, String file_name_starts_with) {
		this.path = path;
		this.folder_destination = folder_destination;
		this.file_name_starts_with = file_name_starts_with;
	}
	
	private boolean createDataFile(int[] data_list, int buffer_size) {
		boolean file_write_success = true;
		try {
			total_sub_files++;
			FileWriter fw = new FileWriter(folder_destination+"/"+ file_name_starts_with +Integer.toString(total_sub_files) + ".txt");
			BufferedWriter bw = new BufferedWriter(fw, buffer_size);
			bw.write(data_list.toString()); 
			
		}catch(Exception e) {
			total_sub_files--;
			file_write_success = false;
			System.out.println("Unable to create a file : " + e.getMessage());
		}
		return file_write_success;
	}
	
	private void extractDetails() {
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String[] info = br.readLine().split("\\s+");
			total_records = Long.parseLong(info[0]);
			memory_limit = Integer.parseInt(info[1].split("[a-zA-Z]")[0]);
			limit_buffer = memory_limit * 1024 * 1024;
		}catch(IOException e) {
		System.out.println(e.getMessage());
		}
	}
	
	public void convertFileDataToStringArray() {
		String data = "";
		int data_int = 0;
		int stop_at = 0;
		try {
			extractDetails();
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr, memory_limit * 1024);
			for(int i = 0; i < 2; i++) {
				br.readLine();
			}
			while(true) {
				while((data_int = br.read()) != -1) {
					data = data + (char)data_int;
					stop_at = stop_at + 8;
					if(stop_at >= limit_buffer) {
						data_int = br.read();
						if(!(data_int >= 48 && data_int <= 57)) {
							break;
						}
						else {
							data = data + (char)data_int;
						}
					}
				}
				String[] data_list_string = data.split("\\s+");
				
				int data_len = data_list_string.length;
				
				int[] data_list = new int[data_len];
				
				for(int i = 0; i < data_len; i++) {
					data_list[i] = Integer.parseInt(data_list_string[i]);
				}
				
				System.out.print(createDataFile(data_list, memory_limit*1024));
				break;
			}
		}catch(IOException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	}