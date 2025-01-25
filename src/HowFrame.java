import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HowFrame extends JFrame {
	ImageIcon backgroundImg = new ImageIcon("resources/다운로드.jpg");
	
	
	
	public static void main(String[] args) {
		new HowFrame(); 
	}
	
	
	public HowFrame() {
		setSize(600, 500);
		setLocation(350, 50);
		setVisible(true);
		
		
		JPanel howpanel = new JPanel();
		howpanel.setBackground(Color.white);
		add(howpanel);
		
		JLabel howlabel = new JLabel();
		howlabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		howlabel.setForeground(Color.darkGray);
		howlabel.setText(
				"<html> <h1>게임 방법</h1><hr>"
				+ "시작 버튼을 누르면 모든 카드가 섞인후 5초 뒤에 뒤집힙니다."
				+ "<br>60초 동안 24장 모든 카드의 짝을 맞춰주세요."
				+ "<br>카드 한 쌍을 맞출 때마다 10점이 추가됩니다."
				+ "<br>모든 카드를 맞췄을 때 '남은 시간 x 10' 만큼의 추가 보너스 점수가 주어집니다.</html>"
				);
		howpanel.add(howlabel);
		setVisible(true);
		
	}
	
	
}
