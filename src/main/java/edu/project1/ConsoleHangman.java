package edu.project1;

import edu.project1.Results.Defeat;
import edu.project1.Results.FailedGuess;
import edu.project1.Results.Result;
import edu.project1.Results.SuccessfulGuess;
import edu.project1.Results.Win;
import edu.project1.WordGenerator.HangmanWordGenerator;
import edu.project1.WordGenerator.Topic;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleHangman {
    private final static Logger LOGGER = LogManager.getLogger();
    private static final String GAME_NAME = "HANG MAN";
    private static final String QUIT_COMMAND = "quit";
    private static final String TEXT_FOR_LETTER_INPUT = "Enter the letter: ";
    private final Scanner in = new Scanner(System.in);
    private final HangmanWordGenerator hangmanWordGenerator = new HangmanWordGenerator();

    public void run() {
        printGameName();
        printGameRules();
        hangmanWordGenerator.fillTopicsWithStartWords();
        Topic selectedTopic = chooseTopic();
        Session session = new Session(selectedTopic, hangmanWordGenerator);
        LOGGER.info(String.format("Hidden word is %s", new String(session.getCurrentStateOfGame().state())));
        Result currentGuessResult;
        do {
            LOGGER.info(TEXT_FOR_LETTER_INPUT);
            String selectedLetter = in.nextLine();
            if (selectedLetter.equals(QUIT_COMMAND)) {
                currentGuessResult = session.giveUp();
            } else {
                currentGuessResult = tryGuess(session, selectedLetter);
            }
            printState(currentGuessResult);
        }
        while (!(currentGuessResult instanceof Defeat || currentGuessResult instanceof Win));
        LOGGER.info("GAME OVER");
    }

    @SuppressWarnings("ParameterAssignment")
    private Result tryGuess(Session session, String input) {
        while (input.length() != 1) {
            LOGGER.info("You can only enter 1 letter at a time\n");
            LOGGER.info(TEXT_FOR_LETTER_INPUT);
            input = in.nextLine();
        }
        char letter = input.charAt(0);
        return session.guess(letter);
    }

    private void printState(Result guess) {
        LOGGER.info(guess.message() + '\n');
        if (guess instanceof SuccessfulGuess || guess instanceof FailedGuess) {
            LOGGER.info(new String(guess.state()) + '\n');
        }
    }

    private Topic chooseTopic() {
        LOGGER.info("Before starting the game, select the topic on which the word will be made.\n");
        Map<Integer, Topic> availableTopics = getAvailableTopics();
        for (Map.Entry<Integer, Topic> pair : availableTopics.entrySet()) {
            LOGGER.info(String.format("%d: %s\n", pair.getKey(), pair.getValue()));
        }
        String messageForTopicInput = "Enter the number of the selected topic: ";
        LOGGER.info(messageForTopicInput);
        String selectedTopicID = in.nextLine();
        while (!selectedTopicID.chars().allMatch(Character::isDigit) || Integer.parseInt(selectedTopicID) <= 0
            || Integer.parseInt(selectedTopicID) > availableTopics.size()) {
            LOGGER.info("\nYou entered an incorrect number of topic. Try Again\n");
            LOGGER.info(messageForTopicInput);
            selectedTopicID = in.nextLine();
        }
        Topic selectedTopic = availableTopics.get(Integer.valueOf(selectedTopicID));
        LOGGER.info(String.format("You choose '%s' topic. Now try to guess the word\n", selectedTopic));
        return selectedTopic;
    }

    private Map<Integer, Topic> getAvailableTopics() {
        Map<Integer, Topic> availableTopics = new HashMap<>();
        int currentIndex = 1;
        for (Topic currentTopic : hangmanWordGenerator.getAvailableTopics()) {
            availableTopics.put(currentIndex++, currentTopic);
        }
        return availableTopics;
    }

    private void printGameRules() {
        LOGGER.info("In this game you have to guess the word for a certain number of attempts.\n");
        LOGGER.info("Use only english letters to guess the word.\n");
        LOGGER.info("At any time, you can enter the command 'quit' to exit the game.\n\n");
    }

    private void printGameName() {
        LOGGER.info(GAME_NAME);
    }
}
