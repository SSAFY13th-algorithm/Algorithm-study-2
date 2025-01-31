import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] numbers = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int result = 0;
        boolean isSort = true;
        for(int i=0; i<n-1; i++) {
            if(numbers[i] > numbers[i+1]) {
                if(!isSort) {
                    System.out.println(0);
                    return;
                }
                if(i==0) {
                    result++;
                }
                if(i-1>=0 && i+1<n && numbers[i-1] <= numbers[i+1]) {
                    result++;
                }
                if(i+2>=n || numbers[i] <= numbers[i+2]) {
                    result++;
                }
                isSort = false;
            }
        }
        if(isSort && result == 0) {
            result = n;
        }

        System.out.println(result);
    }

}