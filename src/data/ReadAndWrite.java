package data;
import java.util.Scanner;
import java.io.*;

public class ReadAndWrite {
	// Read details from a file
	public static Scanner readDetails(String file) {
		Scanner input = new Scanner(System.in);
		try {
			File ReadFile = new File(file);
			input = new Scanner(ReadFile);
		} catch (FileNotFoundException exception) {
			System.out.println("Error reading the file " + file);
		}
		return input;
	}
	
	// Write details to a file
	public static void writeDetails(String file, String input) {
		try {
			// create a file writer and append text in the end
			FileWriter fwriter = new FileWriter(file, true);
			// format output to text
			PrintWriter output = new PrintWriter(fwriter);
			output.println(input);
			output.close();
		} catch (IOException exception) {
			// failed IO operation
			System.out.println("Error writing to file " + file);
		}			
	}
	//Method to empty file
	public static void emptyFile(String file) {
		try {
			// Close the file
			new FileOutputStream(file).close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
