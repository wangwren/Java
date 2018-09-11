package holding;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public class CollectionSequence extends AbstractCollection<Integer> {

	private Integer[] pets = {1,2,3,4,5,6,7,8};
	
	@Override
	public Iterator<Integer> iterator() {
		
		return new Iterator<Integer>() {
			private int index = 0;
			@Override
			public boolean hasNext() {
				
				return index < pets.length;
			}

			@Override
			public Integer next() {

				return pets[index++];
			}

			@Override
			public void remove() {
				//Not implemented
				throw new UnsupportedOperationException();
			}
		};
	}

	@Override
	public int size() {
		
		return pets.length;
	}
	
	public static void main(String[] args) {
		CollectionSequence c = new CollectionSequence();
		InterfaceVsIterator.display(c);
		InterfaceVsIterator.display(c.iterator());
	}
}

class InterfaceVsIterator{
	public static void display(Iterator<Integer> it) {
		while(it.hasNext()) {
			Integer i = it.next();
			System.out.print(i + " ");
		}
		System.out.println();
	}
	public static void display(Collection<Integer> pets) {
		for(Integer i : pets) {
			System.out.print(i + " ");
		}
		System.out.println();
	}
}
