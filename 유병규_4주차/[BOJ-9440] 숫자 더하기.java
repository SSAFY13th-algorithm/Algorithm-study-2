import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringBuilder sb = new StringBuilder();
        while(true) {
            //0 입력 시 종료
            StringTokenizer st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());
            if(count == 0) break;

            //numbers 세팅 및 0 개수 세기
            int zeroCount = 0;
            int[] numbers = new int[count];
            for(int i=0; i<count; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
                if(numbers[i] == 0) zeroCount++;
            }

            //숫자열 오름차순 정렬
            Arrays.sort(numbers);

            //만들어질 수 두 개
            StringBuilder num1 = new StringBuilder();
            StringBuilder num2 = new StringBuilder();

            //만약 주어진 숫자열의 길이가 홀수이면서 0의 개수가 홀수인 경우, 0을 하나 제외
            int start=0;
            if(numbers.length%2!=0 && zeroCount%2!=0) {
                start=1;
            }

            //num1과 num2에 각 숫자 배분
            for(int i=start; i<numbers.length; i++) {
                if(i%2==0)num1.append(numbers[i]);
                else num2.append(numbers[i]);
            }

            //만약 0을 하나 제외했던 경우라면 만들어진 두 수(num1, num2) 중 더 작은 수 앞에 제거했던 0 추가
            if(start == 1) {
                if(Integer.parseInt(num1.toString()) > Integer.parseInt(num2.toString()))
                    num2.insert(0, '0');
                else num1.insert(0, '0');
            }

            //숫자가 0부터 시작한다면 0 다음으로 작은 숫자와 위치 변경하기
            if(num1.charAt(0) == '0') swap(num1);
            if(num2.charAt(0) == '0') swap(num2);

            //두 수의 합 계산
            int result = Integer.parseInt(num1.toString()) + Integer.parseInt(num2.toString());
            sb.append(result).append("\n");
        }

        System.out.println(sb.toString().trim());
    }

    private static void swap(StringBuilder num) {
        int index = 1;
        while(num.charAt(index) == '0') {
            index++;
        }
        num.setCharAt(0, num.charAt(index));
        num.setCharAt(index, '0');
    }
}
