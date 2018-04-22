package com.dajeong.memo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 * 메모
 * */

public class Memo {
	public static final String MEMO_DIR ="temp/memo";
	
	//생성자에서 메모를 저장할 디렉토리 생성해준다.
	public Memo() {
		File dir = new File(MEMO_DIR);
		if(!dir.exists()) {
			dir.mkdirs();
		}
	}

	//종료 커맨드 상수로 정의
	public static final String EXIT = "/exit";
	
	//1.명령어를 출력하는 함수
	public void showCommand() {
		System.out.println("-------아래 명령 번호를 입력하세요.-------");
		System.out.println("1.쓰기 2.읽기 3.수정 4.삭제 0.프로그램종료");
		System.out.println("------------------------------------");
	}

	// 쓰기함수 
	public void write(Scanner scanner) {
		System.out.println("-----쓰기 모드-----");
		//전체글을 저장할 변수
		StringBuilder content = new StringBuilder();
		
		//키보드를 입력받아야 함.
		while(true) {
			String line = scanner.nextLine();
			if(line.equals(EXIT)) {
				break;
			}else {
				content.append(line+"\n");
			}
		}//while
		
		//작성한 내용이 있으면 파일로 쓴다.
		if(!content.toString().equals("")) {
			//가. 현재시간 가져와서 파일명으로 만든다.
			long now = System.currentTimeMillis();
			//나. 년월일_시분초.txt 파일로 포맷
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
			String filename = sdf.format(now)+".txt";
			//다. 내용을 저장할 파일경로 설정
			Path path = Paths.get(MEMO_DIR, filename);
			try {
				//라.파일쓰기
				Files.write(path, content.toString().getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("'"+filename+"'"+ "파일명으로 메모를 등록하였습니다.");
		}
		
		
	}

	//파일목록 보기 읽기 
	public void list() {
		File file = new File(MEMO_DIR);
		String fileList[] = file.list();
		for(String filename : fileList) {
			System.out.println(filename);
		}	
		//선택된 파일 읽기
		boolean runFlag = true;
		while(runFlag) {
			System.out.print("읽을 파일명을 입력하세요.>>>>>");
			Scanner scanner = new Scanner(System.in);
			String filename = scanner.nextLine();
			String result = "";
			for(int i = 0; i<fileList.length; i++) {
				if(fileList[i].equals(filename)) {
					Path path = Paths.get(MEMO_DIR, filename);
					try{
						List<String> lines = Files.readAllLines(path);
						for(String line : lines) {
							System.out.println(line);	
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
					runFlag =false;
				}
			}//for
		}//while

	}
		

	//파일수정
	public void list2() {
		File file = new File(MEMO_DIR);
		String fileList[] = file.list();
		for(String filename : fileList) {
			System.out.println(filename);
		}	
		//선택한 파일 수정 		
		boolean runFlag = true;
		while(runFlag) {
			System.out.print("수정할 파일명을 입력하세요.>>>>>");
			Scanner scanner = new Scanner(System.in);
			String filename = scanner.nextLine();
			StringBuilder content = new StringBuilder(); 
			for(int i = 0; i<fileList.length; i++) {
				if(fileList[i].equals(filename)) {
					Path path = Paths.get(MEMO_DIR, filename);
					try{
						List<String> lines = Files.readAllLines(path);
						System.out.println("수정할 내용을 입력해주세요.");			
						while(true) {
							String line2 = scanner.nextLine();
							if(line2.equals(EXIT)) { // /exit 입력시 수정 종료. 
								System.out.println("수정이 완료되었습니다.");	
								break;
							}else if(line2.equals("/d")) {// /d입력시 기존마지막 한줄 삭제. 
								///////////////////////////////////////////////////////////////////////////
								
								System.out.println("마지막줄이 삭제 되었습니다.");
								break;
							}else {// 그외 입력할 때는 덮어쓰기. 
								Path path2 = Paths.get(MEMO_DIR, filename);
								content.append(line2+"\n");
								try {
									Files.write(path2, content.toString().getBytes());
								}catch(Exception e) {
									e.printStackTrace();
								}
							}
						}//while				
						if(!content.toString().equals("")) {
							Path path2 = Paths.get(MEMO_DIR, filename);
							try {
								Files.write(path2, content.toString().getBytes());
							} catch (Exception e) {
								e.printStackTrace();
							}	
						}//if
					}catch(Exception e) {
						e.printStackTrace();
					}
					runFlag =false;
				}//if
			}//for
		}//while	
	}//list2
	
	


	//파일삭제
	public void list3() {	
		//선택한 파일 삭제  
		boolean runFlag = true;
		while(runFlag) {
			File file = new File(MEMO_DIR);
			String fileList[] = file.list();
			for(String filename : fileList) {
				System.out.println(filename);
			}	
			System.out.print("삭제할 파일명을 입력하세요.>>>>>");
			Scanner scanner = new Scanner(System.in);
			String filename = scanner.nextLine();
			for(int i = 0; i<fileList.length; i++) {
				if(fileList[i].equals(filename)) {
					Path path = Paths.get(MEMO_DIR, filename);
					try{
						Files.delete(path);
						System.out.println("'"+filename+"'" + "(이) 삭제 되었습니다.");
					}catch(Exception e) {
						e.printStackTrace();
					}
					runFlag =false;
				}//if
			}//for
		}//while		
	}
	
}//Memo
