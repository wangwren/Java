package holding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SimpleIteration {

	public static void main(String[] args) {
		List<Integer> list = 
				new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()) {
			Integer i = it.next();
			System.out.print(i + " ");
		}
		System.out.println();
		for(Integer i : list) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		it = list.iterator();
		for(int i = 0;i < 6;i++) {
			it.next();
			it.remove();
		}
		System.out.println(list);
	}
}
