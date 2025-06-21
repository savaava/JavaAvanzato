package com.prove;

//import com.annotations.MyAnnotation;
import java.time.Month;

public class Prova {
    private int val = 0;

    //@MyAnnotation(value=55, myMonths={Month.JANUARY,Month.FEBRUARY})
    public void set(int val){
        this.val = val;
    }

    @Override
    public String toString(){
        return "Classe prova";
    }
}
