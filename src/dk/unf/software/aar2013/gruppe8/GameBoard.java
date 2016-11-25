package dk.unf.software.aar2013.gruppe8;

import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

public class GameBoard {

	int[][] gameBoard = new int[10][10];
	int currentX = 0;
	int currentY = 0;
	boolean redTurn = true;
	int myTurn = 0;
	int yourTurn = 0;
	public String switchMessage = "";
	int[][] charType = new int[][] { { 2, 6, 3, 2, 6, 2, 4, 3, 2, 2 },
			{ 2, 4, 11, 5, 3, 11, 7, 5, 11, 5 },
			{ 9, 11, 0, 11, 4, 8, 1, 7, 2, 2 },
			{ 10, 10, 2, 10, 11, 5, 0, 4, 3, 3 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
			{ 2, 2, 10, 1, 3, 5, 10, 3, 2, 2 },
			{ 2, 4, 11, 5, 3, 11, 7, 5, 11, 5 },
			{ 9, 11, 0, 11, 4, 8, 1, 7, 2, 2 },
			{ 7, 5, 8, 6, 11, 6, 10, 4, 3, 3 } };
	boolean[][] posAvail = new boolean[][] {
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true },
			{ true, true, true, true, true, true, true, true, true, true } };
	int[][] tokenProp = new int[][] { { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };
	int[] selectedToken = null;

	// Her slås brikkerne. Der returneres en int 0, 1, 2 eller 3.
	// 0 = begge dør.
	// 1 = angriberen dør.
	// 2 = modstanderen dør.
	// 3 = angriberen vinder spillet.

	public String screenTouched(MotionEvent event, double distance,
			Display display) {
		// SharedPreferences prefs =
		// PreferenceManager.getDefaultSharedPreferences(context);
		if (redTurn) {
			myTurn = 1;
			yourTurn = 2;
		} else {
			myTurn = 2;
			yourTurn = 1;
		}
		if (display.getHeight() < display.getWidth()) {
			distance = display.getHeight();
		} else {
			distance = display.getWidth();
		}
		double x = event.getX();
		double y = event.getY();
		double pfx = ((x - distance / 30) / distance * 100) / 28 * 30;
		double pfy = ((y - distance / 30) / distance * 100) / 28 * 30;
		int ix = (int) pfx / 10;
		int iy = (int) pfy / 10;
		if (ix < 10 && iy < 10 || ix == 12 && iy == 1
				|| (ix == 11 || ix == 12 || ix == 13) && iy == 2) {
			return ix + "," + iy;
		}
		return "";
	}

	public void switchTurn() {
		redTurn = !redTurn;
	}

	public boolean moveUp(int x, int y) {
		int p1id = charType[y][x];
		int p2id = charType[y - 1][x];
		redTurn = !redTurn;
		if (y != 0 && posAvail[y - 1][x] == true && tokenProp[y - 1][x] == 0) {
			Log.d("Movement", "Flyt op");
			tokenProp[y][x] = 0;
			tokenProp[y - 1][x] = myTurn;
			int char1 = charType[y][x];
			int char2 = charType[y - 1][x];
			charType[y][x] = char2;
			charType[y - 1][x] = char1;
			switchMessage = "You moved.";

			return true;
		} else if (y != 0 && posAvail[y - 1][x] == true
				&& tokenProp[y - 1][x] == yourTurn) {
			Log.d("Combat", "COMBAT!!!");
			Log.d("Combat", "CombRes: " + combat(p1id, p2id));
			int p1 = charType[y][x];
			int p2 = charType[y - 1][x];
			if (combat(p1, p2) == 2) {
				iDie(x, y);
			} else if (combat(p1, p2) == 1) {
				youDie(x, y, 1);
			} else if (combat(p1, p2) == 0) {
				bothDie(x, y, 1);
			}
			return true;
		}

		return false;
	}

	public boolean moveDown(int x, int y) {
		int p1id = charType[y][x];
		int p2id = charType[y + 1][x];
		redTurn = !redTurn;
		if (y != 9 && posAvail[y + 1][x] == true && tokenProp[y + 1][x] == 0) {
			Log.d("Movement", "Flyt ned");
			tokenProp[y][x] = 0;
			tokenProp[y + 1][x] = myTurn;
			int char1 = charType[y][x];
			int char2 = charType[y + 1][x];
			charType[y][x] = char2;
			charType[y + 1][x] = char1;
			switchMessage = "You moved.";
			return true;
		} else if (y != 9 && posAvail[y + 1][x] == true
				&& tokenProp[y + 1][x] == yourTurn) {
			Log.d("Combat", "COMBAT!!!");
			Log.d("Combat", "CombRes: " + combat(p1id, p2id));
			int p1 = charType[y][x];
			int p2 = charType[y + 1][x];
			if (combat(p1, p2) == 2) {
				iDie(x, y);
			} else if (combat(p1, p2) == 1) {
				youDie(x, y, 2);
			} else if (combat(p1, p2) == 0) {
				bothDie(x, y, 2);
			}
			return true;
		}
		return false;
	}

	public boolean moveLeft(int x, int y) {
		int p1id = charType[y][x];
		int p2id = charType[y][x - 1];
		redTurn = !redTurn;
		if (x != 0 && posAvail[y][x - 1] == true && tokenProp[y][x - 1] == 0) {
			Log.d("Movement", "Flyt venstre");
			tokenProp[y][x] = 0;
			tokenProp[y][x - 1] = myTurn;
			int char1 = charType[y][x];
			int char2 = charType[y][x - 1];
			charType[y][x] = char2;
			charType[y][x - 1] = char1;
			switchMessage = "You moved.";
			return true;

		} else if (x != 0 && posAvail[y][x - 1] == true
				&& tokenProp[y][x - 1] == yourTurn) {
			Log.d("Combat", "COMBAT!!!");
			Log.d("Combat", "CombRes: " + combat(p1id, p2id));
			int p1 = charType[y][x];
			int p2 = charType[y][x - 1];
			if (combat(p1, p2) == 2) {
				iDie(x, y);
				return true;
			} else if (combat(p1, p2) == 1) {
				youDie(x, y, 3);
			} else if (combat(p1, p2) == 0) {
				bothDie(x, y, 3);
			}
			return true;
		}
		return false;
	}

	public boolean moveRight(int x, int y) {
		int p1id = charType[y][x];
		int p2id = charType[y][x + 1];
		redTurn = !redTurn;
		if (x != 9 && posAvail[y][x + 1] == true && tokenProp[y][x + 1] == 0) {
			Log.d("Movement", "Flyt højre");
			tokenProp[y][x] = 0;
			tokenProp[y][x + 1] = myTurn;
			int char1 = charType[y][x];
			int char2 = charType[y][x + 1];
			charType[y][x] = char2;
			charType[y][x + 1] = char1;
			switchMessage = "You moved.";
			return true;
		} else if (x != 9 && posAvail[y][x + 1] == true
				&& tokenProp[y][x + 1] == yourTurn) {
			Log.d("Combat", "COMBAT!!!");
			Log.d("Combat", "CombRes: " + combat(p1id, p2id));
			int p1 = charType[y][x];
			int p2 = charType[y][x + 1];
			if (combat(p1, p2) == 2) {
				iDie(x, y);
			} else if (combat(p1, p2) == 1) {
				youDie(x, y, 4);
			} else if (combat(p1, p2) == 0) {
				bothDie(x, y, 4);
			}
			return true;
		}
		return false;
	}

	private int combat(int p1id, int p2id) {
		if (p1id == 3 && p2id == 11) {
			switchMessage = "Your sapper removed a bomb.";
			return 1;
		} else if (p1id == 1 && p2id == 10) {
			switchMessage = "Your spy killed a king.";
			return 1;
		} else if (p2id == 0) {
			switchMessage = "win";
			win();
			return 3;
		} else if (p1id > p2id) {
			switchMessage = "You killed a " + p2id + " with a " + p1id;
			return 1;
		} else if (p1id < p2id) {
			switchMessage = "You were killed by a " + p2id + " with a " + p1id;
			return 1;
		} else if (p1id == p2id) {
			switchMessage = "Both of your " + p1id + "'s died.";
			return 0;
		}
		return 2;
	}

	private void iDie(int x, int y) {
		Log.d("Game", "Your player died!");
		tokenProp[y][x] = 0;
		charType[y][x] = -1;

	}

	private void youDie(int x, int y, int button) {
		int ye = 0;
		int xe = 0;
		if (button == 1) {
			ye = y - 1;
			xe = x;
		} else if (button == 2) {
			ye = y + 1;
			xe = x;
		} else if (button == 3) {
			ye = y;
			xe = x - 1;
		} else if (button == 4) {
			ye = y;
			xe = x + 1;
		}
		Log.d("Game", "Your opponent died!");
		int char1 = charType[y][x];
		charType[ye][xe] = char1;
		tokenProp[y][x] = 0;
		tokenProp[ye][xe] = myTurn;
	}

	private void bothDie(int x, int y, int button) {
		Log.d("Game", "Both died!");
		int ye = 0;
		int xe = 0;
		if (button == 1) {
			ye = y - 1;
			xe = x;
		} else if (button == 2) {
			ye = y + 1;
			xe = x;
		} else if (button == 3) {
			ye = y;
			xe = x - 1;
		} else if (button == 4) {
			ye = y;
			xe = x + 1;
		}
		tokenProp[y][x] = 0;
		tokenProp[ye][xe] = 0;
		charType[y][x] = -1;
		charType[ye][xe] = -1;
	}

	private void win() {
		Log.d("Game", "You won! Congratulations!!!");
	}
}
