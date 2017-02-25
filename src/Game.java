import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Game extends MyButton implements ActionListener {

	private MyButton[][] board; // creates a board of disabled buttons to serve as the board
	private JButton[] buttons; // creates buttons that can be clicked to add the game pieces
	private int[][] counters; // each column gets a counter to keep track of the game pieces
	private int currentPlayer; // allows to switch between the players
	private JFrame frame;  // instantiates the frame
	private int rows; 
	private int columns;
	private int res; // counter to hold the variable across the call to show columns are filled
	private int winCount; // a counter to determine the winner 
	public static final int MAX_ROWS = 20; // max amount of rows allowed
	public static final int MAX_COLUMNS = 20; // max amount of columns allowed
	public static final int MAX_CONNECT = 19; //max amount of pieces to be lined up horizontally, vertically, and diagonally
	public static final int MIN_ROWS = 4; // min amount of rows allowed
	public static final int MIN_COLUMNS = 4; // min amount of columns allowed
	public static final int MIN_CONNECT = 3; // min amount of pieces to be lined up horizontally, vertically, and diagonally
	
	/**
	 * creates a default game of connect 4
	 */
	public Game()
	{
		this.columns = 6;
		this.rows = 7;
		this.winCount = 4;
		this.frame = new JFrame("Connect 4");
		this.buttons = new JButton[columns];
		this.board = new MyButton[rows][columns];
		this.counters = new int[rows][columns];
		this.currentPlayer = 1;
		
		setupBoard();
	}
	
	/**
	 * creates a game of connect 4 through command line
	 * @param int boardSize
	 * @param int winCount
	 */
	public Game(int boardSize, int winCount)
	{
		this.frame = new JFrame("Connect 4");
		this.winCount = winCount;
		this.rows = boardSize;
		this.columns = boardSize;
		this.buttons = new MyButton[boardSize];
		this.board = new MyButton[boardSize][boardSize];
		this.counters = new int[boardSize][boardSize];
		this.currentPlayer = 1;
		
		addCap();
		setupBoard();
	}
	
	/**
	 * creates a game connect 4 with a number
	 * of rows and columns and the number of pieces to win by
	 * @param rows
	 * @param columns
	 * @param winCount
	 */
	public Game(int rows, int columns, int winCount)
	{
		this.frame = new JFrame("Connect 4");
		this.rows = rows;
		this.columns = columns;
		this.winCount = winCount;
		this.buttons = new JButton[columns];
		this.board = new MyButton[rows][columns];
		this.counters = new int[rows][columns];
		this.currentPlayer = 1;
		
		addCap();
		setupBoard();
	}
	
	/**
	 * sets up the frame, panel, button for board
	 * and the buttons iterate over how many columns
	 */
	private void setupBoard()
	{
		ImageIcon img = new ImageIcon("pic.png");
		JPanel panel = new JPanel(new GridLayout(rows + 1, columns));
		frame.setIconImage(img.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500,500));
		frame.setVisible(true);
		frame.add(panel); // adds the JPanel to the JFrame
		
		for(int j = 0; j<columns; j++) // sets up the buttons that will be enabled to be clicked 
		{
			JButton pressed = new JButton();
			buttons[j] = pressed;
			panel.add(buttons[j]);
			buttons[j].addActionListener(this); // adds action listener to the enabled buttons
		}
		
		for(int i = 0; i<rows; i++) // sets up the buttons of the board that consists of disabled buttons
		{						
			for(int j = 0; j<columns; j++)
			{
				MyButton button = new MyButton();
				button.setBorderPainted(false);
				button.setBackground(Color.BLUE);
				button.setEnabled(false);
				board[i][j] = button;
				panel.add(board[i][j]);
			}
		}
		
		setupCounter();
		frame.pack();
		frame.setLocationRelativeTo(null); // centers the board
	}
	
	/**
	 * sets up the counter for each column
	 * on the board to have an individual counter
	 * to keep track of which pieces have been placed
	 * on the board
	 */
	private void setupCounter()
	{
		for(int i = 0; i<rows; i++) 
		{
			for(int j = 0; j<columns; j++)
			{
				counters[i][j] = j;
				counters[i][j] = i;
			}
		}
	}
	
	
	/**
	 * gets the number of rows of the board
	 * @return int of rows
	 */
	public int getRows()
	{
		return this.rows;
	}
	
	/**
	 * gets the number of columns of the board
	 * @return int of columns
	 */
	public int getColumns()
	{
		return this.columns;
	}
	
	/**
	 * prints the counter onto
	 * the console
	 */
	public void print()
	{
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				System.out.print(counters[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	/**
	 * private method to allow player 1 and player 2 to
	 * switch and play turn by turn
	 */
	private void switchPlayer()
	{
		if(currentPlayer == 1)
		{
			currentPlayer = 0;
		}
		else
		{
			currentPlayer = 1;
		}
	}
	
	/**
	 * private method to add a limit to how
	 * big the board can get, and how many pieces in order to win.  System will exit if 
	 * the numbers exceed that limit or doesn't reach at least the minimum
	 */
	private void addCap()
	{
		if(rows > MAX_ROWS || columns > MAX_COLUMNS)
		{
			System.out.println("The number of rows and/or columns exceed the limit.");
			System.exit(0);
		}
		if(rows < MIN_ROWS || columns < MIN_COLUMNS)
		{
			System.out.println("The number of rows and/or columns are lower than the limit.");
			System.exit(0);
		}
		if (winCount < MIN_CONNECT)  
		{  
			System.out.println("The Connect number is lower than the minimum limit.");
		    System.exit(0); 
		}
		if (winCount > MAX_CONNECT || winCount >= Math.min(rows, columns))  
		{  
			System.out.println("The Connect number is greater than max or greater than or equal to rows/columns.");
		    System.exit(0); 
		}
	}
	
	/**
	 * Gives action when one of the enabled buttons has
	 * been pressed and drops down the pieces onto the board by setting 
	 * the disabled button to either black or red
	 * by default player 1 starts with the black piece
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int i = 0;
		for(int j = 0; j < buttons.length; j++)
		{
			if(buttons[j] == e.getSource())
			{	if(i < buttons.length)
				{
				try{ // handles when the row reaches the very last index 
					if(currentPlayer == 1)
					{
						board[(rows-1) - counters[i][j]][j].setColor(Color.BLACK);
						//board[(rows-1) - counters[i][j]][j].repaint();
						if (checkWinner(rows-1 - counters[i][j], j)) { return; }
						if(counters[i][j] == columns-1) // if block to check if there is a draw or tie
						{
							res =  (columns)-1 +res;
							if(res == (columns-1) * columns){displayDraw();}
						}
						
						counters[i][j]++;
						}
				
					else{
						
						board[(rows-1) - counters[i][j]][j].setColor(Color.RED);
						if (checkWinner(rows-1 - counters[i][j], j)) { return; }
						if(counters[i][j] == columns-1) // if block to check if there is a draw or tie
						{
							res =  (columns)-1 +res;
							if(res == (columns-1)*columns){displayDraw();}
						}
						
						counters[i][j]++;
						}
					switchPlayer();
					}
				catch(ArrayIndexOutOfBoundsException a)
					{
						System.out.println("error not enough space in the grid");
					}
				}
			
}
		}
	}
	/**
	 * checks to see if there is a winner returns true or false
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean checkWinner(int row,int column)
	{	
		if (checkVertical() == true) { return true; }
		else if (checkHorizontal() == true) { return true; }
		else if (checkDiagonal(row, column) == true) { return true; } 
		else { return false; }
       	
	}
    
	/**
	 * checks to see if there is a winner at all possible
	 * diagonal combinations
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean checkDiagonal(int row, int column)
	{
	    int r = row;
	    int c = column;
	    int count = 1;
	    Color current = board[r][c].getColor();
	    
	    //System.out.println("first checkBounds is : " + checkBounds(r - 1, c + 1));
	    if (checkBounds(r - 1, c + 1)) //Checking top Right Corner First
	    {
	    	while (checkBounds(r - 1, c + 1))
	    	{
	    		
	    		Color next = board[r - 1][c + 1].getColor();
	    		if (current.equals(next) && !(next.equals(Color.WHITE)))
	    		{	count++;    }
	    		else { break; }
	    		//System.out.println("Top Right Loop: " + count);
	    		if (count == winCount)
	    		{
	    			if (current.equals(Color.RED)) {displayPlayer2(); return true;  }
	    			if (current.equals(Color.BLACK)) {displayPlayer1(); return true; }
	    		}
	    		r--;  c++;
	    	}
	    	if (count < winCount)
	    	{
	    		int r2 = row;
	    		int c2 = column;
	    		//System.out.println("second checkBounds is : " + checkBounds(r2 + 1, c2 - 1));
	    		while(checkBounds(r2 + 1, c2 - 1)) //Checking Bottom Left Corner if Top Right doesn't reach win
		    	{
		    		Color next = board[r2 + 1][c2 - 1].getColor();
		    		if (current.equals(next) && !(current.equals(Color.WHITE)))
		    		{	count++;	}
		    		else { count = 1; break; }
		    		//System.out.println("Bottom Left Loop: " + count);
		    		if (count == winCount) 
		    		{ 
		    			if (current.equals(Color.RED)) {displayPlayer2(); return true;  }
		    			if (current.equals(Color.BLACK)) {displayPlayer1(); return true; }
		    		}
		    		r2++; c2--;
		    	} 
	    	}
	    	
	    }
	    else 
	    {  
	    	count = 1;
	    	int r3 = row;
	    	int c3 = column;
	    	//System.out.println("third checkBounds is : " + checkBounds(r3 + 1, c3 - 1));
	    	while(checkBounds(r3 + 1, c3 - 1)) //Checking Bottom Left Corner if an edge case
	    	{
	    		Color next = board[r3 + 1][c3 - 1].getColor();
	    		if (current.equals(next) && !(current.equals(Color.WHITE)))
	    		{	count++;	}
	    		else { count = 1; break; }
	    		//System.out.println("Bottom Left Loop 2 : " + count);
	    		if (count == winCount) 
	    		{ 
	    			if (current.equals(Color.RED)) {displayPlayer2(); return true;  }
	    			if (current.equals(Color.BLACK)) {displayPlayer1(); return true; }
	    		}
	    		r3++; c3--;
	    	} 
	    }  
	    
	
	    if (checkBounds(r - 1, c - 1)) //Checking top Left Corner First
	    {
	    	count = 1; 
	 	    r = row;
	 	    c = column;
	    	
	    	while (checkBounds(r - 1, c - 1))
	    	{
	    		Color next = board[r - 1][c - 1].getColor();
	    		if (current.equals(next) && !(next.equals(Color.WHITE)))
	    		{	count++;    }
	    		else { break; }
	    		//System.out.println("top Left Loop: " + count);
	    		if (count == winCount)
	    		{
	    			if (current.equals(Color.RED)) {displayPlayer2(); return true;  }
	    			if (current.equals(Color.BLACK)) {displayPlayer1(); return true; }
	    		}
	    		r--;  c--;
	    	}
	    	if (count < winCount)
	    	{
	    		int r2 = row;
	    		int c2 = column;
	    		while(checkBounds(r2 + 1, c2 + 1)) //Checking Bottom Right Corner if Top Left doesn't reach Win
		    	{
		    		Color next = board[r2 + 1][c2 + 1].getColor();
		    		if (current.equals(next) && !(current.equals(Color.WHITE)))
		    		{	count++;	}
		    		else { count = 1; break; }
		    		//System.out.println("Bottom Right Loop: " + count);
		    		if (count == winCount) 
		    		{ 
		    			if (current.equals(Color.RED)) {displayPlayer2(); return true;  }
		    			if (current.equals(Color.BLACK)) {displayPlayer1(); return true; }
		    		}
		    		r2++; c2++;
		    	} 
	    	}
	    	
	    }
	    else 
	    {  
	    	count = 1;
	    	int r3 = row;
    		int c3 = column;
	    	while(checkBounds(r3 + 1, c3 + 1)) //Checking Bottom Right Corner if an edge case
	    	{
	    		Color next = board[r3 + 1][c3 + 1].getColor();
	    		if (current.equals(next) && !(current.equals(Color.WHITE)))
	    		{	count++;	}
	    		else { count = 1; break; }
	    		//System.out.println("Bottom Right Loop 2 : " + count);
	    		if (count == winCount) 
	    		{ 
	    			if (current.equals(Color.RED)) {displayPlayer2(); return true;  }
	    			if (current.equals(Color.BLACK)) {displayPlayer1(); return true; }
	    		}
	    		r3++; c3++;
	    	}
	    }
	    return false;
	   
	   
	}
	
	/**
	 * checks to see if the parameters are in bounds of the board
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean checkBounds(int row, int column)
	{
		boolean inBounds = (row >= 0) && (row < rows) && (column >= 0) && (column < columns);
		return inBounds;
	}
	
	/**
	 * checks a winner at ever vertical
	 * position
	 * @return
	 */
	private boolean checkVertical()
	{
	    int redCount = 1;
	    int blackCount = 1;
		for (int i = rows - 1; i >= 0; i--)
		{
		
			for (int j = 0; j < columns - 1; j++)
			{
				Color current = board[i][j].getColor();
				Color next = board[i][j + 1].getColor();
				if ((current.equals(Color.RED)) && (current.equals(next)))
				{
					redCount++;
				}
				else { redCount = 1; }
				if ((current.equals(Color.BLACK)) && (current.equals(next)))
				{
					blackCount++;
				}
				else { blackCount = 1; }
				if (redCount == winCount) {displayPlayer2(); return true;}
				if (blackCount == winCount) {displayPlayer1(); return true; }
			}
		}
		return false;
	
		
	}
	
	/**
	 * checks to see if there is a winner
	 * at every horizontal line
	 * @return
	 */
	private boolean checkHorizontal()
	{
	    int redCount = 1;
	    int blackCount = 1;
		for (int i = 0; i < columns; i++)
		{
			for (int j = rows - 1; j > 0; j--)
			{
				
				Color current = board[j][i].getColor();
				Color next = board[j - 1][i].getColor();
				if ((current.equals(Color.RED)) && (current.equals(next)))
				{
					redCount++;
				}
				else { redCount = 1; }
				if ((current.equals(Color.BLACK)) && (current.equals(next)))
				{
					blackCount++;
				}
				else { blackCount = 1; }
				if (redCount == winCount) {displayPlayer2(); return true;}
				if (blackCount == winCount) {displayPlayer1(); return true; }
			}
		}
		return false;
	
		
	}
	
	/**
	 * creates a dialog that when certain
	 * when a player 1 wins it will display
	 * a message and option for rematch
	 */
	private void displayPlayer1()
	{
		JOptionPane.showMessageDialog(frame, "Player 1 Wins!");
		int res = JOptionPane.showConfirmDialog(frame, "Rematch?", "Connect 4", JOptionPane.YES_NO_OPTION);
		if(res == 0)
		{
			frame.dispose();
			new Game(rows, columns, winCount);
		}
		else{System.exit(0);}
	}
	
	/**
	 * creates a dialog that when certain
	 * when a player 2 wins it will display
	 * a message and option for rematch
	 */
	private void displayPlayer2()
	{
		JOptionPane.showMessageDialog(frame, "Player 2 Wins!");
		int res = JOptionPane.showConfirmDialog(frame, "Rematch?", "Connect 4", JOptionPane.YES_NO_OPTION);
		if(res == 0)
		{
			frame.dispose();
			new Game(rows, columns, winCount);
		}
		else{System.exit(0);}
	}
	
	/**
	 * creates a dialog that when certain
	 * when no player wins it will display
	 * a message and option for a rematch
	 */
	private void displayDraw()
	{
		JOptionPane.showMessageDialog(frame, "Draw!");
		int res = JOptionPane.showConfirmDialog(frame, "Rematch?", "Connect 4", JOptionPane.YES_NO_OPTION);
		if(res == 0)
		{
			frame.dispose();
			new Game(rows, columns, winCount);
		}
		else{System.exit(0);}
	}
}