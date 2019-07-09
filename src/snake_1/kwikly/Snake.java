package snake_1.kwikly;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Snake extends ImageView {
	Timer snaketimer, dead;
	Sens s = Sens.BAS;
	Body tmp, child, last;
	Item Itmp;
	public int x=0, y=0 , nbchild=0, nbSwap=0, itmp, old_sens, time = 1500, reste=0;
	boolean GO = false, play = false;
	
    public Snake(Context context, AttributeSet attrs){
        super(context, attrs);
        this.setImageResource(R.drawable.oeil);
        this.setId(R.id.oeil);
        this.setRotation(180);
        dead = new Timer();
    }
    
    public void setSens(Sens _s){ s = _s; }
    public void setSens(int _s){ 
    	switch(_s){
		    case 0 : s = Sens.HAUT; setRotation(0); break;
		    case 1 : s = Sens.DROITE; setRotation(90); break;
		    case 2 : s = Sens.BAS; setRotation(180); break;
		    case 3 : s = Sens.GAUCHE; setRotation(270); break;}} 
    public Sens getSens(){ return s; }
    public void addChild(){
		if(nbchild==0){
			tmp = new Body(MainActivity.MA, null, x, y, nbchild);
			child = tmp; last = child;}
		else {
			if(nbSwap%2 == 0){
				tmp = new Body(MainActivity.MA, null, last.getBackx(), last.getBacky(), nbchild);
				tmp.setSens(last.getSens());
				last.setchild(tmp); 
				tmp.setLeader(last); 
				last = tmp;}
			else {
				tmp = new Body(MainActivity.MA, null, child.getx(), child.gety(), nbchild);
				child.setLeader(tmp); 
				tmp.setchild(child); 
				child = tmp; 
				child.setSens(s); 
				child.wait=true;}
		}
		tmp.setVisibility(GONE);
		MainActivity.rootlayout.addView(tmp);
		nbchild++;
    }
	private boolean headOnBody(int x, int y) {
		if(child != null){return child.headOnBody(x, y);}
		return false;
	}
	private boolean swap(){
		if(s == Sens.DROITE && (child.getBackx() == (x+1) || (x==0 && child.getBackx() == x))) {return true;}
		if(s == Sens.BAS 	&& (child.getBacky() == (y+1)  || (y==0 && child.getBacky() == y))) {return true;}
		if(s == Sens.GAUCHE && (child.getBackx() == x-1 ||( x == MainActivity.largeScreen-1 && child.getBackx() == x))) {return true;}
		if(s == Sens.HAUT 	&& (child.getBacky() == y-1 ||( y == MainActivity.longScreen-1 && child.getBacky() == y))) {return true;}
		return false;
	}
	public boolean getPlayState(){ return play; }
	private Item headOnItem(){
		int i;
		for(i=0; i<MainActivity.nbItem; i++){
			if(x == MainActivity.focus[i].getx() && y == MainActivity.focus[i].gety()) return MainActivity.focus[i];}
		return null;}
	public int getx(){return x;}
	public int gety(){return y;}
	public void setx(int x){this.x = x;}
	public void sety(int y){this.y = y;}
	public int getNbChild(){return nbchild;}
	public void setNbChild(int c){nbchild = c;}
	public Body getChild(){return child;}
	public void setReste(int reste){this.reste = reste;}
	public int getReste(){ return reste;}
	public Item getItmp(){ return Itmp;}
	public void setItmp(Item Itmp){this.Itmp = Itmp;}
	
	public void play(){
		if(snaketimer == null){
	        snaketimer = new Timer();
			snaketimer.schedule(new TimerTask() {
				@Override public void run() {
					post(new Runnable(){
						@Override public void run() {
							if(!GO){
								if(child!=null)child.setPosition(x, y);
								
								switch(s){
									case DROITE :
										if(x+1>=MainActivity.largeScreen)x=-1; 
										if(!headOnBody(x+1, y))setX(MainActivity.x[++x]); 
										x = (x == -1) ? 0 : x; break;
									case BAS :
										if(y+1>=MainActivity.longScreen)y=-1; 
										if(!headOnBody(x, y+1))setY(MainActivity.y[++y]);
										y = (y == -1) ? 0 : y; break;
									case GAUCHE :
										if(x-1<0)x=MainActivity.largeScreen; 
										if(!headOnBody(x-1, y))setX(MainActivity.x[--x]); 
										x = (x == MainActivity.largeScreen) ? MainActivity.largeScreen-1 : x; break;
									case HAUT :
										if(y-1<0)y=MainActivity.longScreen; 
										if(!headOnBody(x, y-1))setY(MainActivity.y[--y]);
										y = (y == MainActivity.longScreen) ? MainActivity.longScreen-1 : y; break; }
								if(child != null){	child.setVisibility(VISIBLE);
													child.setSens(s);}}
							
							if( headOnBody(x, y) ){
								if(swap() && !GO){
									nbSwap++;
									child.swapFamily();
									tmp = child; child = last; last = tmp;
									x = child.backx; y = child.backy;
									setX(MainActivity.x[x]); setY(MainActivity.y[y]);
									child.swapSens();
									setSens(child.s);
									setRotation(s.getRotationBySens());}
								else{
								GO = true;
								setImageResource(R.drawable.gameover);
								if(child.getIndex() == 0 || child.getIndex() == nbchild-1) child.setVisibility(GONE);
								if(GO == true){
								dead.schedule(new TimerTask() {
									@Override public void run() {
										post(new Runnable(){
											@Override public void run() {
												if(GO==true){
														tmp = child.getChild();
														MainActivity.rootlayout.removeView(child);
														child = tmp;
														nbchild--;
														MainActivity.scoreView.setText(String.valueOf(--MainActivity.score));
													if(child!=null){child.setPosition(x, y);}
													if(child==null){ 
														setImageResource(R.drawable.oeil); 
														GO = false; 
														MainActivity.score = 0;
														MainActivity.scoreView.setText(String.valueOf(MainActivity.score));
														dead.cancel(); dead = new Timer();}}
								}});}},600,90);}
								}
							}
							
							if(headOnItem() != null){
								Itmp = headOnItem();
								Itmp.setXY(-1, -1);
								Itmp.setVisibility(GONE);
								reste += Itmp.getType().getPointByType();
								System.out.println("HEAD ON ITEM");
								if(Itmp.getType() == Type.SIMPLE_BONUS || Itmp.getType() == Type.DOUBLE_BONUS){
									System.out.println("BONUS");
									MainActivity.scoreView.setText(String.valueOf(MainActivity.score += Itmp.getType().getPointByType()));
									reste -= Itmp.getType().getPointByType();}
							}
								
							if(Itmp != null && Itmp.getType().isBonus() && reste > 0){
								
									addChild(); 
									MainActivity.scoreView.setText(String.valueOf(++MainActivity.score));
									reste--;
							}
							if(MainActivity.score > MainActivity.record){
								MainActivity.recordView.setText(String.valueOf(MainActivity.score));
								MainActivity.record++;
							}
							old_sens = s.ordinal();
					}});
			}}, time, 130);}
		time = 0;
		setVisibility(VISIBLE);
		play = true;
	}
	
	public void pause(){
		if(snaketimer != null){
			snaketimer.cancel(); 
			snaketimer = null;
			play = false;
		}}}

