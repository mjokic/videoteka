package enumeracije;

public enum Pol {
	ZENSKI, // Z
	MUSKI; // M
	
	public static Pol fromSlovo(String slovo){
		switch (slovo) {
		case "M":
			return MUSKI;
		default:
			return ZENSKI;
		}
	}
	
	public static String toSlovo(Pol pol){
		switch (pol) {
		case MUSKI:
			return "M";
		default:
			return "Z";
		}
	}

}
