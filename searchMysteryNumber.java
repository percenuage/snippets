package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    private static final int MIN = 1;
    private static final int MAX = 100;
    private static final int FACTOR = 2;
    private static final int SECRET = 42;
    private static final int LOOP = 1000;

    public static void main(String[] args) {
        int[][] results = loop(false);

        double[] computes = compute(results[0]);
        System.out.println("Average by Dichotomy: " + computes[0]);
        System.out.println("Standard Deviation by Dichotomy: " + computes[1]);

        computes = compute(results[1]);
        System.out.println("Average by Random: " + computes[0]);
        System.out.println("Standard Deviation by Random: " + computes[1]);
    }

    private static int[][] loop(boolean fixedSecret) {
        int min = fixedSecret ? SECRET : MIN;
        int max = fixedSecret ? SECRET: MAX - MIN + 1;
        int[][] results = fixedSecret ? new int[2][LOOP] : new int[2][LOOP * max];
        int index = 0;
        for (int secret = min; secret <= max; secret++) {
            for (int i = 0; i < LOOP; i++) {
                results[0][index] = searchByDichotomy(secret);
                results[1][index] = searchByRandom(secret);
                index++;
//                System.out.println(i + " • " + secret);
            }
        }
        return results;
    }

    private static double[] compute(int... numbers) {
        double mean = IntStream.of(numbers).average().getAsDouble();
        double deviation = Math.sqrt(IntStream.of(numbers).asDoubleStream()
                .reduce(0, (sum, num) -> sum + Math.pow(num - mean, 2)) / numbers.length);
        return new double[] {mean, deviation};
    }

    private static int searchByDichotomy(Integer secret) {
        Integer min = MIN;
        Integer max = MAX;
        Integer number = -1;
        int count = 0;
        while (!number.equals(secret)) {
            if (number >= 0) {
                int compare = secret.compareTo(number);
                if (compare > 0) {
                    number = Math.max((max - number) / FACTOR, 1) + number;
                } else {
                    max = number;
                    number /= FACTOR;
                }
            } else {
                number = (max - min) / FACTOR + min; // rand.nextInt(MAX + 1)
            }
            count++;
//            System.out.println("Tentative #" + count + " • " + number);
        }
//        System.out.println("Found!");
        return count;
    }

    private static int searchByRandom(Integer secret) {
        Integer min = MIN;
        Integer max = MAX;
        Integer number = -1;
        Random rand = new Random();
        ArrayList<Integer> numbers = new ArrayList<>();
        int count = 0;
        while (!number.equals(secret)) {
            if (number >= 0) {
                int compare = secret.compareTo(number);
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
