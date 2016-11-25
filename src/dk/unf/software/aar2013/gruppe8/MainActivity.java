package dk.unf.software.aar2013.gruppe8;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	GameBoard gameBoard;
	GameBoardLayout gameBoardLayout;
	int distance = 0;
	Display display;
	Canvas standardCanvas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(new Panel(this));
		gameBoard = new GameBoard();
		gameBoardLayout = new GameBoardLayout(gameBoard);
		display = getWindowManager().getDefaultDisplay();
		
	}

	class Panel extends View implements OnTouchListener {
		public Panel(Context context) {
			super(context);
			setOnTouchListener(this);
		}

		@Override
		public void onDraw(Canvas canvas) {
			gameBoardLayout.drawLayout(canvas, display, distance);
			standardCanvas = canvas;
		}

		int x = 0;
		int y = 0;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (gameBoardLayout.turnSwitch){
				gameBoardLayout.turnSwitch = false;
			}
			Log.e("", "TOUCH");
			String text = gameBoard.screenTouched(event, distance,
					display);
			Log.d("Position", "Text: " + text);
			if (text.length() == 3) {
				char cx = text.charAt(0);
				char cy = text.charAt(2);
				x = Character.getNumericValue(cx);
				y = Character.getNumericValue(cy);

				// Den får nogle tal i en string tilbage og konverterer dem
				// til int.
				Log.d("Position", "TL == 3: " + x + "," + y);
				gameBoardLayout.chooseToken(x, y);
				// gameBoardLayout.drawLayout(standardCanvas, display,
				// distance);
				invalidate();

			} else if (text.equals("12,1")) {
				if (gameBoard.moveUp(x, y)) {
					gameBoardLayout.setTokenChosen(false);
					gameBoardLayout.turnSwitch = true;
					invalidate();
				}
			} else if (text.equals("11,2")) {
				if (gameBoard.moveLeft(x, y)) {
					gameBoardLayout.setTokenChosen(false);
					gameBoardLayout.turnSwitch = true;
					invalidate();
				}
			} else if (text.equals("12,2")) {
				if (gameBoard.moveDown(x, y)) {
					gameBoardLayout.setTokenChosen(false);
					gameBoardLayout.turnSwitch = true;
					invalidate();
				}
			} else if (text.equals("13,2")) {
				if (gameBoard.moveRight(x, y)) {
					gameBoardLayout.setTokenChosen(false);
					gameBoardLayout.turnSwitch = true;
					invalidate();
				}
			}

			return false;
		}
	}

}
