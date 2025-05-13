import java.util.*;

public class Blackjack {
    static Scanner scanner = new Scanner(System.in);
    static List<String> deck = new ArrayList<>();
    static List<String> playerHand = new ArrayList<>();
    static List<String> dealerHand = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("歡迎來到21點遊戲！");
        initializeDeck();
        shuffleDeck();

        // 發牌
        playerHand.add(drawCard());
        dealerHand.add(drawCard());
        playerHand.add(drawCard());
        dealerHand.add(drawCard());

        showHands(false);

        // 玩家回合
        while (true) {
            System.out.print("你要要牌(Hit)還是停牌(Stand)? (H/S): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (choice.equals("H")) {
                playerHand.add(drawCard());
                showHands(false);
                if (calculatePoints(playerHand) > 21) {
                    System.out.println("你爆牌了！你輸了。");
                    return;
                }
            } else if (choice.equals("S")) {
                break;
            } else {
                System.out.println("請輸入 H 或 S");
            }
        }

        // 莊家回合
        while (calculatePoints(dealerHand) < 17) {
            dealerHand.add(drawCard());
        }

        // 顯示最終手牌
        showHands(true);

        // 判斷勝負
        int playerTotal = calculatePoints(playerHand);
        int dealerTotal = calculatePoints(dealerHand);

        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("你贏了！");
        } else if (playerTotal == dealerTotal) {
            System.out.println("平手！");
        } else {
            System.out.println("你輸了！");
        }
    }

    static void initializeDeck() {
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(rank + suit);
            }
        }
    }

    static void shuffleDeck() {
        Collections.shuffle(deck);
    }

    static String drawCard() {
        return deck.remove(0);
    }

    static int calculatePoints(List<String> hand) {
        int total = 0;
        int aceCount = 0;

        for (String card : hand) {
            String rank = card.substring(0, card.length() - 1);
            if ("JQK".contains(rank)) {
                total += 10;
            } else if (rank.equals("A")) {
                total += 11;
                aceCount++;
            } else {
                total += Integer.parseInt(rank);
            }
        }

        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    static void showHands(boolean showDealerAll) {
        System.out.println("你的手牌: " + playerHand + "（總點數：" + calculatePoints(playerHand) + "）");
        if (showDealerAll) {
            System.out.println("莊家手牌: " + dealerHand + "（總點數：" + calculatePoints(dealerHand) + "）");
        } else {
            System.out.println("莊家手牌: [" + dealerHand.get(0) + ", ??]");
        }
    }
}
