# PokemonCardGame
2-2 객체지향언어2 프로젝트



1. 프로젝트 개요----------------------------------

개발 앱 소개 : 처음 5초간 오픈 된 카드의 위치를 기억한 후 카드를 2장씩 뒤집어 같은 그림을 찾는다. 만약 뒤집은 2장이 서로 다른 그림이라면 다시 뒤집는다. 이를 반복하며 24장의 모든 카드의 짝을 맞추면 끝나는 게임이다.

2. 설계---------------------------------------

1) 구현 기능에 대한 설계 

1.	프레임 나누기 ( 패널 )
2.	카드 24장을 배열로 세팅 후 랜덤으로 배치하는 기능
3.	게임 시작 후 카드 앞면을 보여주기
4.	5초 카운트 다운 후 모든 카드 뒤집기 기능
5.	30초 타이머 시작
6.	카드 두개가 뒤집혔을 때 서로 같은 그림의 카드라면 뒤집힌 채로 유지 (앞면)
-	맞췄을 시 배열에 클릭한 카드 추가  -> 모든 카드가 배열에 들어갔다면 게임 종료
카드 두개가 뒤집혔을 때 서로 다른 그림의 카드라면 1초 후에 다시 뒤집기
7.	성공했을 시 – 성공 다이얼로그, 실패했을 시 – 실패 다이얼로그
8.	실시간으로 점수 계산 –> 보여주기
9.	시작 화면 – 시작 버튼, 게임 방법을 보여주는 버튼, 게임 종료 버튼 만들기
10.	타이머가 10초 남았을 때 바 색상 빨간색으로 변경
11.	마우스 클릭 시, 카드 일치 여부에 따른 효과음 + 10초 효과음 + 전체 배경음악 on/off
12.	뒤로가기 버튼
13.	닉네임 설정



2) 상세 클래스 설계

게임을 시작하고 맨 처음 뜨는 MainFrame 클래스
-	게임 시작 , 게임 방법 ,  게임 종료 버튼
게임 시작을 누르면 뜨는 GameFrame 클래스
-	게임 창이 뜨며, 같은 그림 찾기 게임을 플레이 할 수 있다. 게임의 전체적인 기능이 들어 있다.
게임 방법을 누르면 뜨는 HowFrame 클래스
-	게임 플레이의 방법이 적혀있는 창이 뜬다.


3) 기초 클래스 설계
 <img width="452" alt="Image" src="https://github.com/user-attachments/assets/7fe0ebfd-fa8b-4955-ad3c-86cd8c4cce2d" />


4) 화면 설계
<img width="278" alt="Image" src="https://github.com/user-attachments/assets/e466bbae-ab0d-4f6b-9011-144e6936cc9d" />

<img width="236" alt="Image" src="https://github.com/user-attachments/assets/f0d74a0c-e5e3-4cb1-8a63-77dbd9ace1c8" />


5) 화면 상세 설계

<img width="451" alt="Image" src="https://github.com/user-attachments/assets/fa6b1f6e-1704-4522-967f-70087a20d78c" />

3. 구현

1) 화면 구현

<img width="452" alt="Image" src="https://github.com/user-attachments/assets/8d9278e1-3785-49be-9fdb-70f713807677" />

<img width="452" alt="Image" src="https://github.com/user-attachments/assets/62c4a00d-4787-454a-bff1-60b68cade32c" />

 

		







2) 클래스 기능 구현

Class GameFrame 
-Class TimerThread – 타이머 스레드
-Class TimerPanel – 바 및 타이머 객체 위치 및 색상 설정
-Class ScorePanel – 점수 및 닉네임 설정
	--Class PlayMyActionListener – 음악 재생
-Class GamePanel – 카드 객체와 게임 기능 함수(카드 뒤집기, 섞기, 그림 넣기 등) 구현
	--Class ClickListener – 카드 일치 여부 확인
-Class MyLabel – 바 관련 함수 구현
-Class ConsumerThread – 바 1초마다 줄이는 스레드

Class HowFrame 
Class MainFrame 
	-Class MainPanel – 이미지 삽입

4. 앱 사용 설명서
실행하면 시작화면이 뜬다.
시작버튼을 누르면 게임을 시작할 수 있고 방법 버튼을 누르면 게임 방법을 볼 수 있다.
종료버튼을 누르면 게임이 종료된다.
시작화면에서 캐릭터버튼을 누르면 텍스트 창에 닉네임을 설정할 수 있다.
닉네임을 쓰고 다시 한번 캐릭터 버튼을 눌러주면 닉네임이 저장된다.

시작버튼을 눌러 게임을 시작하면 5초동안 모든 카드의 앞면을 보여준다.
5초가 지난 후 모든 카드가 뒤집히며 이때부터 55초동안 카드를 두 장씩 뒤집어 같은 그림을 찾아 짝을 맞춰야 한다. 
시간 안에 24장의 모든 카드를 뒤집어 짝을 맞추면 게임을 클리어하게 된다.
시간 안에 24장의 모든 카드를 뒤집지 못하면 게임을 실패하게 된다.
점수는 카드 한 쌍을 맞출 때마다 10점, 게임이 종료된 시점에서 남은 초 (sec * 10) 의 추가 보너스 점수가 들어온다.
![image](https://github.com/user-attachments/assets/263a1446-3ce0-4d02-a1d3-0ce599449b52)
