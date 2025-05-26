package blackjack;

import java.util.*;

public class GameManager {
    private Deck deck = new Deck(); //調用Deck.java，建立牌堆
    private Player player = new Player(); //調用Player.java，建立玩家和莊家
    private Player dealer = new Player();
    
    private int playerChips = 6; //紀錄玩家籌碼
    private int dealerChips = 6; //紀錄莊家籌碼
    private int currentBet = 0; //紀錄當局押注籌碼數量
    private boolean dealerRevealed = false;//紀錄莊家的牌是否已經翻開（亮牌）
    private String statusMessage = ""; //儲存當前遊戲的狀態提示

    public void startNewGame() { //開始新的一局遊戲
        deck.reset(); //重新產生一副完整的52張牌並洗牌
        player.clearHand(); //清空玩家與莊家的所有手牌
        dealer.clearHand();
        dealerRevealed = false; //將莊家的牌改為尚未翻開

        player.receiveCard(deck.drawCard()); //雙方一開始抽兩張牌，用drawCard()從牌堆頂抽牌，用receiveCard()加到雙方手牌 
        player.receiveCard(deck.drawCard());

        dealer.receiveCard(deck.drawCard());
        dealer.receiveCard(deck.drawCard());

        statusMessage = "玩家的操作時間"; //修改當前遊戲的狀態提示
        
     // 每次開始新的一局時重設押注
        this.currentBet = 0;
    }
    
    //玩家選擇押注籌碼，範圍 1-3
    public void setCurrentBet(int betAmount) {
        if (betAmount >= 1 && betAmount <= 3) {
            currentBet = betAmount;
        } else {
            throw new IllegalArgumentException("Bet must be between 1 and 3");
        }
    }
    
    public void playerHits() { //玩家要求抽一張牌
        player.receiveCard(deck.drawCard()); //玩家抽一張牌
        if (player.getTotal() > 21) { //計算玩家手牌總點數，如果超過21點就是爆牌
            dealerRevealed = true;
            statusMessage = "玩家爆牌! 莊家獲勝";
            playerChips -= currentBet;
            dealerChips += currentBet;
        }
    }

    public void playerStands() { //玩家選擇停牌
        dealerRevealed = true; //莊家的牌要翻開
        while (dealer.getTotal() < 17) { //如果莊家的總點數小於17，就繼續抽牌
            dealer.receiveCard(deck.drawCard());
        }

        int playerTotal = player.getTotal(); //取得雙方手牌總點數
        int dealerTotal = dealer.getTotal();

        if (dealerTotal > 21) { //若莊家爆牌，則玩家獲勝
            statusMessage = "莊家爆牌!玩家獲勝";
            playerChips += currentBet;
            dealerChips -= currentBet;
        }else if (playerTotal > dealerTotal) { //若玩家點數較高，則玩家獲勝
            statusMessage = "玩家獲勝";
            playerChips += currentBet;
            dealerChips -= currentBet;
        }else if (dealerTotal == playerTotal) { //若雙方點數相同，則平手
            statusMessage = "平手";
        } else { //其他情況(莊家點數較高且沒爆牌)，則莊家勝利
            statusMessage = "莊家獲勝";
            playerChips -= currentBet;
            dealerChips += currentBet;
        }
    }
    
    public boolean isGameOver() { //偵測任意一方是否籌碼歸零
        return playerChips == 0 || dealerChips == 0;
    }
    
    public String getGameOverMessage() { //玩家和莊家的勝利訊息
        if (playerChips == 0) return "遊戲結束! 莊家贏得了所有的籌碼!";
        if (dealerChips == 0) return "恭喜! 你贏得了所有的籌碼!";
        return "";
    }
    
    public void resetChips() { //重置雙方籌碼
        playerChips = 6;
        dealerChips = 6;
    }
    
    //把GameManager裡的重要狀態資訊提供給GUI顯示或其他類別讀取
    public int getPlayerChips() { return playerChips; } //傳回玩家的籌碼數
    public int getDealerChips() { return dealerChips; } //傳回莊家的籌碼數
    public int getCurrentBet() { return currentBet; } //傳回當局押注籌碼數量
    public int getPlayerHandTotal() {return player.getTotal();} //傳回玩家的手牌總點數
    public int getDealerHandTotal() {return dealer.getTotal();} //傳回莊家的手牌總點數
    public List<Card> getPlayerHand() { return player.getHand(); } //傳回玩家的手牌
    public List<Card> getDealerHand() { return dealer.getHand(); } //傳回莊家的手牌
    public boolean isDealerRevealed() { return dealerRevealed; } //傳回莊家的牌是否「亮牌」
    public String getStatusMessage() { return statusMessage; } //傳回目前的提示訊息
}