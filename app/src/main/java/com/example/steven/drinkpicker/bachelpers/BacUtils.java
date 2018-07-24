package com.example.steven.drinkpicker.bachelpers;

public class BacUtils {

    public static final double R_FACTOR_MEN = 0.68;
    public static final double R_FACTOR_WOMEN = 0.55;

    public static final double BETA = 0.17;

    public void calculateBac(){

        double bac = (n * V * p * 8) / (r * m / (1.055)) - t * BETA;
    }
}
