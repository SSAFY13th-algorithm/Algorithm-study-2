import java.io.*;
import java.util.*;

public class Main {
	private static int[][] d = {{0,1},{0,-1},{-1,0},{1,0}};
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        
        Cell[][] board = new Cell[n][n];
        for(int i=0; i<n; i++) {
        	st = new StringTokenizer(br.readLine());
        	for(int j=0; j<n; j++) {
        		board[i][j] = new Cell(Integer.parseInt(st.nextToken()));
        	}
        }

        Piece[] pieces = new Piece[k];
        for(int i=0; i<k; i++) {
        	st = new StringTokenizer(br.readLine());
        	int x = Integer.parseInt(st.nextToken())-1;
        	int y = Integer.parseInt(st.nextToken())-1;
        	int d = Integer.parseInt(st.nextToken())-1;
        	
        	pieces[i] = new Piece((i+1), x, y, d);
        	board[x][y].bottom = pieces[i];
        }
        
        int count = 0;
        while(count <= 1000) {
        	count++;
        	for(int i=0; i<k; i++) {
        		Piece piece = pieces[i];
        		
        		int nx = piece.x + d[piece.dir][0];
        		int ny = piece.y + d[piece.dir][1];
        		//이동할 곳이 보드 밖이거나 파란색일 경우
        		if(nx<0 || nx>=n || ny<0 || ny>=n || board[nx][ny].type == 2) {
        			//방향전환하고
        			piece.changeDir();
        			//이동할 곳
        			nx = piece.x + d[piece.dir][0];
            		ny = piece.y + d[piece.dir][1];
            		
            		//이동할 곳이 다시 보드 밖이거나 파란색일 경우 이동하지 않음
            		if(nx<0 || nx>=n || ny<0 || ny>=n || board[nx][ny].type == 2) continue;
        		}
        		//흰색이거나 빨간색인 경우 이동함        		
        		//말 아래에 말이 있던 경우에는 연결 끊기
        		if(piece.down != null) {
        			piece.down.up = null;
        			piece.down = null;
        		}

        		//움직이는 말이 보드에 직접 맞닿아 있는 말이라면 기존 칸 위에는 말이 없음
        		if(board[piece.x][piece.y].bottom == piece)
        			board[piece.x][piece.y].bottom = null;
        		
        		//말 이동
        		piece.x = nx;
        		piece.y = ny;
        		piece.update();
        		
        		//빨간색이고 말 위에 말이 있다면
        		if(board[nx][ny].type == 1 && piece.up != null) {
        			//뒤집음
        			Piece bottom = piece;
        			while(piece != null) {
        				Piece temp = piece.up;
        				piece.up = piece.down;
        				piece.down = temp;
        				bottom = piece;
        				piece = temp;
        			}
        			
        			piece = bottom;
        		}
        		
        		//해당 칸에 말이 없을 경우
        		if(board[nx][ny].bottom == null) {
        			board[nx][ny].bottom = piece;
        		}
        		
        		//해당 칸에 말이 있을 경우
        		else {
        			Piece top = board[nx][ny].bottom.getTop();
        			piece.down = top;
        			top.up = piece;
        		}
        		
        		//개수 체크
        		if(board[nx][ny].bottom.size() >= 4) {
        			System.out.println(count);
        			return;
        		}
        	}
        }
        
        System.out.println(-1);
    }
    
    private static class Cell{
    	int type;
    	Piece bottom;
    	
    	public Cell(int type) {
    		this.type = type;
    	}
    	
    	@Override
    	public String toString() {
    		String sType = "W";
    		if(type == 1) sType = "R";
    		else if(type == 2) sType = "B";
    		
    		String sbottom = bottom == null ? "x" : bottom.toString();
    		
    		return "["+sType+"/"+sbottom+"]";
    	}
    }
    
    private static class Piece{
    	int num;
    	int x, y;
    	int dir;
    	Piece up, down;
    	
    	public Piece(int num, int x, int y, int dir) {
    		this.num = num;
    		this.x = x;
    		this.y = y;
    		this.dir = dir;
    	}

		public int size() {
			Piece next = this;
			int count = 1;
			while(next.up != null) {
				next = next.up;
				count++;
			}
			return count;
		}

		public Main.Piece getTop() {
			Piece next = this;
			while(next.up != null) {
				next = next.up;
			}
			return next;
		}

		public void update() {
			Piece next = this.up;
			while(next != null) {
				next.x = this.x;
				next.y = this.y;
				next = next.up;
			}
		}

		public void changeDir() {
			switch(dir) {
			case 0: dir = 1; break;
			case 1: dir = 0; break;
			case 2: dir = 3; break;
			case 3: dir = 2; break;
			}
		}
		
		public String getSDir() {
			String sdir = "→";
    		if(dir == 1) sdir = "←";
    		else if(dir == 2) sdir = "↑";
    		else if(dir == 3) sdir = "↓";
    		return sdir;
		}
		
    	@Override
    	public String toString() {
    		String snum = num+"("+getSDir()+")";
    		Piece next = this.up;
    		while(next != null) {
    			snum += ","+next.num+"("+next.getSDir()+")";
    			next = next.up;
    		}
    		return snum;
    	}
    }
}
