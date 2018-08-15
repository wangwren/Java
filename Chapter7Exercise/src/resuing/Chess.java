package resuing;

class Game{
	Game(int i){
		System.out.println("Game constructor");
	}
}

class BoardGame extends Game{

	BoardGame(int i) {
		super(i);	//该语句必须放在构造器第一行，否则编译不通过
		System.out.println("BoardGame constructor");
	}
}

public class Chess extends BoardGame {

	Chess(){
		super(11);
		System.out.println("Chess constructor");
	}
	
	public static void main(String[] args) {
		Chess x = new Chess();
	}
}
