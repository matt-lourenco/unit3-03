/****************************************************************************
 *
 * Created by: Matthew Lourenco
 * Created on: Mar 2018
 * This program creates a list of 250 values to test a class that preforms 
 *     sort and binary search methods.
 *
 * Edited on: Apr 2018
 * Edited to search recursively
 *
 ****************************************************************************/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class BinarySearch {
	
	public ArrayList<Integer> unsortedList = new ArrayList<Integer>();
	public ArrayList<Integer> sortedList = new ArrayList<Integer>();
	private int _min;
	private int _max;
	private int _length;
	private Random rand = new Random();
	
	private ArrayList<Integer> oldPositions = new ArrayList<Integer>();
	private int repeats = 1;
	private int index = -2;
	private int maxRecursiveChecks = 3;
	
	public BinarySearch() {
		//Default constructor
		this(250, 0, 500);
	}
	
	public BinarySearch(int length, int min, int max) {
		//Contructor for setting the size, upper, and lower bounds
		this._length = length;		
		this._min = min;
		this._max = max;
		
		this.newList();
	}
	
	public void newList() {
		//Creates a random list and returns it in string format. The
		//    random list is assigned to the unsortedlist field.
		
		for(int index=0; index<_length; index++) {
			unsortedList.add(rand.nextInt(_max + 1 - _min) + _min);
		}
		this.sort();
	}
	
	public void sort() {
		//Sorts the unsorted list and assigns the result to the sorted list
		@SuppressWarnings("unchecked")
		ArrayList<Integer> tempList = (ArrayList<Integer>) unsortedList.clone();
		Integer smallestNumber = 0;
		for(int repeat = 0 ; repeat<tempList.size() ; ) {
			
			smallestNumber = tempList.get(0);
			for(int number: tempList) {
				smallestNumber = smallestNumber > number ? number : smallestNumber;
			}
			
			sortedList.add(smallestNumber);
			tempList.remove(smallestNumber);
		}
	}
	
	public int searchBinary(int item) {
		// Use a recursive search
		return recursiveSearch(item, sortedList.size() / 2);
	}
	
	private boolean checkRecursion(double index) {
		// Stops the binary search if it is going on for too long
		
		//Allow the program to end the loop if it has to recheck
		//    the same value multiple times.
		if(oldPositions.size() >= maxRecursiveChecks) {
			if(oldPositions.get(oldPositions.size() - 1).equals( 
					oldPositions.get(oldPositions.size() - maxRecursiveChecks))) {
				return true;
			}
		}
		if((int) Math.round(index) >= sortedList.size()) {
			//Break loop if search resorts to checking outside of list
			return true;
		}
		return false;
	}
	
	private int recursiveSearch(int item, double index) {
		// Uses a recursive binary search to find the inputted item
		if(checkRecursion(index)) {
			this.index = -1;
		}
		//Check if index is correct
		else if(sortedList.get((int) Math.round(index)) == item) {
			this.index = (int) Math.round(index);
		} else if(sortedList.get((int) Math.round(index)) > item) {
			oldPositions.add((int) Math.round(index));
			index -= Math.pow(0.5, ++repeats) * sortedList.size();
			recursiveSearch(item, index);
		} else {
			oldPositions.add((int) Math.round(index));
			index += Math.pow(0.5, ++repeats) * sortedList.size();
			recursiveSearch(item, index);
		}
		return this.index;
	}
	
	public static void main(String[] args) throws Exception {
		//Generate a SearchableList and allow the user to add a number and
		//    search for a number.
		BufferedReader reader = new BufferedReader(new InputStreamReader
				(System.in));
		
		BinarySearch list = new BinarySearch(20, 0, 50);
		System.out.println(list.unsortedList.toString());
		System.out.println(list.sortedList.toString());
		
		System.out.println("Which number would you like to search for?");
		String input2 = reader.readLine();
		Integer numberToSearch = Integer.parseInt(input2);
		int index = list.searchBinary(numberToSearch);
		if(index != -1) {
			System.out.println(numberToSearch + " is located at index = " + index);
		} else {
			System.out.println(numberToSearch + " is not located in the list");
		}
	}
}