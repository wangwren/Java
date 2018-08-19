package polymorphism.rtti;

class Useful{
	public void f() {
		
	}
	public void g() {
		
	}
}

class MoreUseful extends Useful{
	public void f() {
		
	}
	public void g() {
		
	}
	public void u() {
		
	}
	public void v() {
		
	}
	public void w() {
		
	}
}

public class RTTI {

	public static void main(String[] args) {
		Useful[] x = {
			new Useful(),new MoreUseful()	
		};
		//x[1].u  访问不到
		
		((MoreUseful) x[1]).u();  //向下转型
		((MoreUseful) x[0]).u();  //java.lang.ClassCastException6
	}
}
