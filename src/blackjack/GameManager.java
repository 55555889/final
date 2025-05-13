package blackjack;

import java.util.*;

public class GameManager {
    private Deck deck = new Deck();
    private Player player = new Player();
    private Player dealer = new Player();

    private int wins = 0;
    private int losses = 0;
    private boolean dealerRevealed = false;
    private String statusMessage = "";

    public void startNewGame() {
        deck.reset();
        player.clearHand();
        dealer.clearHand();
        dealerRevealed = false;

        player.receiveCard(deck.drawCard());
        player.receiveCard(deck.drawCard());

        dealer.receiveCard(deck.drawCard());
        dealer.receiveCard(deck.drawCard());

        statusMessage = "Your move!";
    }

    public void playerHits() {
        player.receiveCard(deck.drawCard());
        if (player.getTotal() > 21) {
            dealerRevealed = true;
            statusMessage = "You bust! Dealer wins.";
            losses++;
        }
    }

    public void playerStands() {
        dealerRevealed = true;
        while (dealer.getTotal() < 17) {
            dealer.receiveCard(deck.drawCard());
        }

        int playerTotal = player.getTotal();
        int dealerTotal = dealer.getTotal();

        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            wins++;
            statusMessage = "You win!";
        } else if (dealerTotal == playerTotal) {
            statusMessage = "Push (tie)";
        } else {
            losses++;
            statusMessage = "Dealer wins.";
        }
    }

    public List<Card> getPlayerHand() { return player.getHand(); }
    public List<Card> getDealerHand() { return dealer.getHand(); }
    public boolean isDealerRevealed() { return dealerRevealed; }
    public String getStatusMessage() { return statusMessage; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
}