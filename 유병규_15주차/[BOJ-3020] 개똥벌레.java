import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        //입력 값 저장
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());

        int[] number = new int[n];
        for(int i=0; i<n; i++) {
            number[i] = Integer.parseInt(br.readLine());
        }

        //석순의 변화량을 체크
        int[] count = new int[h];
        for(int i=0; i<n; i+=2) {
            count[0]++;
            count[number[i]]--;
        }

        //종유석의 변화량을 체크
        for(int i=1; i<n; i+=2) {
            count[h-number[i]]++;
        }

        //누적합 배열 세팅
        int[] sum = new int[h];
        sum[0] = count[0];
        for(int i=1; i<h; i++) {
            sum[i] = sum[i-1]+count[i];
        }

        //가장 작은 값과 그 개수 구하기
        Arrays.sort(sum);
        int result = 1;
        for(int i=1; i<h; i++) {
            if(sum[0] != sum[i]) break;
            result++;
        }

        System.out.println(sum[0]+" "+result);
    }
}
