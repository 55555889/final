package blackjack;

public class Card {
    private final String suit; //撲克牌花色
    private final String rank; //撲克牌數字

    public Card(String suit, String rank) { //建立撲克牌
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() { //提取花色
        return suit;
    }

    public String getRank() { //提取數字
        return rank;
    }
    
    public int getValue() { //提取撲克牌實際數值(用於算牌)
        return switch (rank) {
            case "A" -> 11; //當數字="A"，回傳11
            case "K", "Q", "J" -> 10; //當數字="K", "Q", "J"，回傳10
            default -> Integer.parseInt(rank); //當數字="2"~"10"，轉成整數並回傳
        };
    }

    public String getImagePath() { //用來傳回目標牌對應的圖片檔案路徑
        String rankFormatted = switch (rank) {
            case "J" -> "jack";
            case "Q" -> "queen";
            case "K" -> "king";
            case "A" -> "ace";
            default -> rank;
        };

        return "/cards/" + rankFormatted.toLowerCase() + "_of_" + suit.toLowerCase() + ".png";
    }

    @Override //一個註解，用來標示你正在覆寫父類別或介面中的方法。
    public String toString() { //自訂Card物件在轉成字串時該怎麼顯示
        return rank + " of " + suit;
    }
}