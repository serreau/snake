package snake_1.kwikly;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Item extends ImageView {
	
	private Random randomplace;
	public Timer focustimer;
	private int x=-1, y=-1, index;
	private boolean pause = true;
	private Type type = Type.SIMPLE;
	
	public Item(Context context, AttributeSet attrs) {super(context, attrs);}
	public Item(Context context, AttributeSet attrs, int idx, Type type) {
		super(context, attrs);
		
		index = idx;
		this.type = type;
		randomplace = new Random();
		
		setVisibility(GONE);
		setLayoutParams(MainActivity.lp);

		setImageResource(type.getIdResourceByType());
		
		MainActivity.rootlayout.addView(this);
		
	}

	public void setXY(int x, int y){setx(x);setx(y);}
	public int getx(){return x;}
	public int gety(){return y;}
	public void setx(int x){this.x = x;}
	public void sety(int y){this.y = y;}
	public int getIndex(){return index;}
	public Type getType(){ return type;}
	public void setTypeByOrdinal(int ordinal){
		switch(ordinal){
		case 1 : type = Type.SIMPLE;
		case 2 : type = Type.TRIPLE;
		case 3 : type = Type.QUINTUPLE;
		case 4 : type = Type.SIMPLE_BONUS;
		case 5 : type = Type.DOUBLE_BONUS;
		case 6 : type = Type.RAPID_SNAKE;
		case 7 : type = Type.GOST_SNAKE;
		case 8 : type = Type.CONSTANT_ITEM;
		case 9 : type = Type.MAJOR_ITEM;

		case 10 : type = Type.LONG_SNAKE;
		case 11 : type = Type.BAD_BODY;
		case 12 : type = Type.RANDOM_PLACE;
		case 13 : type = Type.SHORT_SNAKE;}
	}
	
	public void play(int time, int delay){
		focustimer = new Timer();
		focustimer.schedule(new TimerTask() {
			@Override public void run() {
				post(new Runnable() {
					public void run() {
						if(!pause){
							do{
									setVisibility(VISIBLE);
									x = randomplace.nextInt(MainActivity.largeScreen-1); 
									y = randomplace.nextInt(MainActivity.longScreen-1);
									setX(MainActivity.x[x]);setY(MainActivity.y[y]);
							}while(itemOnItem() || (x == MainActivity.snake.getx() && y == MainActivity.snake.gety()));
						}
						pause=false;
					}});}}, delay, time);
	}
	
	private boolean itemOnItem(){
		for(int i=0; i<index; i++){
			if(MainActivity.focus[i] != null){
				if(x == MainActivity.focus[i].getx() && y == MainActivity.focus[i].gety()) return true;}}
		return false;}
	
	public void pause(){
		setVisibility(GONE);
		pause = true;
		focustimer.cancel();
		focustimer = null;
	}

}
