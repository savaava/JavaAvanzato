package org.example;

import annotation.AtMostThree;
import annotation.FieldNumberConstraint;

@AtMostThree
@FieldNumberConstraint(2)
public class LongClass {
    public int x;

    public void m1(){}
    public void m2(){}
    public void m3(){}
    public void m4(){}
}
