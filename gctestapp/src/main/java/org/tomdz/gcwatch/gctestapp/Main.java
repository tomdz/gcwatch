package org.tomdz.gcwatch.gctestapp;

import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        final long sleepTime = (args.length > 0 ? Integer.parseInt(args[0]) : 10);
        final long maxMem = Runtime.getRuntime().maxMemory();
        final int minArrLength = 10*1024*1024;
        final int maxArrLength = (maxMem < 200*1024*1024 ? minArrLength + 1 : (int)(maxMem/20));

        final int numWorkers = 10;
        final ExecutorService executor = Executors.newFixedThreadPool(numWorkers);

        for (int idx = 0; idx < numWorkers; idx++) {
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    final Random random = new Random(System.currentTimeMillis());
                    final WeakHashMap<Integer, byte[]> container = new WeakHashMap<Integer, byte[]>();

                    while (!Thread.interrupted()) {
                        int arrLength = minArrLength + random.nextInt(maxArrLength - minArrLength);

                        container.put(arrLength, new byte[arrLength]);
                        if (sleepTime > 0) {
                            try {
                                Thread.sleep(sleepTime);
                            }
                            catch (InterruptedException ex) {
                                return;
                            }
                        }
                    }
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }
    }
}
