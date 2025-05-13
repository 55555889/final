package blackjack;

import java.util.*;

public class Player {
    private final List<Card> hand = new ArrayList<>();

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getTotal() {
        int total = 0;
        int aceCount = 0;
        for (Card card : hand) {
            total += card.getValue();
            if (card.getValue() == 11) aceCount++;
        }
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    public void clearHand() {
        hand.clear();
    }
}