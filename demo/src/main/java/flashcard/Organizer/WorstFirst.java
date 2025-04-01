package flashcard.Organizer;

import java.util.List;

import flashcard.Edits.Card;

public class WorstFirst implements CardOrganizer{
    @Override
    public List<Card> organize(List<Card> cards){
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).getMistakeCounter() <= cards.get(j).getMistakeCounter()) {
                    Card temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
            }
        }
        return cards;
    }
}
