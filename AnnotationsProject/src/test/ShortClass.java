package test;

import annotation.FieldNumberConstraint;
import annotation.AtMostThree;

@AtMostThree
@FieldNumberConstraint(1)
public class ShortClass {
    private int x,y,z;

    public void m1(){}
    public void m2(){}
}
