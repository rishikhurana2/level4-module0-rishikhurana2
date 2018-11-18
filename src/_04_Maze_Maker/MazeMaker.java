package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeMaker {

	private static int width;
	private static int height;

	private static Maze maze;

	private static Stack<Cell> uncheckedCells = new Stack<Cell>();

	public static Maze generateMaze(int w, int h) {
		width = w;
		height = h;
		maze = new Maze(width, height);

		// 4. select a random cell to start
		// for (int i = 0; i < uncheckedCells.size(); i++) {
		// uncheckedCells.set(i, uncheckedCells.get(i - 1));
		// uncheckedCells.set(i + 1, uncheckedCells.get(i + 3));
		// }

		Random randGen = new Random();
		int x = randGen.nextInt(width);
		int y = randGen.nextInt(height);
		Cell c = maze.getCell(x, y);
		// 5. call selectNextPath method with the randomly selected cell
		selectNextPath(c);
		return maze;
	}

	// 6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		// A. mark cell as visited
		currentCell.setBeenVisited(true);
		// B. check for unvisited neighbors using the cell
		// C. if has unvisited neighbors,
		ArrayList<Cell> unvisitedNeighbors = getUnvisitedNeighbors(currentCell);
		// System.out.println(unvisitedNeighbors.size());
		if (unvisitedNeighbors.size() > 0) {

			Random randUnvisited = new Random();
			int rand = randUnvisited.nextInt(unvisitedNeighbors.size());

			// C1. select one at random.
			Cell c1 = unvisitedNeighbors.get(rand);
			uncheckedCells.push(c1);
			// C2. push it to the stack
			// C3. remove the wall between the two cells
			removeWalls(currentCell, c1);
			// C4. make the new cell the current cell and mark it as visited
			currentCell = c1;
			currentCell.setBeenVisited(true);
			// C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);

		}
		// D. if all neighbors are visited
		else {
			// D1. if the stack is not empty
			if (!uncheckedCells.isEmpty()) {
				// D1a. pop a cell from the stack
				currentCell = uncheckedCells.pop();
				// D1b. make that the current cell

				// D1c. call the selectNextPath method with the current cell
				selectNextPath(currentCell);
			}
		}

	}

	// 7. Complete the remove walls method.
	// This method will check if c1 and c2 are adjacent.
	// If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		// remove c1 east || c2 west
		if (c1.getX() + 1 == c2.getX()) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		}
		// remove c1 west || c2 east
		if (c1.getX() - 1 == c2.getX()) {
			c1.setWestWall(false);
			c2.setEastWall(false);
		}
		// remove c1 north || c2 south
		if (c1.getY() - 1 == c2.getY()) {
			c1.setNorthWall(false);
			c2.setSouthWall(false);
		}
		// remove c1 south || c2 north
		if (c1.getY() + 1 == c2.getY()) {
			c1.setSouthWall(false);
			c2.setNorthWall(false);
		}
		Cell cStart = maze.getCell(0, 0);
		cStart.setNorthWall(false);
		Cell cEnd = maze.getCell(width - 1, height - 1);
		cEnd.setEastWall(false);
	}

	// 8. Complete the getUnvisitedNeighbors method
	// Any unvisited neighbor of the passed in cell gets added
	// to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> unvisitedCells = new ArrayList<Cell>();
		for (int i = c.getX() - 1; i <= c.getX() + 1; i++) {
			for (int j = c.getY() - 1; j <= c.getY() + 1; j++) {
				if ((i == c.getX() - 1 && j == c.getY() - 1) || (i == c.getX() + 1 && j == c.getY() + 1)
						|| (i == c.getX() + 1 && j == c.getY() - 1) || (i == c.getX() - 1 && j == c.getY() + 1)
						|| (i == c.getX() && j == c.getY())) {
					// System.out.println("bad cell");

				} else if ((i >= 0 && j >= 0) && (i < width && j < height)) {
					Cell neighborCell = maze.getCell(i, j);
					if (!neighborCell.hasBeenVisited()) {
						// System.out.println("adding cell");
						unvisitedCells.add(neighborCell);
					}
				}
			}
		}
		return unvisitedCells;
	}
}
