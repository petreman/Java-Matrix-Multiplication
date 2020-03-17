/**
 * AUCSC 350 Winter 2019
 * Programming Assignment #1: Matrix Multiplication
 * 
 * By Matthew Barney (1560206) and Keegan Petreman (1528679)
 * Started February 5, 2019
 * Completed February 18, 2019
 * 
 * Public Methods:
 *  - main() -> void
 *     It's main. All it does here is call the matrixTesting() function
 * 
 *  - matrixTesting() -> void
 *      This function runs through the testing for our matrix class, and 
 *      demonstrates SerialReg, SerialTrans, ManyThreads and RowThreads, as
 *      well as each one's execution time
 * 
 *  - validSize(int colA, int rowB) -> boolean
 *      checks if the two inputted matrices are compatible to be multiplied, 
 *      by taking as input the number of columns in the first matrix 
 *      (left hand side of multiplication) and then the number of rows 
 *      in the second matrix (right hand side of multiplication). Checks if 
 *      colA = rowB, and returns true if they are, false otherwise
 * 
 */
package matrixmult;

import java.util.Scanner;

/**
 * Main controls everything but it is here where user 
 * input is accepted and stored to create the matrices
 * @author Keegan
 */
public class MatrixMult {

    /**
     * @param args the command line arguments (none)
     */
    public static void main(String[] args) {
        
        matrixTesting();
        
    }//main
    
    /**
     * Main function to demonstrate how the functions we created work, as well
     * as their speed. Calls validSize to ensure the matrices can be multiplied
     * and prompts the user if they wish to have matrices printed, fill the
     * matrices randomly or manually, and if they wish to have the testing
     * and timing done once, or if they want it timed 3 times and averaged.
     */
    public static void matrixTesting(){
        
        //variables
        int rowA, colA, rowB, colB, getInput;
        int count = 0;
        long average = 0;
        boolean matrixCheck;
        boolean printCheck = false;     //default to false
        boolean multiTestCheck = false; //default to false
        boolean fillCheck = false;      //default to false
        long startTime, stopTime, timeElapsed;
        Scanner keyboard;
        matrix C = null; //has to point to something (or so netbeans tells me)
        
        //This loop repeats until two valid matrices are input
        while (true){
            
            System.out.print("Enter the size of matrix A (row col): ");
            keyboard = new Scanner(System.in);
            rowA = keyboard.nextInt();
            colA = keyboard.nextInt();
            
            System.out.print("Enter the size of matrix B (row col): ");
            keyboard = new Scanner(System.in);
            rowB = keyboard.nextInt();
            colB = keyboard.nextInt();
        
            matrixCheck = validSize(colA, rowB);
            
            if (matrixCheck == true){
                break;
            }//if
            
        }//while
        
        //prompt user if they want matrices to be printed
        //it is a very good idea to say no for matrices larger than 15x15
        System.out.println();
        System.out.print("Do you want matrices to be printed? "
                    + "(0 = no, 1 = yes, defaults to no): ");
        keyboard = new Scanner(System.in);    
        getInput = keyboard.nextInt();
        System.out.println();
        
        if (getInput == 1){
            printCheck = true;
        }//if 
            
        //prompt user if they want matrices to be filled manually
        //used mainly for testing, but we'll leave it in
        System.out.println();
        System.out.print("Do you want matrices to be filled manually? "
                    + "(0 = no, 1 = yes, defaults to no): ");
        keyboard = new Scanner(System.in);    
        getInput = keyboard.nextInt();
        System.out.println();
        
        if (getInput == 1){
            fillCheck = true;
        }//if 
        
        //create first matrix
        matrix A = new matrix(rowA, colA);
        
        //fill matrix based on answer
        if(fillCheck == true){
            A.fillManual();
        }//if 
        else{
            A.fillRandom();
        }//else

        //print matrix A if valid
        if(printCheck == true){
            System.out.println("Matrix A:");
            System.out.println(A);
        }//if
            
        //create the second matrix
        matrix B = new matrix(rowB, colB);
        
        //fill matrix based on answer
        if(fillCheck == true){
            B.fillManual();
        }//if 
        else{
            B.fillRandom();
        }//else 
        
        //print matrix B if valid
        if(printCheck == true){
            System.out.println("Matrix B:");
            System.out.println(B);
        }//if
        
        //User has a choice to run the multiplication once, or three times
        //with an average run time. Designed to be a time saving technique
        System.out.println();
        System.out.print("Do you to test 3 times and get average? "
                    + "(0 = no, 1 = yes, defaults to no): ");
        keyboard = new Scanner(System.in);    
        getInput = keyboard.nextInt();
        System.out.println();
        
        if (getInput == 1){
            multiTestCheck = true;
        }//if
        
        System.out.println("Matrix AB runtime using SerialReg:");
        
        //go in loop if 3 tests was chosen
        //could be easily modified to do more tests
        while(multiTestCheck == true){
            
            //multiply and store in a new matrix
            startTime = System.nanoTime();
            C = A.SerialReg(B);
            stopTime = System.nanoTime();
            timeElapsed = stopTime - startTime;
        
            count++;
            average += timeElapsed;
            
            System.out.println("SerialReg run " + count + 
                    " execution time: " + timeElapsed + " nanoseconds (" + 
                    timeElapsed/(float)1000000000 + " seconds)" );
            
            if(count == 3){
                System.out.println("Average of 3 run times is " + 
                        average/(float)3 + " nanoseconds "
                                + "(" + average/(float)3/1000000000 + 
                                    " seconds)");
                count = 0; //reset count, reuse for serial trans
                average = 0; //reset average
                break;
                
            }//if
            
        }//while
        
        //go here if just one test is desired
        if(multiTestCheck == false){
            //multiply and store in a new matrix
            startTime = System.nanoTime();
            C = A.SerialReg(B);
            stopTime = System.nanoTime();
            timeElapsed = stopTime - startTime;
            
            System.out.println("Execution time in nanoseconds: " + timeElapsed);
            System.out.println("Execution time in seconds: " + 
                timeElapsed/(float)1000000000);
            System.out.println();
        }//if
        
        //print result AB (SerialReg) if valid
        if(printCheck == true){
            System.out.println("\nMatrix AB Result (SerialReg):");
            System.out.println(C);
        }//if    

        //"transpose" B, save it
        matrix Binvert = B.invert();
        
        System.out.println("Matrix AB runtime using SerialTrans:");
        
        //go into while loop if 3 successive tests are wanted
        while(multiTestCheck == true){
            
            //multiply and store in a new matrix
            startTime = System.nanoTime();
            C = A.SerialTrans(Binvert);
            stopTime = System.nanoTime();
            timeElapsed = stopTime - startTime;
        
            count++;
            average += timeElapsed;
            
            System.out.println("SerialTrans run " + count + 
                    " execution time: " + timeElapsed + " nanoseconds (" + 
                    timeElapsed/(float)1000000000 + " seconds)" );
            
            if(count == 3){
                System.out.println("Average of 3 run times is " + 
                        average/(float)3 + " nanoseconds "
                                + "(" + average/(float)3/1000000000 + 
                                    " seconds)");
                count = 0; //reset count, reuse 
                average = 0; //reset average
                break;
            }//if
            
        }//while
        
        //if only 1 test was chosen
        if(multiTestCheck == false){
            //multiply and store in a new matrix
            startTime = System.nanoTime();
            C = A.SerialTrans(Binvert);
            stopTime = System.nanoTime();
            timeElapsed = stopTime - startTime;
            
            System.out.println("Execution time in nanoseconds: " + timeElapsed);
            System.out.println("Execution time in seconds: " + 
                timeElapsed/(float)1000000000);
            System.out.println();
        }//if
        
        //print if valid
        if(printCheck == true){
            System.out.println("\nMatrix AB Result (SerialTrans):");
            System.out.println(C);
        }//if    
        
        System.out.println("Matrix AB runtime using ManyThreads:");
        
        //go into while loop if 3 successive tests are wanted
        while(multiTestCheck == true){
            
            //multiply and store in a new matrix
            startTime = System.nanoTime();
            C = A.ManyThreads(Binvert);
            stopTime = System.nanoTime();
            timeElapsed = stopTime - startTime;
        
            count++;
            average += timeElapsed;
            
            System.out.println("ManyThreads run " + count + 
                    " execution time: " + timeElapsed + " nanoseconds (" + 
                    timeElapsed/(float)1000000000 + " seconds)" );
            
            if(count == 3){
                System.out.println("Average of 3 run times is " + 
                        average/3 + " nanoseconds "
                                + "(" + average/(float)3/1000000000 + 
                                    " seconds)");
                count = 0; //reset count, reuse 
                average = 0; //reset average
                break;
            }//if
            
        }//while
        
        //if only 1 test was chosen
        if(multiTestCheck == false){
            //multiply and store in a new matrix
            startTime = System.nanoTime();
            C = A.ManyThreads(Binvert);
            stopTime = System.nanoTime();
            timeElapsed = stopTime - startTime;
            
            System.out.println("Execution time in nanoseconds: " + timeElapsed);
            System.out.println("Execution time in seconds: " + 
                timeElapsed/(float)1000000000);
            System.out.println();
        }//if
        
        //print if valid
        if(printCheck == true){
            System.out.println("\nMatrix AB Result (ManyThreads):");
            System.out.println(C);
        }//if
        
        System.out.println("Matrix AB runtime using RowThreads:");
        
        //go into while loop if 3 successive tests are wanted
        while(multiTestCheck == true){
            
            //multiply and store in a new matrix
            startTime = System.nanoTime();
            C = A.RowThreads(Binvert);
            stopTime = System.nanoTime();
            timeElapsed = stopTime - startTime;
        
            count++;
            average += timeElapsed;
            
            System.out.println("RowThreads run " + count + 
                    " execution time: " + timeElapsed + " nanoseconds (" + 
                    timeElapsed/(float)1000000000 + " seconds)" );
            
            if(count == 3){
                System.out.println("Average of 3 run times is " + 
                        average/(float)3 + " nanoseconds "
                                + "(" + average/(float)3/1000000000 + 
                                    " seconds)");
                count = 0; //reset count, reuse 
                average = 0; //reset average
                break;
            }//if
            
        }//while
        
        //if only 1 test was chosen
        if(multiTestCheck == false){
            //multiply and store in a new matrix
            startTime = System.nanoTime();
            C = A.RowThreads(Binvert);
            stopTime = System.nanoTime();
            timeElapsed = stopTime - startTime;
            
            System.out.println("Execution time in nanoseconds: " + timeElapsed);
            System.out.println("Execution time in seconds: " + 
                timeElapsed/(float)1000000000);
            System.out.println();
        }//if
        
        //print if valid
        if(printCheck == true){
            System.out.println("\nMatrix AB Result (RowThreads):");
            System.out.println(C);
        }//if
        
    }//matrixTesting

    /**
     * Checks to make sure that multiplication can be performed on the 2
     * inputted matrices. This could be a part of the multiplication function
     * itself, but considering that we're timing how long the function takes
     * only to multiply, we shouldn't add anything to the function that will 
     * make it run unnecessarily longer (less is more).
     * 
     * @param passColA # of columns in matrix A that is passed
     * @param passRowB # of rows in matrix B that is passed
     * @return ability to perform matrix multiplication on matrices A and B
    */
    public static boolean validSize(int passColA, int passRowB){
        
        if (passColA == passRowB){
            return true;
        }//if
            
        else {
            System.out.println("Error: unable to perform matrix "
                + "multiplication. Please try again.\n");
            return false;       
        }//else
        
    }//validSize
   
}//class
