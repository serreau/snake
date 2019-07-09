package snake_1.kwikly;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;

public class MainActivity extends Activity {
	public static Item focus[];
	public static LayoutParams lp;
	public static RelativeLayout rootlayout;
	public static Snake snake;
	public static int score = 0, record = 0, longScreen, largeScreen = 25, nbItem = 8, x[], y[];
	private int lastx, lasty, sens, reste, Itmp, childx[], childy[];
	public static MainActivity MA;
	public static TextView scoreView, recordView;
	private Body b_tmp;
	private ImageView pauseScreen;
	private boolean onPauseScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setRequestedOrientation(0);
		rootlayout = (RelativeLayout) findViewById(R.id.rl);
		MA = this;

		scoreView = (TextView) findViewById(R.id.score);
		recordView = (TextView) findViewById(R.id.record);

		initProportion();
		snake = (Snake) findViewById(R.id.oeil);
		lp = (LayoutParams) snake.getLayoutParams();
		lp.height = x[1];
		lp.width = x[1];
		snake.setLayoutParams(lp);
		
		focus = new Item[nbItem];
		for (int i = 0; i < nbItem; i++) {
			if(i < 1)focus[i] = new Item(rootlayout.getContext(), null, i, Type.QUINTUPLE);
			else if(i < 2) focus[i] = new Item(rootlayout.getContext(), null, i, Type.DOUBLE_BONUS);
			else if(i < 3) focus[i] = new Item(rootlayout.getContext(), null, i, Type.SIMPLE_BONUS);
			else if(i < 5) focus[i] = new Item(rootlayout.getContext(), null, i, Type.TRIPLE);
			else focus[i] = new Item(rootlayout.getContext(), null, i, Type.SIMPLE);
			focus[i].play(1000+i*500, 0);
		}
		
		snake.play();
		for(int i =0; i < snake.getNbChild()-1; i++) snake.getChild().play();
		
		rootlayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				if(!onPauseScreen){
					if (e.getX() >= rootlayout.getWidth()/2) {
						switch (snake.getSens()) {
						case DROITE: snake.setSens(Sens.BAS); snake.setRotation(180); break;
						case BAS: snake.setSens(Sens.GAUCHE); snake.setRotation(270); break;
						case GAUCHE: snake.setSens(Sens.HAUT); snake.setRotation(0); break;
						case HAUT: snake.setSens(Sens.DROITE); snake.setRotation(90); break;} 
					} else {
						switch (snake.getSens()) {
						case DROITE: snake.setSens(Sens.HAUT); snake.setRotation(0); break;
						case HAUT: snake.setSens(Sens.GAUCHE); snake.setRotation(270); break;
						case GAUCHE: snake.setSens(Sens.BAS); snake.setRotation(180); break;
						case BAS: snake.setSens(Sens.DROITE); snake.setRotation(90); break;}}}
				
				return false;}});
		
		if (savedInstanceState != null) {
			recordView.setText(savedInstanceState.getString("recordView"));
			scoreView.setText(savedInstanceState.getString("scoreView"));
			score = savedInstanceState.getInt("score");
			record = savedInstanceState.getInt("record");
			snake.setReste(savedInstanceState.getInt("reste"));
			snake.setx(savedInstanceState.getInt("x"));
			snake.sety(savedInstanceState.getInt("y"));
			snake.setX(x[snake.getx()]);
			snake.setY(y[snake.gety()]);
			for(int i=0; i<nbItem; i++) 
				{if(focus[i].getIndex() == savedInstanceState.getInt("Itmp")) snake.setItmp(focus[i]);}
			childx = new int[snake.getNbChild()]; 
			childy = new int[snake.getNbChild()];
			snake.setSens(savedInstanceState.getInt("sens"));
			for (int i = 0; i < savedInstanceState.getInt("nbchild"); i++) {
				snake.addChild();
				snake.last.setx(savedInstanceState.getIntArray("childx")[i]);
				snake.last.sety(savedInstanceState.getIntArray("childy")[i]);
				snake.last.setX(x[savedInstanceState.getIntArray("childx")[i]]);
				snake.last.setY(y[savedInstanceState.getIntArray("childy")[i]]);
				snake.last.setVisibility(ImageView.VISIBLE);
			}
			callPauseScreen();
		}
	}

	private void initProportion() {
		DisplayMetrics ecran = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(ecran);
		x = new int[largeScreen];
		for (int i = 0; i < largeScreen; i++)
			x[i] = ecran.widthPixels / largeScreen * i;
		longScreen = (ecran.heightPixels - (ecran.heightPixels % x[1])) / x[1];
		y = new int[longScreen];
		for (int i = 0; i < longScreen; i++)
			y[i] = ecran.heightPixels / longScreen * i;

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("recordView", recordView.getText().toString());
		savedInstanceState.putString("scoreView", scoreView.getText().toString());
		savedInstanceState.putInt("score", score);
		savedInstanceState.putInt("record", record);
		savedInstanceState.putInt("x", lastx);
		savedInstanceState.putInt("y", lasty);
		savedInstanceState.putInt("nbchild", snake.getNbChild());
		savedInstanceState.putInt("sens", sens);
		savedInstanceState.putInt("reste", reste);
		savedInstanceState.putInt("Itmp", Itmp);
		savedInstanceState.putIntArray("childx", childx);
		savedInstanceState.putIntArray("childy", childy);
		savedInstanceState.putBoolean("onPauseScreen", onPauseScreen);
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(snake.getPlayState()){
			if(keyCode == KeyEvent.KEYCODE_BACK){
				callPauseScreen();
				return true;
			}
		} 
		finish();
		return false;
	}
	
	private void callPauseScreen() {
		onPauseScreen=true;
		snake.pause();
		for(int i =0; i < nbItem; i++) focus[i].pause();
		pauseScreen = (ImageView) findViewById(R.id.pauseButton);
		pauseScreen.bringToFront();
		pauseScreen.setVisibility(ImageView.VISIBLE);
		pauseScreen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!snake.getPlayState()){
					snake.play();
					for(int i =0; i < MainActivity.nbItem; i++) MainActivity.focus[i].play(1000+i*500, 0);
					for(int i =0; i < snake.getNbChild()-1; i++) snake.getChild().play();
					pauseScreen.setVisibility(ImageView.GONE);
					onPauseScreen=false;
				}
			}
		});
	}

	@Override
	protected void onPause(){
		super.onPause();
		snake.pause();
		lastx = snake.getx();
		lasty = snake.gety();
		sens = snake.getSens().ordinal();
		if(snake.getChild() != null){
			b_tmp = snake.getChild();
			childx = new int[snake.getNbChild()];
			childy = new int[snake.getNbChild()];
			for(int i=0; i < snake.getNbChild(); i++){
				childx[i] = b_tmp.getx();
				childy[i] = b_tmp.gety();
				if(b_tmp.getChild() != null) b_tmp = b_tmp.getChild();
			}
		}
		reste = snake.getReste();
		Itmp = snake.getItmp().getIndex();
	}
}
