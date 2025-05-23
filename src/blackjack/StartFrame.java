package blackjack;

import javax.swing.*;

import blackjack.BlackjackGUI.BackgroundPanel;

import java.awt.*;

public class StartFrame extends JFrame {

    public StartFrame() {
        setTitle("21點遊戲 - 開始介面");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //當介面右上角的「關閉（X）」被按下時關閉介面並結束應用程式
        setLocationRelativeTo(null); //讓視窗在螢幕上置中顯示
        setContentPane(new BackgroundPanel("/background/start_back.png")); //設置背景圖

        JButton startButton = new JButton("Start"); //建立一個按鈕，標示為"Start"
        startButton.setFont(new Font("Arial", Font.BOLD, 24)); //設定按鈕文字字型
        startButton.setFocusPainted(false); //取消按鈕在被點選或取得鍵盤焦點時周圍的預設虛線框，讓按鈕看起來更乾淨
        
        
        
        startButton.addActionListener(e -> { //當"Start"按鈕被點擊時觸發
            BlackjackGUI game = new BlackjackGUI(); //建立新的BlackjackGUI遊戲視窗物件。
            game.setVisible(true); //顯示遊戲視窗
            this.dispose(); //關閉目前這個起始視窗
        });

        setLayout(new BorderLayout()); //將介面區分為五個區塊(上北,下南,左西,右東,中間)的版面配置方式
        
        JPanel startPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));//建立起始按鈕的區域，版面配置為FlowLayout，且對齊方式是置中。
        startPanel.setOpaque(false); //將區域設為透明，避免擋到背景
        startPanel.add(startButton); //把起始按鈕放在起始按鈕的區域中

        add(startPanel, BorderLayout.SOUTH); //把起始按鈕的區域放在介面的下(南)方
    }
    
    class BackgroundPanel extends JPanel {
        private final Image backgroundImage; //用於儲存要顯示的背景圖片

        public BackgroundPanel(String imagePath) { //接收圖片路徑，並從資源目錄載入圖片，存入backgroundImage
            backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) { //將背景圖片繪製到整個面板範圍，這樣無論面板大小如何變化，背景圖片都會自動縮放填滿。
            super.paintComponent(g); //確保面板原本的背景等基本繪製先完成
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); //把背景圖片繪製在面板整個區域 (從 0,0 到面板寬高)
        }
    }
    
    public static void main(String[] args) { //用於啟動整個程式
        SwingUtilities.invokeLater(() -> new StartFrame().setVisible(true)); //前半段保證程式在「正確的執行緒(EDT)」執行，後半段建立並顯示介面
    }
}
