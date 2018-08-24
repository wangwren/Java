package interfaces.adventure;
/**
 * ʹ�ù�����������һ����ܣ�������ִ����Ӳ�Һ������ӹ���
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
 * ��Ӳ��
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
 * ������
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
