package com.algorithm.study;

import java.util.Arrays;

/**
 * https://programmers.co.kr/learn/courses/30/lessons/68645
 * 정수 n이 매개변수로 주어집니다. 
 * 다음 그림과 같이 밑변의 길이와 높이가 n인 삼각형에서 맨 위 꼭짓점부터 반시계 방향으로 달팽이 채우기를 진행한 후, 
 * 첫 행부터 마지막 행까지 모두 순서대로 합친 새로운 배열을 return 하도록 solution 함수를 완성해주세요.
 * 
 *    1             1 
 *   2 9          2  12
 *  3 10 8       3 13 11
 * 4 5  6 7     4 14 15 10
 *             5  6  7  8  9
 * 
 * 제한사항
 *  n은 1 이상 1,000 이하입니다.
 *   입출력 예
 * n	result
 * 4	[1,2,9,3,10,8,4,5,6,7]
 * 5	[1,2,12,3,13,11,4,14,15,10,5,6,7,8,9]
 * 6	[1,2,15,3,16,14,4,17,21,13,5,18,19,20,12,6,7,8,9,10,11]
 */
public class TriangleSnail {
	
	public static void main(String[] args) {
		TriangleSnail t = new TriangleSnail();
		
		int[] res = t.solution(4);
		
		System.out.println(Arrays.toString(res));
	}
	
	public int[] solution(int n) {
        int seq = 1;
        
        int gear = 0; //진행방향
        int num = n;  //해당 방향에서의 반복 수행 횟수
        
        //좌표
        int x = 0; 
        int y = -1;        
        
        int[][] arr = new int[n][n];
        
        while(num > 0) {
        	for(int i = 0; i < num; i++) {
        		if(gear % 3 == 0) {
            		y++;
            	}else if(gear % 3 == 1) {
            		x++;
            	}else if(gear % 3 == 2) {
            		y--; x--;
            	}
        		arr[y][x] = seq++;
        	}
        	
        	num--; 
        	gear++; 
        }
        
        int[] answer = new int[getLength(n)];
        
        int idx = 0;
        for(int i = 0; i < n; i++) {
        	for(int j = 0; j < n; j++) {
        		if(arr[i][j] == 0) { break; }
        		answer[idx++] = arr[i][j];
        	}
        }
        
        return answer;
    }
	
	//삼각달팽이 배열길이 리턴 
	public int getLength(int n) {
		return n * (n + 1) / 2;
	}
	
}
