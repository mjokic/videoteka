package enumeracije;

import videoteka.Videoteka;

public enum Medijum {

	VHS, // 0
	DVD, // 1
	BRDVD; // 2

	public static Medijum fromBroj(int broj) {
		switch (broj) {
		case 0:
			return VHS;
		case 1:
			return DVD;
		default:
			return BRDVD;
		}
	}

	public static int toBroj(Medijum medijum) {
		switch (medijum) {
		case VHS:
			return 0;

		case DVD:
			return 1;
		default:
			return 2;
		}
	}

	public static int toCena(Medijum medijum) {
		switch (medijum) {
		case VHS:
			return Videoteka.cenaVHS();
		case DVD:
			return Videoteka.cenaDVD();
		default:
			return Videoteka.cenaBRDVD();
		}
	}

}
