import org.apache.logging.log4j.core.util.JsonUtils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlusOneArray {
    public static int[] upArray(final int[] arr) {
        if (arr == null || arr.length == 0 || Arrays.stream(arr).anyMatch(i -> i < 0 || i > 9))
            return null;
        String number = Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining());
        BigInteger incremented = new BigInteger(number).add(BigInteger.ONE);
        return incremented.toString().chars().map(i -> i - '0').toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(upArray(null)));
    }
}