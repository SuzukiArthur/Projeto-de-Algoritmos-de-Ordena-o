import java.util.Random;

public final class DataGenerator {
    private DataGenerator() {
    }

    public static int[] random(int n, long seed) {
        Random random = new Random(seed);
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(1_000_000);
        }
        return array;
    }

    public static int[] sorted(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i;
        }
        return array;
    }

    public static int[] reversed(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = n - i - 1;
        }
        return array;
    }
}
