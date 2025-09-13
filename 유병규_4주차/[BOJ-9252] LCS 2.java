import java.io.*;
import java.util.*;

public class Main {
    private static StringBuilder sb =new StringBuilder();
    private static char[] str1, str2;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        str1 = br.readLine().toCharArray();
        str2 = br.readLine().toCharArray();

        int length1 = str1.length;
        int length2 = str2.length;

        int[][] dp = new int[length1+1][length2+1];
        for(int i=1; i<=length1; i++) {
            for(int j=1; j<=length2; j++) {
                if(str1[i-1] == str2[j-1]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        getString(dp, length1, length2);
        System.out.println(dp[length1][length2]);
        if(dp[length1][length2] == 0) return;
        System.out.println(sb.toString());
    }

    private static void getString(int[][] dp, int x, int y) {
        Stack<Character> stack = new Stack<>();
        while(x>0 && y>0) {
            if(dp[x][y] == dp[x-1][y]) {
                x--;
                continue;
            }
            if(dp[x][y] == dp[x][y-1]) {
                y--;
                continue;
            }
            stack.push(str1[x-1]);
            x--;
            y--;
        }

        while(!stack.isEmpty()) {
            sb.append(stack.pop()+"");
        }
    }
}
