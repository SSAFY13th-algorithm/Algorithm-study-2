import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Boj17074 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final int MIN_VALUE = -1000000005;
    private static final int MAX_VALUE = 1000000005;
    private static int[] sequence;
    private static int index = 0;
    private static int breakCount = 0;
    private static int n;
    public static void main(String[] args) throws IOException {
        n = Integer.parseInt(br.readLine());
        int count = 0;
        String[] input = br.readLine().split(" ");

        sequence = new int[n + 2];
        sequence[0] = MIN_VALUE;
        sequence[sequence.length - 1] = MAX_VALUE;

        for (int i = 1; i <= n; i++) {
        	sequence[i] = Integer.parseInt(input[i - 1]);
        }
        
        analyzeSequence();
        if (breakCount == 0) {
        	count = n;
        }
        
        if (breakCount == 1) {
        	if (sequence[index - 1] <= sequence[index + 1]) count++;
        	if (sequence[index] <= sequence[index + 2]) count++;
        }

        bw.write(count + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    private static void analyzeSequence() {
        for (int i = 0; i < sequence.length - 1; i++) {
        	if (sequence[i] > sequence[i + 1]) {
        		index = i;
        		breakCount++;
        	}
        }
    }
}