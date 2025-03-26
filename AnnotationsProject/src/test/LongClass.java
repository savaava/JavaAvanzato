package test;

import annotation.FieldNumberConstraint;
import annotation.AtMostThree;

@AtMostThree
@FieldNumberConstraint(1)
public class LongClass {
    private int x;

    public void m1(){}
    public void m2(){}
    public void m3(){}
}
