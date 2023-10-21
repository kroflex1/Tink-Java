package edu.project1;

import edu.project1.GuessResult.Defeat;
import edu.project1.GuessResult.FailedGuess;
import edu.project1.GuessResult.GuessResult;
import edu.project1.GuessResult.SuccessfulGuess;
import edu.project1.GuessResult.Win;
import edu.project1.Word.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Console;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleHangman {
    private final String QUIT_COMMAND = "quit";
    private final Scanner in = new Scanner(System.in);

    public void run() {
        printGameName();
        printGameRules();
        Topic selectedTopic = chooseTopic();
        Session session = new Session(selectedTopic);
        GuessResult currentGuessResult;
        System.out.printf("You choose '%s' topic. Now try to guess the word\n", selectedTopic.toString());
        do {
            System.out.print("Enter the letter: ");
            String selectedLetter = in.nextLine();
            if (selectedLetter.equals(QUIT_COMMAND)) {
                currentGuessResult = session.giveUp();
            } else {
                currentGuessResult = tryGuess(session, selectedLetter);
            }
            printState(currentGuessResult);
        }
        while (!(currentGuessResult instanceof Defeat || currentGuessResult instanceof Win));
        System.out.print("Game over");
    }

    private GuessResult tryGuess(Session session, String input) {
        while (input.length() != 1) {
            System.out.print("You can only enter 1 letter at a time\n");
            System.out.print("Enter the letter:");
            System.out.print("\n:");
            input = in.nextLine();
        }
        char letter = input.charAt(0);
        return session.guess(letter);
    }

    private void printState(GuessResult guess) {
        System.out.print(guess.message() + '\n');
        if (guess instanceof SuccessfulGuess || guess instanceof FailedGuess) {
            System.out.print(new String(guess.state()) + '\n');
        }
    }

    private Topic chooseTopic() {
        System.out.print("Before starting the game, select the topic on which the word will be made.\n");
        Map<Integer, Topic> availableTopics = getAvailableTopics();
        for (Map.Entry<Integer, Topic> pair : availableTopics.entrySet()) {
            System.out.printf("%d: %s\n", pair.getKey(), pair.getValue());
        }
        System.out.print("Enter the number of the selected topic: ");
        System.out.print("\n");
        String selectedTopicID = in.nextLine();
        while (!selectedTopicID.chars().allMatch(Character::isDigit) || Integer.parseInt(selectedTopicID) <= 0 ||
            Integer.parseInt(selectedTopicID) > availableTopics.size()) {
            System.out.print("You entered an incorrect number of topic. Try Again\n");
            System.out.print("Enter the number of the selected topic: ");
            System.out.print("\n");
            selectedTopicID = in.nextLine();
        }
        return availableTopics.get(Integer.parseInt(selectedTopicID));
    }

    private Map<Integer, Topic> getAvailableTopics() {
        Map<Integer, Topic> availableTopics = new HashMap<>();
        int currentIndex = 1;
        for (Topic currentTopic : Topic.class.getEnumConstants()) {
            availableTopics.put(currentIndex++, currentTopic);
        }
        return availableTopics;
    }

    private void printGameRules() {
        System.out.print("In this game you have to guess the word for a certain number of attempts.\n");
        System.out.print("At any time, you can enter the command 'quit' to exit the game.\n\n");
    }

    private void printGameName() {
        String word =
            "                                                                                                           \n" +
                "     /\\__\\         /\\  \\         /\\__\\         /\\  \\                  /\\__\\         /\\  \\         /\\__\\    \n" +
                "    /:/  /        /::\\  \\       /::|  |       /::\\  \\                /::|  |       /::\\  \\       /::|  |   \n" +
                "   /:/__/        /:/\\:\\  \\     /:|:|  |      /:/\\:\\  \\              /:|:|  |      /:/\\:\\  \\     /:|:|  |   \n" +
                "  /::\\  \\ ___   /::\\~\\:\\  \\   /:/|:|  |__   /:/  \\:\\  \\            /:/|:|__|__   /::\\~\\:\\  \\   /:/|:|  |__ \n" +
                " /:/\\:\\  /\\__\\ /:/\\:\\ \\:\\__\\ /:/ |:| /\\__\\ /:/__/_\\:\\__\\          /:/ |::::\\__\\ /:/\\:\\ \\:\\__\\ /:/ |:| /\\__\\\n" +
                " \\/__\\:\\/:/  / \\/__\\:\\/:/  / \\/__|:|/:/  / \\:\\  /\\ \\/__/          \\/__/~~/:/  / \\/__\\:\\/:/  / \\/__|:|/:/  /\n" +
                "      \\::/  /       \\::/  /      |:/:/  /   \\:\\ \\:\\__\\                  /:/  /       \\::/  /      |:/:/  / \n" +
                "      /:/  /        /:/  /       |::/  /     \\:\\/:/  /                 /:/  /        /:/  /       |::/  /  \n" +
                "     /:/  /        /:/  /        /:/  /       \\::/  /                 /:/  /        /:/  /        /:/  /   \n" +
                "     \\/__/         \\/__/         \\/__/         \\/__/                  \\/__/         \\/__/         \\/__/    ";
        System.out.print(word + "\n\n\n");
    }
}
