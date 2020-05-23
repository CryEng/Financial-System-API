/*
 * @Description: This is small bank account program that show some accounts
                 and also create new accouns. program consist on main program
                 which has do while loop and switch statements. first it print 
                 the inital database of bank then it prompt the user to perform
                 different functions. user can create new account, check bal
                 deposit and withdraw amount and can delete the account. once
                 user enter the choice then switch statment call that function 
                 to perfrom different actions. If user enter Q then it will 
                 stop the program and display final database. All output stores 
                 in the file by using FILE IO.

*/


package bank;

import java.io.*;
import java.util.Scanner;

public class Bank {

    public static void main(String[] args) throws IOException {
        
        //constant definitions

        final int MAX_NUM = 50;

        //array of account numbers
        
        int[] acctNumArray = new int[MAX_NUM];

        //array of balances
        
        double[] balanceArray = new double[MAX_NUM];

        //number of accounts
        
        int numAccts;

        //menu item selected
        
        char choice;

        //create Scanner object 
        
        Scanner keyboard = new Scanner(System.in);
        


        // To store results
        
        PrintStream outFile = new PrintStream("myoutput.txt");

     
        // get number of accounts and store result in numAccts 
        
        numAccts = readAccts(acctNumArray, balanceArray, MAX_NUM);
        
        // print initial database 
        
        printAccts(acctNumArray, balanceArray, numAccts, outFile);
        
        outFile.println();

        do {

            menu();
            
            choice = keyboard.next().charAt(0);

            switch (choice) {

                case 'q':
                case 'Q':
                    printAccts(acctNumArray, balanceArray, numAccts, outFile);

                    break;

                case 'b':
                case 'B':
                    balance(acctNumArray, balanceArray, numAccts, outFile,
                            keyboard);

                    break;

                case 'd':
                case 'D':
                    deposit(acctNumArray, balanceArray, numAccts, outFile,
                            keyboard);

                    break;
                    
                case 'w':
                case 'W':
                    withdrawal(acctNumArray, balanceArray, numAccts, outFile,
                            keyboard);

                    break;

                case 'n':
                case 'N':
                    numAccts = newAcct(acctNumArray, balanceArray, numAccts, 
                            outFile, keyboard);

                    break;
                    
                case 'x':
                case 'X':
                    numAccts = deleteAcct(acctNumArray, balanceArray, numAccts, 
                            outFile, keyboard);

                    break;

                default:
                    outFile.println("Error: " + choice + " is an invalid "
                            + "selection -  try again");
                    outFile.println();

                    break;

            }

        } while (choice != 'Q' && choice != 'q');

        //close the output file
        outFile.close();

        //close the scanner 
        
        keyboard.close();

        System.out.println();

    } // end of main
    
    /* Method printAccts:
    
     Input:
        acctNumArray - array of account numbers
        balanceArray - array of account balances
        numAccts - number of active accounts
        outFile - reference to the output file
    
      Process:
               Prints the database of accounts and balances
      Output:
              Prints the database of accounts and balances
     */
    public static void printAccts(int[] acctNumArray, double[] balanceArray, 
            int numAccts, PrintStream outFile) {

        outFile.println();
        outFile.println("\t**********************************");
        outFile.println("\t   Database of Account Accounts");
        outFile.println("\t**********************************");
        outFile.println();
        outFile.println("Account   Balance");
        outFile.println();
        for (int index = 0; index < numAccts; index++) {
            outFile.printf("%7d  $%7.2f", acctNumArray[index], 
                    balanceArray[index]);
            outFile.println();
        }
        outFile.println();

    }

    /* Method menu()
    
	  Input:
                none
	  Process:
                  Prints the menu of transaction choices
	  Output:
                  Prints the menu of transaction choices
     */
    public static void menu() {
        System.out.println();
        System.out.println("Select one of the following transactions:");
        System.out.println("\t****************************");
        System.out.println("\t      Selection         ");
        System.out.println("\t****************************");
        System.out.println("\t     W -- Withdrawal");
        System.out.println("\t     D -- Deposit");
        System.out.println("\t     N -- New Account");
        System.out.println("\t     B -- Balance Inquiry");
        System.out.println("\t     X -- Delete Account");
        System.out.println("\t     Q -- Quit");
        System.out.println();
        System.out.print("\tEnter your selection: ");
    }
    
      /* Method readAccts()
    
      Input:
             acctNumArray - array of account numbers
             balanceArray - array of account balances
             maxAccts -  total accounts limit
              
      Process:
              Reads the initial database of accounts and balances
      Output:
             Fills in the initial account and balance arrays and returns 
             the number of active accounts
     */

    public static int readAccts(int[] acctNumArray, double[] balanceArray, 
            int maxAccts) throws IOException {

        // open database input file
        // create File object
        
        File inputFile = new File("mytestcases.txt");

        // create Scanner object
        
        Scanner keyboard = new Scanner(inputFile);

        // account number counter
        
        int count = 0;

        while (keyboard.hasNext() && count < maxAccts) {
            acctNumArray[count] = keyboard.nextInt();
            balanceArray[count] = keyboard.nextDouble();
            count++;
        }

        // close the input file
        
        keyboard.close();

        // return the account number count
        
        return count;
    }

    /* Method findAcct:
    
	 Input:
               acctNumArray - array of account numbers
               numAccts - number of active accounts
               requestedAccount - requested account requested_number
	 Process:
                  Performs a linear search on the acctNunArray for the 
                  requested account
	 Output:
                If found, the index of the requested account is returned
                Otherwise, returns -1
    
     */
    
    public static int findAcct(int[] acctNumArray, int numAccts, 
            int requestedAccount) {
        
        
        for (int index = 0; index < numAccts; index++) {
            if (acctNumArray[index] == requestedAccount) {
                return index;
            }
        }
        
        return -1;
    }

    /* Method balance:
    
         Input:
             acctNumArray - array of account numbers
             balanceArray - array of account balances
             numAccts - number of active accounts
             outFile - reference to output file
             keyboard - reference to the input 
	 Process:
                 Prompts for the requested account
                 Calls findAcct() to see if the account exists
                 If the account exists, the balance is printed
                 Otherwise, an error message is printed
	 Output:
                If the account exists, the balance is printed
                Otherwise, an error message is printed
     */
    
    public static void balance(int[] acctNumArray, double[] balanceArray, 
            int numAccts, PrintStream outFile, Scanner keyboard) {
        
        
        int requestedAccount, index;

        System.out.println();
        System.out.print("Enter the account number: ");

        // read-in the account number
        requestedAccount = keyboard.nextInt();

        // call findAcct to search if requestedAccount exists
        index = findAcct(acctNumArray, numAccts, requestedAccount);

        // if invalid account
        if (index == -1) {
            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Error: Account number " + requestedAccount 
                    + " does not exist");
            outFile.println();
            outFile.println();
        } else {
            
            // if valid account

            outFile.println("Transaction Requested: Balance Inquiry");
            outFile.println("Account Number: " + requestedAccount);
            outFile.printf("Current Balance: $%.2f", balanceArray[index]);
            outFile.println();
            outFile.println();
        }

        outFile.println();

    }

    /* Method deposit:
    
	 Input:
              acctNumArray - array of account numbers
              balanceArray - array of account balances
              numAccts - number of active accounts
              outFile - reference to the output file
              keyboard - reference to the input
	 Process:
                 Prompts for the requested account
                 Calls findacct() to see if the account exists
                 If the account exists, prompts for the amount to deposit
                 If the amount is valid, it makes the deposit and prints the 
                 new balance.
                 Otherwise, an error message is printed 
         Output:
                For a valid deposit, the deposit transaction is printed
                Otherwise, an error message is printed
    
     */
    
    public static void deposit(int[] acctNumArray, double[] balanceArray, 
            int numAccts, PrintStream outFile, Scanner keyboard) {

        int requestedAccount, index;
        double amountToDeposit;

        System.out.println();
        System.out.print("Enter the account number: ");

        // read-in the account number
        
        requestedAccount = keyboard.nextInt();

        // call findAcct to search if requestedAccount exists
        
        index = findAcct(acctNumArray, numAccts, requestedAccount);

        //invalid account
        
        if (index == -1) {
            outFile.println("Transaction Requested: Deposit");
            outFile.println("Error: Account number " + requestedAccount 
                    + " does not exist");
            outFile.println();
            outFile.println();
            
        } else {
            
            //valid Account

            System.out.print("Enter amount to deposit: ");

            // read-in the amount to deposit
            
            amountToDeposit = keyboard.nextDouble();

            if (amountToDeposit <= 0.00) {

                //invalid amount to deposit
                
                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.println("Amount to Deposit: $" + amountToDeposit);
                outFile.printf("Error: $%.2f is an invalid amount", 
                        amountToDeposit);
                outFile.println();
                outFile.println();
                
            } else {

                outFile.println("Transaction Requested: Deposit");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Current Balance: $%.2f", balanceArray[index]);
                outFile.println();
                outFile.println("Amount to Deposit: $" + amountToDeposit);

                //make the deposit
                
                balanceArray[index] += amountToDeposit;
                outFile.printf("New Balance: $%.2f", balanceArray[index]);
                outFile.println();
                outFile.println();
                
            }
        }
        outFile.println();

    }

    /* Function withdrawal:
     Input:
	   acctNumArray - array of account numbers
	   balanceArray - array of account balances
	   numAccts - number of active accounts
	   outFile - reference to the output file
	   keyboard - reference to the input
      Process:
             Prompts for the requested account
             Calls findAcct() to see if the account exists
             If the account exists, prompts for the amount to withdraw
             If the amount is valid, it withdraws the amount and prints the
             new balance.
             Otherwise, an error message is printed
     Output:
            For a valid withdrawal, the deposit transaction is printed
            Otherwise, an error message is printed
     */

    public static void withdrawal(int[] acctNumArray, double[] balanceArray, 
            int numAccts, PrintStream outFile, Scanner keyboard) {

        int requestedAccount, index;

        double amountToWithdraw = 0;

        System.out.println("Enter your account number: ");
        requestedAccount = keyboard.nextInt();
        
        index = findAcct(acctNumArray, numAccts, requestedAccount);
        
        if (index == -1) {
            
            outFile.println(requestedAccount +" account not found");
            outFile.println();
            outFile.println();

        } else {
            System.out.println("Enter withdraw ammount: ");

            amountToWithdraw = keyboard.nextDouble();
            
            // if withdraw greater than balance
            
            if (amountToWithdraw > balanceArray[index]) {
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error, Withdrawal amount: $%.2f", 
                        amountToWithdraw);
                outFile.print(" \nis higher than account balance of $ " 
                        + balanceArray[index]);
                outFile.println();
                outFile.println();
                
                
            }
               // if withdraw less zero
            
             else if (amountToWithdraw < 0) {
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("Error, Withdrawal amount: $%.2f", 
                        amountToWithdraw);
                outFile.print(" \nis invalid");
                outFile.println();
                outFile.println();
                
                
            }
             // if valid withdraw
             
              else {
                
                
                balanceArray[index] -= amountToWithdraw;
                outFile.println("Transaction Requested: Withdrawal");
                outFile.println("Account Number: " + requestedAccount);
                outFile.printf("current Balance: $%.2f", balanceArray[index]);
                outFile.println();
                outFile.println("Amount to Withdraw: $ " + amountToWithdraw);

                outFile.printf("New Balance: $%.2f", balanceArray[index]);
                outFile.println();
                outFile.println("Withdrawal Successful");
                outFile.println();
                outFile.println();
            }
        }
    }

    /* Function newAcct:
      Input:
	    acctNumArray - array of account numbers
	    balanceArray - array of account balances
	    numAccts - number of active accounts
	    outFile - reference to the output file
	    keyboard - reference to the input
     Process:
       Prompts for the requested account
       Calls findAcct() to see if the account exists
       If the account exists, display error massage
       If the account does not exist, it makes the account and returns
     Output:
       For a valid new account, a success message is printed with the new number
     */

    public static int newAcct(int[] acctNumArray, double[] balanceArray, 
            int numAccts, PrintStream outFile, Scanner keyboard) {

        int index;

        System.out.println("Enter Account Number");

        int requestedAccount = keyboard.nextInt();

        index = findAcct(acctNumArray, numAccts, requestedAccount);

        if (index == requestedAccount) {

            outFile.println("Transaction Requested: New Account");
            outFile.println("Error: Account Number: " + requestedAccount
                    + " Already Exists");
            outFile.println();
            outFile.println();

            return numAccts;

        }
        
        if (requestedAccount < 0 || requestedAccount > 999999) {

            outFile.println("Transaction Requested: New Account");
            outFile.println("Error: Account Number: " + requestedAccount
                    + " is an Invalid Account Number");
            outFile.println();
            outFile.println();
            
            return numAccts;

        }

        acctNumArray[numAccts] = requestedAccount;

        balanceArray[numAccts] = 0.00;

        outFile.println();
        outFile.println("Transaction Requested: New Account");
        outFile.println("Account " + requestedAccount + " Created");
        outFile.println();
        outFile.println();

        return ++numAccts;
    }

    /*
      Method deleteAcct:
      Input:
            acctNumArray - array of account numbers
	    balanceArray - array of account balances
	    numAccts - number of active accounts
	    outFile - reference to the output file
	    keyboard - reference to the input
      Process: 
              Deletes an existing account from the database.
    
    output:
           Display that account number successfully deleted
     */
    
    public static int deleteAcct(int[] acctNumArray, double[] balanceArray, 
            int numAccts, PrintStream outFile, Scanner keyboard) {

        int deleteAccount, index;

        // enter an account you want to delete
        
        System.out.println("Enter the account number to delete:");
        deleteAccount = keyboard.nextInt();

        index = findAcct(acctNumArray, numAccts, deleteAccount);
        
        // Extra credit one
        
        if (index == -1) {
            outFile.println("Accout number " +deleteAccount + " was not found");
            outFile.println();
            outFile.println();
            
        } else {

            for (int i = index; i <= numAccts; i++) {
                acctNumArray[i] = acctNumArray[i + 1];
                balanceArray[i] = balanceArray[i + 1];
            }
            
            numAccts--;
            outFile.println("Transaction Requested: Delete Account ");
            outFile.println("Account Number: " + deleteAccount
                    + " successfully deleted");
            outFile.println();
            outFile.println();
            
        }
        return numAccts;
    }

} // end of class 
