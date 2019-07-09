package snake_1.kwikly;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Launcher extends Activity {
	ImageView play;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		setRequestedOrientation(0);
		
		play = (ImageView) findViewById(R.id.startButton);
		play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent activity = new Intent(Launcher.this, MainActivity.class);
				startActivity(activity);
			}
		});
	}
	
}
