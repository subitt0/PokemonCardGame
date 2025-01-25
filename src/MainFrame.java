import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class MainFrame extends JFrame {
   private JButton startbtn, howbtn, endbtn, button;
   private JScrollPane scrollPanel;
   private JLabel on;
   private JTextField txtfield;
   private String result;

   private class MainPanel extends JPanel {
      private Image imgMain;

      public MainPanel() {
         imgMain = new ImageIcon(getClass().getClassLoader().getResource("resources/mainPicture.jpg")).getImage();
      }

      @Override
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.drawImage(imgMain, 0, 0, getWidth(), getHeight(), null);
      }
   }

   public MainFrame() {
      MainPanel mainPanel = new MainPanel();
      setContentPane(mainPanel);
      mainPanel.setLayout(null);

      setTitle("카드 게임");
      setLocation(0, 0);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setSize(1920, 1080);
      setVisible(true);

      startbtn = new JButton(loadImage("resources/start.jpg"));
      startbtn.setSize(160, 50);
      startbtn.setLocation(20, 600);
      startbtn.addActionListener(e -> {
         new GameFrame();
         setVisible(false);
      });
      mainPanel.add(startbtn);

      howbtn = new JButton(loadImage("resources/how.jpg"));
      howbtn.setSize(160, 50);
      howbtn.setLocation(20, 660);
      howbtn.addActionListener(e -> new HowFrame());
      mainPanel.add(howbtn);

      endbtn = new JButton(loadImage("resources/exit.jpg"));
      endbtn.setSize(160, 50);
      endbtn.setLocation(20, 720);
      endbtn.addActionListener(e -> System.exit(0));
      mainPanel.add(endbtn);

//      button = new JButton(loadImage("resources/피카츄.jpg"));
//      button.setSize(50, 50);
//      button.setLocation(130, 540);
//      mainPanel.add(button);

//      txtfield = new JTextField();
//      scrollPanel = new JScrollPane(txtfield);
//      scrollPanel.setSize(100, 50);
//      scrollPanel.setLocation(20, 540);
//      mainPanel.add(scrollPanel);

      button.addActionListener(e -> {
         result = txtfield.getText();
         on.setText(result);
         try (OutputStream output = new FileOutputStream("resources/outputName.txt")) {
            output.write(result.getBytes());
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      });
   }

   private ImageIcon loadImage(String path) {
      return new ImageIcon(getClass().getClassLoader().getResource(path));
   }

   public static void main(String[] args) {
      new MainFrame();
   }
}
