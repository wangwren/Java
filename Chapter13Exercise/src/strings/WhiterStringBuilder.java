package strings;

public class WhiterStringBuilder {

	public String implicit(String[] fileds) {
		String result = "";
		for(int i = 0;i < fileds.length;i++) {
			result += fileds[i];
		}
		return result;
	}
	public String explicit(String[] fileds) {
		StringBuilder result = new StringBuilder();
		for(int i = 0;i < fileds.length;i++) {
			result.append(fileds[i]);
		}
		return result.toString();
	}
}
