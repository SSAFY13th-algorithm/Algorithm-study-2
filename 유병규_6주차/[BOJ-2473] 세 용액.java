import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] numbers = new int[n];
        for(int i=0; i<n; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(numbers);

        long min = Long.MAX_VALUE;
        long[] result = new long[3];
        for(int i=0; i<n-2; i++) {
            long first = numbers[i];

            int left = i+1;
            int right = n-1;
            while(left < right) {
                long second = numbers[left];
                long third = numbers[right];

                long sum = first+second+third;

                if(sum == 0) {
                    System.out.println(first+" "+second+" "+third);
                    return;
                }

                if(Math.abs(min-0) > Math.abs(sum-0)) {
                    min = sum;
                    result[0] = first;
                    result[1] = second;
                    result[2] = third;
                }

                if(sum > 0) {
                    right--;
                    continue;
                }
                left++;
                continue;
            }
        }

        for(int i=0; i<3; i++) {
            System.out.print(result[i]+" ");
        }
    }
}
