package interfaces.adventure;

interface CanFight{
	void fight();
}

interface CanSwin{
	void swim();
}

interface CanFly{
	void fly();
}

class ActionCharacter{
	public void fight() {
		
	}
}

class Hero extends ActionCharacter implements CanFight,CanSwin,CanFly{
	
	//CanFight接口与ActionCharacter类中的fight()方法的特征签名是一样的，而且在Hero
	//中并没有提供 fight() 的定义。
	
	@Override
	public void fly() {
		
	}

	@Override
	public void swim() {
		
	}
	
}

public class Adventure {

	public static void t(CanFight x) {
		x.fight();
	}
	public static void u(CanSwin x) {
		x.swim();
	}
	public static void v(CanFly x) {
		x.fly();
	}
	public static void w(ActionCharacter x) {
		x.fight();
	}
	public static void main(String[] args) {
		Hero h = new Hero();
		t(h);	//Treat it as a CanFight
		u(h);	//Treat it as a CanSwim
		v(h);	//Treat it as a CanFly
		w(h);	//Treat it as an ActionCharacter
	}
}
