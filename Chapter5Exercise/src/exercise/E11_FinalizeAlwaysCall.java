package exercise;

public class E11_FinalizeAlwaysCall {

	@Override
	protected void finalize() throws Throwable {
		System.out.println("finalize() called");
	}

	public static void main(String[] args) {
		
		new E11_FinalizeAlwaysCall();
		System.gc();	//��������������
		System.runFinalization();	// ���д��ڹ�����ֹ״̬�����ж������ֹ������
	}
}
