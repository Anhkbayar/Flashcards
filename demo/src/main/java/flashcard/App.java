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

//java -cp target/demo-1.0-SNAPSHOT.jar
/**
 * GGEZ
 *
 */
/**
 * Flashcard main class
 *
 */
public class App {

    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[37m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private static final String HELP_MESSAGE = """
            Usage: flashcard <cards-file> [options]
            Options:
              --help                      Show help message
              --order <order>             Set card order (random, worst-first, recent-mistakes-first)
              --repetitions <num>         Number of repetitions per card
              --invertCards               Invert question and answer
            """;
    private static final String START_MESSAGE = """
            Hervee tohirgoog uurchilhiig husvel --help gej bichin tuslamj avna uu 
            """;
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner mainScanner = new Scanner(System.in);

        String cardsFile = "G:\\Semester4\\Buteelt\\Flashcards\\demo\\src\\main\\java\\flashcard\\cards.txt";
        String order = "random";
        int repetitions = 1;
        boolean invertCards = false;

        while (true) {
            System.out.println(START_MESSAGE);
            String input = mainScanner.nextLine();
            String[] settings = input.split(" ");
            for (int i = 0; i < settings.length; i++) {
                switch (settings[i]) {
                    case "--help" -> {
                        System.out.println(HELP_MESSAGE);
                        continue;
                    }
                    case ""->{
                        continue;
                    }
                    case "--order" -> {
                        if (i + 1 <= settings.length) {
                            order = settings[++i];
                        } else {
                            System.err.println("Error: Missing argument for --order");
                            return;
                        }
                    }
                    case "--repetitions" -> {
                        if (i + 1 < settings.length) {
                            try {
                                repetitions = Integer.parseInt(settings[++i]);
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
                        System.err.println("Error: " + settings[i]);
                        System.out.println("--help gej bicheed tohirgoog harna uu");
                    }

                }
            }

            System.out.println("\nType 'start' to begin flashcards or 'quit' to exit");
            String command = mainScanner.nextLine().trim().toLowerCase();

            if (command.equals("quit")) {
                System.out.print("Exiting.......");
                mainScanner.close();
                break;
            }
            if (!command.equals("start")) {
                System.out.print("Medehgui command bainoo :)");
                continue;
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

            startFlashCard(cards, repetitions, mainScanner);
        }

        mainScanner.close();
    }

    /**
     * Asuultuudiig file aas unshih
     */
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

    /**
     * Togloomiig ehluulj asuultuudiig terminald hevleh
     *
     * @param cards
     * @param repetitions
     * @param scanner
     *
     */
    @SuppressWarnings("ConvertToTryWithResources")
    private static void startFlashCard(List<Card> cards, int repetitions, Scanner scanner) {
        int correct = 0;
        int incorrect = 0;
        for (Card card : cards) {
            for (int i = 0; i < repetitions; i++) {
                System.out.println("Asuult: " + card.getQuestion());
                System.out.print("Hariult: ");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase(card.getAnswer())) {
                    System.out.println(ANSI_GREEN + "Zuv hariult" + ANSI_RESET);
                    correct++;
                } else {
                    System.out.println(ANSI_RED + "Buruu hariult. Zuv hariult ni: " + ANSI_YELLOW + card.getAnswer() + ANSI_RESET
                    );
                    card.mistakeAdder();
                    incorrect++;
                }
            }
        }
        int sum = correct + incorrect;
        float percent = ((float) correct / (float) sum) * 100;
        System.out.println("Zuv hariulsan: " + correct + " Buruu hariulsan: " + incorrect);
        if (percent < 85) {
            System.out.println(percent + "% hangaltgui baina");
        } else {
            System.out.println("woow " + percent + "% sain baina");
        }
    }

}
