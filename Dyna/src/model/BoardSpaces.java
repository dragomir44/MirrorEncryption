package model;


import java.util.*;

public class BoardSpaces {
	
	private Map<Integer, Map<Integer, Mirror>> MirrorMap;

	public BoardSpaces() {
		MirrorMap = new HashMap<Integer, Map<Integer, Mirror>>();
	}
	
	public BoardSpaces(BoardSpaces toCopy) {
		MirrorMap = new HashMap<Integer, Map<Integer, Mirror>>();
		Set<Integer> key1Set = toCopy.getMap().keySet();
		Map<Integer, Mirror> map;
		for (int key1 : key1Set) {
			map = new HashMap<Integer, Mirror>(toCopy.getMap().get(key1));
			MirrorMap.put(key1, map);
		}	
	}
	
	public Map<Integer, Map<Integer, Mirror>> getMap() {
		return MirrorMap;
	}

	public Mirror put(Integer key1, Integer key2, Mirror mirror) {
	    Map<Integer, Mirror> map;
	    if (MirrorMap.containsKey(key1)) {
	        map = MirrorMap.get(key1);
	    } else {
	        map = new HashMap<Integer, Mirror>();
	        MirrorMap.put(key1, map);
	    }	
	    return map.put(key2, mirror);
	}
	
	public Mirror get(Integer key1, Integer key2) {
	    if (MirrorMap.containsKey(key1)) {
	        return MirrorMap.get(key1).get(key2);
	    } else {
	        return null;
	    }
	}
	
	public boolean containsKeys(Integer key1, Integer key2) {
	    return MirrorMap.containsKey(key1) && MirrorMap.get(key1).containsKey(key2);
	}
	
	/** Returns Mirror array of columns adjecent to the row and col.
	 * @param row
	 * @param col
	 * @return array of Mirrors [Left, Bottom, Right, Top]
	 */
	public ArrayList<ArrayList<Mirror>>  getAdjecentLines(int row, int col) {
		ArrayList<ArrayList<Mirror>> returnMirrors = new ArrayList<ArrayList<Mirror>>(5);
		for (int i = 0; i < 5; i++) {
			ArrayList<Mirror> MirrorRow = new ArrayList<Mirror>();
			int rowCount = row;
			int colCount = col;
			boolean walking = true;
			while (walking) { // walk in direction until no next Mirror
				switch (i) {
					case 0: // walk right
						colCount++;
						break;
					case 1: // walk down
						rowCount++;
						break;
					case 2: // walk left
						colCount--;
						break;
					case 3: // walk up
						rowCount--;
						break;
					case 4: // Check field (row,col)
						break;
					
				}
				Mirror nextMirror = this.get(rowCount, colCount);
				if (nextMirror == null) { // if no next Mirror walk next direction
					walking = false;
					switch (i) { // make sure it returns lines in same order
						case 2: // walk left
							Collections.reverse(MirrorRow);
							break;
						case 1: // walk down
							Collections.reverse(MirrorRow);
							break;
					}
				} else { // else store the Mirror
					MirrorRow.add(nextMirror);
					walking = false;
				}
			}
			returnMirrors.add(i, MirrorRow);
			
		}
		return returnMirrors;
	}	
	
	public int[] getBoardBounds() {
		// set minimum board size values
		int minRow = 0;
		int maxRow = 13;
		int minCol = 0;
		int maxCol = 13;

		return new int[] {minRow, maxRow, minCol, maxCol};
	}

}
