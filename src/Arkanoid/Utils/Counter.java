package Arkanoid.Utils;

/**
 * Arkanoid.Utils.Counter class.
 */
public class Counter {
    private int counter;
    /**
     * add number to current count.
     * @param number num to increase counter
     */
    public void increase(int number) {
        counter += number;
    }
    /**
     * subtract number from current count.
     * @param number num to decrease counter
     */
    public void decrease(int number) {
        counter -= number;
    }
    /**
     * get current count.
     * @return counter
     */
    public int getValue() {
        return counter;
    }
}
