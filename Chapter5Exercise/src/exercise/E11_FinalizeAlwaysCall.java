package exercise;

public class E11_FinalizeAlwaysCall {

	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalize() called");
	}

	public static void main(String[] args) {
		
		new E11_FinalizeAlwaysCall();
		System.gc();	//运行垃圾回收器
		System.runFinalization();	// 运行处于挂起终止状态的所有对象的终止方法。
	}
}
