package finalproject;

import java.util.*;
import java.io.*;


public class ChessSudoku
{
	/* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
	 * a standard Sudoku puzzle, SIZE is 3 and N is 9. 
	 */
	public int SIZE, N;

	/* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
	 * not yet been revealed are stored as 0. 
	 */
	public int grid[][];

	/* Booleans indicating whether of not one or more of the chess rules should be 
	 * applied to this Sudoku. 
	 */
	public boolean knightRule;
	public boolean kingRule;
	public boolean queenRule;

	
	// Field that stores the same Sudoku puzzle solved in all possible ways
	public HashSet<ChessSudoku> solutions = new HashSet<ChessSudoku>();


	/* The solve() method should remove all the unknown characters ('x') in the grid
	 * and replace them with the numbers in the correct range that satisfy the constraints
	 * of the Sudoku puzzle. If true is provided as input, the method should find finds ALL 
	 * possible solutions and store them in the field named solutions. */
	public void solve(boolean allSolutions) {
		if (allSolutions==false){
			solver2();
		}
		
		else {
			solver();
		}
	}

	private boolean solver() {
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (grid[row][column] == 0) {
                    for (int num = 1; num < N + 1; num++) {
                        if (!(sameRow(row, num) || sameCol(column, num) || sameBox(row, column, num) || knight(row, column, num) || king(row, column, num) || queen(row, column, num))) {
                            grid[row][column] = num;
                            if(solver()){
                            	int[][] grid2 = new int[N][N];
                            	ChessSudoku answer = new ChessSudoku(this.SIZE);
                            	for(int k=0; k<this.grid.length; k++) {
                            		for(int l=0; l<this.grid.length; l++) {
                            			grid2[k][l] = this.grid[k][l];
                            		}
                            	}
                            	answer.grid = grid2;
                            	this.solutions.add(answer);
                            	grid[row][column]=0;
                            }
                            else {
                                grid[row][column]=0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
	private boolean solver2() {
        for (int row = 0; row < N; row++) {
            for (int column = 0; column < N; column++) {
                if (grid[row][column] == 0) {
                    for (int num = 1; num < N + 1; num++) {
                        if (!(sameRow(row, num) || sameCol(column, num) || sameBox(row, column, num) || knight(row, column, num) || king(row, column, num) || queen(row, column, num))) {
                            grid[row][column] = num;
                            if(solver2()){
                            	return true;
                            }
                            else {
                                grid[row][column]=0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
	
	//check if it is contained in a row
	private boolean sameRow(int row,int number)
	{
	    for(int i=0;i<N;i++)
	    {
	        if(grid[row][i]==number)
	        {
	            return true;
	        }
	    }
	    return false;
	}
	
	//checks if it is contained in a column
	private boolean sameCol(int col,int number)
	{
	    for(int i=0;i< N;i++)
	    {
	        if(grid[i][col]==number)
	        {
	            return true;
	        }
	    }
	    return false;
	}
	
	private boolean sameBox(int row, int col,int number)
	{
	    int ro = row - row%SIZE;
	    int co = col - col%SIZE;
	    for(int i = ro ; i< ro+SIZE ; i++)
	    {
	        for(int j = co; j < co+SIZE ; j++)
	        {
	            if(grid[i][j]==number)
	            {
	                return true;
	            }
	        }
	          
	    }
	    return false;
	}
	
	private boolean knight(int row, int col, int number){
		if (knightRule==false) {
			return false;
		}
		
		try {
			if(grid[row + 2][col+1] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row + 1][col+2] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row - 2][col-1] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row -1][col-2] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row + 2][col-1] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row - 2][col+1] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row - 1][col+2] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row +1][col-2] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		return false;
	}
	
	private boolean king(int row, int col, int number) {
		if (kingRule==false) {
				return false;
		}
			
		try {
			if(grid[row + 1][col+1] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row -1][col-1] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row +1][col-1] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		try {
			if(grid[row -1][col+1] == number) {
				return true;
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException) {
		}
		return false;
		
	}

	private boolean queen(int row, int col, int number) {
		if (queenRule==false || number!=N) {
			return false;
		}
		int first=1;
		int second=1;
		int third=1;
		int fourth=1;
		for(int i = 1; i<N; i++) {
			if (first==1) {
				try {
					if(grid[row + i][col+i] == N) {
						return true;
					}
				}
				catch(Exception ArrayIndexOutOfBoundsException) {
					first=0;
				}
			}
			if (second==1) {
				try {
					if(grid[row - i][col-i] == N) {
						return true;
					}
				}
				catch(Exception ArrayIndexOutOfBoundsException) {
					second=0;
				}
			}
			if (third==1) {
				try {
					if(grid[row + i][col-i] == N) {
						return true;
					}
				}
				catch(Exception ArrayIndexOutOfBoundsException) {
					third=0;
				}
			}
			if (fourth==1) {
				try {
					if(grid[row - i][col+i] == N) {
						return true;
					}
				}
				catch(Exception ArrayIndexOutOfBoundsException) {
					fourth=0;
				}
			}
			if (first+second+third+fourth==0) {
				break;
			}
		}
		return false;
		
	}

	/*****************************************************************************/
	/* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE METHODS BELOW THIS LINE. */
	/*****************************************************************************/

	/* Default constructor.  This will initialize all positions to the default 0
	 * value.  Use the read() function to load the Sudoku puzzle from a file or
	 * the standard input. */
	public ChessSudoku( int size ) {
		SIZE = size;
		N = size*size;

		grid = new int[N][N];
		for( int i = 0; i < N; i++ ) 
			for( int j = 0; j < N; j++ ) 
				grid[i][j] = 0;
	}


	/* readInteger is a helper function for the reading of the input file.  It reads
	 * words until it finds one that represents an integer. For convenience, it will also
	 * recognize the string "x" as equivalent to "0". */
	static int readInteger( InputStream in ) throws Exception {
		int result = 0;
		boolean success = false;

		while( !success ) {
			String word = readWord( in );

			try {
				result = Integer.parseInt( word );
				success = true;
			} catch( Exception e ) {
				// Convert 'x' words into 0's
				if( word.compareTo("x") == 0 ) {
					result = 0;
					success = true;
				}
				// Ignore all other words that are not integers
			}
		}

		return result;
	}


	/* readWord is a helper function that reads a word separated by white space. */
	static String readWord( InputStream in ) throws Exception {
		StringBuffer result = new StringBuffer();
		int currentChar = in.read();
		String whiteSpace = " \t\r\n";
		// Ignore any leading white space
		while( whiteSpace.indexOf(currentChar) > -1 ) {
			currentChar = in.read();
		}

		// Read all characters until you reach white space
		while( whiteSpace.indexOf(currentChar) == -1 ) {
			result.append( (char) currentChar );
			currentChar = in.read();
		}
		return result.toString();
	}


	/* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
	 * grid is filled in one row at at time, from left to right.  All non-valid
	 * characters are ignored by this function and may be used in the Sudoku file
	 * to increase its legibility. */
	public void read( InputStream in ) throws Exception {
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				grid[i][j] = readInteger( in );
			}
		}
	}


	/* Helper function for the printing of Sudoku puzzle.  This function will print
	 * out text, preceded by enough ' ' characters to make sure that the printint out
	 * takes at least width characters.  */
	void printFixedWidth( String text, int width ) {
		for( int i = 0; i < width - text.length(); i++ )
			System.out.print( " " );
		System.out.print( text );
	}


	/* The print() function outputs the Sudoku grid to the standard output, using
	 * a bit of extra formatting to make the result clearly readable. */
	public void print() {
		// Compute the number of digits necessary to print out each number in the Sudoku puzzle
		int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

		// Create a dashed line to separate the boxes 
		int lineLength = (digits + 1) * N + 2 * SIZE - 3;
		StringBuffer line = new StringBuffer();
		for( int lineInit = 0; lineInit < lineLength; lineInit++ )
			line.append('-');

		// Go through the grid, printing out its values separated by spaces
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				printFixedWidth( String.valueOf( grid[i][j] ), digits );
				// Print the vertical lines between boxes 
				if( (j < N-1) && ((j+1) % SIZE == 0) )
					System.out.print( " |" );
				System.out.print( " " );
			}
			System.out.println();

			// Print the horizontal line between boxes
			if( (i < N-1) && ((i+1) % SIZE == 0) )
				System.out.println( line.toString() );
		}
	}


	/* The main function reads in a Sudoku puzzle from the standard input, 
	 * unless a file name is provided as a run-time argument, in which case the
	 * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
	 * outputs the completed puzzle to the standard output. */
	public static void main( String args[] ) throws Exception {
		InputStream in = new FileInputStream("knightSudokuHard3x3.txt");

		// The first number in all Sudoku files must represent the size of the puzzle.  See
		// the example files for the file format.
		int puzzleSize = readInteger( in );
		if( puzzleSize > 100 || puzzleSize < 1 ) {
			System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
			System.exit(-1);
		}

		ChessSudoku s = new ChessSudoku( puzzleSize );
		
		// You can modify these to add rules to your sudoku
		s.knightRule = false;
		s.kingRule = false;
		s.queenRule = false;
		
		// read the rest of the Sudoku puzzle
		s.read( in );

		System.out.println("Before the solve:");
		s.print();
		System.out.println();

		// Solve the puzzle by finding one solution.
		s.solve(false);
		
		// Print out the (hopefully completed!) puzzle
		
		System.out.println("After the solve:");
		s.print();
		/*
		//for multiple
		Iterator<ChessSudoku> i = s.solutions.iterator();
		while(i.hasNext()) {
			i.next().print();
			System.out.println();
			System.out.println();
		}
 		//System.out.println(s.verify()))
 		 */
	}
}

