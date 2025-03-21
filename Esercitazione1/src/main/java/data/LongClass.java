package data;

import myannotations.AtMostThree;
import myannotations.FieldNumberConstraint;

@AtMostThree
@FieldNumberConstraint(3)
public class LongClass {
    public String a1;
    public String a2;

    public void m1(){}
    public void m2(){}
    public void m3(){}
    public void m4(){}
}
