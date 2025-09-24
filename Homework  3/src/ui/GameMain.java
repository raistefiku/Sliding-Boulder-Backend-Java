package ui;

import static api.CellType.EXIT;
import static api.CellType.GROUND;
import static api.CellType.WALL;
import static api.Orientation.HORIZONTAL;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import api.Cell;
import hw3.Boulder;
import hw3.Board;

/**
 * Main class for a GUI for a boulder sliding game. Sets up a GamePanel instance in
 * a frame.
 * <p>
 * EDIT THE create() METHOD TO CHANGE THE GAME.
 * 
 * @author smkautz
 * @author tancreti
 */
public class GameMain {
	/**
	 * Cell size in pixels.
	 */
	public static final int SIZE = 40;

	/**
	 * Dot size in pixels, must be less than or equal to SIZE.
	 */
	public static final int DOT_SIZE = 20;

	/**
	 * Line width in pixels.
	 */
	public static final int LINE_SIZE = 28;

	/**
	 * Font size for displaying score.
	 */
	public static final int SCORE_FONT = 24;

	/**
	 * Colors...
	 */
	public static final Color BACKGROUND_COLOR = new Color(0x8cb369);
	public static final Color WALL_COLOR = new Color(0x444444);
	public static final Color EXIT_COLOR = new Color(0xe4d275);
	public static final Color BLOCK_COLOR = new Color(0x6e44ff);
	public static final Color GRID_COLOR = Color.LIGHT_GRAY;

	/**
	 * a board grid for testing
	 */
	public static final Cell[][] testGrid1 = {
			{ new Cell(0, 0, WALL), new Cell(0, 1, WALL), new Cell(0, 2, WALL), new Cell(0, 3, WALL),
					new Cell(0, 4, WALL) },
			{ new Cell(1, 0, WALL), new Cell(1, 1, GROUND), new Cell(1, 2, GROUND), new Cell(1, 3, GROUND),
					new Cell(1, 4, WALL) },
			{ new Cell(2, 0, WALL), new Cell(2, 1, GROUND), new Cell(2, 2, GROUND), new Cell(2, 3, GROUND),
					new Cell(2, 4, EXIT) },
			{ new Cell(3, 0, WALL), new Cell(3, 1, GROUND), new Cell(3, 2, GROUND), new Cell(3, 3, GROUND),
					new Cell(3, 4, WALL) },
			{ new Cell(4, 0, WALL), new Cell(4, 1, WALL), new Cell(4, 2, WALL), new Cell(4, 3, WALL),
					new Cell(4, 4, WALL) } };

	/**
	 * Creates a list of blocks for testing.
	 * 
	 * @return a list of blocks
	 */
	public static ArrayList<Boulder> makeTest1Boulders() {
		ArrayList<Boulder> blocks = new ArrayList<Boulder>();
		blocks.add(new Boulder(2, 1, 2, HORIZONTAL));
		return blocks;
	}

	/**
	 * Helper method for instantiating the components. This method should be
	 * executed in the context of the Swing event thread only.
	 */
	private static void create() {
		// EDIT HERE TO CHANGE THE GAME BEING CREATED
		// ------------------------------------------

		// this will work if you don't have GridUtil implemented yet...
		Board board = new Board(testGrid1, makeTest1Boulders());

		// ------------------------------------------

		// create the three UI panels
		ScorePanel scorePanel = new ScorePanel();
		BoardPanel boardPanel = new BoardPanel(board, scorePanel);
		ButtonPanel buttonPanel = new ButtonPanel(boardPanel, scorePanel);

		// arrange the panels vertically
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(buttonPanel);
		mainPanel.add(scorePanel);
		mainPanel.add(boardPanel);

		// put main panel in a window
		JFrame frame = new JFrame("COM S 227 Boulder Slider");
		frame.getContentPane().add(mainPanel);

		// give panels a nonzero size
		Dimension d = new Dimension(board.getColSize() * GameMain.SIZE, board.getRowSize() * GameMain.SIZE);
		boardPanel.setPreferredSize(d);
		d = new Dimension(board.getColSize() * GameMain.SIZE, 3 * GameMain.SIZE);
		scorePanel.setPreferredSize(d);
		d = new Dimension(board.getColSize() * GameMain.SIZE, 2 * GameMain.SIZE);
		buttonPanel.setPreferredSize(d);
		frame.pack();

		// we want to shut down the application if the
		// "close" button is pressed on the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// rock and roll...
		frame.setVisible(true);
	}

	/**
	 * Entry point. Main thread passed control immediately to the Swing event
	 * thread.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				create();
			}
		};
		SwingUtilities.invokeLater(r);
	}
}
