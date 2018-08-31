package innerclasses;

public class Parcel9 {
	
	/*
	 *  Java ���˼���õ��� Java 5 �ı�������
	 *  �� Java 8 ֮ǰ�����а汾�� Java��
	 *  �ֲ��ڲ���������ڲ�����ʵľֲ�����������final���Σ�
	 *  java8��ʼ�����Բ���final���η�����ϵͳĬ����ӡ�
	 *  java��������ܳ�Ϊ��Effectively final ���ܡ�
	 */
	public Destination destination(final String dest) {//java8 ���Բ���final
		
		return new Destination() {
			private String label = dest;
			@Override
			public String readLabel() {
				return label;
			}
		};
	}
	public static void main(String[] args) {
		Parcel9 p = new Parcel9();
		Destination d = p.destination("Tasmania");
	}
}
