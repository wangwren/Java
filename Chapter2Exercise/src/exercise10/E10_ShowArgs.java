package exercise10;
/**
 * 编写一个程序，
 * 打印出从命令行获得的三个参数。
 * 为此需要确定命令行数组中String的下标。
 * @author wwr
 *
 */
public class E10_ShowArgs {

	//如果使用eclipse来运行，选择run -> run configurations... 这一项，在Argument中输入参数
	public static void main(String[] args) {
		
		System.out.println(args.length);
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
	}
}
