package flashcard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import flashcard.Edits.Card;
import flashcard.Organizer.CardOrganizer;
import flashcard.Organizer.RandomOrganizer;
import flashcard.Organizer.RecentMistakesFirst;

/**
 * TODO textnii ungu white bolgoh
 * TODO help menug yanzlah
 * TODO start hesegtei bolgoh
 * 
 */


/**
 * Hello world!
 *
 */
public class App {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[30m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private static final String HELP_MESSAGE = """
            Usage: flashcard <cards-file> [options]
            Options:
              --help                      Show help message
              --order <order>             Set card order (random, worst-first, recent-mistakes-first)
              --repetitions <num>         Number of repetitions per card
              --invertCards               Invert question and answer
            """;

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help")) {
            System.out.println(HELP_MESSAGE);
        }

        String cardsFile = "G:\\Semester4\\Buteelt\\Flashcards\\demo\\src\\main\\java\\flashcard\\cards.txt";
        String order = "random";
        int repetitions = 1;
        boolean invertCards = false;

        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--order" -> {
                    if (i + 1 < args.length) {
                        order = args[i++];
                    } else {
                        System.err.println("Error: Missing argument for --order");
                        return;
                    }
                }
                case "--repetitions" -> {
                    if (i + 1 < args.length) {
                        try {
                            repetitions = Integer.parseInt(args[i++]);
                        } catch (NumberFormatException e) {
                            System.err.println("Error: WRONG NUMBER FOOL");
                            return;
                        }
                    }
                }
                case "--invertCards" -> {
                    invertCards = true;

                }
                default -> {
                    System.err.println("Error: Evdelchleeshdee " + args[i]);
                }

            }
        }

        List<Card> cards = loadCards(cardsFile);
        if (cards == null) {
            return;
        }

        if (invertCards) {
            for (Card card : cards) {
                String temp = card.getQuestion();
                card = new Card(card.getAnswer(), temp);
            }
        }

        CardOrganizer organizer = new RandomOrganizer();
        switch (order) {
            case "random" -> {
                organizer = new RandomOrganizer();
            }
            case "recent-mistakes-first" -> {
                organizer = new RecentMistakesFirst();
            }
            default -> {
                System.err.println("Error: Iiim daraalal baihgueeee");
            }
        }

        cards = organizer.organize(cards);

        startFlashCard(cards, repetitions);
    }

    private static List<Card> loadCards(String filePath) {
        List<Card> cards = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    cards.add(new Card(parts[0].trim(), parts[1].trim()));
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File oldsongue :( " + filePath);
            return null;
        }

        return cards;

    }

    @SuppressWarnings("ConvertToTryWithResources")
    private static void startFlashCard(List<Card> cards, int repetitions) {
        Scanner scanner = new Scanner(System.in);
        for (Card card : cards) {
            for (int i = 0; i < repetitions; i++) {
                System.out.println("Asuult: " + card.getQuestion());
                System.out.print("Hariult: ");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase(card.getAnswer())) {
                    System.out.println(ANSI_GREEN + "Zuv hariult" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "Buruu hariult. Zuv hariult ni: " + ANSI_YELLOW + card.getAnswer() + ANSI_RESET
                    );
                    card.mistakeAdder();
                }
            }
        }
        scanner.close();
    }

}
