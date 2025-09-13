import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        //단어 저장
        List<Integer> words = new ArrayList<>();
        for(int i=0; i<n; i++) {
            String input = br.readLine();
            int word = 0;
            for(char c : input.toCharArray()) {
                int offset = c - 'a';
                int target = (1 << offset);
                word = word | target;
            }
            words.add(word);
        }

        //현재 기억하는 알파벳 정보
        int current = (1 << ('z'-'a') + 1) - 1;

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<m; i++) {
            String[] input = br.readLine().split(" ");

            int offset = input[1].charAt(0) - 'a';
            int target = (1 << offset);

            //1이면 해당 알파벳을 잊어버림
            if(input[0].equals("1")) current = current ^ target;
                //0이면 다시 기억함
            else current = current | target;

            //모든 단어와 비교하여 해당 단어의 알파벳을 모두 기억하면 +1
            int count = 0;
            for(int word : words) {
                if(word == (word & current)) count++;
            }
            sb.append(count).append("\n");
        }

        System.out.println(sb.toString().trim());
    }
}
