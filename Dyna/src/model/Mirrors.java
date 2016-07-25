package model;

import java.util.ArrayList;

public class Mirrors {
	private static ArrayList<Mirror> mirrorList = new ArrayList<Mirror>();
	
	public static StringBuilder mirrorsString = new StringBuilder();
	
	public Mirrors() {
		fillString();
		fillMirrors();
	}
	
	public StringBuilder fillString() {
		mirrorsString.append(
						"   LL  RL    " +
						"            L" +
						"   R         " +
						"      L     L" +
						"    L        " +
						"  R      R   " +
						"L  R      L  " +
						"     L       " +
						"LR           " +
						"R            " +
						"          L  " +
						"    LR       " +
						"   R       R "
		);
		return mirrorsString;
	}
	
	public void fillMirrors() {
		for (int i = 0; i < mirrorsString.length(); i++) {
			if (mirrorsString.charAt(i) == 'L') {
				int row = i % 13;
				int col = i / 13;
				mirrorList.add(new Mirror(row, col, "\\"));
			} else if (mirrorsString.charAt(i) == 'R') {
				int row = i % 13;
				int col = i / 13;
				mirrorList.add(new Mirror(row, col, "/"));
			}
		}
	}
	
	public ArrayList<Mirror> getMirrorList() {
		return mirrorList;
	}
	
	public static void main(String[] args) {
		Mirrors m = new Mirrors();
		System.out.println(m.fillString());
		System.out.println("mirrorList size: " + mirrorList.size());
		for (int i = 0; i < mirrorList.size(); i++) {
			System.out.println("Mirror: " + mirrorList.get(i) + " on col " + mirrorList.get(i).col + " and row: " + mirrorList.get(i).row);
		}
		

	}
}
