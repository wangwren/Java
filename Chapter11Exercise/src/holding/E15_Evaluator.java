package holding;
/**
 * ջ�ڱ�������о��������Ա��ʽ��ֵ��
 * ��ʹ��net.mindview.util.Stack������ı��ʽ��ֵ��
 * ���� + ��ʾ���������ĸѹ��ջ���� - ��ʾ����ջ����ĸ����ӡ��
 * +U+n+c---+e+r+t---+a-+i-+n+t+y---+-+r+u--+l+e+s---
 * @author wwr
 *
 */
public class E15_Evaluator {

	public static void main(String[] args) {
		evaluate("+U+n+c---+e+r+t---+a-+i-+n+t+y---+-+r+u--+l+e+s---");
	}

	private static void evaluate(String string) {
		Stack<Character> stack = new Stack<Character>();
		char[] ch = string.toCharArray();
		for(int i = 0;i < ch.length;) {
			//����i����++��
			switch (ch[i++]) {
			case '+':
				//�����+����ȡ+������ַ�������ջ�С�i�ټ�1
				stack.push(ch[i++]);
				break;
			case '-':
				System.out.print(stack.pop());
			}
		}
	}
}
