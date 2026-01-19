import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int TIME_LIMIT = 15;
    private static final int BAR_LENGTH = 20;
    private static final int CORRECT_OPTION = 4;

    public static void main(String[] args) throws InterruptedException {

        List<String> options = List.of(
                "var a = 10;",
                "let variable;",
                "variable a = 10;",
                "int i = 10"
        );

        AtomicBoolean finished = new AtomicBoolean(false);
        AtomicInteger chosenOption = new AtomicInteger(-1);

        printQuestion(options);

        Thread timerThread = new Thread(() -> runProgressBarTimer(finished));
        Thread inputThread = new Thread(() -> readInput(finished, chosenOption));

        timerThread.start();
        inputThread.start();

        // Ensure program finishes cleanly
        timerThread.join();
        inputThread.join();

        System.out.println("\n\n✅ Program finished.");
    }

    private static void printQuestion(List<String> options) {
        System.out.println("=====================================");
        System.out.println("          Java Quiz");
        System.out.println("=====================================");
        System.out.println("Q: What is the correct way to declare a variable in Java?\n");

        for (int i = 0; i < options.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, options.get(i));
        }

        System.out.println("\nEnter 0 to exit");
        System.out.println("-------------------------------------");
    }

    private static void runProgressBarTimer(AtomicBoolean finished) {
        for (int timeLeft = TIME_LIMIT; timeLeft >= 0; timeLeft--) {

            if (finished.get()) return;

            int filled = (int) (((TIME_LIMIT - timeLeft) / (double) TIME_LIMIT) * BAR_LENGTH);
            String bar = "█".repeat(filled) + "░".repeat(BAR_LENGTH - filled);

            System.out.print("\r⏳ [" + bar + "] " + timeLeft + "s ");
            System.out.flush();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
        }

        finished.set(true);
        System.out.println("\n\n⏰ Time's up!");
    }

    private static void readInput(AtomicBoolean finished, AtomicInteger chosenOption) {
        Scanner scanner = new Scanner(System.in);

        // Ensure prompt is visible
        System.out.println();
        System.out.print("👉 Enter your option (1-4): ");
        System.out.flush();

        if (!scanner.hasNextInt()) {
            finished.set(true);
            System.out.println("\n❌ Invalid input.");
            return;
        }

        int input = scanner.nextInt();
        finished.set(true);
        chosenOption.set(input);

        System.out.println("\n-------------------------------------");

        if (input == 0) {
            System.out.println("👋 Exiting quiz.");
            return;
        }

        if (input == CORRECT_OPTION) {
            System.out.println("✅ Correct! Well done.");
        } else {
            System.out.println("❌ Wrong answer.");
            System.out.println("✔ Correct answer: 4) int i = 10");
        }
    }
}
