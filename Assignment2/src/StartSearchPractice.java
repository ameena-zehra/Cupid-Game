


import java.io.FileNotFoundException;
import java.io.IOException;


public class StartSearchPractice {
	
	private Map targetMap; 		// this variable will reference the object representing the map where Cupid and the targets are located
	private static int numArrows; // how many arrows Cupid has fired to so far--> compared to the quiverSize from the Map
	//DOUBLE CHECK TO SEE IF YOU CAN KEEP THIS IS AS STATIC
	private int inertia;		// used for tracking how many times an arrow has travelled in the same direction , start at 0
	private int direction; 		// used for tracking the direction of the arrow--> 0= north, 1=east, 2=south, 3=west
	
	
	//Constructor
	/*
	 * must create an object of the class Map--> passing as a parameter the given input file--> displays the map on the screen
	 */
	public StartSearchPractice(String filename) {
		try {
			targetMap = new Map(filename);
		} catch (InvalidMapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private MapCell maintainDirection(MapCell cell, int direction) {
		if ((cell.getNeighbour(direction)!=null) && (cell.getNeighbour(direction).isMarked()==false) && (cell.getNeighbour(direction).isBlackHole() ==false)) {
			if ((direction==0|| direction ==2) && ((cell.isVerticalPath()==true)|| (cell.isCrossPath()==true)) && ((cell.getNeighbour(direction).isTarget()==true)||(cell.getNeighbour(direction).isVerticalPath()==true)|| (cell.getNeighbour(direction).isCrossPath()==true))){
				inertia++;
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
	private MapCell changeDirection(MapCell cell) {
		if (cell.isCrossPath()==true){
			for (int i=0; i<4;i++) {
				if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isMarked()==false) && (cell.getNeighbour(i).isBlackHole()==false) && (cell.getNeighbour(i).isTarget()==true)) {
					inertia = 0;
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
	public MapCell nextCell(MapCell cell) {
		if (cell.isStart()) {
			return setDirection(cell); // the change direction will return the next cell for the start cell or null if there are no available neighboring cells
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
		
	
	
	
	//Special helper method was created for turning direction that only turns if the neighbor cell is a cross path
	private MapCell turnDirection(MapCell cell) {
		if (cell.isTarget()==true) {
		// for loop will iterate through for indexes 1 to 4
		for (int i=0; i<4; i++) {
		// if the neighbor cell is the target and the cell is not blocked or marked then return the cell
		if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isTarget()==true) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
			direction =i;
			inertia =0;			// as it is turning into a new direction the inertia is set to 0
			return cell.getNeighbour(i);
			}
		}
		if (cell.isCrossPath()==true) {
		// for loop will iterate through for indexes 1 to 4
		for (int i=0; i<4; i++) {
			// if the neighbor cell is the Cross path and the cell is not blocked or marked then return the cell
		if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isCrossPath()==true) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
			direction =i;
			inertia =0;			// as it is turning into a new direction the inertia is set to 0
			return cell.getNeighbour(i);
			}
		}
		
		}
		else if (cell.isVerticalPath()==true) {
			// for loop will iterate through for indexes 0 and 2
			for (int i=0; i<4; i++) {
			// if the neighbor cell is the target and the cell is not blocked or marked then return the cell
			if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isTarget()==true) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
				direction =i;
				inertia =0;			// as it is turning into a new direction the inertia is set to 0
				return cell.getNeighbour(i);
				}
			}
			// for loop will iterate through for indexes 0 and 2
			for (int i=0; i<4; i+=2) {
				// if the neighbor cell is the Cross path and the cell is not blocked or marked then return the cell
			if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isCrossPath()==true) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
				direction =i;
				inertia =0;			// as it is turning into a new direction the inertia is set to 0
				return cell.getNeighbour(i);
				}
			}
		}
		else if (cell.isHorizontalPath()==true) {
			// for loop will iterate through for indexes 1 and 3
			for (int i=1; i<4; i+=2) {
			// if the neighbor cell is the target and the cell is not blocked or marked then return the cell
				if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isTarget()==true) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
					direction =i;
					inertia =0;			// as it is turning into a new direction the inertia is set to 0
					return cell.getNeighbour(i);
					}
				}
			// for loop will iterate through for indexes 1 and 3
			for (int i=1; i<4; i+=2) {
				// if the neighbor cell is the Cross path and the cell is not blocked or marked then return the cell
				if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isCrossPath()==true) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
					direction =i;
					inertia =0;			// as it is turning into a new direction the inertia is set to 0
					return cell.getNeighbour(i);
					}
				}
		}

		
		
	}
		return null;
	}
	// SPECIFICALLY FOR THE FRONT CELL
	private MapCell setDirection(MapCell cell) {
		// for loop will iterate through for indexes 1 to 4
		for (int i=0; i<4; i++) {
			// if the neighbor cell is the target and the cell is not blocked or marked then return the cell
			if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isTarget()==true) && (cell.getNeighbour(i).isBlackHole()==false) && (cell.getNeighbour(i).isMarked()==false)) {
				direction =i;
				inertia =0;			// as it is turning into a new direction the inertia is set to 0
				
				return cell.getNeighbour(i);
			}
		}
		// for loop will iterate through for indexes 1 to 4
		for (int i=0; i<4; i++) {
			// if the neighbor cell is the Cross path and the cell is not blocked or marked then return the cell
			if ((cell.getNeighbour(i)!=null) && (cell.getNeighbour(i).isCrossPath()==true) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
				direction =i;
				inertia =0;			// as it is turning into a new direction the inertia is set to 0
				return cell.getNeighbour(i);
				}
		}
		// for loop will iterate through for indexes 1 to 4
		for (int i=0; i<4; i++) {
		// if the cell is not blocked or marked then return the cell
			if ((cell.getNeighbour(i)!=null) && ((cell.getNeighbour(i).isBlackHole()==false) && cell.getNeighbour(i).isMarked()==false)) {
				direction =i;
				inertia =0;			// as it is turning into a new direction the inertia is set to 0
				return cell.getNeighbour(i);
				}
			}
		return null;					// if there are no available neighboring cells the method will return null, signifying that we have to end the program (as backtracking is not available to the front cell)
	}
	private MapCell getStart() {
		return targetMap.getStart();
	}
	private int getQuiver() {
		return targetMap.quiverSize();
	}
	public int getDirection() {
		return direction;
	}
	
	/* ROUGH WORK GET SECOND ROUGH DRAFT MAKE SURE TO DELETE THIS UPON SUBMISSION
	private MapCell getSecond(MapCell startcell) {
		MapCell neighbour0 = startcell.getNeighbour(0);
		MapCell neighbour1 = startcell.getNeighbour(1);
		MapCell neighbour2 = startcell.getNeighbour(2);
		MapCell neighbour3 = startcell.getNeighbour(3);
		if (neighbour0.isTarget()|| neighbour1.isTarget()|| neighbour2.isTarget()||neighbour3.isTarget()) {
			if (neighbour0.isTarget()&& (neighbour0.isMarked()==false)){
				direction =0;
				return neighbour0;
			}
			else if (neighbour1.isTarget()&& (neighbour1.isMarked()==false)){
				direction =1;
				return neighbour1;
			}
			else if (neighbour2.isTarget()&& (neighbour2.isMarked()==false)){
				direction =2;
				return neighbour2;
			}
			else if (neighbour3.isTarget()&& (neighbour3.isMarked()==false)){
				direction =3;
				return neighbour3;
			}
		}
		else if (neighbour0.isCrossPath()|| neighbour1.isCrossPath()|| neighbour2.isCrossPath()||neighbour3.isCrossPath()) {
			if (neighbour0.isCrossPath()&& (neighbour0.isMarked()==false)){
				direction = 0;
				return neighbour0;
			}
			else if (neighbour1.isCrossPath()&& (neighbour1.isMarked()==false)){
				direction =1;
				return neighbour1;
			}
			else if (neighbour2.isCrossPath()&& (neighbour2.isMarked()==false)){
				direction =2;
				return neighbour2;
			}
			else if (neighbour3.isCrossPath()&& (neighbour3.isMarked()==false)){
				direction =3;
				return neighbour3;
			}
		}
		else {
			if (neighbour0.isMarked()==false){
				direction =0;
				return neighbour0;
			}
			else if (neighbour1.isMarked()==false){
				direction =1;
				return neighbour1;
			}
			else if (neighbour2.isMarked()==false){
				direction =2;
				return neighbour2;
			}
			else if (neighbour3.isMarked()==false){
				direction = 3;
				return neighbour3;
			}
		}
		inertia =0;
		return null;
	}*/
	
	public static void main (String[] args) {
		if (args.length < 1) {
			System.out.println("You must provide the name of the input file"); 
			System.exit(0);
		}
		String nameofMapFile = args[0];
		int maxPathLength;
		if (args.length>1) {
			maxPathLength = Integer.parseInt(args[1]); //longest path that Cupid's arrow can take to find targets
		}
		else {
			maxPathLength = 1000;
			// if else statement for the max path length, if not given, set it some arbitrary large value
		}
		
		// java StartSearch nameOfMapFile (optional)maxPathLength;
		// for when I type into the command line
		int foundTargets =0;
		ArrayStack<Object> stack =  new ArrayStack<Object>(maxPathLength);
		StartSearchPractice searchobject = new StartSearchPractice(nameofMapFile);
		
		
		// ANOTHER while loop where we are doing it until the length of our current arrow runs out
		int quiverSize = searchobject.getQuiver();	// quiver size
		int cellstravelled =0; 		// keeps track of the number of cells traveled
		for (numArrows=1; numArrows<= quiverSize; numArrows++) {
			boolean targetFound = true; // if the target has been found this will set to true and the next arrow will be used
			boolean maxedout = true; 	 // if the options are maxed out this will set to true and the next arrow will be used
			int pathlength =0;			 // compared to the maxPathLength, counter for the number of squares an arrow passes
			int backtracks =0; 			 // the number of backtracks for each arrow, can only go up to 3
			// START CELL INSTRUCTIONS
			// Get the start cell
			MapCell startCell = searchobject.getStart();
			// Push the start cell onto the stack
			stack.push(startCell);
			// Mark the start cell as inStack
			startCell.markInStack();
			// while the target is not found or the map has not been maxed out or the path length is less than or equal to the max path length
			System.out.println("ON THE "+numArrows+"th iteration");
			while (targetFound==true||maxedout==true|| pathlength<=maxPathLength){
				// peek at the top of the stack to get the current cell
				MapCell currentCell = (MapCell) stack.peek();
				MapCell nextCell = searchobject.nextCell(currentCell);
				System.out.println(nextCell);
				System.out.println(targetFound);
				if (nextCell!= null) { 		   // if the next cell isn't equal to null then push it onto the stack and mark it as inStack
					stack.push(nextCell);
					nextCell.markInStack();
					pathlength++;
					if (nextCell.isTarget()) { // if this next cell is the target then set the target found to true causing the loop to exit
						foundTargets++;
						cellstravelled++;
						System.out.println("THE TARGET HAS BEEN FOUND");
						targetFound =false;
						break;
					}
				}
				if (nextCell ==null) {
					if (currentCell.isStart()){ // if the current cell is the first cell and all of the options around the cell are returning null
						maxedout = false;		// then the map has been maxed out causing the loop to exit
						break;
					}
					else {  // WE ARE GOING TO BACKTRACK
						while (backtracks<3 && nextCell==null){
						// backtrack to the previous cell
							currentCell = (MapCell) stack.pop();
							MapCell predecessor = (MapCell) stack.peek();
							System.out.println("DIRECTION"+searchobject.getDirection());
							backtracks++;
							System.out.println("BACKTRACKS"+backtracks);
							pathlength++;
							nextCell = searchobject.nextCell(predecessor);
							
						}
						if (nextCell != null) {
							stack.push(nextCell);
							nextCell.markInStack();
							pathlength++;
						}
						else {
							maxedout = false;
							break;
						}
						
						
					}
					
				}
				
			
			}
			
			// eventually when the target has been found or the map has been maxed out the stack is popped off until the second last where it is popped off but marked outofstack
			Object last = null;
			System.out.println("WE MADE IT OUT BOIS");
			while (stack.size()>1) {
				last = stack.pop();
				System.out.println(last);
			}
			if (last!=null) {
				((MapCell) last).markOutStack();
			}
			if (stack.size()==1) {
				stack.pop();
			}
			System.out.println("WE REALLY MADE IT OUT BOIS");
		}
		
		
	}

}
