package snake_1.kwikly;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Body extends ImageView {
	int x, y, backx, backy, index;
	Body child, leader, tmp;
	Sens s = Sens.HAUT;
	boolean wait=false;
	
	public Body(Context context, AttributeSet attrs) {super(context, attrs);}
	public Body(Context context, AttributeSet attrs, int x, int y, int index) {
		super(context, attrs);
		this.index = index;
		this.setImageResource(R.drawable.body);
		setLayoutParams(MainActivity.lp);
		this.x = x; this.y = y;
		setPosition( x, y);
		
	}

    public void setchild(Body child){this.child = child;}
    public void setLeader(Body leader){this.leader = leader;}
    public int getIndex(){return index;}
    public Body getChild(){return child;}
    public Body getLeader(){return leader;}
    public void setPosition(int _x, int _y){
    	backx = x; backy = y;
    	if(child!=null && !wait){child.setPosition(x, y);}
    	wait = false;
    	x = _x; y = _y;
    	setX(MainActivity.x[_x]); setY(MainActivity.y[_y]);
    	if(x == MainActivity.snake.getx() && y == MainActivity.snake.gety())setVisibility(GONE);
    	else setVisibility(VISIBLE);
    	
    	if(leader != null){
    	if(leader.s.ordinal() == 0)			s = Sens.HAUT;
    	else if(leader.s.ordinal() == 1)	s = Sens.DROITE;
    	else if(leader.s.ordinal() == 2)	s = Sens.BAS;
    	else if(leader.s.ordinal() == 3)	s = Sens.GAUCHE;}
    }
    public int getx(){return x;}
    public int gety(){return y;}
    public void setx(int x){this.x = x;}
    public void sety(int y){this.y = y;}
    public Sens getSens(){return s;}
    public void setSens(Sens _s){ s = _s; }
    public void setSens(int _s){ 
    	switch(_s){
		    case 0 : s = Sens.HAUT;
		    case 1 : s = Sens.DROITE;
		    case 2 : s = Sens.BAS;
		    case 3 : s = Sens.GAUCHE;} 
    }
    public void setBackx(int backx){this.backx = backx;}
    public void setBacky(int backy){this.backy = backy;}
    public int getBackx(){return backx;}
    public int getBacky(){return backy;}
    public boolean headOnBody(int headx, int heady){
    	if(headx == x && heady == y){return true;}
    	else if (child != null) return child.headOnBody(headx, heady);
    	return false;
    }
    public void swapFamily(){
    	if(child != null){child.swapFamily();}
    	tmp = child;
    	child = leader;
    	leader = tmp;
    }
    public void swapSens(){
    	if(child != null){child.swapSens();}
    	s = s.getSwap();
    }
   /* public void pause(){
    	setVisibility(GONE);
    	if(child != null) child.pause();
    }*/
    public void play(){
    	setVisibility(VISIBLE);
    	if(child != null) child.play();
    }
}
