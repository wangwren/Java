package exercise11;

public class E11_AllTheColorsOfTheRainbow {

	int anIntegerRepresentingColors;
	void changeTheHueOfTheColor(int newHue) {
		anIntegerRepresentingColors = newHue;
	}
	
	public static void main(String[] args) {
		
		E11_AllTheColorsOfTheRainbow rainbow = new E11_AllTheColorsOfTheRainbow();
		rainbow.changeTheHueOfTheColor(47);
	}
}
