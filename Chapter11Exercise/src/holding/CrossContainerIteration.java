package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class CrossContainerIteration {

	public static void display(Iterator<Integer> it) {
		while(it.hasNext()) {
			Integer i = it.next();
			System.out.print(i + " ");
		}
		System.out.println();
	}
	public static void main(String[] args) {
		ArrayList<Integer> aList = 
				new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		LinkedList<Integer> linkedList = 
				new LinkedList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		HashSet<Integer> hashSet = 
				new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		TreeSet<Integer> treeSet = 
				new TreeSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		display(aList.iterator());
		display(linkedList.iterator());
		display(hashSet.iterator());
		display(treeSet.iterator());
	}
}
