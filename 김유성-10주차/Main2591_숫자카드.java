import java.util.Scanner;

public class Main {
	
		static String input;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		input = sc.next();
		
		int dp[] = new int [input.length() + 1];
		
        if ((input.charAt(0) - '0') == 0) {
        	System.out.println("0");
        	return;
        }
        	
		dp[1] = 1;
        
		if (input.length() > 1) {
			int a = (input.charAt(0) - '0') * 10 + (input.charAt(1) - '0');
			if (a > 10 && a <= 34)
				dp[2] = 2;
			else
				dp[2] = 1;
		}
			
		for (int i = 3; i <= input.length(); i++) {
            int one = (input.charAt(i - 1) - '0');
			int two = (input.charAt(i - 2) - '0') * 10 + one;
            
            if (one >= 1)
                dp[i] += dp[i - 1];
            if (10 <= two && two <= 34)
                dp[i] += dp[i - 2];
			
		}
		
		System.out.println(dp[input.length()]);
	}
}
