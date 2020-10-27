package ds;

import java.io.Serializable;

public class Expenses implements Serializable {
    public int value=0;

    public Expenses(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value += value;
    }
    public int addValue(int val){
        int tempVal= getValue();
        int value = tempVal+val;
        setValue(value);
        return value;
    }
}
