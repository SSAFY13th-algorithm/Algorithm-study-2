import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        int[] calendar = new int[366];
        int n = Integer.parseInt(br.readLine());
        for (int repeat = 0; repeat < n; repeat++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            for (int i = start; i <= end; i++) {
                calendar[i]++;
            }
        }

        int total = 0;
        int width = 0, height = 0;
        for (int i = 1; i <= 365; i++) {
            if (calendar[i-1] == 0 && calendar[i] != 0) {
                width = 1;
                height = calendar[i];
                continue;
            }
            if (calendar[i - 1] != 0 && calendar[i] != 0) {
                width++;
                height = Math.max(height, calendar[i]);
                continue;
            }
            if (calendar[i-1] != 0 && calendar[i] == 0) {
                total += width*height;
                width = 0;
                height = 0;
                continue;
            }
        }
        total += width*height;
        System.out.println(total);
    }
}
