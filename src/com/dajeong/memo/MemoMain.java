package com.dajeong.memo;

import java.util.Scanner;

public class MemoMain {

	public static void main(String[] args) {
		Memo memo = new Memo();
		
		Scanner scanner = new Scanner(System.in);
		boolean runFlag = true;
	
		while(runFlag) {
			memo.showCommand();
			String cmd = scanner.nextLine();
			switch(cmd) {
			case "1": //1. 쓰기 
				memo.write(scanner);
				break;
			case "2"://2. 읽기 
				memo.list();
				break;
			case "3"://3. 수정 
				memo.list2();
				break;
			case "4"://4. 삭제 
				memo.list3();
				break;
			case "0": //0.프로그램종료
				runFlag = false;
				break; 
			default: 
				System.out.println("명령어가 잘못되었습니다.");
			}
		}//while
		
		System.out.println("프로그램이 종료되었습니다.");

	}

}
