package hw3;

import static api.Direction.*;
import static api.Orientation.*;

import java.util.ArrayList;

import api.Cell;
import api.Direction;
import api.Move;

/**
 * Represents a board in the game. A board contains a 2D grid of cells and a
 * list of boulders that slide over the cells.
 */
public class Board {
	/**
	 * 2D array of cells, the indexes signify (row, column) with (0, 0) representing
	 * the upper-left corner of the board.
	 */
	private Cell[][] grid;

	/**
	 * A list of boulders that are positioned on the board.
	 */
	private ArrayList<Boulder> boulders;

	/**
	 * A list of moves that have been made in order to get to the current position
	 * of boulders on the board.
	 */
	private ArrayList<Move> moveHistory;

	/**
	 * Constructs a new board from a given 2D array of cells and list of boulders. The
	 * cells of the grid should be updated to indicate which cells have boulders
	 * placed over them (i.e., placeBoulder() method of Cell). The move history should
	 * be initialized as empty.
	 * 
	 * @param grid   a 2D array of cells which is expected to be a rectangular shape
	 * @param boulders list of boulders already containing row-column position which
	 *               should be placed on the board
	 */
	public Board(Cell[][] grid, ArrayList<Boulder> boulders) {
		this.grid = grid;
	    this.boulders = boulders;
	    moveHistory = new ArrayList<Move>();
	    
	    for (Boulder b : boulders) {
	        int len = b.getLength();
	        int r = b.getFirstRow();
	        int c = b.getFirstCol();
	        api.Orientation ori = b.getOrientation();
	        for (int i = 0; i < len; i++) {
	            int rr = r + (ori == api.Orientation.VERTICAL ? i : 0);
	            int cc = c + (ori == api.Orientation.HORIZONTAL ? i : 0);
	            grid[rr][cc].placeBoulder(b);
	        }
	    }
	}

	/**
	 * DO NOT MODIFY THIS CONSTRUCTOR
	 * <p>
	 * Constructs a new board from a given 2D array of String descriptions.
	 * 
	 * @param desc 2D array of descriptions
	 */
	public Board(String[][] desc) {
		this(GridUtil.createGrid(desc), GridUtil.findBoulders(desc));
	}

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return number of rows
	 */
	public int getRowSize() {
		return grid.length;
	}

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return number of columns
	 */
	public int getColSize() {
		return grid[0].length;
	}

	/**
	 * Returns the cell located at a given row and column.
	 * 
	 * @param row the given row
	 * @param col the given column
	 * @return the cell at the specified location
	 */
	public Cell getCellAt(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Returns the total number of moves (calls to moveGrabbedBoulder which
	 * resulted in a boulder being moved) made so far in the game.
	 * 
	 * @return the number of moves
	 */
	public int getMoveCount() {
		int count = 0;
	    for (Move move : moveHistory) {
	        if (move.getDirection() != null) {
	            count++;
	        }
	    }
	    return count;
	}

	/**
	 * Returns a list of all boulders on the board.
	 * 
	 * @return a list of all boulders
	 */
	public ArrayList<Boulder> getBoulders() {
		return boulders;
	}

	/**
	 * Returns true if the player has completed the puzzle by positioning a boulder
	 * over an exit, false otherwise.
	 * 
	 * @return true if the game is over
	 */
	public boolean isGameOver() {
		if (moveHistory.isEmpty()) {
	        return false;
	    }
		for (Boulder b : boulders) {
	        int row = b.getFirstRow();
	        int col = b.getFirstCol();
	        int len = b.getLength();
	        for (int i = 0; i < len; i++) {
	            int r = row + (b.getOrientation() == VERTICAL ? i : 0);
	            int c = col + (b.getOrientation() == HORIZONTAL ? i : 0);
	            if (grid[r][c].isExit()) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	/**
	 * Models the user grabbing (mouse button down) a boulder over the given row and
	 * column. The purpose of grabbing a boulder is for the user to be able to drag
	 * the boulder to a new position, which is performed by calling
	 * moveGrabbedBoulder().
	 * <p>
	 * This method should find which boulder has been grabbed (if any) and record
	 * that boulder as grabbed in some way.
	 * 
	 * @param row row to grab the boulder from
	 * @param col column to grab the boulder from
	 */
	public void grabBoulderAt(int row, int col) {
		for (Boulder b : boulders) {
	        int len = b.getLength();
	        int r = b.getFirstRow();
	        int c = b.getFirstCol();
	        api.Orientation ori = b.getOrientation();
	        
	        for (int i = 0; i < len; i++) {
	            int checkRow = r + (ori == VERTICAL ? i : 0);
	            int checkCol = c + (ori == HORIZONTAL ? i : 0);
	            if (checkRow == row && checkCol == col) {
	                moveHistory.add(new Move(b, null));
	                return;
	            }
	        }
	    }
	}

	/**
	 * Models the user releasing (mouse button up) the currently grabbed boulder
	 * (if any). Update the object accordingly to indicate no boulder is
	 * currently being grabbed.
	 */
	public void releaseBoulder() {
		if (!moveHistory.isEmpty() && moveHistory.get(moveHistory.size() - 1).getDirection() == null) {
			moveHistory.remove(moveHistory.size() - 1);
		}
	}

	/**
	 * Returns the currently grabbed boulder. If there is no currently grabbed
	 * boulder the method return null.
	 * 
	 * @return the currently grabbed boulder or null if none
	 */
	public Boulder getGrabbedBoulder() {
		for (int i = moveHistory.size() - 1; i >= 0; i--) {
	        Move move = moveHistory.get(i);
	        if (move.getDirection() == null) {
	            return move.getBoulder();
	        }
	    }
	    return null;
	}

	/**
	 * Returns true if the cell at the given row and column is available for a
	 * boulder to be placed over it. Boulders can only be placed over ground
	 * and exits. Additionally, a boulder cannot be placed over a cell that is
	 * already occupied by another boulder.
	 * 
	 * @param row row location of the cell
	 * @param col column location of the cell
	 * @return true if the cell is available for a boulder, otherwise false
	 */
	public boolean isAvailable(int row, int col) {
		if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
			return false;
		}
		if (grid[row][col].hasBoulder()) {
			return false;
		}
		
		return grid[row][col].isGround() || grid[row][col].isExit();
	}

	/**
	 * Moves the currently grabbed boulder by one cell in the given direction. A
	 * horizontal boulder is only allowed to move right and left and a vertical boulder
	 * is only allowed to move up and down. A boulder can only move over a cell that
	 * is a floor or exit and is not already occupied by another boulder. The method
	 * does nothing under any of the following conditions:
	 * <ul>
	 * <li>The game is over.</li>
	 * <li>No boulder is currently grabbed by the user.</li>
	 * <li>A boulder is currently grabbed by the user, but the boulder is not allowed to
	 * move in the given direction.</li>
	 * </ul>
	 * If none of the above conditions are meet, the method does at least the following:
	 * <ul>
	 * <li>Moves the boulder object by calling its move() method.</li>
	 * <li>Calls placeBoulder() for the grid cell that the boulder is being moved into.</li>
	 * <li>Calls removeBoulder() for the grid cell that the boulder is being moved out of.</li>
	 * <li>Adds the move (as a Move object) to the end of the move history list.</li>
	 * <li>Increments the count of total moves made in the game.</li>
	 * </ul>
	 * 
	 * @param dir the direction to move
	 */
	public void moveGrabbedBoulder(Direction dir) {
		if (isGameOver()) return;
	    Boulder b = getGrabbedBoulder();
	    if (b == null) return;

	    if ((b.getOrientation() == HORIZONTAL && !(dir == LEFT || dir == RIGHT)) || 
	        (b.getOrientation() == VERTICAL && !(dir == UP || dir == DOWN))) {
	        return;
	    }

	    int fromRow = b.getFirstRow();
	    int fromCol = b.getFirstCol();
	    int deltaRow = (dir == UP) ? -1 : (dir == DOWN) ? 1 : 0;
	    int deltaCol = (dir == LEFT) ? -1 : (dir == RIGHT) ? 1 : 0;
	    int toRow = fromRow + deltaRow;
	    int toCol = fromCol + deltaCol;
	    int len = b.getLength();
	    api.Orientation ori = b.getOrientation();

	    for (int i = 0; i < len; i++) {
	        int r = fromRow + (ori == VERTICAL ? i : 0);
	        int c = fromCol + (ori == HORIZONTAL ? i : 0);
	        grid[r][c].removeBoulder();
	    }

	    boolean canMove = true;
	    for (int i = 0; i < len; i++) {
	        int r = toRow + (ori == VERTICAL ? i : 0);
	        int c = toCol + (ori == HORIZONTAL ? i : 0);
	        if (!isAvailable(r, c)) {
	            canMove = false;
	            break;
	        }
	    }

	    if (canMove) {
	        b.move(dir);
	        for (int i = 0; i < len; i++) {
	            int r = b.getFirstRow() + (ori == VERTICAL ? i : 0);
	            int c = b.getFirstCol() + (ori == HORIZONTAL ? i : 0);
	            grid[r][c].placeBoulder(b);
	        }
	        if (!moveHistory.isEmpty() && moveHistory.get(moveHistory.size() - 1).getDirection() == null) {
	            moveHistory.set(moveHistory.size() - 1, new Move(b, dir));
	        } 
	        else {
	            moveHistory.add(new Move(b, dir));
	        }
	    } 
	    else {
	        for (int i = 0; i < len; i++) {
	            int r = fromRow + (ori == VERTICAL ? i : 0);
	            int c = fromCol + (ori == HORIZONTAL ? i : 0);
	            grid[r][c].placeBoulder(b);
	        }
	    }
	}

	/**
	 * Resets the state of the game back to the start, which includes the move
	 * count, the move history, and whether the game is over. The method calls the
	 * reset method of each boulder object. It also updates each grid cells by calling
	 * their placeBoulder method to either set a boulder if one is located over the cell
	 * or set null if no boulder is located over the cell.
	 */
	public void reset() {
		moveHistory.clear();
	    for (Boulder b : boulders) {
	        b.reset();
	    }
	    
	    for (int row = 0; row < getRowSize(); row++) {
	        for (int col = 0; col < getColSize(); col++) {
	            grid[row][col].removeBoulder();
	        }
	    }
	    
	    for (Boulder b : boulders) {
	        int r = b.getFirstRow();
	        int c = b.getFirstCol();
	        api.Orientation ori = b.getOrientation();
	        int len = b.getLength();
	        
	        for (int i = 0; i < len; i++) {
	            int currentRow = r + (ori == VERTICAL ? i : 0);
	            int currentCol = c + (ori == HORIZONTAL ? i : 0);
	            grid[currentRow][currentCol].placeBoulder(b);
	        }
	    }
	}

	/**
	 * Returns a list of all legal moves that can be made by any boulder on the
	 * current board.
	 * 
	 * @return a list of legal moves
	 */
	public ArrayList<Move> getAllPossibleMoves() {
		ArrayList<Move> moves = new ArrayList<>();
	    for (Boulder b : boulders) {
	        int len = b.getLength();
	        int r = b.getFirstRow();
	        int c = b.getFirstCol();
	        if (b.getOrientation() == HORIZONTAL) {
	            if (isAvailable(r, c - 1)) {
	                moves.add(new Move(b, LEFT));
	            }
	            if (isAvailable(r, c + len)) {
	                moves.add(new Move(b, RIGHT));
	            }
	        } else {
	            if (isAvailable(r - 1, c)) {
	                moves.add(new Move(b, UP));
	            }
	            if (isAvailable(r + len, c)) {
	                moves.add(new Move(b, DOWN));
	            }
	        }
	    }
	    return moves;
	}

	/**
	 * Gets the list of all moves performed to get to the current position on the
	 * board.
	 * 
	 * @return a list of moves performed to get to the current position
	 */
	public ArrayList<Move> getMoveHistory() {
		return new ArrayList<>(moveHistory);
	}

	/**
	 * EXTRA CREDIT 5 POINTS
	 * <p>
	 * This method is only used by the Solver.
	 * <p>
	 * Undo the previous move. The method gets the last move on the moveHistory list
	 * and performs the opposite actions of that move, which are the following:
	 * <ul>
	 * <li>if required, sets is game over to false</li>
	 * <li>grabs the moved boulder and calls moveGrabbedBoulder passing the opposite
	 * direction</li>
	 * <li>decreases the total move count by two to undo the effect of calling
	 * moveGrabbedBoulder twice</li>
	 * <li>removes the move from the moveHistory list</li>
	 * </ul>
	 * If the moveHistory list is empty this method does nothing.
	 */
	public void undoMove() {
		// TODO
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		boolean first = true;
		for (Cell row[] : grid) {
			if (!first) {
				buff.append("\n");
			} else {
				first = false;
			}
			for (Cell cell : row) {
				buff.append(cell.toString());
				buff.append(" ");
			}
		}
		return buff.toString();
	}
}
