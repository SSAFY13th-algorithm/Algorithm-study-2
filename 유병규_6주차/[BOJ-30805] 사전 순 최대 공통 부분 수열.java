import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());

        Queue<Integer> number1 = new LinkedList<>();
        st = new StringTokenizer(br.readLine());
        int max = 0;
        for(int i=0; i<n; i++) {
            int num = Integer.parseInt(st.nextToken());
            number1.offer(num);
            max = Math.max(max, num);
        }

        int m = Integer.parseInt(br.readLine());
        Queue<Integer> number2 = new LinkedList<>();
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<m; i++) {
            number2.offer(Integer.parseInt(st.nextToken()));
        }

        int result = 0;
        StringBuilder sb = new StringBuilder();
        for(int i=max; i>0; i--) {
            while(number1.contains(i) && number2.contains(i)) {
                sb.append(i).append(" ");
                result++;

                while(number1.peek() != i) {
                    number1.poll();
                }
                while(number2.peek() != i) {
                    number2.poll();
                }
                if(number1.isEmpty() || number2.isEmpty()) break;
                number1.poll();
                number2.poll();
            }

        }

        System.out.println(result);
        if(result != 0) System.out.println(sb.toString());
    }
}
