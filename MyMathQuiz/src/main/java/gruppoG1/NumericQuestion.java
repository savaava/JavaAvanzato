package gruppoG1;

import java.util.Random;

public class NumericQuestion {
    private int result;
    private int num1;
    private int num2;
    private String operatore;

    public NumericQuestion() {
            Random r = new Random();
            if(r.nextInt(2) == 0)
                operatore = "+";
            else
                operatore = "-";

            num1 = r.nextInt(99) + 1;
            num2 = r.nextInt(99) + 1;
    }

    public int getNum1() {
        return num1;
    }

    public int getNum2() {
        return num2;
    }

    public String getOperatore() {
        return operatore;
    }

    public int getResult(){
        return result = operatore.equals("+") ? num1 + num2 : num1 - num2;
    }

    @Override
    public String toString() {
        return num1 + operatore + num2 + " = ";
    }

}
