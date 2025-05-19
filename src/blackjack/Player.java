package blackjack;

import java.util.*;

public class Player {
    private final List<Card> hand = new ArrayList<>(); //建立玩家或莊家目前的手牌清單

    public void receiveCard(Card card) { //把牌加入手牌
        hand.add(card);
    }

    public List<Card> getHand() { //傳回目前的手牌
        return hand;
    }

    public int getTotal() { //計算目前手牌的總點數
        int total = 0;
        int aceCount = 0;
        for (Card card : hand) { //對手上的每一張牌
            total += card.getValue(); //累加點數
            if (card.getValue() == 11) aceCount++; //如果該牌是A，aceCount加1
        }
        while (total > 21 && aceCount > 0) { //如果總點數>21，且aceCount>1，則將一張A的點數從11改為1，重複這個調整直到總點數≤21或沒有A可改 
            total -= 10;
            aceCount--;
        }
        return total; //傳回總點數
    }

    public void clearHand() { //清空所有手牌
        hand.clear();
    }
}