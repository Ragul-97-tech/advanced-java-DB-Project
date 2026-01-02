import org.mindrot.jbcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw(1234+"",BCrypt.gensalt(10)));
        System.out.println();
    }
}