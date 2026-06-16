import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

class InvalidAgeException extends Exception {
    public InvalidAgeException(String msg) {
        super(msg);
    }
}

public class Main {

    public static void main(String[] args) throws InvalidAgeException {
        System.out.println(List.of("hi", "hello"));
    }

    /*public static void singleExecutor() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future = service.submit(() -> {
            Scanner inputs = new Scanner(System.in);
            return inputs.nextLine();
        });

        try {
            String answer = future.get(10, TimeUnit.SECONDS);
            System.out.println("Your answer: " + answer);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            System.out.println("Time's up");
            future.cancel(true);
        }
        service.shutdown();
    }*/
}
