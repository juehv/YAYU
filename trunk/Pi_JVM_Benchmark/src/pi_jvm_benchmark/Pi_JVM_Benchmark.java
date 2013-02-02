/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_jvm_benchmark;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jens
 */
public class Pi_JVM_Benchmark {

    private static final int randomRounds = 1000000;
    private static double[] randoms = new double[randomRounds];
    private static String[] strings = new String[randomRounds];
    private static final int intRounds = 1000000;
    private static final int treadMax = 1000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        long statTime;
        long tmpTime;
        long overallTime = 0;
        // Random Number
        statTime = System.currentTimeMillis();
        for (int i = 0; i < randomRounds; i++) {
            randoms[i] = Math.random();
        }
        tmpTime = System.currentTimeMillis() - statTime;
        overallTime += tmpTime;
        System.out.println("Time for " + randomRounds + " random Number generaton:");
        System.out.println(tmpTime + "ms");

        // Multiplication (fp)
        statTime = System.currentTimeMillis();
        for (int i = 0; i < randomRounds; i += 2) {
            double tmpDouble = randoms[i] * randoms[i + 1];
        }
        tmpTime = System.currentTimeMillis() - statTime;
        overallTime += tmpTime;
        System.out.println("Time for " + randomRounds / 2 + " Multiplications:");
        System.out.println(tmpTime + "ms");

        // Add Integer
        statTime = System.currentTimeMillis();
        int tmpInt = 0;
        for (int i = 0; i < intRounds; i++) {
            tmpInt = tmpInt + i;
        }
        tmpTime = System.currentTimeMillis() - statTime;
        overallTime += tmpTime;
        System.out.println("Time for " + intRounds + " Integer additions:");
        System.out.println(tmpTime + "ms");

        // String concat
        statTime = System.currentTimeMillis();
        for (int i = 0; i < randomRounds; i++) {
            String tmpString = "test" + String.valueOf(randoms[i]);
            strings[i] = tmpString;
        }
        tmpTime = System.currentTimeMillis() - statTime;
        overallTime += tmpTime;
        System.out.println("Time for concat " + randomRounds + " Strings:");
        System.out.println(tmpTime + "ms");

        // IO
        FileWriter writer = new FileWriter("./test.txt");
        statTime = System.currentTimeMillis();
        for (int i = 0; i < randomRounds; i++) {
            writer.append(strings[i]);
        }
        tmpTime = System.currentTimeMillis() - statTime;
        overallTime += tmpTime;
        System.out.println("Time for write " + randomRounds + " Textlines:");
        System.out.println(tmpTime + "ms");

        // Treads
        for (int j = 1; j < treadMax; j *= 10) {
            statTime = System.currentTimeMillis();
            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < j; i++) {
                Thread runner = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < randomRounds; i += 2) {
                            double tmpDouble = randoms[i] * randoms[i + 1];
                        }
                    }
                });
                threads.add(runner);
                runner.start();
            }
            for (Thread runner : threads) {
                try {
                    runner.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Pi_JVM_Benchmark.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            tmpTime = System.currentTimeMillis() - statTime;
            overallTime += tmpTime;
            System.out.println("Time for generate " + j + " Threads with " + randomRounds / 2 + " parallel Multiplications:");
            System.out.println(tmpTime + "ms");
        }
        System.out.println("The whole Benchmark took: " + overallTime / 1000 + "s (lower is better)");
    }
}
