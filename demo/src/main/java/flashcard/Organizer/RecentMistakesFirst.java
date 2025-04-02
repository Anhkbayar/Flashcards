package flashcard.Organizer;

import java.util.ArrayList;
import java.util.List;

import flashcard.Edits.Card;

public class RecentMistakesFirst implements CardOrganizer {

    @Override
    public List<Card> organize(List<Card> cards) {
        List<Card> mistakenCards = new ArrayList<>();

        for(Card card: cards){
            if(card.getMistakeCounter()>0){
                mistakenCards.add(card);
                cards.remove(card);
            }
        }
        List<Card> reversedList = new ArrayList<>();
        for (int i = mistakenCards.size() - 1; i >= 0; i--) {
            reversedList.add(mistakenCards.get(i));
        }
        reversedList.addAll(cards);
        return reversedList;
    }

}
