package polymorphism.shape;

import java.util.Random;

public class RandomShapeGenerator {

	private Random rand = new Random(47);
	
	public Shape next() {
		//return就已经跳出switch了，不需要再写break了，写了反而报错
		switch (rand.nextInt(3)) {
			default:
				
			case 0:
				return new Circle();
			case 1:
				return new Square();
			case 2:
				return new Triangle();
		}
	}
}
