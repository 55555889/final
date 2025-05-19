package blackjack;

import java.util.*;

public class GameManager {
    private Deck deck = new Deck(); //調用Deck.java，建立牌堆
    private Player player = new Player(); //調用Player.java，建立玩家和莊家
    private Player dealer = new Player();

    private int wins = 0; //紀錄玩家目前的勝場與敗場
    private int losses = 0; 
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

        statusMessage = "Your move!"; //修改當前遊戲的狀態提示
    }

    public void playerHits() { //玩家要求抽一張牌
        player.receiveCard(deck.drawCard()); //玩家抽一張牌
        if (player.getTotal() > 21) { //計算玩家手牌總點數，如果超過21點就是爆牌
            dealerRevealed = true;
            statusMessage = "You bust! Dealer wins.";
            losses++;
        }
    }

    public void playerStands() { //玩家選擇停牌
        dealerRevealed = true; //莊家的牌要翻開
        while (dealer.getTotal() < 17) { //如果莊家的總點數小於17，就繼續抽牌
            dealer.receiveCard(deck.drawCard());
        }

        int playerTotal = player.getTotal(); //取得雙方手牌總點數
        int dealerTotal = dealer.getTotal();

        if (dealerTotal > 21 || playerTotal > dealerTotal) { //若莊家爆牌，或玩家點數較高，則玩家獲勝
            wins++;
            statusMessage = "You win!";
        } else if (dealerTotal == playerTotal) { //若雙方點數相同，則平手
            statusMessage = "Push (tie)";
        } else { //其他情況(莊家點數較高且沒爆牌)，則莊家勝利
            losses++;
            statusMessage = "Dealer wins.";
        }
    }
    
    //把GameManager裡的重要狀態資訊提供給GUI顯示或其他類別讀取
    public List<Card> getPlayerHand() { return player.getHand(); } //傳回玩家的手牌
    public List<Card> getDealerHand() { return dealer.getHand(); } //傳回莊家的手牌
    public boolean isDealerRevealed() { return dealerRevealed; } //傳回莊家的牌是否「亮牌」
    public String getStatusMessage() { return statusMessage; } //傳回目前的提示訊息
    public int getWins() { return wins; } //傳回玩家累計勝場數
    public int getLosses() { return losses; } //傳回玩家累計敗場數
}