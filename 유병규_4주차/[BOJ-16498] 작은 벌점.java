import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int[] nums1 = createSortedNums(a);
        int[] nums2 = createSortedNums(b);
        int[] nums3 = createSortedNums(c);

        int minValue = Math.min(Math.min(a, b), c);

        int result = 0;
        if(minValue == a) result = findMin(nums1, nums2, nums3);
        else if(minValue == b) result = findMin(nums2, nums1, nums3);
        else result = findMin(nums3, nums1, nums2);

        System.out.println(result);
    }

    private static int findMin(int[] base, int[] arr1, int[] arr2) {
        int min = Integer.MAX_VALUE;

        for(int i=0; i<base.length; i++) {
            int num = base[i];
            int[] nearNums1 = getNearNums(arr1, num);
            int[] nearNums2 = getNearNums(arr2, num);

            for(int num1 : nearNums1) {
                for(int num2 : nearNums2) {
                    min = Math.min(min, Math.abs(
                            Math.max(Math.max(num, num1), num2)
                                    - Math.min(Math.min(num, num1), num2)));
                }
            }
        }
        return min;
    }

    private static int[] getNearNums(int[] arr, int num) {
        int left=0, right=arr.length-1, mid=0;

        while(left < right) {
            int nextMid = (right+left)/2;
            if(nextMid == mid) break;
            mid = nextMid;

            if(arr[mid] == num) return new int[] {num};

            if(arr[mid] > num) {
                right = mid;
                continue;
            }
            left = mid;
        }

        return new int[] {arr[left], arr[right]};
    }

    private static int[] createSortedNums(int length) throws IOException{
        st = new StringTokenizer(br.readLine());
        int[] nums = new int[length];
        for(int i=0; i<length; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(nums);
        return nums;
    }
}
