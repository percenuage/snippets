package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    private static final int MAX = 100;
    private static final int FACTOR = 2;
    private static final int SECRET = 42;
    private static final int LOOP = 100000;

    public static void main(String[] args) {
        int[][] count = new int[2][LOOP];
        for (int i = 0; i < LOOP; i++) {
            count[0][i] = start(false);
            count[1][i] = start(true);
            System.out.println(i);
        }
        double[] computes = compute(count[0]);
        System.out.println("Average by Dichotomy: " + computes[0]);
        System.out.println("Standard Deviation by Dichotomy: " + computes[1]);

        computes = compute(count[1]);
        System.out.println("Average by Random: " + computes[0]);
        System.out.println("Standard Deviation by Random: " + computes[1]);
    }

    private static double[] compute(int... numbers) {
        double mean = IntStream.of(numbers).average().getAsDouble();
        double deviation = Math.sqrt(IntStream.of(numbers).asDoubleStream()
                .reduce(0, (sum, num) -> sum + Math.pow(num - mean, 2)) / numbers.length);
        return new double[] {mean, deviation};
    }

    public static int start(boolean random) {
        return random ? startByRand() : startByDichotomy();
    }

    private static int startByDichotomy() {
        Integer max = MAX;
        Integer number = -1;
        int count = 0;
        Random rand = new Random();
        while (!number.equals(SECRET)) {
            if (number >= 0) {
                int compare = Integer.valueOf(SECRET).compareTo(number);
                if (compare > 0) {
                    number = (max - number) / FACTOR + number;
                } else {
                    max = number;
                    number /= FACTOR;
                }
            } else {
                number = rand.nextInt(MAX + 1);
            }
            count++;
            //System.out.println("Tentative #" + count + " • " + number);
        }
        //System.out.println("Found!");
        return count;
    }

    private static int startByRand() {
        Integer min = 0;
        Integer max = MAX;
        Integer number = -1;
        int count = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        Random rand = new Random();
        while (!number.equals(SECRET)) {
            if (number >= 0) {
                int compare = Integer.valueOf(SECRET).compareTo(number);
                if (compare > 0) {
                    min = number;
                } else {
                    max = number;
                }
            }
            do {
                number = rand.nextInt(max - min + 1) + min;
            } while (numbers.contains(number));
            numbers.add(number);
            count++;
        }
        return count;
    }
}
