package is.hi.hugbo.model;

import java.util.ArrayList;
import java.util.Collections;

public class Holes {
  private ArrayList<Integer> holeList = new ArrayList<Integer>(Collections.nCopies(18, 0));

  public Holes() {
  }

  public void setSize(int size) {
    this.holeList = new ArrayList<Integer>(Collections.nCopies(size, 0));
  }

  public int[] getHoles() {
    int[] holes;
    if (this.holeList.get(9) == 0) {
      holes = new int[9];
      holes = this.holeList.subList(0, 9).stream().mapToInt(i -> i).toArray();
    } else {
      holes = new int[18];
      holes = this.holeList.stream().mapToInt(i -> i).toArray();
    }

    return holes;
  }

  public ArrayList<Integer> getHoleList() {
    return this.holeList;
  }
}
