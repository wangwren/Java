package interfaces.adventure;
/**
 * 使用工厂方法创建一个框架，它可以执行抛硬币和掷骰子功能
 * @author wwr
 *
 */


interface Tossing{
	boolean event();
}

interface TossingFactory{
	Tossing getTossing();
}

/**
 * 抛硬币
 * @author wwr
 *
 */
class CoinTossing implements Tossing{

	private int events;
	private static final int EVENT = 2;
	
	@Override
	public boolean event() {
		System.out.println("Coin tossing event " + events);
		return ++events != EVENT;
	}
	
}

class CoinTossingFactory implements TossingFactory{

	@Override
	public Tossing getTossing() {
		return new CoinTossing();
	}
}

/**
 * 掷骰子
 * @author wwr
 *
 */
class DiceTossing implements Tossing{

	private int events;
	private static final int EVENT = 6;
	
	@Override
	public boolean event() {
		System.out.println("Dice tossing event " + events);
		return ++events != EVENT;
	}
}

class DiceTossingFactory implements TossingFactory{

	@Override
	public Tossing getTossing() {
		return new DiceTossing();
	}
	
}

public class E19_TossingFramework {

	public static void simulate(TossingFactory factory) {
		Tossing tossing = factory.getTossing();
		while(tossing.event());
	}
	
	public static void main(String[] args) {
		simulate(new CoinTossingFactory());
		simulate(new DiceTossingFactory());
	}
}
