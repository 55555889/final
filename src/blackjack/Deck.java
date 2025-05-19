package blackjack;

import java.util.*;

public class Deck {
    private final List<Card> cards = new ArrayList<>(); //建立一個可儲存多張撲克牌的清單

    public Deck() {
        reset();
    }

    public void reset() { //用來重建並洗牌整副撲克牌
        cards.clear(); //清空原本的牌
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        for (String suit : suits) { //建立雙層迴圈，把52張牌加入cards清單
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(cards); //將cards清單中的牌順序隨機打亂
    }

    public Card drawCard() { //抽牌
        return cards.remove(0); //從cards清單中移除並傳回第一張牌
    }
}