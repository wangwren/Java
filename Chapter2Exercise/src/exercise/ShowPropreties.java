package exercise;

public class ShowPropreties {

	public static void main(String[] args) {
		
		//System.getProperties()��������һ��properties����
		//list()��������Properties�����еķ���
		//list()�������þ��ǣ��������б������ָ���������
		System.getProperties().list(System.out);
		System.out.println(System.getProperty("user.name"));
		System.out.println(System.getProperty("java.library.path"));
		
	}
}
