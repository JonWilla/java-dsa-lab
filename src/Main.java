import datastructures.queue.ArrayQueue;

public class Main {

    public static void main(String[] args) {
        ArrayQueue<String> missionQueue = new ArrayQueue<>();

        missionQueue.enqueue("Validate telemetry");
        missionQueue.enqueue("Analyze sensor data");
        missionQueue.enqueue("Generate mission report");

        System.out.println("Next task: " + missionQueue.peek());

        while (!missionQueue.isEmpty()) {
            System.out.println("Completed: " + missionQueue.dequeue());
        }
    }
}