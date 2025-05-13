package blackjack;

public class Card {
    private final String suit;
    private final String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }
    
    public int getValue() {
        return switch (rank) {
            case "A" -> 11;
            case "K", "Q", "J" -> 10;
            default -> Integer.parseInt(rank);
        };
    }

    public String getImagePath() {
        // 這裡是關鍵：將 J/Q/K/A 和數字做對應
        String rankFormatted = switch (rank) {
            case "J" -> "jack";
            case "Q" -> "queen";
            case "K" -> "king";
            case "A" -> "ace";
            default -> rank; // "2" 到 "10" 不變
        };

        return "/cards/" + rankFormatted.toLowerCase() + "_of_" + suit.toLowerCase() + ".png";
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}