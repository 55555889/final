package blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.net.URL;

public class BlackjackGUI extends JFrame {
    private final GameManager gameManager = new GameManager(); //用來調動GameManager.java裡的程式(發牌，計算勝負等)

    private final JPanel playerPanel = new JPanel(); //玩家的卡片區域
    private final JPanel dealerPanel = new JPanel(); //莊家的卡片區域
    private final JLabel statusLabel = new JLabel("Welcome to Blackjack!"); //當前遊戲狀態的提示訊息區域
    private final JLabel scoreLabel = new JLabel("Wins: 0  Losses: 0"); //目前累積的勝負次數
    private final JLabel playerScoreLabel = new JLabel(); //玩家的當前手牌總點數
    private final JLabel dealerScoreLabel = new JLabel(); //莊家的當前手牌總點數
    
    private final JButton hitButton = new JButton("Hit"); //建立一個按鈕，標示為 "Hit"
    private final JButton standButton = new JButton("Stand"); //建立一個按鈕，標示為 "Stand"

    public BlackjackGUI() { //建立並設定介面
        setTitle("Blackjack 21 點遊戲"); //介面標題
        setSize(800, 600); //介面大小
        setDefaultCloseOperation(EXIT_ON_CLOSE); //當介面右上角的「關閉（X）」被按下時關閉介面並結束應用程式
        setLocationRelativeTo(null); //讓視窗在螢幕上置中顯示
        setContentPane(new BackgroundPanel("/background/desk_back.jpg")); //設置背景圖
        setLayout(new BorderLayout()); //將介面區分為五個區塊(上北,下南,左西,右東,中間)的版面配置方式
        
        JPanel dealerContainer = new JPanel(new BorderLayout()); //設置莊家區域
        javax.swing.border.TitledBorder dealerBorder = BorderFactory.createTitledBorder("Dealer"); //建立一個寫著Dealer的標題邊框
        dealerBorder.setTitleColor(Color.WHITE); //將標題邊框的標題顏色變成白色
        dealerBorder.setBorder(BorderFactory.createLineBorder(Color.WHITE));  //將標題邊框的邊框顏色變成白色
        dealerContainer.setBorder(dealerBorder); //將標題邊框加入莊家區域
        dealerContainer.add(dealerScoreLabel, BorderLayout.NORTH); //把莊家的分數區域放在莊家區域的上(北)方
        dealerContainer.add(dealerPanel, BorderLayout.CENTER); //把莊家的卡片區域放在莊家區域的中間
        add(dealerContainer, BorderLayout.NORTH); //把莊家區域放在介面的上(北)方
        dealerPanel.setOpaque(false); //將區域設為透明，避免擋到背景
        dealerContainer.setOpaque(false);
        
        dealerScoreLabel.setForeground(Color.WHITE); //把莊家的分數區域文字顏色變成白色

        JPanel playerContainer = new JPanel(new BorderLayout()); //設置玩家區域
        javax.swing.border.TitledBorder playerBorder = BorderFactory.createTitledBorder("You"); //建立一個寫著You的標題邊框
        playerBorder.setTitleColor(Color.WHITE); //將標題邊框的標題顏色變成白色
        playerBorder.setBorder(BorderFactory.createLineBorder(Color.WHITE)); //將標題邊框的邊框顏色變成白色
        playerContainer.setBorder(playerBorder);  //將標題邊框加入玩家區域
        playerContainer.add(playerScoreLabel, BorderLayout.NORTH); //把玩家的分數區域放在介面的上(北)方
        playerContainer.add(playerPanel, BorderLayout.CENTER); //把玩家的卡片區域放在介面的中間
        add(playerContainer, BorderLayout.CENTER); //把玩家區域放在介面的中間
        playerPanel.setOpaque(false); //將區域設為透明，避免擋到背景
        playerContainer.setOpaque(false);
        
        playerScoreLabel.setForeground(Color.WHITE); //把玩家的分數區域文字顏色變成白色
        
        JPanel controlPanel = new JPanel(); //建立控制面板的區域(放按鈕和勝負次數)
        JButton newGameButton = new JButton("New Game"); //建立一個按鈕，標示為 "New Game"

        controlPanel.add(hitButton); //把各個按鈕和勝負次數放進控制面板的區域
        controlPanel.add(standButton);
        controlPanel.add(newGameButton);
        controlPanel.add(scoreLabel);
        
        hitButton.setFocusPainted(false); //取消按鈕在被點選或取得鍵盤焦點時周圍的預設虛線框，讓按鈕看起來更乾淨
        standButton.setFocusPainted(false);
        newGameButton.setFocusPainted(false);
        
        scoreLabel.setForeground(Color.WHITE);  //把勝負次數文字顏色變成白色
        
        JPanel bottomPanel = new JPanel(new BorderLayout()); //設置底部區域
        bottomPanel.add(controlPanel, BorderLayout.CENTER); //把控制面板的區域放在底部區域的中間
        bottomPanel.add(statusLabel, BorderLayout.SOUTH); //把當前遊戲狀態的提示訊息區域放在底部區域的下(南)方
        add(bottomPanel, BorderLayout.SOUTH); //把底部區域放在介面的下(南)方
        controlPanel.setOpaque(false); //將區域設為透明，避免擋到背景
        bottomPanel.setOpaque(false);
        
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER); //讓文字在水平方向置中
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16)); //設定字型為Arial,字體樣式為粗體,字體大小為16pt
        
        hitButton.addActionListener(e -> { //當"Hit"按鈕被按下時
            gameManager.playerHits(); //執行GameManager.java裡的playerHits()
            updateUI(); //刷新介面
        });

        standButton.addActionListener(e -> { //當"Stand"按鈕被按下時
            gameManager.playerStands(); //執行GameManager.java裡的playerStands()
            updateUI(); //刷新介面
        });

        newGameButton.addActionListener(e -> { //當"New Game"按鈕被按下時
            gameManager.startNewGame(); //執行GameManager.java裡的startNewGame()
            updateUI(); //刷新介面
        });
        
        //剛開始要初始化
        gameManager.startNewGame(); //執行GameManager.java裡的startNewGame()
        updateUI(); //刷新介面
    }

    private void updateUI() { //刷新介面功能的詳細步驟
        dealerPanel.removeAll(); //清空莊家的卡片區域
        playerPanel.removeAll(); //清空玩家的卡片區域
        
     
        boolean showOnlyOneCard = !gameManager.isDealerRevealed();
        
        //把當前莊家持有的卡牌圖加到莊家的卡片區域中(莊家需確認是否已開牌)
        addCardsToPanel(dealerPanel, gameManager.getDealerHand(), !showOnlyOneCard);
        
        //把當前玩家持有的卡牌圖加到玩家的卡片區域中(玩家默認開牌)
        addCardsToPanel(playerPanel, gameManager.getPlayerHand(), true);
              
        playerScoreLabel.setText("Total: " + gameManager.getPlayerHandTotal()); //更新玩家目前手牌的總點數
        dealerScoreLabel.setText(
            gameManager.isDealerRevealed() //如果莊家還沒亮牌就只顯示 "?"，如果莊家亮牌了，才顯示實際點數
                ? "Total: " + gameManager.getDealerHandTotal()
                : "Total: ?"
        );
        
        //更新當前遊戲狀態的提示訊息
        String message = gameManager.getStatusMessage();
        statusLabel.setText(message);

        //根據訊息自動變色
        if (message.contains("You win")) {
            statusLabel.setForeground(Color.GREEN); //當訊息中有"You win"時，文字顏色變為綠色
        } else if (message.contains("Dealer wins")) { //當訊息中有"Dealer wins"時，文字顏色變為紅色
            statusLabel.setForeground(Color.RED);
        } else if (message.contains("Push")) { //當訊息中有"Push"時，文字顏色變為紅色
            statusLabel.setForeground(Color.GRAY);
        } else {
            statusLabel.setForeground(Color.WHITE); //預設顏色為白色
        }
        
        scoreLabel.setText("Wins: " + gameManager.getWins() + "  Losses: " + gameManager.getLosses()); //更新勝負次數
        
        //根據遊戲狀態啟用或停用按鈕
        boolean gameOngoing = !gameManager.isDealerRevealed(); //藉由莊家是否亮牌來判斷遊戲是否還在進行中
        hitButton.setEnabled(gameOngoing); //如果gameOngoing = true，則按鈕可以點擊，如果gameOngoing = false，則按鈕會變成灰色，不可點擊
        standButton.setEnabled(gameOngoing);
        
        revalidate(); //更新位置與大小等佈局資訊（處理排版）
        repaint(); //強制重繪畫面（處理外觀）
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
    
    private void addCardsToPanel(JPanel panel, List<Card> hand, boolean revealAll) { 
    	//用於將卡牌圖像加入到畫面上的目標卡片區域，參數分別為要加牌的目標卡片區域、要加的牌、是否要顯示牌的正面(如果是莊家未亮牌，會顯示背面)
    	panel.removeAll();
    	
        for (int i = 0; i < hand.size(); i++) { //用for迴圈遍歷整副手牌
            Card card = hand.get(i); //取出第i張手牌
            String path;
            if (revealAll) { //確認是否要亮出全部手牌(莊家有一張暗牌)
                path = card.getImagePath();
            } else {
                if (i == 1) { // 莊家未開牌前只亮第二張牌，其他牌顯示背面
                    path = card.getImagePath();
                } else {
                    path = "/cards/back.png";
                }
            }
            
            URL imgURL = getClass().getResource(path); //從資源資料夾中載入圖片URL
            if (imgURL == null) { //如果找不到圖片，就跳過這張牌，並在錯誤輸出提示
                System.err.println("⚠️ 無法找到圖片：" + path);
                continue; 
            }
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(path)); //從資源路徑載入圖片，建立一個原始大小的ImageIcon(用於顯示的類別)
            Image scaledImage = originalIcon.getImage().getScaledInstance(70, 100, Image.SCALE_SMOOTH); //把ImageIcon轉成Image(用於處理圖像的類別)，並縮放圖片到70x100像素，使用平滑演算法(SCALE_SMOOTH)來保持圖片品質 
            ImageIcon scaledIcon = new ImageIcon(scaledImage); //把Image轉回ImageIcon
            JLabel cardLabel = new JLabel(scaledIcon); //將圖片包裝進一個JLabel
            panel.add(cardLabel); //把這張圖加到要加牌的目標卡片區域
        }
    }

    public static void main(String[] args) { //用於啟動整個程式
        SwingUtilities.invokeLater(() -> new BlackjackGUI().setVisible(true)); //前半段保證程式在「正確的執行緒(EDT)」執行，後半段建立並顯示介面
    }
}
