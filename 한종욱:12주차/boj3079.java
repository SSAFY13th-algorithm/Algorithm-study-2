import java.io.*;
import java.util.StringTokenizer;

public class boj3079 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static int[] desk;
    private static int n, m;

    public static void main(String[] args) throws IOException {
        init();
        long answer = binarySearch();

        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
        br.close();
    }

    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        desk = new int[n];

        for (int i = 0; i < n; i++) {
            desk[i] = Integer.parseInt(br.readLine());
        }
    }

    private static long binarySearch() {
        long result = 0;
        long left = 1;
        long right = Long.MAX_VALUE;

        while (left <= right) {
            long mid = left + (right - left) / 2;

            if (valid(mid)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    private static boolean valid(long target) {
        long cnt = 0;

        for (int d : desk) {
            cnt += target / d;
            if (cnt >= m) return true;
        }

        return false;
    }
}