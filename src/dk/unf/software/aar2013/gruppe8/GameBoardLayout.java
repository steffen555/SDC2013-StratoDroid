package dk.unf.software.aar2013.gruppe8;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.Display;

public class GameBoardLayout {

	GameBoard gameBoard;
	int[][] charType;
	boolean[][] posAvail;
	int[][] tokenProp;
	boolean tokenChosen = false;

	public boolean isTokenChosen() {
		return tokenChosen;
	}

	public void setTokenChosen(boolean tokenChosen) {
		this.tokenChosen = tokenChosen;
	}

	int tokenChosenX;
	int tokenChosenY;
	Canvas lCanvas;
	Display lDisplay;
	int lDistance;
	Paint playfieldcolour;
	float playfield;
	int distance;
	Paint linePaint = new Paint();
	public boolean turnSwitch;
	int myTurn = 0;
	int yourTurn = 0;

	public GameBoardLayout(GameBoard gameBoard2) {
		gameBoard = gameBoard2;
	}

	public void drawLayout(Canvas canvas, Display display, int distance) {
		Log.d("OnDraw", "Drawing");
		if (gameBoard.redTurn) {
			myTurn = 1;
			yourTurn = 2;
		} else {
			myTurn = 2;
			yourTurn = 1;
		}
		lCanvas = canvas;
		lDisplay = display;
		lDistance = distance;
		this.distance = distance;
		linePaint.setColor(Color.WHITE);
		linePaint.setStrokeWidth(5);

		tokenProp = gameBoard.tokenProp;
		posAvail = gameBoard.posAvail;
		charType = gameBoard.charType;

		canvas.drawColor(Color.BLACK);

		if (display.getHeight() < display.getWidth()) {
			distance = display.getHeight();
			playfield = display.getHeight();
		} else {
			distance = display.getWidth();
			playfield = display.getWidth();
		}

		// Squires!
		playfieldcolour = new Paint();
		Paint tokencolour = new Paint();
		tokencolour.setAntiAlias(true);
		Paint textcolour = new Paint();
		textcolour.setTextAlign(Align.CENTER);
		textcolour.setTextSize(20);

		drawBackgroundSquares(canvas);
		// Tilføjer specielle knapper (hardcoded)
		drawControls(canvas);
		Log.d("OnDraw", "Drawing tokens");
		for (int i = 0; i < 10; i++) {
			String debug = "";
			for (int j = 0; j < 10; j++) {
				// Tegner brikker.
				debug += " " + tokenProp[i][j];
				tokencolour.setColor(Color.RED);
				if (tokenProp[j][i] == 1) {
					tokencolour.setColor(Color.RED);
					canvas.drawCircle((distance - distance / 15) / 10 * i
							+ distance / 30 + distance / 30 * 28 / 20,
							(distance - distance / 15) / 10 * j + distance / 30
									+ distance / 30 * 28 / 20, distance / 30,
							tokencolour);

				} else if (tokenProp[j][i] == 2) {
					tokencolour.setColor(Color.BLUE);
					canvas.drawCircle((distance - distance / 15) / 10 * i
							+ distance / 30 + distance / 30 * 28 / 20,
							(distance - distance / 15) / 10 * j + distance / 30
									+ distance / 30 * 28 / 20, distance / 30,
							tokencolour);
				} else if (tokenProp[j][i] == 3) {
					tokencolour.setColor(Color.WHITE);
					canvas.drawCircle((distance - distance / 15) / 10 * i
							+ distance / 30 + distance / 30 * 28 / 20,
							(distance - distance / 15) / 10 * j + distance / 30
									+ distance / 30 * 28 / 20, distance / 30,
							tokencolour);
				}
				// Skriver tal på brikkerne.

				if (tokenChosen && j == tokenChosenY && i == tokenChosenX)
					textcolour.setColor(Color.BLACK);
				else
					textcolour.setColor(Color.WHITE);

				if (tokenProp[j][i] == myTurn || tokenProp[j][i] == 3) {
					String text = "";
					if (charType[j][i] == 11)
						text = "B";
					else if (charType[j][i] == 0)
						text = "F";
					else
						text = "" + charType[j][i];

					canvas.drawText(text, (distance - distance / 15) / 10 * i
							+ distance / 30 + distance / 30 * 28 / 20,
							(distance - distance / 15) / 10 * (j + 1),
							textcolour);
				}
			}
			Log.d("tag", debug);
			for (int i1 = 0; i1 < 11; i1++) {
				// xStart, yStart, xSlut, ySlut
				// Matematik-ting for at placere den ordentligt.
				// Afstand til alle kanter = højde/30
				// Vandrette streger
				canvas.drawLine(distance / 30, (distance - distance / 15) / 10
						* i1 + distance / 30, (distance - distance / 30),
						(distance - distance / 15) / 10 * i1 + distance / 30,
						linePaint);
			}
			for (int j = 0; j < 11; j++) {
				// Lodrette streger
				canvas.drawLine((distance - distance / 15) / 10 * j + distance
						/ 30, distance / 30, (distance - distance / 15) / 10
						* j + distance / 30, (distance - distance / 30),
						linePaint);
			}
			for (int j = 12; j < 14; j++) {
				canvas.drawLine((distance - distance / 15) / 10 * j + distance
						/ 30, (distance - distance / 15) / 10 * 2 + distance
						/ 30, (distance - distance / 15) / 10 * j + distance
						/ 30, (distance - distance / 15) / 10 * 3 + distance
						/ 30, linePaint);
			}
			canvas.drawLine((distance - distance / 15) / 10 * 12 + distance
					/ 30, (distance - distance / 15) / 10 * 2 + distance / 30,
					(distance - distance / 15) / 10 * 13 + distance / 30,
					(distance - distance / 15) / 10 * 2 + distance / 30,
					linePaint);
		}
		if (turnSwitch) {
			canvas.drawColor(Color.BLACK);
			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(distance / 20);
			String turnColor = "";
			if (myTurn == 1)
				turnColor = "Red";
			else if (myTurn == 2)
				turnColor = "Blue";
			if (gameBoard.switchMessage != "win") {
				canvas.drawText(gameBoard.switchMessage, distance / 2,
						distance / 2 - 90, paint);
				canvas.drawText(turnColor + " player's turn.", distance / 2,
						distance / 2 - 30, paint);
				canvas.drawText("Please pass the device.", distance / 2,
						distance / 2 +30, paint);
				canvas.drawText("Touch inside the playfield when ready.",
						distance / 2, distance / 2 + 90, paint);
			} else {
				canvas.drawText("You captured the flag", distance / 2,
						distance / 2 - 30, paint);
				canvas.drawText("and won the game.", distance / 2,
						distance / 2 + 30, paint);
			}
			canvas.drawLine(distance / 30, (distance - distance / 15) / 10 * 0
					+ distance / 30, (distance - distance / 30),
					(distance - distance / 15) / 10 * 0 + distance / 30,
					linePaint);
			canvas.drawLine(
					(distance - distance / 15) / 10 * 0 + distance / 30,
					distance / 30, (distance - distance / 15) / 10 * 0
							+ distance / 30, (distance - distance / 30),
					linePaint);
			canvas.drawLine(distance / 30, (distance - distance / 15) / 10 * 10
					+ distance / 30, (distance - distance / 30),
					(distance - distance / 15) / 10 * 10 + distance / 30,
					linePaint);
			canvas.drawLine((distance - distance / 15) / 10 * 10 + distance
					/ 30, distance / 30, (distance - distance / 15) / 10 * 10
					+ distance / 30, (distance - distance / 30), linePaint);
		}

	}

	public void chooseToken(int x, int y) {
		Log.d("Debug", "chooseToken");
		if (x >= 0 && x <= 9 && y >= 0 && y <= 9) {
			if (tokenProp[y][x] == myTurn) {
				setChoosen(x, y);
			}
		}
	}

	public void setChoosen(int x, int y) {
		if (tokenChosen == false) {
			tokenChosen = true;
			tokenProp[y][x] = 3;
			tokenChosenX = x;
			tokenChosenY = y;
		} else {
			tokenProp[tokenChosenY][tokenChosenX] = myTurn;
			tokenProp[y][x] = 3;
			tokenChosenX = x;
			tokenChosenY = y;
		}
	}

	public void drawBackgroundSquares(Canvas canvas) {
		Log.d("OnDraw", "Drawing squares");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (posAvail[j][i] == true) {
					playfieldcolour.setColor(Color.GREEN);
				} else {
					playfieldcolour.setColor(Color.GRAY);
				}
				// Tegner spillefelter.
				canvas.drawRect((playfield - playfield / 15) / 10 * i
						+ playfield / 30, (playfield - playfield / 15) / 10 * j
						+ playfield / 30, (playfield - playfield / 15) / 10
						* (i + 1) + playfield / 30,
						(playfield - playfield / 15) / 10 * (j + 1) + playfield
								/ 30, playfieldcolour);

			}
		}
	}

	public void drawControls(Canvas canvas) {
		Log.d("OnDraw", "Drawing controls");
		canvas.drawRect(
				(playfield - playfield / 15) / 10 * 12 + playfield / 30,
				(playfield - playfield / 15) / 10 * 1 + playfield / 30,
				(playfield - playfield / 15) / 10 * (12 + 1) + playfield / 30,
				(playfield - playfield / 15) / 10 * (1 + 1) + playfield / 30,
				playfieldcolour);
		canvas.drawRect(
				(playfield - playfield / 15) / 10 * 11 + playfield / 30,
				(playfield - playfield / 15) / 10 * 2 + playfield / 30,
				(playfield - playfield / 15) / 10 * (11 + 3) + playfield / 30,
				(playfield - playfield / 15) / 10 * (2 + 1) + playfield / 30,
				playfieldcolour);

		// Seperators
	}

}
