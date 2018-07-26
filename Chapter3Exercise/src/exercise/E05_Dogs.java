package exercise;

public class E05_Dogs {

	public static void main(String[] args) {
		
		Dog spot = new Dog();
		
		Dog scruffy = new Dog();
		
		spot.name = "spot";
		spot.says = "Ruff";
		
		scruffy.name = "scruffy";
		scruffy.says = "Wurf";
		
//		System.out.println("spot.name = " + spot.name);
//		System.out.println("spot.says = " + spot.says);
//		System.out.println("scruffy.name = " + scruffy.name);
//		System.out.println("scruffy.says = " + scruffy.says);
		
		
		Dog dog = new Dog();
		
//		dog = spot;
		System.out.println(dog.equals(spot));
		
	}
	
}

class Dog{
	
	String name;
	String says;
}