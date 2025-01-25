import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import javax.swing.border.LineBorder;
import javax.swing.event.*;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class GameFrame extends JFrame {
   Container contentPane = getContentPane();
   JPanel timerPanel;


   int sec=60; //타이머 = 60초
   int startCheck;

   JLabel labelTimer;       // 시간초 보여줄 timerPanel의 라벨
   Timer timerSec = new Timer(); //라벨의 타이머 초기화
   Timer timerMix = new Timer();
   Timer timerHide = new Timer(); // 카드 뒤집기 전 5초 보여주기
   Timer timerCardCheck = new Timer(); // 두 개의 카드가 다를 때 3초 보여주기


   JButton btnStart;
   JPanel gamePanel; // 게임 패널
   int cardrandom[] = new int [24]; // 카드 배열
   ImageIcon imageBack;
   ImageIcon imageIcon[] = new ImageIcon[24] ;


   JButton cardCheck; // 카드 체크
   JButton firstCard; // 첫 카드
   JButton secondCard;
   int homework=0;

   int pressedCard; // 버튼 몇 번 눌렀는지 확인
   int firstCardnum; // 첫 카드 배열에 저장
   int secondCardnum;
   int cardNum; // 카드 번호 저장
   ImageIcon selectedImg; // 선택 한 카드 보여주기
   int isClear;
   JPanel scorePanel; // 점수 패널
   JLabel labelScore;
   int score=0; //점수
   JLabel la_name;

   private Clip clip;


   public GameFrame() {
      setLayout(new BorderLayout());
      //프레임설정
      setTitle("같은 그림 찾기");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(1920,1080);

      setVisible(true);
      //컨텐트 팬
       //컨텐트 팬 알아내기
      contentPane.setBackground(Color.WHITE); //배경 색 변경
      contentPane.setLayout(new BorderLayout(10,50));

      //패널 설정
      timerPanel = new TimerPanel();
      gamePanel = new GamePanel();
      scorePanel = new ScorePanel();

      timerPanel.setBounds(0, 0, 1920, 70);
      gamePanel.setBounds(500, 100, 1420, 980);
      scorePanel.setBounds(0, 100, 500, 580);




      JButton btnBack = new JButton("뒤로"); // 뒤로 버튼
      btnBack.setBackground(Color.orange);
      btnBack.setSize(145, 30);
      btnBack.setLocation(50, 50);

      btnBack.addActionListener(new ActionListener() { // 버튼 누르면 다시 시작 화면으로

            public void actionPerformed(ActionEvent e) {
               setVisible(false);
               isClear=1;
               new MainFrame();
            }
        });
      add(btnBack);



      add(timerPanel, BorderLayout.NORTH);
      add(gamePanel); // 게임판 패널
      add(scorePanel); // 캐릭터, 이름, 점수 등 패널

      } //게임패널



   class TimerThread extends Thread {

      public TimerThread(JLabel labelTimer) {
         labelTimer = labelTimer; // 타이머 카운트를 출력할 레이블
      }
      // 스레드 코드. run()이 종료하면 스레드 종료
      @Override
      public void run() {
         while(true) { // 무한 루프
            labelTimer.setText(Integer.toString(sec) + "초");// 레이블에 카운트 값 출력
            sec--; // 카운트 감소
            try {
               Thread.sleep(1000); // 1초 동안 잠을 잔다.
            }
            catch(InterruptedException e) {
               return; // 예외가 발생하면 스레드 종료
            }
            if(sec<=-1) {
            	sec=0;

            	dialogResult();

            }
            if(isClear==1)
            	break;
         }
      }
   }



   class TimerPanel extends JPanel{

      public TimerPanel(){

         this.setLayout(null);
         setBackground(Color.LIGHT_GRAY);
         setLayout(new GridLayout(1,3,30,10));


         TimerThread th = new TimerThread(labelTimer);
         setSize(1200,170);
         setVisible(true);

          labelTimer = new JLabel("  "+sec+" 초");
          labelTimer.setFont(new Font("GOTHIC",Font.BOLD , 20));
          labelTimer.setHorizontalAlignment(NORMAL);


          th.start(); // 타이머 스레드의 실행을 시작하게 한다.

          MyLabel bar = new MyLabel(60);
          bar.setBackground(Color.ORANGE);
          bar.setOpaque(true);
          bar.setLocation(20,50);
          bar.setSize(300, 20); // 300x20 크기의 바
          ConsumerThread b = new ConsumerThread(bar); // 스레드 생성
          b.start(); // 스레드 시작

          add(bar);
          add(labelTimer);
      }
}
   class ScorePanel extends JPanel{

      ScorePanel(){
         this.setLayout(null);
         setBackground(Color.WHITE);


         //캐릭터 사진

         ImageIcon chImage = new ImageIcon(getClass().getClassLoader().getResource("resources/chImage.jpg"));
         JLabel la_character = new JLabel(chImage);
         la_character.setSize(280,397);
         la_character.setLocation(120,80);
         la_character.setBorder(new LineBorder(Color.BLACK, 2, true));
         la_character.setVisible(true);
         this.add(la_character);

         //닉네임
//         la_name = new JLabel("닉네임을 입력하세요");
//         la_name.setSize(300,50);
//
//
//         la_name.setLocation(100,530);
//         la_name.setFont(new Font("맑은 고딕", Font.BOLD, 30));
//         la_name.setVisible(true);
//         this.add(la_name);
//         fileRead();

         //점수
         JLabel labelScore = new JLabel("점수 : " + score + "점");
         labelScore.setSize(145, 30);
         labelScore.setLocation(100, 600);
         labelScore.setFont(new Font("맑은 고딕", Font.BOLD, 30));
         add(labelScore);

         //점수 보이기
         timerSec = new Timer();
         timerSec.scheduleAtFixedRate(new TimerTask() {
            public void run() {
               labelScore.setText(score + "점");
            }
         }, 100, 1); //1초에 한번씩 갱신

         //음악 플레이어
         PlayMyActionListener al = new PlayMyActionListener();


         JButton btnMusicPlay = new JButton("PLAY");
         btnMusicPlay.setBackground(Color.ORANGE);
         btnMusicPlay.setSize(145, 60);
         btnMusicPlay.setLocation(80, 650);
         add(btnMusicPlay);
         setVisible(true);

         JButton btnMusicStop = new JButton("STOP");
         btnMusicStop.setBackground(Color.orange);
         btnMusicStop.setSize(145, 60);
         btnMusicStop.setLocation(240, 650);
         add(btnMusicStop);

         btnMusicPlay.addActionListener(al);
         btnMusicStop.addActionListener(al);
         loadAudio("audio/audio.wav");


      }
      private void loadAudio(String pathName) {
          try {
              clip = AudioSystem.getClip();
              File audioFile = new File(pathName);
              AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
              clip.open(audioStream);
          }
          catch (LineUnavailableException e) { e.printStackTrace(); }
          catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
          catch (IOException e) { e.printStackTrace(); }

      }

      class PlayMyActionListener implements ActionListener {
          public void actionPerformed(ActionEvent e) {
              switch(e.getActionCommand()) {
                  case "PLAY": clip.start(); //
                      break;
                  case "STOP": clip.stop(); //
                      break;
              }
          }
      }
   }

   class GamePanel extends JPanel{
	   //GamePanel 배경 사진 넣기
	   ImageIcon backgroundImg = new ImageIcon(getClass().getClassLoader().getResource("resources/GameBackground.jpg"));

	   public void paintComponent(Graphics g) {
	        g.drawImage(backgroundImg.getImage(), 0, 0, null);
	        setOpaque(false);//그림을 표시하게 설정,투명하게 조절
	        super.paintComponent(g);
	   }


      JButton card[] = new JButton[24]; //메인 카드 24장

      GamePanel(){


         this.setLayout(null);


         mixNumber();
         setImage();

         setButtonResetImage();
         setButtonName();
         for (int i=0; i<24; i++) {
				card[i].addMouseListener(new ClickListener());
			}

         action();


         card[0].setLocation(120,80);
         card[1].setLocation(250,80);
         card[2].setLocation(380,80);
         card[3].setLocation(510,80);
         card[4].setLocation(640,80);
         card[5].setLocation(770,80);

         card[6].setLocation(120,210);
         card[7].setLocation(250,210);
         card[8].setLocation(380,210);
         card[9].setLocation(510,210);
         card[10].setLocation(640,210);
         card[11].setLocation(770,210);

         card[12].setLocation(120,340);
         card[13].setLocation(250,340);
         card[14].setLocation(380,340);
         card[15].setLocation(510,340);
         card[16].setLocation(640,340);
         card[17].setLocation(770,340);

         card[18].setLocation(120,470);
         card[19].setLocation(250,470);
         card[20].setLocation(380,470);
         card[21].setLocation(510,470);
         card[22].setLocation(640,470);
         card[23].setLocation(770,470);

      } //GamePanel

      class ClickListener implements MouseListener{ // 게임 진행을 위한 코드

         @Override
         public void mouseClicked(MouseEvent e) {

         }

         @Override
         public void mousePressed(MouseEvent e) {
            cardCheck = ((JButton)e.getSource());
            alert("audio/IfCorrect.wav");
            if(cardCheck.getName() == "Check") {
               pressedCard = 0;
            }
            else if (startCheck == 1 && (pressedCard == 0 || pressedCard == 1)) {
               pressedCard += 1;
               if(pressedCard == 1) {
                  firstCard = ((JButton)e.getSource()); // 첫 카드
                  cardNum = Integer.parseInt(firstCard.getName()) - 1;
                  selectedImg = new ImageIcon(getClass().getClassLoader().getResource("resources/" + cardrandom[cardNum] + ".jpg"));
                  firstCard.setIcon(selectedImg); // 눌려진 카드 보여주기

                  firstCardnum = cardrandom[cardNum]; //
                  if (cardrandom[cardNum] > 12) // 숫자 다시 정해주기
                     firstCardnum = cardrandom[cardNum] - 12;


               } // 첫 카드
               if(cardCheck.getName() == firstCard.getName()) { // 두 번째 카드
                  pressedCard = 1;
               }
               else if(pressedCard == 2) {
                  secondCard = ((JButton)e.getSource());
                  cardNum = Integer.parseInt(secondCard.getName()) - 1;
                  selectedImg = new ImageIcon(getClass().getClassLoader().getResource("resources/" + cardrandom[cardNum] + ".jpg"));
                  secondCard.setIcon(selectedImg);


                  secondCardnum = cardrandom[cardNum];
                  if(cardrandom[cardNum] > 12) { // 숫자 다시 정해주기
                     secondCardnum = cardrandom[cardNum] -12;
                  }
                  if(firstCardnum == secondCardnum) {
                     pressedCard = 0;
                     firstCard.setName("Check");
                     secondCard.setName("Check");
                     score+=10; // 한 쌍 맞출때마다 10점
                     alert("audio/성공.wav");

                     ////////////끝나고 점수 계산/////////////////
                     int end=0;  // 패 몇개 맞췄는지 확인하기
 					 for(int i=0; i<24; i++){		 // 패 몇개 맞췄는지 확인하기
 						if((card[i].getName()).equals("Check"))
 							end=end+1;
 						if (end==24){// 24개 전부 맞았는지 체크
 							alert("audio/clearGame.wav");
 							isClear=1;//  타이머 정지
 							score = score + sec*10;
 							//축하 다이얼로그 띄움 + 점수
 							clip.stop();
 							clearGame();

 							if(sec<0){  // 60초 넘도록 못 풀면
 								sec=0;
 							}

 							labelScore.setText("점수 :  "+score+" 점");
 							break;
                           }

                     }

                  }
                  else {
                     timerCardCheck = new Timer();
                     timerCardCheck.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                           pressedCard = 0;
                           firstCard.setIcon(imageBack);
                           secondCard.setIcon(imageBack);
                           timerCardCheck.cancel();
                           alert("audio/beep.wav");
                        }
                     }, 300, 1);
                  }
               } //
            }

         }



         @Override
         public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

         }

         @Override
         public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

         }

         @Override
         public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

         }

      }

      public void action() {
    	 startCheck=1;
         sec = 60;
         score=0;
         labelTimer.setText("  "+sec+" 초"); // 타이머 60초로 초기화



         timerMix.cancel();
         timerMix = new Timer();

         timerHide.cancel();
         timerHide = new Timer();
         timerHide.scheduleAtFixedRate(new TimerTask() { // 패 5 초 보여주고 뒤집기
             public void run() {
                hideButtonImage(); // 카드 뒷 면
                timerHide.cancel();

             }
          }, 6000, 1);


          alert("audio/Timer.wav");
      }

      public void setImage(){            // 이미지객체에 그림 가져오기
         imageBack = new ImageIcon(getClass().getClassLoader().getResource("resources/back.jpg"));
         for ( int i = 0; i<24; i++){
             imageIcon[i] = new ImageIcon(getClass().getClassLoader().getResource("resources/" + cardrandom[i] + ".jpg"));

         }
      }

      public void setButtonResetImage(){      // 버튼에 리셋된 이미지 재설정하기

         for ( int i = 0; i<24; i++){
             card[i]=new JButton(imageIcon[i]);
             this.add(card[i]);//프레임에 버튼 추가
             card[i].setVisible(true);//보이게
             card[i].setSize(120,120);
             }
      }



      public void hideButtonImage(){      // 버튼 그림 뒷면 보이기
         for ( int i = 0; i<24; i++){
             card[i].setIcon(imageBack);
         }
      }

      public void setButtonName(){      // 버튼에 이름주기

         for ( int i = 0; i<24; i++){
             card[i].setName(Integer.toString(i+1));
         }
      }

      public void mixNumber(){         // 카드 섞기

         int i=0;
         int rand=0;
         while(true){

            rand =  (int)(Math.random()*24+1);
            cardrandom[i] =rand;

        aa : for(int j=0; j<24; j++){
               if(j==i)
                  break aa ;
               if(cardrandom[j]==rand){
                  i=i-1 ;
                  }
            }i=i+1;
            if(i==-1)i=0;
            if(i==24)break;
         }

      }




   }
   class MyLabel extends JLabel {
      private int barSize = 60; // 현재 그려져야할 바의 크기
      private int maxBarSize; // 바의 최대 크기

      public MyLabel(int maxBarSize) {
         this.maxBarSize = maxBarSize;
      }

      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setColor(Color.BLACK);
         int width = (int) (((double)(this.getWidth()))/maxBarSize*barSize);
         if(width==0) return;
         g.fillRect(0, 0, width, this.getHeight());
         if(barSize<=10) { // 10초 남으면 바 색깔 빨간색으로 바꿈
        	 g.setColor(Color.RED);
        	 g.fillRect(0, 0, width, this.getHeight());


        	 }

      }

      synchronized public void fill() {
         if(barSize == maxBarSize) {
            try {
               wait(); // 바의 크기가 줄어들때까지 대기
            } catch (InterruptedException e) { return; }
         }
         barSize++;
         repaint();
         notify(); // ConsumerThread 스레드 깨우기
      }
      synchronized public void consume() {
         if(barSize == 0) {
            try {
               wait();
            } catch (InterruptedException e) { return; }
         }
         barSize--;
         repaint();
         notify(); // 이벤트 스레드 깨우기
         if(barSize >= 0 && barSize <=9)
        	 alert("audio/NoTime.wav");
      }
   }

   class ConsumerThread extends Thread {
      private MyLabel bar;

      public ConsumerThread(MyLabel bar) {
         this.bar = bar;
      }

      @Override
      public void run() {
         while(true) {
            try {
               sleep(1000);
               bar.consume(); // 1초마다 바를 1씩 줄인다.
            } catch (InterruptedException e) { return; }
            if(isClear==1)
            	break;
         }
      }
   }

	void dialogResult(){	// 다이얼로그 보여주기

		alert("audio/NoClearGame.wav");
		int result = JOptionPane.showConfirmDialog(null,
				"Time Over. 점수는 " + score + " 점. 메인메뉴로 돌아갈까요?", "결과", JOptionPane.ERROR_MESSAGE);

		      if(result == JOptionPane.CLOSED_OPTION) { // 그냥 닫았을 때
		    	  return;
		      	}
			  if(result == JOptionPane.YES_OPTION) { /// YES 메인화면으로 돌아감
				  new MainFrame();
				  setVisible(false);
				  isClear=1;

			  }
			  else {
				 // NO 다시 시작
				  System.exit(0); // 게임 아예 종료
			  }

		}

	void clearGame() {
		int result = JOptionPane.showConfirmDialog(null,
				"축하합니다! 점수는 " + score + " 점. 메인메뉴로 돌아갈까요?", "결과", JOptionPane.ERROR_MESSAGE);

		      if(result == JOptionPane.CLOSED_OPTION) { // 그냥 닫았을 때
		    	  return;
		      	}
			  if(result == JOptionPane.YES_OPTION) { /// YES 메인화면으로 돌아감
				  new MainFrame();
				  setVisible(false);
				  isClear=1;
			  }
			  else {

				  System.exit(0); // 게임 아예 종료
			  }

	}

//	public void fileRead() { //이름 저장된 파일 읽어오기
//		BufferedReader in = null;
//		 try {
//		 in = new BufferedReader(
//		 new FileReader("resources/outputName.txt"));
//		 String s;
//		 while ((s = in.readLine()) != null) {
//		 la_name.setText(s);
//		 }
//		 in.close();
//		 } catch (IOException e) {
//		System.out.println("입출력 오류");
//		 }
//	}

	public void alert(String path) { //효과음 삽입
		try
		{
		AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
		Clip clip = AudioSystem.getClip();
		clip.stop();
		clip.open(ais);
		clip.start();
		}
		catch(Exception ex)
		{
		}
	}


   public static void main(String[] args) {
      new GameFrame();

      }

}