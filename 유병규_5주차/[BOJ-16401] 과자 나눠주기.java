import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());

        int[] snacks = new int[n];
        for(int i=0; i<n; i++) {
            snacks[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(snacks);

        int result = 0;

        int left = 1;
        int right = snacks[n-1];

        while(left <= right) {
            int mid = (left+right)/2;

            int count = 0;
            for(int i=n-1; i>=0; i--) {
                count += snacks[i]/mid;
                if(count >= m) {
                    result = mid;
                    break;
                }
            }

            if(count < m) right = mid-1;
            else left = mid+1;
        }

        System.out.println(result);
    }
}
