package dk.unf.software.aar2013.gruppe8;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class StartMenuActivity extends Activity {
	
	

	Button bPlay_sm; 
	Button bGR_sm;
	Button bROT_sm;
	ImageView ivBomb;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_start_menu);		
		
		bPlay_sm = (Button) findViewById (R.id.bPlay_sm);
		bGR_sm = (Button) findViewById (R.id.bGR_sm);
		bROT_sm = (Button) findViewById (R.id.bROT_sm);
		ivBomb = (ImageView) findViewById(R.id.ivBomb);
		
			bPlay_sm.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent playknap = new Intent(StartMenuActivity.this,MainActivity.class);
					startActivity(playknap);
					
				}
			});
			
			bGR_sm.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				Intent gameruleknap = new Intent(StartMenuActivity.this,GameRuleActivity.class);	
					startActivity(gameruleknap);
				}
			});
			
			bROT_sm.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				Intent ranklistknap = new Intent(StartMenuActivity.this,RankListActivity.class);	
				startActivity(ranklistknap);
				}
			});
			
			ivBomb.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
					
				}
			});
		}
		
	}



