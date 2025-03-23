package flashcard.Organizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import flashcard.Edits.Card;

public class RandomOrganizer implements CardOrganizer {

    @Override
    public List<Card> organize(List<Card> cards) {
        List<Card> shuffledCards = new ArrayList<>(cards);
        Collections.shuffle(shuffledCards);
        return shuffledCards;
    }
}
