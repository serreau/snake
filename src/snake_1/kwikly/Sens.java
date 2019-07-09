package snake_1.kwikly;

public enum Sens {
HAUT,
DROITE,
BAS,
GAUCHE;

	public Sens getSwap(){
		switch(this){
			case HAUT: return BAS;
			case DROITE: return GAUCHE;
			case BAS: return HAUT;
			case GAUCHE: return DROITE;
			default : return null;}
	}
	
	public int getRotationBySens(){
		switch(this){
			case HAUT: return 0;
			case DROITE: return 90;
			case BAS: return 180;
			case GAUCHE: return 270;
			default : return -1;}
	}
}
