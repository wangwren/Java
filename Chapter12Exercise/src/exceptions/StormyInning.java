package exceptions;

class BaseballException extends Exception{}
class Foul extends BaseballException{}
class Strike extends BaseballException{}

abstract class Inning{
	public Inning() throws BaseballException{}
	public void event() throws BaseballException {
		//Dosen't actually have to throw anything
	}
	public abstract void atBat() throws Strike,Foul;
	public void walk(){}
}

class StormException extends Exception{}
class RainedOut extends StormException{}
class PopFoul extends Foul{}

interface Storm{
	public void event() throws RainedOut;
	public void rainHard() throws RainedOut;
}

public class StormyInning extends Inning implements Storm{

	public StormyInning() throws RainedOut,BaseballException {
		
	}
	
	public StormyInning(String s) throws Foul,BaseballException{
		
	}
	
	//实现的接口中有该方法，抽象类中也有该方法，可以选择不抛出异常
	public void event() {}
	
	
	@Override
	public void rainHard() throws RainedOut {}

	@Override
	public void atBat() throws PopFoul{}
	
	public static void main(String[] args) {
		try {
			StormyInning si = new StormyInning();
			si.atBat();
		}catch (PopFoul e) {
			System.out.println("Pop foul");
		}catch (RainedOut e) {
			System.out.println("Rained out");
		}catch (BaseballException e) {
			System.out.println("Generic baseball exception");
		}
		
		try {
			Inning i = new StormyInning();
			i.atBat();
		}catch (Strike e) {
			System.out.println("Strike");
		}catch (Foul e) {
			System.out.println("Foul");
		}catch (RainedOut e) {
			System.out.println("Rained out");
		}catch (BaseballException e) {
			System.out.println("Generic baseball exception");
		}
	}
}
