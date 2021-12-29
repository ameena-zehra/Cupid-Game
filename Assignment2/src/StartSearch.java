


import java.io.FileNotFoundException;
import java.io.IOException;


public class StartSearch {
	
	private Map targetMap;	// this variable will reference the object representing the map where Cupid and the targets are located
	private int numArrows;	// how many arrows Cupid has fired to so far--> compared to the quiverSize from the Map
	private int inertia;	// used for tracking how many times an arrow has traveled in the same direction, starts at 0
	private int direction;	// used for tracking the direction of the arrow--> 0= north, 1=east, 2=south, 3=west
	
	
	
	/**
	* Constructor
	* Creates an object of the class Map passing as parameter the given input file
	* Catches InvalidMapException, FileNotFoundException and IOException and prints a message and the stacktrace below it
	* @param String filename containing the description of the map
	*/
	public StartSearch(String filename) {
		try {
			targetMap = new Map(filename);
		} catch (InvalidMapException e) {
			System.out.println("getMessage(): " + e.getMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("getMessage(): " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("getMessage(): " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	/**
	* Public  nextCell method
	* @param  MapCell current cell
	* @return MapCell the best neighboring cell to continue the path from the current one given the program limitations
	* 		  If no cell is found this method will return null
	*/
	public MapCell nextCell(MapCell cell) {
		if (cell.isStart()) {
			return setDirection(cell); 
		}
		else {
			// if the neighboring cell at the set direction is NOT marked AND the cell is NOT blocked, then return the neighboring cell at the direction
			MapCell nextCell = maintainDirection(cell, direction);
			if (nextCell != null) {
				return nextCell;
			}
			else if (inertia < 3) { // when the inertia is less than 3 then the cell has unlimited movement/ turns in either cross paths
				return changeDirection(cell); //can only turn if the cell is a cross path
			}
		}
		return null;
	}
	
	/**
	* Private helper method
	* Used to set the direction if the current cell given is the starting cell
	* @param MapCell cell --> start cell
	* @return the best neighboring cell prioritized based on index, type and whether or not it is either marked or blocked
	* 		  if no cell is found this method returns null
	*/
	private MapCell setDirection(MapCell cell) {
		for (int i=0; i<4; i++) {
			if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isTarget()==true) && (cell.getNeighbour(i).isBlackHole()==false) && (cell.getNeighbour(i).isMarked()==false)) {
				direction = i;
				inertia = 0;					// as it is turning into a new direction the inertia is set to 0
				return cell.getNeighbour(i);
			}
		}
		for (int i=0; i<4; i++) {
			if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isCrossPath()==true) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
				direction = i;
				inertia = 0;
				return cell.getNeighbour(i);
				}
		}
		for (int i=0; i<4; i++) {
			if ((cell.getNeighbour(i)!=null) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
				direction = i;
				inertia = 0;
				return cell.getNeighbour(i);
				}
		}
		return null;
	}
	
	/**
	* Private helper method
	* Used to attempt to initially go straight and return the next available cell if it fulfills the specifications
	* If direction is maintained inertia is incremented
	* @param MapCell current cell
	* @param int current direction
	* @return the straight neighboring cell suitable based on direction, type and whether or not it is either marked or blocked
	* 		  if no cell is found this method returns null
	*/
	private MapCell maintainDirection(MapCell cell, int direction) {
		if ((cell.getNeighbour(direction)!=null) && (cell.getNeighbour(direction).isMarked()==false) && (cell.getNeighbour(direction).isBlackHole() ==false)) {
			if ((direction==0|| direction ==2) && ((cell.isVerticalPath()==true)|| (cell.isCrossPath()==true)) && ((cell.getNeighbour(direction).isTarget()==true)||(cell.getNeighbour(direction).isVerticalPath()==true)|| (cell.getNeighbour(direction).isCrossPath()==true))){
				inertia++;					// as it is continuing in the same direction the inertia is incremented
				return cell.getNeighbour(direction);
			}
			if ((direction==1|| direction ==3) && ((cell.isHorizontalPath()==true)|| (cell.isCrossPath()==true)) && ((cell.getNeighbour(direction).isTarget()==true)||(cell.getNeighbour(direction).isHorizontalPath()==true)|| (cell.getNeighbour(direction).isCrossPath()==true))){
				inertia++;
				return cell.getNeighbour(direction);
			}
			else {
				return null;
			}
		}
		return null;
	}
	
	/**
	* Private helper method
	* If the maintainDirection returns null and inertia is less than 3 then the direction is changed
	* Only turns if the cell is a CrossPath and prioritizes the cells based on index, type as well as if its not blocked or marked
	* @param MapCell current cell
	* @return the next best neighboring cell declared suitable based on direction, type and whether or not it is either marked or blocked
	* 		  if no cell is found this method returns null
	*/
	private MapCell changeDirection(MapCell cell) {
		if (cell.isCrossPath()==true){
			for (int i=0; i<4;i++) {
				if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isMarked()==false) && (cell.getNeighbour(i).isBlackHole()==false) && (cell.getNeighbour(i).isTarget()==true)) {
					inertia = 0;			// as the cell is moving in a different direction inertia is set to 0
					direction = i;
					return cell.getNeighbour(i);
				}
			}
			for (int i=0; i<4;i++) {
				if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isMarked()==false) && (cell.getNeighbour(i).isBlackHole()==false) && (cell.getNeighbour(i).isCrossPath()==true)) {
					inertia = 0;
					direction = i;
					return cell.getNeighbour(i);
				}
			}
			for (int i=0; i<4;i++) {
				if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isMarked()==false) && (cell.getNeighbour(i).isBlackHole()==false)) {
					if ((i==0||i==2)&&(cell.getNeighbour(i).isVerticalPath()==true)) {
						inertia = 0;
						direction = i;
						return cell.getNeighbour(i);
					}
					if ((i==1||i==3)&&(cell.getNeighbour(i).isHorizontalPath()==true)) {
						inertia = 0;
						direction = i;
						return cell.getNeighbour(i);
					}
				}
			}
		}
		return null;
	}
	
	/**
	* Private Accessor Method
	* Used in main to get the starter cell
	* @return the start cell using the targetMap getStart() method
	*/
	private MapCell getStart() {
		return targetMap.getStart();
	}
	
	/**
	* Private Accessor Method
	* Used in main to get cupid's quiver size
	* @return the quiver size using the targetMap getStart() method
	*/
	private int getQuiver() {
		return targetMap.quiverSize();
	}
	
	/**
	* Main method that will first create an object "searchobject" of the class StartSearch using the arguments given for the name of the input file and the maxPathlength
	* For each arrow the start cell is initialized and the program enters into a while loop to find the next suitable cell using the nextCell method
	* While the target has not been found or the map has not been maxed out or the number of arrows is less than the quiverSize the nextCell method is returned
	* If the nextCell method returns null then the cells are backtracked up to 3 times per arrow
	*/
	public static void main (String[] args) {
		if (args.length < 1) {
			System.out.println("You must provide the name of the input file"); 
			System.exit(0);
		}
		String nameofMapFile = args[0];
		int maxPathLength;							// longest path that Cupid's arrow can take to find targets
		if (args.length>1) {
			maxPathLength = Integer.parseInt(args[1]);
		}
		else {
			maxPathLength = 1000;
			// if else statement for the max path length, if not given, set it some arbitrary large value
		}
		
		int foundTargets =0;
		ArrayStack<Object> stack =  new ArrayStack<Object>(maxPathLength);
		StartSearch searchobject = new StartSearch(nameofMapFile);
		int quiverSize = searchobject.getQuiver();
		for (searchobject.numArrows=1; searchobject.numArrows<= quiverSize; searchobject.numArrows++) {
			boolean targetFound = false;			// if the target has been found this will set to true and the next arrow will be used
			boolean maxedout = false; 	 			// if the options are maxed out this will set to true and the next arrow will be used
			int pathlength =0;			 			// compared to the maxPathLength, counter for the number of squares an arrow passes
			int backtracks =0; 			 			// the number of backtracks for each arrow, can only go up to 3
			MapCell startCell = searchobject.getStart();
			stack.push(startCell);
			startCell.markInStack();
			while (targetFound==false||maxedout==false|| pathlength<=maxPathLength){
				MapCell currentCell = (MapCell) stack.peek();
				MapCell nextCell = searchobject.nextCell(currentCell);
				if (nextCell!= null) { 		   		// if the next cell isn't equal to null then push it onto the stack and mark it as inStack
					stack.push(nextCell);
					nextCell.markInStack();
					pathlength++;
					if (nextCell.isTarget()) { 		// if this next cell is the target then set the target found to true causing the loop to exit
						foundTargets++;
						targetFound =true;
						break;
					}
				}
				if (nextCell ==null) {
					if (currentCell.isStart()){ 	// if the current cell is the first cell and all of the options around the cell are returning null
						maxedout = true;			// then the map has been maxed out causing the loop to exit
						break;
					}
					else { 							// while the backtrack counter is less than or equal to 3 and the nextCell method returns null, the program keeps backtracking
						while (backtracks<3 && nextCell==null){
							currentCell = (MapCell) stack.pop();
							MapCell predecessor = (MapCell) stack.peek();
							backtracks++;
							pathlength++;
							nextCell = searchobject.nextCell(predecessor);		
						}
						if (nextCell != null) {
							stack.push(nextCell);
							nextCell.markInStack();
							pathlength++;
						}
						else {
							maxedout = true;
							break;
						}
					}
				}
			}
			
			// eventually when the target has been found or the map has been maxed out the stack is popped off 
			// until the second last where it is popped off but marked outofstack
			Object last = null;
			while (stack.size()>1) {
				last = stack.pop();
			}
			if (last!=null) {
				((MapCell) last).markOutStack();
			}
			if (stack.size()==1) {
				stack.pop();
			}
		}
		System.out.println("NUMBER OF FOUND TARGETS:     "+ foundTargets);
	}
}
