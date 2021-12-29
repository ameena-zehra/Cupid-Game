

public class ArrayStack<T> implements ArrayStackADT <T> {
	
	
	private T[] stack; 				// stores the data items of the stack
	private int top;				// stores the position of the last data item in the stack
	public static String sequence; 	// used for tracking the arrow path
	
	
	/**
	* Constructor
	* For cases where the initialCapacity is not given
	* Creates an empty stack at the default length being 14
	*/
	public ArrayStack(){
		sequence="";
		top = -1;
		int initialCapacity = 14;
		stack = (T[])(new Object[initialCapacity]);
	}
	
	/**
	* Constructor
	* For cases where the initialCapacity is given
	* Creates an empty stack using an array of length equal to the value of the parameter.
	* @param int initialCapacity is used to set the length of the array
	*/
	public ArrayStack(int initialCapacity){
		sequence ="";
		top = -1;
		stack = (T[])(new Object[initialCapacity]);
	}
	
	/**
	* Public push method that adds a dataItem to the top of the stack
	* If the array storing the data item is full expandCapacity() is called 
	* @param T dataItem is the data item to be added to the top of the stack
	*/
	public void push(T dataItem) {
		if (top+1==stack.length) {
			expandCapacity();
		}
		stack[top+1]= dataItem;
		top++;
		if (dataItem instanceof MapCell) {
			sequence += "push" + ((MapCell)dataItem).getIdentifier();
			}
			else {
			sequence += "push" + dataItem.toString();
			}

	}
	
	/**
	* Private helper method called by the push method above
	* If the capacity of the array is smaller than 50 then the capacity of the array will increase by 10
	* Else the capacity of the array will increase by doubling the initial size
	*/
	private void expandCapacity(){
		if (stack.length<50) {
			T[] larger = (T[])(new Object[stack.length+10]);
			for (int index=0; index<stack.length; index++) {
				larger[index] = stack[index];
			}
			stack=larger;
		}
		else {
			T[] larger = (T[])(new Object[stack.length*2]);
			for (int index =0; index<stack.length; index++) {
				larger[index] = stack[index];
			}
			stack=larger;
		}
	}
	
	/**
	* Public pop method that removes and returns the data item at the top of the stack
	* EmptyStackException is thrown if the stack is empty
	* If the number of remaining data items is smaller then 1/4th the length of the array shrinkSize() is called 
	* @return DataItem at the top of the stack
	*/
	public T pop() throws EmptyStackException{
		if (top+1==0) {
			throw new EmptyStackException("This Stack is Empty");
		}
		top--;
		T result = stack[top+1];
		stack[top+1]=null;
		if (top+1<((stack.length)/4)) {
			shrinkSize();
		}
		if (result instanceof MapCell) {
			sequence += "pop" + ((MapCell)result).getIdentifier(); }
		else {
			sequence += "pop" + result.toString(); }
		return result;	
	}
	
	/**
	* Private helper method called by the pop method above meant to shrink the stack
	* If half the length of the stack is greater than 14 then the smaller stack is set to this new length
	* Otherwise the length of the stack is set to the default of 14
	*/
	private void shrinkSize(){
		if ((stack.length/2)>14) {
			int newlength = (stack.length)/2;
			T[] smaller = (T[])(new Object[newlength]);
			for (int index=0; index<top+1; index++) {
				smaller[index] = stack[index];
			}
			stack = smaller;
		}
		else {
			T[] smaller = (T[])(new Object[14]);
			for (int index =0; index<top+1; index++) {
				smaller[index] = stack[index];
			}
			stack=smaller;
		}
	}
	
	/**
	* Public peek method that returns the data item at the top of the stack without removing it
	* EmptyStackException is thrown if the stack is empty 
	* @return DataItem at the top of the stack
	*/
	public T peek() throws EmptyStackException{
		// removes and returns the data items at the top of the stack
		// an empty stack exception is thrown if the stack is empty
		if (top+1==0) {
			throw new EmptyStackException("The Stack is Empty");
		}
		return stack[top];
	}
	
	/**
	* Public isEmpty method that returns true if the stack is empty and returns false otherwise
	* @return boolean whether or not the stack is empty
	*/
	public boolean isEmpty() {
		return top+1==0;
	}
	
	/**
	* Public size method that returns the number of data items in the stack
	* @return int number of data items in the stack
	*/
	public int size() {
		return top+1;	
	}
	
	/**
	* Public length method that returns the capacity of the array stack
	* @return int the capacity of the array stack based on its length
	*/
	public int length() {
		return stack.length;
	}
	
	/**
	* Public toString method that returns a string representation of the stack
	* @return a String representation of the stack
	*/
	public String toString() {
		String result = "Stack: ";
		for (int index=0; index<top;index++) {
			result += stack[index].toString()+", ";
		}
		result += stack[top].toString();
		return result;
	}

}
