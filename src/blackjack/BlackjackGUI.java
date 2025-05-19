package blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.net.URL;

public class BlackjackGUI extends JFrame {
    private final GameManager gameManager = new GameManager(); //用來調動GameManager.java裡的程式(發牌，計算勝負等)

    private final JPanel playerPanel = new JPanel(); //玩家的卡片區域
    private final JPanel dealerPanel = new JPanel(); //莊家的卡片區域
    private final JLabel statusLabel = new JLabel("Welcome to Blackjack!"); //當前遊戲狀態的提示訊息區域(不知道為什麼沒顯示)
    private final JLabel scoreLabel = new JLabel("Wins: 0  Losses: 0"); //目前累積的勝負次數
    private final JLabel playerScoreLabel = new JLabel(); //玩家的當前手牌總點數
    private final JLabel dealerScoreLabel = new JLabel(); //莊家的當前手牌總點數
    
    private final JButton hitButton = new JButton("Hit"); //建立一個按鈕，標示為 "Hit"
    private final JButton standButton = new JButton("Stand"); //建立一個按鈕，標示為 "Stand"

    public BlackjackGUI() { //建立並設定介面
        setTitle("Blackjack 21 點遊戲"); //介面標題
        setSize(800, 600); //介面大小
        setDefaultCloseOperation(EXIT_ON_CLOSE); //當介面右上角的「關閉（X）」被按下時關閉介面並結束應用程式
        setLayout(new BorderLayout()); //將介面區分為五個區塊(上北,下南,左西,右東,中間)的版面配置方式
        
        JPanel dealerContainer = new JPanel(new BorderLayout()); //設置莊家區域
        dealerContainer.setBorder(BorderFactory.createTitledBorder("Dealer")); //在莊家區域加上一個寫著Dealer的邊框 
        dealerContainer.add(dealerScoreLabel, BorderLayout.NORTH); //把莊家的分數區域放在莊家區域的上(北)方
        dealerContainer.add(dealerPanel, BorderLayout.CENTER); //把莊家的卡片區域放在莊家區域的中間
        add(dealerContainer, BorderLayout.NORTH); //把莊家區域放在介面的上(北)方

        JPanel playerContainer = new JPanel(new BorderLayout()); //設置玩家區域
        playerContainer.setBorder(BorderFactory.createTitledBorder("You")); //在玩家區域加上一個寫著You的邊框
        playerContainer.add(playerScoreLabel, BorderLayout.NORTH); //把玩家的分數區域放在介面的上(北)方
        playerContainer.add(playerPanel, BorderLayout.CENTER); //把玩家的卡片區域放在介面的中間
        add(playerContainer, BorderLayout.CENTER); //把玩家區域放在介面的中間

        JPanel controlPanel = new JPanel(); //建立控制面板的區域(放按鈕和勝負次數)
        JButton newGameButton = new JButton("New Game"); //建立一個按鈕，標示為 "New Game"

        controlPanel.add(hitButton); //把各個按鈕和勝負次數放進控制面板的區域
        controlPanel.add(standButton);
        controlPanel.add(newGameButton);
        controlPanel.add(scoreLabel);

        add(statusLabel, BorderLayout.SOUTH); //把當前遊戲狀態的提示訊息區域放在介面的下(南)方(不知道為什麼沒顯示)
        add(controlPanel, BorderLayout.PAGE_END); //把控制面板的區域放在介面的下方

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

        addCardsToPanel(dealerPanel, gameManager.getDealerHand(), gameManager.isDealerRevealed()); //把當前莊家持有的卡牌圖加到莊家的卡片區域中(莊家需確認是否已開牌) 
        addCardsToPanel(playerPanel, gameManager.getPlayerHand(), true); //把當前玩家持有的卡牌圖加到玩家的卡片區域中(玩家默認開牌)
        
        playerScoreLabel.setText("Total: " + gameManager.getPlayerHandTotal()); //更新玩家目前手牌的總點數
        dealerScoreLabel.setText(
            gameManager.isDealerRevealed() //如果莊家還沒亮牌就只顯示 "?"，如果莊家亮牌了，才顯示實際點數
                ? "Total: " + gameManager.getDealerHandTotal()
                : "Total: ?"
        );
        
        statusLabel.setText(gameManager.getStatusMessage()); //更新當前遊戲狀態的提示訊息
        scoreLabel.setText("Wins: " + gameManager.getWins() + "  Losses: " + gameManager.getLosses()); //更新勝負次數
        
        //根據遊戲狀態啟用或停用按鈕
        boolean gameOngoing = !gameManager.isDealerRevealed(); //藉由莊家是否亮牌來判斷遊戲是否還在進行中
        hitButton.setEnabled(gameOngoing); //如果gameOngoing = true，則按鈕可以點擊，如果gameOngoing = false，則按鈕會變成灰色，不可點擊
        standButton.setEnabled(gameOngoing);
        
        revalidate(); //更新位置與大小等佈局資訊（處理排版）
        repaint(); //強制重繪畫面（處理外觀）
    }

    private void addCardsToPanel(JPanel panel, List<Card> hand, boolean revealAll) { 
    	//用於將卡牌圖像加入到畫面上的目標卡片區域，參數分別為要加牌的目標卡片區域、要加的牌、是否要顯示牌的正面(如果是莊家未亮牌，會顯示背面)
    	
        for (int i = 0; i < hand.size(); i++) { //用for迴圈遍歷整副手牌
            Card card = hand.get(i); //取出第i張手牌
            String path = revealAll ? card.getImagePath() : "/cards/back.png"; //判斷要顯示牌的正面或背面圖
            
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