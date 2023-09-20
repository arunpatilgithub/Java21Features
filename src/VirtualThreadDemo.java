/**
 * This class demonstrates the benefits of using virtual threads in Java.
 * It compares the time taken to complete 10,000 IO-bound tasks using traditional threads
 * versus using virtual threads. Each thread simulates an IO-bound operation by "sleeping" for 200 milliseconds.
 */
public class VirtualThreadDemo {

    public static void main(String[] args) throws InterruptedException {

        int numTasks = 10000; // Number of IO-bound tasks
        long sleepTime = 200; // Time in milliseconds to simulate IO-bound work

        // Using Traditional Threads
        long start = System.currentTimeMillis(); // Capture the start time
        Thread[] traditionalThreads = new Thread[numTasks];

        /**
         * Create and start 10,000 traditional threads to perform IO-bound tasks.
         */
        for (int i = 0; i < numTasks; i++) {
            traditionalThreads[i] = new Thread(() -> {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            traditionalThreads[i].start();
        }

        /**
         * Wait for all traditional threads to complete.
         */
        for (Thread t : traditionalThreads) {
            t.join();
        }

        long end = System.currentTimeMillis(); // Capture the end time
        System.out.println("Time taken using traditional threads: " + (end - start) + " milliseconds");

        // Using Virtual Threads
        start = System.currentTimeMillis(); // Reset the start time

        Thread[] virtualThreads = new Thread[numTasks];

        /**
         * Create and start 10,000 virtual threads to perform IO-bound tasks.
         */
        for (int i = 0; i < numTasks; i++) {
            virtualThreads[i] = Thread.ofVirtual().unstarted(() -> {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            virtualThreads[i].start();
        }

        /**
         * Wait for all virtual threads to complete.
         */
        for (Thread t : virtualThreads) {
            t.join();
        }

        end = System.currentTimeMillis(); // Capture the end time
        System.out.println("Time taken using virtual threads: " + (end - start) + " milliseconds");
    }
}