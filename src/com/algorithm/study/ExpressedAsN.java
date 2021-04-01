package com.algorithm.study;

/*

아래와 같이 5와 사칙연산만으로 12를 표현할 수 있습니다.

12 = 5 + 5 + (5 / 5) + (5 / 5)
12 = 55 / 5 + 5 / 5
12 = (55 + 5) / 5

5를 사용한 횟수는 각각 6,5,4 입니다. 그리고 이중 가장 작은 경우는 4입니다.
이처럼 숫자 N과 number가 주어질 때, N과 사칙연산만 사용해서 표현 할 수 있는 방법 중 
N 사용횟수의 최솟값을 return 하도록 solution 함수를 작성하세요.

제한사항
N은 1 이상 9 이하입니다.
number는 1 이상 32,000 이하입니다.
수식에는 괄호와 사칙연산만 가능하며 나누기 연산에서 나머지는 무시합니다.
최솟값이 8보다 크면 -1을 return 합니다.
입출력 예
N	number	return
5	12	4
2	11	3
입출력 예 설명
예제 #1
문제에 나온 예와 같습니다.

예제 #2
11 = 22 / 2와 같이 2를 3번만 사용하여 표현할 수 있습니다.


 */

import java.util.*;

public class ExpressedAsN {
	
	private List<Set<Integer>> cases;

	private void init() {
		this.cases = new ArrayList<Set<Integer>>();
		this.cases.add(null);//0번 인덱스는 사용하지 않는다.
	}
	
	public int solution(int N, int number) {
		init();
		
		int limitOver = -1;
		
		int count = 1;
		
		while(true) {
			Set<Integer> set = addCase(N, count);
			
			if( set.contains(number) ) { 
				break; 
			}else if(count == 8) {
				count = limitOver;
				break;
			}else {
				this.cases.add(set);
				count++; 
			}
		}
		
        return count;
    }
	
	//n을 count 만큼 사용하여 만들 수 있는 모든 값을 set에 담는다. 
	private Set<Integer> addCase(int n, int count) {
		Set<Integer> set = new HashSet<>();
		set.add(getNumberConsistingOfN(n, count));//ex1) n=2, count=2  return 22  ex2) n=5, count=4 return 5555
		
		int a = 0;
		int b = 0;
		
		//i 와 j의 합은 count다
		for(int i = 1; i <= count / 2; i++) {
			int j = count - i;
			
			Iterator<Integer> ii = cases.get(i).iterator();
			while(ii.hasNext()) {
				a = ii.next();
				
				Iterator<Integer> ji = cases.get(j).iterator();
				while(ji.hasNext()) {
					b = ji.next();
					
					set.add(sum(a, b));
					set.add(subtract(a, b));
					set.add(subtract(b, a));
					set.add(divide(a, b));
					set.add(divide(b, a));
					set.add(multiply(a, b));
				}
			}
		}
		
		return set;
	}
	
	//n으로만 이루어진 수 구하기 
	public int getNumberConsistingOfN(int n, int count) {
		int res = n;
		
		for(int i = 1; i < count; i++) {
			res = res * 10 + n;
		}
		
		return res;
	}
	
	public int sum(int a, int b) { return a + b; }
	public int subtract(int a, int b) { return a - b; }
	public int multiply(int a, int b) { return a * b; }
	public int divide(int a, int b) { return b == 0 ? 0 : a / b; }
	
	public static void main(String[] args) {
		ExpressedAsN e = new ExpressedAsN();
		
		int n1 = 2;
		int number = 22;
		
		int n2 = 5;
		int number2 = 12;

		int n3 = 2;
		int number3 = 12;
		
		int n4 = 1;
		int number4 = 1121;
		
		
		
		int res = e.solution(n1, number);
		System.out.println(res);
		e.init();
		
		res = e.solution(n2, number2);
		System.out.println(res);
		e.init();
		res = e.solution(n3, number3);
		System.out.println(res);
		e.init();
		res = e.solution(n4, number4);
		System.out.println(res);
		e.init();
		
		
		System.out.println(e.getNumberConsistingOfN(5,2));
	}
}
