package innerclasses;

public class Parcel8 {

	public Warpping warpping(int x) {
		return new Warpping(x) {
			public int value() {
				return super.value() * 47;
			}
		};
	}
	
	public static void main(String[] args) {
		Parcel8 p = new Parcel8();
		Warpping w = p.warpping(10);
	}
}
