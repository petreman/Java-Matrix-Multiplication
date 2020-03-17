/**
 * AUCSC 350 Winter 2019
 * Programming Assignment #1: Matrix Multiplication
 * 
 * public class matrix
 * 
 * By Matthew Barney (1560206) and Keegan Petreman (1528679)
 * Started February 6, 2019
 * 
 * Public Methods:
 *  - matrix(int x, int y) -> matrix (constructor)
 *      Creates a matrix with x rows and y columns
 *      The matrix itself holds doubles
 *  
 *  - fillRandom() -> void
 *      Fills the matrix with random doubles
 * 
 *  - fillManual() -> void
 *      Fills the matrix with values input by the user. This was mainly used
 *      for testing
 * 
 *  - getRow() -> int
 *      Returns the number of rows in the matrix
 * 
 *  - getCol() -> int
 *      Returns the number of columns in the matrix
 * 
 *  - getValue(int row, int col) -> double
 *      Goes to the given row and column of the matrix and returns the 
 *      value stored there
 * 
 *  - setValue(int row, int col, double value) -> void
 *      Take a specified coordinate in the matrix, and sets it to the
 *      double specified in 'value'
 * 
 *  - addToValue(int row, int col, double value) -> void
 *      Adds the provided value to the given position of a matrix
 * 
 *  - toString() -> String
 *      Returns a more matrix-y formatted string. Not perfect, but readable
 * 
 *  - transpose() -> matrix
 *      For n*n size matrices, returns its transpose form
 * 
 *  - invert() -> matrix
 *      For n*m matrices, inverts the matrix (rows becomes columns and columns
 *      become rows) and returns it
 * 
 *  - SerialReg(matrix otherMatrix) -> matrix
 *      This is regular serial multiplication between matrices A (this) and 
 *      B (otherMatrix) -> AB, where A is size n*m and B is m*l
 * 
 *  - SerialTrans(matrix otherMatrix) -> matrix
 *      Serial multiplication, but with the 2nd parameter in transpose form. 
 *      A (this) and B (otherMatrix) -> AB, where A is size n*m and B is l*m
 * 
 *  - ManyThreads(matrix otherMatrix) -> matrix
 *      Parallel matrix multiplication. 2nd parameter must be in transpose
 *      form. A (this) and B (otherMatrix) -> AB, where A is size n*m and B is 
 *      l*m. Each cell of the result matrix AB is determined by its own thread.
 * 
 *  - RowThreads(matrix otherMatrix) -> matrix
 *      Parallel matrix multiplication. 2nd parameter must be in transpose
 *      form. A (this) and B (otherMatrix) -> AB, where A is size n*m and B is 
 *      l*m. Each row of the result matrix AB is determined by its own thread.
 * 
 */
package matrixmult;

import java.util.Random;
import java.util.Scanner;

/**
 * Creates a matrix and defines operations that can be performed on it
 * (either by itself or with another matrix)
 * @author Keegan Petreman
 */
public class matrix{
    
    //Instance Data
    private final int numRow, numCol;
    private final double[][] theMatrix;
    
    //Constructor
    matrix(int x, int y){
       numRow = x;
       numCol = y;
       theMatrix = new double[numRow][numCol]; 
       
    }//constructor
       
    //Methods
    
    /**
     * Fills the matrix with random doubles
     */
    public void fillRandom(){
       
        Random randomNum = new Random();
        
        for(int i = 0; i < numRow; i++){
            for(int j = 0; j < numCol; j++){
                theMatrix[i][j] = randomNum.nextDouble();
            }//for
        }//for
    }//fillRandom
    
    /**
     * Fills the matrix with user input values.
     * Used in this assignment for testing matrix multiplication with simple
     * values to see easily if the algorithms implemented are correct
     */
    public void fillManual(){
        
        Scanner keyboard = new Scanner(System.in);
        
        for(int i = 0; i < numRow; i++){
            for(int j = 0; j < numCol; j++){
                System.out.print("Value for row " + i + ", column "
                        + j + ": ");
                
                theMatrix[i][j] = keyboard.nextDouble();
                
            } //for  
         
        }//for 
        
        System.out.println();
        
    }//filManual    
        
    //Getter: returns the number of rows in a matrix
    public int getRow(){
        return numRow;
    }//getRow
    
    //Getter: returns the number of columns in a matrix
    public int getColumn(){
        return numCol;
    }//getColumn
    
    //Getter: returns the value at a specified coordinate in the matrix
    public double getValue(int aRow, int aCol){
        return theMatrix[aRow][aCol];
    }//getValue
    
    //Setter: sets the value at a specified coordinate in the matrix
    public void setValue(int aRow, int aCol, double value){
        theMatrix[aRow][aCol] = value;
    }//setValue
    
    //Setter: adds a given value to the provided spot in a matrix
    public void addToValue(int aRow, int aCol, double value){
        theMatrix[aRow][aCol] += value; 
    }//addToValue
    
    /**
     * @return a more matrix-y formatted string output
     * It's not perfect, but is still fairly readable
     */
    @Override
    public String toString(){
        
        String matrixString = "";
        
        for (int i = 0; i < numRow; i++){
            for (int j = 0; j < numCol; j++){
                System.out.print(theMatrix[i][j] + "  ");
            }//for

            System.out.println();
            
        }//for
        
        System.out.println();
        
        return matrixString;
        
    }//toString

    /**
     * This is a "true" transpose function for matrices that are size n*n
     * This function will probably go unused for this assignment, but I'll
     * keep it in, just as a reminder that the "transposing" that the
     * assignment calls for isn't at all a transpose
     * @return - the appropriate matrix in transpose form
     */
    public matrix transpose(){
        
        //set up the transposed matrix with empty values
        //because this only works for n*n matrices,
        //numCol and numRow could have been swapped in the below consturctor
        matrix transMatrix = new matrix(this.numCol, this.numRow);
        
        for (int i = 0; i < numRow; i++){
            for (int j = 0; j < numCol; j++){
                transMatrix.setValue(i, j, theMatrix[j][i]);
            }//for
            
            System.out.println();
            
        }//for
        
        return transMatrix;
        
    }//transpose
    
    /**
     * This is a inversion function for matrices that are size n*m
     * It also works for size n*n, which in a way makes transpose() pointless
     * @return - the matrix in inverted form
     */
    public matrix invert(){
        
        //set up a inverted matrix to be used
        matrix invertMatrix = new matrix(this.numCol, this.numRow);
        
        //basically just swapping the two for-loops, which cause the rows
        //to become columns, and the columns to become rows
        for (int j = 0; j < numCol; j++){
            for (int i = 0; i < numRow; i++){
                invertMatrix.setValue(j, i, theMatrix[i][j]);
            }//for
            
            System.out.println();
            
        }//for
        
        return invertMatrix;
        
    }//transpose
    
    /**
     * This is regular serial multiplication between a matrices A (this) and 
     * B (otherMatrix) -> AB, where A is size n*m and B is m*l.
     * This is a loop of 3 variables (i, j, k)
     * 
     * @param otherMatrix - matrix on the right hand side of multiplication
     * @return - matrix AB (A*B, NOT to be confused with B*A)
     */
    public matrix SerialReg(matrix otherMatrix){
        
        matrix AB = new matrix(this.getRow(), otherMatrix.getColumn());
        
        for (int i=0; i<this.getRow(); ++i){
            for (int j=0; j<otherMatrix.getColumn(); ++j){
                for (int k=0; k<this.getColumn(); ++k){
                    AB.addToValue(i, j, (this.getValue(i,k) * 
                            otherMatrix.getValue(k, j)));
                }//for
            }//for
        }//for  
        
        return AB;
        
    }//SerialReg
    
    /** 
     * Computes matrix AB from A (this) and B (otherMatrix), where B is in
     * inverse (transpose) form. So if A is size n*m, B is size l*m
     * This is also a loop of 3 variables (i, j, k) 
     *  
     * @param otherMatrix the inverted matrix in position B
     * @return multiplication of this and otherMatrix
     */
    public matrix SerialTrans(matrix otherMatrix){
        
        //unlike SerialReg, otherMatrix's row numbers is actually it's column
        //numbers because otherMatrix is in transpose (inverse) form
        matrix AB = new matrix(this.getRow(), otherMatrix.getRow());
        
        for (int i=0 ; i<this.getRow() ; i++){
            
            for(int j=0 ; j<otherMatrix.getRow(); j++){
                
                for (int k=0 ; k<this.getColumn() ; k++){
                    
                    AB.addToValue(i, j, (this.getValue(i, k)* 
                            otherMatrix.getValue(j, k)));   
                }//for

            }//for
            
        }//for
        
        return AB;
        
    }//SerialTrans 
    
    /** 
     * Computes matrix AB from A (this) and B (otherMatrix), where B is in
     * inverse (transpose) form. So if A is size n*m, B is size l*m
     * A thread is created to compute each cell of matrix AB.
     * To accomplish this, a class ManyThreadsParameters is needed. An instance
     * of this class is created for every cell, and holds the parameters
     * needed to compute the answer for that cell. When start() is called on 
     * on an instance, the thread is run for that cell, and all the threads
     * are eventually called back together in a loop
     *  
     * @param otherMatrix the inverted matrix in position B
     * @return multiplication of this and otherMatrix
     */
    public matrix ManyThreads(matrix otherMatrix){
        
        matrix AB = new matrix(this.getRow(), otherMatrix.getRow());
        
        //Local class to hold the parameters needed to calculate each cell 
        //of AB, as run() can't take parameters
        class ManyThreadsParameters extends Thread{
            
            private final matrix A, B, AB;
            private final int i, j;
            
            ManyThreadsParameters(matrix A, matrix B, matrix AB, int i, int j){
                this.A = A;
                this.B = B;
                this.AB = AB;
                this.i = i;
                this.j = j;
                
            }//matrixThread
            
            /**
             * This calculates the answer of cell [i][j] in AB, based on the
             * parameters assigned to the instance of ManyThreadsParameters
             */
            @Override
            public void run(){
        
                for(int k=0 ; k< this.A.getColumn(); k++){
                    AB.addToValue(this.i, this.j, (this.A.getValue(this.i, k)* 
                            this.B.getValue(this.j, k)));
            
                }//for
        
                //the below line was used in testing
                //System.out.println("Thread "+this.i+","+this.j+" complete."); 
                
            }//run
            
        }//class
        
        //create array of the parameters needed for their result cell
        ManyThreadsParameters[][] newThread = 
                new ManyThreadsParameters[this.getRow()][otherMatrix.getRow()];
        
        for (int n=0 ; n<this.getRow() ; n++){
            
            for(int m=0 ; m<otherMatrix.getRow(); m++){
                
                //create new thread with parameters A, B, AB, n, m
                //this is how the parameters are given for each cell in AB
                newThread[n][m] = 
                        new ManyThreadsParameters(this, otherMatrix, AB, n, m);
                newThread[n][m].start();
                
            }//for
        
        }//for    
        
        //if here, then each result has been calculated. 
        //Attempt to join the results under the same instance
        for(int i=0 ; i<this.getRow() ; i++){
            for(int j=0 ; j<otherMatrix.getRow() ; j++){
                try{
                    newThread[i][j].join();
                }//for
                catch(InterruptedException e){}
            }//for
        }//for
        
        return AB;
        
    }//ManyThreads  
    
    /** 
     * Computes matrix AB from A (this) and B (otherMatrix), where B is in
     * inverse (transpose) form. So if A is size n*m, B is size l*m
     * A thread is created to compute each row of matrix AB.
     * To accomplish this, a class RowThreadsParameters is needed. An instance
     * of this class is created for every row, and holds the parameters
     * needed to compute the answer for that cell. When start() is called on 
     * on an instance, the thread is run for that row.
     *  
     * @param otherMatrix the inverted matrix in position B
     * @return multiplication of this and otherMatrix
     */
    public matrix RowThreads(matrix otherMatrix){
        
        matrix AB = new matrix(this.getRow(), otherMatrix.getRow());
        
        //needed to hold parameters to calculate each row of AB, 
        //as run() can't take parameters
        class RowThreadsParameters extends Thread{
            
            private final matrix A, B, AB;
            private final int i;
            
            RowThreadsParameters(matrix A, matrix B, matrix AB, int i){
                this.A = A;
                this.B = B;
                this.AB = AB;
                this.i = i;
                
            }//matrixThread
            
            /**
             * This calculates the answer of row i in AB, based on the
             * parameters assigned to the instance of RowThreadsParameters
             */
            @Override
            public void run(){
        
                for(int l = 0 ; l < AB.getColumn() ; l++)
                    for(int k=0 ; k< this.A.getColumn(); k++){
                        AB.addToValue(this.i, l, (this.A.getValue(this.i, k)* 
                            this.B.getValue(l, k)));
            
                }//for
        
                //the below line was used in testing
                //System.out.println("Thread "+this.i+","+this.j+" complete."); 
                
            }//run
            
        }//class
        
        RowThreadsParameters[] newThread = 
                new RowThreadsParameters[AB.getRow()];
        
        for (int n=0 ; n<AB.getRow() ; n++){
            
            //create new thread with parameters A, B, AB, n
            //this is how the parameters are given for each row in AB,
            //where n is the current row to solve
            newThread[n] = new RowThreadsParameters(this, otherMatrix, AB, n);
            newThread[n].start();
            
        }//for    
        
        //if here, then each row has been calculated. Attempt to join the
        //row under the same instance of AB
        for(int i=0 ; i<this.getRow() ; i++){
            
            try{
                newThread[i].join();
            }//for
                
            catch(InterruptedException e){}
                
        }//for

        return AB;
        
    }//ManyThreads
    
}//class
