package holding;

import java.util.Iterator;

class PetSequence{
	protected Integer[] pets = {1,2,3,4,5,6,7,8};
}

public class NonCollectionSequence extends PetSequence {
	public Iterator<Integer> iterator(){
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
				throw new UnsupportedOperationException();
			}
		};
	}
	public static void main(String[] args) {
		NonCollectionSequence nc = new NonCollectionSequence();
		InterfaceVsIterator.display(nc.iterator());
	}
}
