package enumeracije;

public enum Aktivan {

	AKTIVAN, // 0
	NEAKTIVAN; // 1

	public static int toInt(Aktivan ak) {
		switch (ak) {
		case NEAKTIVAN:
			return 1;
		default:
			return 0;
		}
	}

	public static Aktivan fromInt(int akt) {
		switch (akt) {
		case 1:
			return NEAKTIVAN;

		default:
			return Aktivan.AKTIVAN;
		}
	}

}
