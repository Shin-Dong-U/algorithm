package com.algorithm.study;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
방금그곡

라디오를 자주 듣는 네오는 라디오에서 방금 나왔던 음악이 무슨 음악인지 궁금해질 때가 많다. 
그럴 때 네오는 다음 포털의 '방금그곡' 서비스를 이용하곤 한다. 
방금그곡에서는 TV, 라디오 등에서 나온 음악에 관해 제목 등의 정보를 제공하는 서비스이다.

네오는 자신이 기억한 멜로디를 가지고 방금그곡을 이용해 음악을 찾는다. 
그런데 라디오 방송에서는 한 음악을 반복해서 재생할 때도 있어서 
네오가 기억하고 있는 멜로디는 음악 끝부분과 처음 부분이 이어서 재생된 멜로디일 수도 있다. 
반대로, 한 음악을 중간에 끊을 경우 원본 음악에는 네오가 기억한 멜로디가 들어있다 해도 
그 곡이 네오가 들은 곡이 아닐 수도 있다. 
그렇기 때문에 네오는 기억한 멜로디를 재생 시간과 제공된 악보를 직접 보면서 비교하려고 한다. 

다음과 같은 가정을 할 때 네오가 찾으려는 음악의 제목을 구하여라.

방금그곡 서비스에서는 음악 제목, 재생이 시작되고 끝난 시각, 악보를 제공한다.

네오가 기억한 멜로디와 악보에 사용되는 음은 C, C#, D, D#, E, F, F#, G, G#, A, A#, B 12개이다.
각 음은 1분에 1개씩 재생된다. 

음악은 반드시 처음부터 재생되며 음악 길이보다 재생된 시간이 길 때는 음악이 끊김 없이 처음부터 반복해서 재생된다. 

음악 길이보다 재생된 시간이 짧을 때는 처음부터 재생 시간만큼만 재생된다.

음악이 00:00를 넘겨서까지 재생되는 일은 없다.

조건이 일치하는 음악이 여러 개일 때에는 라디오에서 재생된 시간이 제일 긴 음악 제목을 반환한다. 

재생된 시간도 같을 경우 먼저 입력된 음악 제목을 반환한다.

조건이 일치하는 음악이 없을 때에는 “(None)”을 반환한다.

입력 형식
입력으로 네오가 기억한 멜로디를 담은 문자열 m과 방송된 곡의 정보를 담고 있는 배열 musicinfos가 주어진다.

m은 음 1개 이상 1439개 이하로 구성되어 있다.
musicinfos는 100개 이하의 곡 정보를 담고 있는 배열로, 
각각의 곡 정보는 음악이 시작한 시각, 끝난 시각, 음악 제목, 악보 정보가 ','로 구분된 문자열이다.
음악의 시작 시각과 끝난 시각은 24시간 HH:MM 형식이다.
음악 제목은 ',' 이외의 출력 가능한 문자로 표현된 길이 1 이상 64 이하의 문자열이다.
악보 정보는 음 1개 이상 1439개 이하로 구성되어 있다.
출력 형식
조건과 일치하는 음악 제목을 출력한다.

입출력 예시
m	musicinfos	answer
"ABCDEFG"	["12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"]	"HELLO"
"CC#BCC#BCC#BCC#B"	["03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"]	"FOO"
"ABC"	["12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"]	"WORLD"
설명
첫 번째 예시에서 HELLO는 길이가 7분이지만 12:00부터 12:14까지 재생되었으므로 
실제로 CDEFGABCDEFGAB로 재생되었고, 이 중에 기억한 멜로디인 ABCDEFG가 들어있다.

세 번째 예시에서 HELLO는 C#DEFGABC#DEFGAB로, WORLD는 ABCDE로 재생되었다. 
HELLO 안에 있는 ABC#은 기억한 멜로디인 ABC와 일치하지 않고, WORLD 안에 있는 ABC가 기억한 멜로디와 일치한다.

 */

public class JustThatSong {
	
	public String solution(String m, String[] musicinfos) throws ParseException {
		int len = musicinfos.length;
		
		List<String[]> infoList = new ArrayList<>();
		
		for(int i = 0; i < len; i++) { infoList.add(musicinfos[i].split(",")); }  
		
		String startTime, endTime, title, melody;
		String res = "(None)";

		int playTime = 0;//곡이 재생 된 시간
		String playedMelody;//위 시간 동안 재생 된 멜로디 
		
		int maxPlayTime = -1;
		
		for(int i = 0; i < len; i++) {
			startTime = infoList.get(i)[0];
			endTime = infoList.get(i)[1];
			title = infoList.get(i)[2];
			melody = infoList.get(i)[3];
			
			melody = convertSharpScaleStr(melody);//A# B# 등 -> a b 로 치환
			
			playTime = getPlayTime(startTime, endTime);
			playedMelody = getPlayedMelody(melody, playTime);
			
			if(isThisSong(m, playedMelody)) {//멜로디가 일치한다면
				if(playTime > maxPlayTime) {//가장 긴 재생시간 선택 (가장 긴 재생시간이 복수의 값이라면 앞 순번 선택 됨) 
					maxPlayTime = playTime;
					res = title;
				}
			}
		}
		
        return res;
    }
	
	//플레이 시간
	public int getPlayTime(String start, String end) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("HH:mm");
		Date s = f.parse(start);
		Date e = f.parse(end);
		
		long diff = (e.getTime() - s.getTime()) / 1000 / 60 ;
		
		return (int)diff;
	}
	
	//재생 된 멜로디
	public String getPlayedMelody(String melody, int playTime) {
		int len = melody.length();
		char[] melodyChars = melody.toCharArray();
		
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < playTime; i++) { 
			sb.append( melodyChars[i % len] ); 
		}
		
		return sb.toString();
	}
	
	//기억 하고 있는 멜로디가 곡의 일부와 일치하는지 확인
	public boolean isThisSong(String rememberMelody, String melody) {
		rememberMelody = convertSharpScaleStr(rememberMelody);
		melody = convertSharpScaleStr(melody);
		
		return melody.indexOf(rememberMelody) >= 0;
	}
	
	//#이 들어간 문자열 치환
	public String convertSharpScaleStr(String melody) {
		String convertStr = melody.replaceAll("A#", "a")
								  .replaceAll("C#", "c")
								  .replaceAll("D#", "d")
								  .replaceAll("F#", "f")
								  .replaceAll("G#", "g")
								  .replaceAll("B#", "C")
								  .replaceAll("E#", "F");
		
		return convertStr; 
	}
	
	public static void main(String[] args) throws ParseException {
		String m1 = "ABCDEFG";
		String m2 = "CC#BCC#BCC#BCC#B";
		String m3 = "ABC";
		String m4 = "ABC";
		String m5 = "ABC";
		String m6 = "ABC";
		
		String[] musicinfos1 = {"12:00,12:14,HELLO,CDEFGAB", "13:00,13:05,WORLD,ABCDEF"};
		String[] musicinfos2 = {"03:00,03:30,FOO,CC#B", "04:00,04:08,BAR,CC#BCC#BCC#B"};
		String[] musicinfos3 = {"12:00,12:14,HELLO,C#DEFGAB", "13:00,13:05,WORLD,ABCDEF"};
		String[] musicinfos4 = {"00:00,00:05,HI,ABC#ABC"};
		String[] musicinfos5 = {"00:00,00:06,HI,ABC#ABC"};
		
		
		JustThatSong jts = new JustThatSong();
		System.out.println(jts.solution(m1, musicinfos1));
		System.out.println(jts.solution(m2, musicinfos2));
		System.out.println(jts.solution(m3, musicinfos3));
		System.out.println(jts.solution(m4, musicinfos4));
		System.out.println(jts.solution(m5, musicinfos5));
		
	}
}
