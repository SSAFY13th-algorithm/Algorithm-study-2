import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boj6209 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static int[] island;
    private static int d, n, m;

    public static void main(String[] args) throws IOException {
        init();

        int answer = binarySearch();
        bw.write(answer + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        d = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        island = new int[n];

        for (int i = 0; i < n; i++) {
            island[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(island);
    }

    private static int binarySearch() {
        int left = 1;
        int right = d;
        int result = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (valid(mid)) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    private static boolean valid(int target) {
        int count = n - m;
        int lastPos = 0;

        for (int i = 0; i < n; i++) {
            if (island[i] - lastPos >= target && count > 0) {
                count--;
                lastPos = island[i];
            }
        }

        return d - lastPos >= 0 && count == 0;
    }
}