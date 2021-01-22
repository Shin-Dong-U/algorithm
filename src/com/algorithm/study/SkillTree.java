package com.algorithm.study;

/*
문제 설명
선행 스킬이란 어떤 스킬을 배우기 전에 먼저 배워야 하는 스킬을 뜻합니다.

예를 들어 선행 스킬 순서가 스파크 → 라이트닝 볼트 → 썬더일때, 썬더를 배우려면 먼저 라이트닝 볼트를 배워야 하고, 라이트닝 볼트를 배우려면 먼저 스파크를 배워야 합니다.

위 순서에 없는 다른 스킬(힐링 등)은 순서에 상관없이 배울 수 있습니다. 따라서 스파크 → 힐링 → 라이트닝 볼트 → 썬더와 같은 스킬트리는 가능하지만, 썬더 → 스파크나 라이트닝 볼트 → 스파크 → 힐링 → 썬더와 같은 스킬트리는 불가능합니다.

선행 스킬 순서 skill과 유저들이 만든 스킬트리1를 담은 배열 skill_trees가 매개변수로 주어질 때, 가능한 스킬트리 개수를 return 하는 solution 함수를 작성해주세요.

제한 조건
스킬은 알파벳 대문자로 표기하며, 모든 문자열은 알파벳 대문자로만 이루어져 있습니다.
스킬 순서와 스킬트리는 문자열로 표기합니다.
예를 들어, C → B → D 라면 CBD로 표기합니다
선행 스킬 순서 skill의 길이는 1 이상 26 이하이며, 스킬은 중복해 주어지지 않습니다.
skill_trees는 길이 1 이상 20 이하인 배열입니다.
skill_trees의 원소는 스킬을 나타내는 문자열입니다.
skill_trees의 원소는 길이가 2 이상 26 이하인 문자열이며, 스킬이 중복해 주어지지 않습니다.
입출력 예
skill	skill_trees	return
"CBD"	["BACDE", "CBADF", "AECB", "BDA"]	2
입출력 예 설명
BACDE: B 스킬을 배우기 전에 C 스킬을 먼저 배워야 합니다. 불가능한 스킬트립니다.
CBADF: 가능한 스킬트리입니다.
AECB: 가능한 스킬트리입니다.
BDA: B 스킬을 배우기 전에 C 스킬을 먼저 배워야 합니다. 불가능한 스킬트리입니다.
 */
public class SkillTree {
	public static void main(String[] args) {
		SkillTree st = new SkillTree();
		String skill = "CBD";
		String[] trees = {"BACDE", "CBADF", "AECB", "BDA"};
		int res = st.solution(skill, trees);
		System.out.println(res);
	}

	/*
	 * for
	 * 	   1. 스킬트리 문자열 변환 (스킬에 없는 char는 스킬트리에 영향을 끼치지 않으므로 삭제처리)
	 *     2. 스킬트리 순서 체크
	 * 	   3. 맞다면 카운트 + 1
	 * end for
	 */
	public int solution(String skill, String[] skill_trees) {
        int cnt = 0;

        String convertTreeStr = "";
        for(int i = 0; i < skill_trees.length; i++){
        	convertTreeStr = excludeNonTreeChar(skill, skill_trees[i]);
        	if(isPossibleTree(skill, convertTreeStr)) { cnt++; }
        }
        
        return cnt;
    }
	
	//가능한 스킬트리인지 검증
	public boolean isPossibleTree(String skill, String convertTreeStr){
		if(skill.length() < convertTreeStr.length()) { return false; } // 예외처리. 변환 된 문자열 길이는 스킬의 문자열 길이를 초과 할 수 없음. 
		
		char[] skillChars = skill.toCharArray();
		char[] treeChars = convertTreeStr.toCharArray();
		
		for(int i = 0; i < treeChars.length; i++) {
			if( !isSame(skillChars[i], treeChars[i]) ) return false;
		}
		return true;
	}
	
	//skill에 포함 되지 않은 문자열 삭제
	public String excludeNonTreeChar(String skill, String tree) {
		StringBuffer sb = new StringBuffer();
		
		char[] treeChars = tree.toCharArray();
		for(int i = 0; i < tree.length(); i++) {
			char e = treeChars[i];
			if(isElement(skill, e)) { sb.append(e); }
		}
		
		return sb.toString();
	}
	
	//skill에 포함 된 문자열인지 체크
	public boolean isElement(String skill, char c) {
		char[] skillChars = skill.toCharArray();
		
		for(int i = 0; i < skillChars.length; i++) { 
			if(skillChars[i] == c) { return true; } 
		}
		
		return false;
	}
	
	public boolean isSame(char a, char b) {
		return a == b;
	}
}
