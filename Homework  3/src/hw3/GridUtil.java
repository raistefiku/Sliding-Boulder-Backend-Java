package hw3;

import static api.Orientation.*;

import static api.CellType.*;

import java.util.ArrayList;

import api.Cell;
import api.CellType;

/**
 * Utilities for parsing string descriptions of a grid.
 */
public class GridUtil {
	/**
	 * Constructs a 2D grid of Cell objects given a 2D array of cell descriptions.
	 * String descriptions are a single character and have the following meaning.
	 * <ul>
	 * <li>"*" represents a wall.</li>
	 * <li>"e" represents an exit.</li>
	 * <li>"." represents a ground.</li>
	 * <li>"[", "]", "^", "v", or "#" represent a part of a boulder. A boulder is not a
	 * type of cell, it is something placed on a cell ground. For these descriptions
	 * a cell is created with CellType of GROUND. This method does not create any
	 * boulders or set boulders on cells.</li>
	 * </ul>
	 * The method only creates cells and not boulders. See the other utility method
	 * findBoulders which is used to create the boulders.
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a 2D array of cells the represent the grid without any boulders present
	 */
	public static Cell[][] createGrid(String[][] desc) {
		int rows = desc.length;
		int cols = desc[0].length;
		Cell[][] grid = new Cell[rows][cols];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				String symbol = desc[r][c];
				CellType type;

				if (symbol.equals("*")) {
					type = WALL;
				} else if (symbol.equals("e")) {
					type = EXIT;
				} else {
					type = GROUND;  // includes ".", "[", "]", "^", "v", "#"
				}

				grid[r][c] = new Cell(r, c, type);
			}
		}
		return grid;
	}

	/**
	 * Returns a list of boulders that are constructed from a given 2D array of cell
	 * descriptions. String descriptions are a single character and have the
	 * following meanings.
	 * <ul>
	 * <li>"[" the start (left most column) of a horizontal boulder</li>
	 * <li>"]" the end (right most column) of a horizontal boulder</li>
	 * <li>"^" the start (top most row) of a vertical boulder</li>
	 * <li>"v" the end (bottom most column) of a vertical boulder</li>
	 * <li>"#" inner segments of a boulder, these are always placed between the start
	 * and end of the boulder</li>
	 * <li>"*", ".", and "e" symbols that describe cell types, meaning there is not
	 * boulder currently over the cell</li>
	 * </ul>
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a list of boulders found in the given grid description
	 */
	public static ArrayList<Boulder> findBoulders(String[][] desc) {
		ArrayList<Boulder> boulders = new ArrayList<>();

		int rows = desc.length;
		int cols = desc[0].length;

		// Find horizontal boulders
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (desc[r][c].equals("[")) {
					int startCol = c;
					int len = 1;
					c++;
					while (c < cols && !desc[r][c].equals("]")) {
						len++;
						c++;
					}
					if (c < cols && desc[r][c].equals("]")) {
						len++;  // include the "]" cell
						boulders.add(new Boulder(r, startCol, len, HORIZONTAL));
					}
				}
			}
		}

		// Find vertical boulders
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r < rows; r++) {
				if (desc[r][c].equals("^")) {
					int startRow = r;
					int len = 1;
					r++;
					while (r < rows && !desc[r][c].equals("v")) {
						len++;
						r++;
					}
					if (r < rows && desc[r][c].equals("v")) {
						len++;  // include the "v" cell
						boulders.add(new Boulder(startRow, c, len, VERTICAL));
					}
				}
			}
		}

		return boulders;
	}

}
