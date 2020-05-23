package customer_package;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class customer_data 
{ 
	// Only accessible within this package
	
	
	// Name belongs only within customer_package
	private String first_name;
	private String last_name;
	private String name;
	private int social;
	// Non-static, dont need object, value does not persist
	private int k = 0;
	// Only accessible within this package
	private final int SIZE = 100;
	private String all_names[][] = new String[SIZE][SIZE];
	private HashSet<Integer> social_num = new HashSet<Integer>();
	Scanner sc = new Scanner(System.in);
	void customer_info() throws IOException
	{
		// Requires FileWriter package and IOException, write to file
		File input = new File("data.txt");
		FileWriter myWriter = new FileWriter(input);
		// Sentinel loop, takes 2 elements for name
		int p = 0;
		
		while(all_names.length <= SIZE)
		{
		 do{ // needs exception
		if(k==0) { System.out.print("Name: ");
				   // Stores first name
				   first_name = sc.next(); 
				 }
		k++;	  // Stores last name
		if(k==1) { 
		last_name = sc.next();
			 }		   
		} while(k!=2);
	//	myWriter.write(String.format("First Name: %s\n Last Name: " + last_name , first_name));
		name = first_name+ " " + last_name;
		Random store = new Random();
		int number = store.nextInt(SIZE);
		all_names[number][number] = name;
		File customer = new File(name+number+".txt");
		if(!customer.exists()) customer.createNewFile();
		FileWriter staticWriter = new FileWriter(customer);
		staticWriter.write(String.format("First Name: %s\n Last Name: " + last_name , first_name));
//		staticWriter.write("\nSocial Security: " + social);
	/*	Random store = new Random();
		int number = store.nextInt(SIZE);
		all_names[number][number] = name; */
		System.out.print("9-Digit Social Security Number: "); // needs exception
		social = sc.nextInt();
		staticWriter.write("\nSocial Security: " + social);
		if(social_num.contains(social)) { System.out.println("This social security number is associated with another name");
		continue;}
		social_num.add(social);
		staticWriter.close();
	/*	File customer = new File(name+number+".txt");
		if(!customer.exists()) customer.createNewFile();
		myWriter.write("\nSocial Security: " + social); */
		}
		myWriter.close();
		
	}
	void search_customer()
	{
		for(int i = 0; i<all_names.length; i++)
		{
			for(int j = 0; j<all_names.length; j++)
			{
				if(all_names[i][j]!=null)
					{System.out.println("Name Addresses: " + i 
										  + " " + j + "\nName: " + name);
					all_names[i][j] = name;	
					}
			}
		}
	}
	// Default constructor to refer to individual objects
	customer_data(){}
	// Constructor to refer to all objects
	customer_data(String newName,int newSocial,int newBirthday)
	{
		
	}
	// Main method for testing
	public static void main(String[] args) throws IOException
	{
		// Constructs new static customer_data that can be defined for the program and used for reference
		customer_data customerObj = new customer_data(); 
		customerObj.customer_info();
		customerObj.search_customer();
	}
}