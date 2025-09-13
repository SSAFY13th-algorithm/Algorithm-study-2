import java.io.*;
import java.util.*;

public class boj10216 {
    // 입출력을 위한 BufferedReader, BufferedWriter 선언
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static final StringBuilder sb = new StringBuilder();

    // 분리집합(서로소 집합)의 루트 노드들을 저장하는 집합
    private static Set<Integer> set;

    // 유니온-파인드를 위한 배열 (x, y, r, parent)
    private static int[][] uf;

    // 각 집합의 크기를 저장하는 배열
    private static int[] size;

    // 적군 진영의 수
    private static int n;

    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(br.readLine());  // 테스트 케이스 개수

        for (int i = 0; i < t; i++) {
            init();            // 초기 데이터 설정 및 연결 관계 계산
            getDisjointSets();  // 분리된 집합의 수 계산
            sb.append(set.size()).append("\n");  // 결과 저장
        }

        bw.write(sb.toString());  // 모든 결과 출력
        bw.flush();
        bw.close();
        br.close();
    }

    // 초기 데이터를 설정하고 연결 관계를 계산하는 메소드
    private static void init() throws IOException {
        n = Integer.parseInt(br.readLine());  // 적군 진영의 수

        // 배열 초기화
        uf = new int[n][4];  // [x좌표, y좌표, 통신 반경, 부모 인덱스]
        size = new int[n];    // 각 집합의 크기
        set = new HashSet<>();  // 분리집합의 루트 노드 집합

        // 각 진영 정보 입력 및 연결 관계 계산
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            uf[i][0] = Integer.parseInt(st.nextToken());  // x좌표
            uf[i][1] = Integer.parseInt(st.nextToken());  // y좌표
            uf[i][2] = Integer.parseInt(st.nextToken());  // 통신 반경
            uf[i][3] = i;  // 자기 자신을 부모로 초기화
            size[i] = 1;   // 초기 집합 크기는 1

            // 이전에 입력받은 모든 진영과의 연결 여부 확인
            for (int j = 0; j < i; j++) {
                // 두 진영의 통신 반경 합의 제곱이 두 점 사이의 거리의 제곱보다 크거나 같으면 연결 가능
                // (r1 + r2)^2 >= (x1 - x2)^2 + (y1 - y2)^2
                if ((uf[i][2] + uf[j][2]) * (uf[i][2] + uf[j][2])
                    >= Math.abs(uf[i][0] - uf[j][0]) * Math.abs(uf[i][0] - uf[j][0])
                    + Math.abs(uf[i][1] - uf[j][1]) * Math.abs(uf[i][1] - uf[j][1])) {
                    union(i, j);  // 두 진영을 같은 집합으로 합침
                }
            }
        }
    }

    // 두 집합을 합치는 메소드
    private static void union(int x, int y) {
        int X = find(x);  // x의 루트 찾기
        int Y = find(y);  // y의 루트 찾기

        if (X == Y) return;  // 이미 같은 집합이면 종료

        // 더 작은 집합을 더 큰 집합에 합침 (최적화)
        if (size[X] < size[Y]) {
            uf[X][3] = Y;         // X의 부모를 Y로 설정
            size[Y] += size[X];  // Y 집합의 크기 증가
        } else {
            uf[Y][3] = X;         // Y의 부모를 X로 설정
            size[X] += size[Y];  // X 집합의 크기 증가
        }
    }

    // 요소의 루트를 찾는 메소드 (경로 압축 사용)
    private static int find(int x) {
        if (uf[x][3] == x) return x;  // 자기 자신이 루트인 경우
        return uf[x][3] = find(uf[x][3]);  // 경로 압축
    }

    // 분리된 집합의 수를 계산하는 메소드
    private static void getDisjointSets() {
        for (int i = 0; i < n; i++) {
            int root = find(i);  // 각 요소의 루트 찾기
            if (!set.contains(root)) set.add(root);  // 새로운 루트면 집합에 추가
        }
    }
}