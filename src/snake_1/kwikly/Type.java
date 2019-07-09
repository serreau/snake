package snake_1.kwikly;

public enum Type {
//BONUS
SIMPLE,
TRIPLE,
QUINTUPLE,
SIMPLE_BONUS,
DOUBLE_BONUS,
RAPID_SNAKE,
GOST_SNAKE,
CONSTANT_ITEM,
MAJOR_ITEM,
// MALUS
LONG_SNAKE,
BAD_BODY,
RANDOM_PLACE,
SHORT_SNAKE;

	public int getIdResourceByType(){
		switch(this){
		case SIMPLE : return R.drawable.simple;
		case TRIPLE : return R.drawable.triple;
		case QUINTUPLE : return R.drawable.quintuple;
		case SIMPLE_BONUS : return R.drawable.simple_bonus;
		case DOUBLE_BONUS : return R.drawable.double_bonus;
		default : return R.drawable.simple;
		}
	}

	public int getPointByType(){
		switch(this){
		case SIMPLE 		: return 1;
		case TRIPLE 		: return 3;
		case QUINTUPLE 		: return 5;
		case SIMPLE_BONUS 	: return 1;
		case DOUBLE_BONUS 	: return 2;
		case RAPID_SNAKE 	: return 0;
		case GOST_SNAKE 	: return 0;
		case CONSTANT_ITEM 	: return 1;
		case MAJOR_ITEM 	: return 0;
		
		default : return 0;
		}
	}
	

	public boolean isBonus(){
		switch(this){
		case SIMPLE : return true;
		case TRIPLE : return true;
		case QUINTUPLE : return true;
		case SIMPLE_BONUS : return true;
		case DOUBLE_BONUS : return true;
		case RAPID_SNAKE : return true;
		case GOST_SNAKE : return true;
		case CONSTANT_ITEM : return true;
		case MAJOR_ITEM : return true;
		
		case LONG_SNAKE : return false;
		case BAD_BODY : return false;
		case RANDOM_PLACE : return false;
		case SHORT_SNAKE : return false;
		
		default : return true;
		}
	}
}
