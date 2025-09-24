package api;

import hw3.Boulder;

/**
 * Represents moving a boulder.
 * 
 * @author tancreti
 */
public class Move {
	/**
	 * the moved boulder
	 */
	private Boulder boulder;

	/**
	 * the direction the boulder is moved
	 */
	private Direction direction;

	/**
	 * Constructs a new move for a given boulder and direction.
	 * 
	 * @param grabbedBoulder  the given boulder
	 * @param direction       the given direction
	 */
	public Move(Boulder grabbedBoulder, Direction direction) {
		this.boulder = grabbedBoulder;
		this.direction = direction;
	}

	/**
	 * Returns the moved boulder.
	 * 
	 * @return the moved boulder
	 */
	public Boulder getBoulder() {
		return boulder;
	}

	/**
	 * Returns the moved direction.
	 * 
	 * @return the moved direction
	 */
	public Direction getDirection() {
		return direction;
	}

	@Override
	public String toString() {
		return "(" + boulder.getFirstRow() + ", " + boulder.getFirstCol() + ") one cell " + direction;
	}
}
