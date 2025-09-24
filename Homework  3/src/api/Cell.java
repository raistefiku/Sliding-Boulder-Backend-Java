package api;

import static api.CellType.*;

import hw3.Boulder;

/**
 * Class representing one cell in a 2D grid of cells for a boulder sliding
 * game. Each cell has a type, either WALL, GROUND, or EXIT.
 * <p> 
 * In addition to the cell type, a boulder may be placed on a cell and
 * the cell is aware of the presence of the boulder. In the game, boulders
 * can only be on GROUND and EXIT cells, not WALLs. See the methods
 * placeBoulder, getBoulder, and removeBoulder.
 *
 * @author tancreti
 */
public class Cell {
	/**
	 * The row location of the cell in the grid.
	 */
	private int row;

	/**
	 * The column location of the cell in the grid.
	 */
	private int col;

	/**
	 * The type of cell, either WALL, GROUND, or EXIT.
	 */
	private CellType type;

	/**
	 * The boulder that is located on the cell. It will be null if no
	 * boulder is present.
	 */
	private Boulder boulder;

	/**
	 * Constructs an cell with a given location on the grid and type.
	 * 
	 * @param row  the row location of the cell
	 * @param col  the column location of the cell
	 * @param type the given type
	 */
	public Cell(int row, int col, CellType type) {
		this.row = row;
		this.col = col;
		this.type = type;
		boulder = null;
	}

	/**
	 * Determines whether this cell is a wall.
	 * 
	 * @return true if this cell is a wall, false otherwise
	 */
	public boolean isWall() {
		return type == WALL;
	}

	/**
	 * Determines whether this cell is ground.
	 * 
	 * @return true if this cell is a floor, false otherwise
	 */
	public boolean isGround() {
		return type == GROUND;
	}

	/**
	 * Determines whether this cell is an exit.
	 * 
	 * @return true if this cell is an exit, false otherwise
	 */
	public boolean isExit() {
		return type == EXIT;
	}

	/**
	 * Determines whether a block is present on this cell.
	 * 
	 * @return true if a block is present, false otherwise
	 */
	public boolean hasBoulder() {
		return boulder != null;
	}

	/**
	 * Returns the boulder located on this cell.
	 * 
	 * @return the boulder located on this cell or null if none
	 */
	public Boulder getBoulder() {
		return boulder;
	}

	/**
	 * Sets the boulder for this cell to null.
	 */
	public void removeBoulder() {
		boulder = null;
	}

	/**
	 * Sets the boulder for this cell to the given object.
	 * 
	 * @param boulder the boulder to set
	 */
	public void placeBoulder(Boulder boulder) {
		this.boulder = boulder;
	}

	/**
	 * Gets the column location of the cell.
	 * 
	 * @return the column location of the cell
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Gets the row location of the cell.
	 * 
	 * @return the row location of the cell
	 */
	public int getRow() {
		return row;
	}

	@Override
	public String toString() {
		if (isWall()) {
			return "*";
		}
		if (isExit()) {
			return "e";
		}
		if (hasBoulder()) {
			if (boulder.getOrientation() == Orientation.HORIZONTAL) {
				if (col == boulder.getFirstCol()) {
					return "[";
				}
				if (col == boulder.getFirstCol() + boulder.getLength() - 1) {
					return "]";
				}
			} else {
				if (row == boulder.getFirstRow()) {
					return "^";
				}
				if (row == boulder.getFirstRow() + boulder.getLength() - 1) {
					return "v";
				}
			}
			return "#";
		}
		return ".";
	}
}
