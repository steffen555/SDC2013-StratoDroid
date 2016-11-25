package dk.unf.software.aar2013.gruppe8;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;

public class GameTurnSwitch extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_turn_switch);

		AlertDialog.Builder TurnPopup = new AlertDialog.Builder(this);
		TurnPopup.setMessage("Player COLOUR's Turn").setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//Intent ResumeGame = new Intent(GameTurnSwitch.this,
							//	MainActivity.class);
						finish();

					}
				});
		AlertDialog alert = TurnPopup.create();
		alert.show();

	}

}
