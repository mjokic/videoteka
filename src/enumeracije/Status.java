package enumeracije;

public enum Status {
	ADMIN, // admin - 0
	ZAPOSLEN; // zaposlen - 1
	
	public static Status toStatus(int broj){
		switch (broj) {
		case 0:
			return ADMIN;
		default:
			return ZAPOSLEN;
		}
	}
	
	public static int toNum(Status status){
		switch (status) {
		case ADMIN:
			return 0;
		default:
			return 1;
		}
	}

}
