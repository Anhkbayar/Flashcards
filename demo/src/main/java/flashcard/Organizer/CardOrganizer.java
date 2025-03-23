package flashcard.Organizer;

import java.util.List;

import flashcard.Edits.Card;

public interface CardOrganizer {

    List<Card> organize(List<Card> cards);
}
