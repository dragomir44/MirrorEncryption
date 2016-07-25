package control;

import java.util.ArrayList;
import java.util.Arrays;

import model.BoardSpaces;
import model.Mirror;
import model.Mirrors;

public class MirrorEncryption {

	private BoardSpaces spaces = new BoardSpaces();
	private final static String PASSWORD = "TpnQSjdmZdpoohd";
	public StringBuilder decrypted = new StringBuilder();
	char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	char[] leftcol = Arrays.copyOfRange(alphabet, 0, alphabet.length / 4);;
	char[] bottomrow = Arrays.copyOfRange(alphabet, alphabet.length / 4, alphabet.length / 2);;
	char[] toprow = Arrays.copyOfRange(
			alphabet, alphabet.length / 2, (alphabet.length + alphabet.length / 2) / 2);;
	char[] rightcol = Arrays.copyOfRange(
			alphabet, (alphabet.length + alphabet.length / 2) / 2, alphabet.length);;
	int letter;

	
	// Method to print out the Textual Interface in the console
	public String toString(BoardSpaces space, ArrayList<Mirror> mirrors) {
		BoardSpaces protoMirrors = new BoardSpaces(space); 

		for (Mirror mirror: mirrors) {
			protoMirrors.put(mirror.col, mirror.row, mirror);
		}
		spaces = protoMirrors;
		StringBuilder boardString = new StringBuilder(" ");
		String rowline = "|\n";
		
		// get the size of the board
		int[] bounds = protoMirrors.getBoardBounds();
		int minRow = bounds[0];
		int maxRow = bounds[1];
		int minCol = bounds[2];
		int maxCol = bounds[3];


		for (int k = minCol; k < maxCol; k++) {
			boardString.append("|" + toprow[k]); // add top row
		}
		
		boardString.append(rowline);
		for (int i = minRow; i < maxRow; i++) { // loop trough rows
			boardString.append(leftcol[i]); // add left col
			for (int j = minCol; j < maxCol; j++) { // loop trough cols
				boardString.append("|");
				if (protoMirrors.containsKeys(i, j)) { // check if grid contains mirror
					boardString.append(protoMirrors.get(i, j).toString() + "");
				} else {
					boardString.append(" ");
				}
			}
			boardString.append("|" + rightcol[i] + rowline);
		}
		boardString.append(" ");
		for (int k = minCol; k < maxCol; k++) {
			boardString.append("|" + bottomrow[k]); // add bottom row
		}
		boardString.append(rowline); // end of row
		return boardString.toString();
	}
	
	public MirrorEncryption() {
		Mirrors mirrors = new Mirrors();
		toString(spaces, mirrors.getMirrorList());
		char[] p = PASSWORD.toCharArray();
		int position = 0;
		int start = 0;
		int direction = 0;
		letter = 0;

		for (int i = 0; i <= PASSWORD.length() - 1; i++) {
			char current = p[letter];
			String currentS = Character.toString(p[letter]);
			System.out.println("Doing letter: " + current);
			
			// Check where the first letter has to start
			if (new String(leftcol).contains(currentS)) {
				position = 0;
				start = inArray(leftcol, current);
				direction = 0;
			} else if (new String(bottomrow).contains(currentS)) {
				position = inArray(bottomrow, current);
				start = 12;
				direction = 1;
			} else if (new String(toprow).contains(currentS)) {
				position = inArray(toprow, current);
				start = 0;
				direction = 2;
			} else if (new String(rightcol).contains(currentS)) {
				position = 12;
				start = inArray(rightcol, current);
				direction = 3;
			} else {
				System.out.println("Letter not in matrix");
			}
			walk(direction, position, start);
			System.out.println(toString(spaces, mirrors.getMirrorList()));
		}
		System.out.println(decrypted.toString());
	}
	
	
	public int walk(int direction, int position, int left) {
		System.out.println(direction + " " + position + " " + left);
		switch (direction) {
			case 0: // walk right
				System.out.println("right");
				for (int i = position; i <= 12; i++) {
					ArrayList<ArrayList<Mirror>> next = spaces.getAdjecentLines(left, i);
					System.out.println("right:" + next.get(0) + " down:" + next.get(1) + " left:" + next.get(2) + " up:" + next.get(3) +  " self:" + next.get(4) + " Checking x: " + i + " y: " + left);
					if (!next.get(0).isEmpty()) {
						if (next.get(0).get(0).toString() == "/") { 
							System.out.println("/");
							return walk(1, i + 1, left - 1);
						} else if (next.get(0).get(0).toString() == "\\") { 
							System.out.println("\\");
							return walk(2, i + 1, left + 1);
						} else {
							System.out.println("Something went wrong");
						}
					}
				}
				decrypted.append(Character.toString(rightcol[left]));
				System.out.println(decrypted.toString());
				letter++;
				return 0;
			case 1: // walk up
				System.out.println("up");
				for (int i = left; i >= 0; i--) {
					ArrayList<ArrayList<Mirror>> next = spaces.getAdjecentLines(i, position);
					System.out.println("right:" + next.get(0) + " down:" + next.get(1) + " left:" + next.get(2) + " up:" + next.get(3) +  " self:" + next.get(4) + " Checking x: " + position + " y: " + i);
					if (!next.get(4).isEmpty() && left == 12) {
						System.out.println("I am: " + next.get(4).get(0).toString());
						if (next.get(4).get(0).toString() == "/") {
							System.out.println("/");
							return walk(0, position, i);
						}
					}
					if (!next.get(3).isEmpty()) {
						if (next.get(3).get(0).toString() == "/") {
							System.out.println("/");
							return walk(0, position, i - 1);
						} else if (next.get(3).get(0).toString() == "\\") {
							System.out.println("\\");
							return walk(3, position - 1, i - 1);
						} else {
							System.out.println("Something went wrong");
						}
					}
				}
				decrypted.append(Character.toString(toprow[position]));
				System.out.println(decrypted.toString());
				letter++;
				return 0;
			case 2: // walk down
				System.out.println("down");
				for (int i = left; i <= 12; i++) {
					ArrayList<ArrayList<Mirror>> next = spaces.getAdjecentLines(i, position);
					System.out.println("right:" + next.get(0) + " down:" + next.get(1) + " left:" + next.get(2) + " up:" + next.get(3) +  " self:" + next.get(4) + " Checking x: " + position + " y: " + i);
					if (!next.get(4).isEmpty() && left == 0) {
						System.out.println("I am: " + next.get(4).get(0).toString());
						if (next.get(4).get(0).toString() == "/") {
							System.out.println("/");
							return walk(3, position, i);
						} else if (next.get(4).get(0).toString() == "\\") {
							System.out.println("\\");
							return walk(0, position, i);
						}
					}
					if (!next.get(1).isEmpty()) {
						if (next.get(1).get(0).toString() == "/") {
							System.out.println("/");
							return walk(3, position, i + 1);
						} else if (next.get(1).get(0).toString() == "\\") {
							System.out.println("\\");
							return walk(0, position, i + 1);
						} else {
							System.out.println("Something went wrong");
						}
					} 
				}
				decrypted.append(Character.toString(bottomrow[position]));
				System.out.println(decrypted.toString());
				letter++;
				return 0;
			case 3: // walk left
				System.out.println("left");
				for (int i = position; i >= 0; i--) {
					ArrayList<ArrayList<Mirror>> next = spaces.getAdjecentLines(left, i);
					System.out.println("right:" + next.get(0) + " down:" + next.get(1) + " left:" + next.get(2) + " up:" + next.get(3) + " self: " + next.get(4) + " Checking x: " + i + " y: " + left);
					if (!next.get(4).isEmpty() && i == 12) {
						System.out.println("I am: " + next.get(4).get(0).toString());
						if (next.get(4).get(0).toString() == "/") {
							System.out.println("/");
							return walk(2, position, i);
						} else if (next.get(4).get(0).toString() == "\\") {
							System.out.println("\\");
							return walk(1, position, left);
						}
					}
					if (!next.get(2).isEmpty()) {
						if (next.get(2).get(0).toString() == "/") {
							System.out.println("/");
							return walk(2, i - 1, left);
						} else if (next.get(2).get(0).toString() == "\\") {
							System.out.println("\\");
							return walk(1, i - 1, left);
						} else {
							System.out.println("Something went wrong");
						}
					}
				}
				decrypted.append(Character.toString(leftcol[left]));
				System.out.println(decrypted.toString());
				letter++;
				return 0;
		}
		return 0;
	}
	
	// Method that returns the number in which place the character is in an array
	public static int inArray(char[] array, char check) {
		int i = 0;
		for (char l : array) {
			if (l == check) { 
				break;
			} else if (l != check) {
				i++;
			}
		}
		return i;
	}
	
	public static void main(String[] args) {
		MirrorEncryption m1 = new MirrorEncryption();;

		System.out.println("");

		System.out.println("Password: " + PASSWORD);
		System.out.println("");
		System.out.println("Decrytped: " + m1.decrypted.toString());
	}
}
