package net.demomaker.blockcounter.algorithm;

public class CountingBoundary {

  int lowest;
  int highest;

  public CountingBoundary(int lowest, int highest) {
    this.lowest = lowest;
    this.highest = highest;
    this.swapIfIncorrectOrder();
  }

  public void swapIfIncorrectOrder() {
    if (this.lowest > this.highest) {
      int temp = this.lowest;
      this.lowest = this.highest;
      this.highest = temp;
    }
  }
}
