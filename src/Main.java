import datastructures.queue.ArrayQueue;
import datastructures.stack.ArrayStack;

public class Main {

    public static void main(String[] args) {
        demonstrateQueue();
        System.out.println();
        demonstrateStack();
    }

    private static void demonstrateQueue() {
        System.out.println("=== Queue Demonstration ===");

        ArrayQueue<String> missionTasks = new ArrayQueue<>();

        missionTasks.enqueue("Validate telemetry");
        missionTasks.enqueue("Analyze sensor data");
        missionTasks.enqueue("Generate mission report");

        System.out.println("Next task: " + missionTasks.peek());

        while (!missionTasks.isEmpty()) {
            System.out.println("Completed: " + missionTasks.dequeue());
        }
    }

    private static void demonstrateStack() {
        System.out.println("=== Stack Demonstration ===");

        ArrayStack<String> navigationHistory = new ArrayStack<>();

        navigationHistory.push("Mission Dashboard");
        navigationHistory.push("Telemetry Report");
        navigationHistory.push("Navigation Analysis");

        System.out.println("Current page: " + navigationHistory.peek());
        System.out.println("Returning from: " + navigationHistory.pop());
        System.out.println("Previous page: " + navigationHistory.peek());
        System.out.println("Pages remaining: " + navigationHistory.size());
    }
}