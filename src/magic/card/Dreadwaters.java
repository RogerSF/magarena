package magic.card;

import magic.model.MagicGame;
import magic.model.MagicPayedCost;
import magic.model.MagicPlayer;
import magic.model.MagicType;
import magic.model.action.MagicMillLibraryAction;
import magic.model.action.MagicPlayerAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.event.MagicSpellCardEvent;
import magic.model.stack.MagicCardOnStack;

public class Dreadwaters {
    public static final MagicSpellCardEvent E = new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(
                final MagicCardOnStack cardOnStack,
                final MagicPayedCost payedCost) {
            return new MagicEvent(
                    cardOnStack,
                    MagicTargetChoice.TARGET_PLAYER,
                    this,
                    "Target player$ puts the top X cards of his or her library into " +
                    "his or her graveyard, where X is the number of lands you control.");
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            event.processTargetPlayer(game,choiceResults,new MagicPlayerAction() {
                public void doAction(final MagicPlayer target) {
                    final MagicPlayer player = event.getPlayer();
                    final int amount = player.getNrOfPermanentsWithType(MagicType.Land);
                    game.doAction(new MagicMillLibraryAction(target,amount));
                }
            });
        }
    };
}
