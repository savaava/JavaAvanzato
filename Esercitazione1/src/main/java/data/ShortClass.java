package data;

import myannotations.AtMostThree;
import myannotations.FieldNumberConstraint;

@AtMostThree
@FieldNumberConstraint(1)
public class ShortClass {
    private String a1;

    public String getA1(){
        return a1;
    }

    private void m(){

    }
}
