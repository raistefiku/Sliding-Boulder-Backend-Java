import static api.CellType.*;
import static api.Orientation.*;
import static api.Direction.*;

import java.util.ArrayList;
import java.util.Arrays;

import api.Cell;
import api.Move;
import hw3.Board;
import hw3.Boulder;
import hw3.GridUtil;

public class SimpleTests {
	public static final String[][] testDescription1 =
		{ { "*", "*", "*", "*", "*" },
		  { "*", ".", ".", ".", "*" },
		  { "*", "[", "]", ".", "e" },
		  { "*", ".", ".", ".", "*" },
		  { "*", "*", "*", "*", "*" } };

	private static final Cell[][] testGrid1 = {
			{ new Cell(0, 0, WALL), new Cell(0, 1, WALL), new Cell(0, 2, WALL), new Cell(0, 3, WALL), new Cell(0, 4, WALL) },
			{ new Cell(1, 0, WALL), new Cell(1, 1, GROUND), new Cell(1, 2, GROUND), new Cell(1, 3, GROUND), new Cell(1, 4, WALL) },
			{ new Cell(2, 0, WALL), new Cell(2, 1, GROUND), new Cell(2, 2, GROUND), new Cell(2, 3, GROUND), new Cell(2, 4, EXIT) },
			{ new Cell(3, 0, WALL), new Cell(3, 1, GROUND), new Cell(3, 2, GROUND), new Cell(3, 3, GROUND), new Cell(3, 4, WALL) },
			{ new Cell(4, 0, WALL), new Cell(4, 1, WALL), new Cell(4, 2, WALL), new Cell(4, 3, WALL), new Cell(4, 4, WALL) } };

	private static ArrayList<Boulder> makeTest1Boulders() {
		ArrayList<Boulder> boulders = new ArrayList<Boulder>();
		boulders.add(new Boulder(2, 1, 2, HORIZONTAL));
		return boulders;
	}

	public static void main(String args[]) {
		Boulder boulder = new Boulder(2, 1, 2, HORIZONTAL);
		System.out.println("Boulder is " + boulder);
		boulder.move(DOWN);
		System.out.println("After move DOWN, boulder is " + boulder);
		System.out.println("Expected boulder at (row=2, col=1).");
		boulder.move(RIGHT);
		System.out.println("After move RIGHT, boulder is " + boulder);
		System.out.println("Expected boulder at (row=2, col=2).");
		System.out.println();

		Cell[][] cells = GridUtil.createGrid(testDescription1);
		System.out.println("Using testDescription1, cell (2, 4) is an exit is "
					+ cells[2][4].isExit() + ", expected is true.");
		
		ArrayList<Boulder> boulders = GridUtil.findBoulders(testDescription1);
		System.out.println("Using testDescription1, number of boulders is "
					+ boulders.size() + ", expected is 1.");
		System.out.println("Using testDescription1, first boulder is length "
					+ boulders.get(0).getLength() + ", expected is 2.");
		System.out.println();


		System.out.println("Making board with testGrid1.");
		Board board = new Board(testGrid1, makeTest1Boulders());
		System.out.println(board.toString());
		System.out.println();

		board.grabBoulderAt(2, 1);
		System.out.println("Grabbed boulder " + board.getGrabbedBoulder());
		System.out.println("Grabbed boulder is at (" + board.getGrabbedBoulder().getFirstRow()
					+ ", " + board.getGrabbedBoulder().getFirstCol() + ") " + ", expected (2, 1).");
		System.out.println();
		
		board.moveGrabbedBoulder(RIGHT);
		System.out.println("After moving boulder right one time game over is " + board.isGameOver()
				+ ", expected is false.");
		System.out.println(board.toString());
		System.out.println();

		board.moveGrabbedBoulder(RIGHT);
		System.out.println("After moving boulder right two times game over is " + board.isGameOver()
				+ ", expected is true.");
		System.out.println(board.toString());
		System.out.println();
		
		board.reset();
		System.out.println("After reset:");
		System.out.println(board.toString());
		System.out.println();

		ArrayList<Move> moves = board.getAllPossibleMoves();
		System.out.println("All possible moves: " + Arrays.toString(moves.toArray()));
	}
}
