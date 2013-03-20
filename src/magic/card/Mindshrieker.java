package magic.card;

import magic.model.MagicCard;
import magic.model.MagicGame;
import magic.model.MagicManaCost;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicSource;
import magic.model.action.MagicChangeTurnPTAction;
import magic.model.action.MagicMillLibraryAction;
import magic.model.action.MagicPlayerAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.condition.MagicCondition;
import magic.model.condition.MagicConditionFactory;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPayManaCostEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicTiming;

public class Mindshrieker {
    public static final MagicPermanentActivation A = new MagicPermanentActivation(
            new MagicCondition[]{MagicConditionFactory.ManaCost("{2}")},
            new MagicActivationHints(MagicTiming.Pump,false),
            "Mill"
            ) {
        
        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return new MagicEvent[]{new MagicPayManaCostEvent(source,source.getController(),MagicManaCost.create("{2}"))};
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                    source,
                    MagicTargetChoice.TARGET_PLAYER,
                    this,
                    "Target player$ puts the top card of his or her library " +
                    "into his or her graveyard. SN gets +X/+X " +
                    "until end of turn, where X is that card's converted mana cost");
        }

        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event,
                final Object[] choiceResults) {
            event.processTargetPlayer(game,choiceResults,new MagicPlayerAction() {
                public void doAction(final MagicPlayer player) {
                    game.doAction(new MagicMillLibraryAction(player,1));
                    final MagicCard card = player.getGraveyard().get(player.getGraveyard().size()-1);
                    final int amount = card.getCardDefinition().getConvertedCost();
                    game.doAction(new MagicChangeTurnPTAction(event.getPermanent(),amount,amount));
                }
            });
        }
    };
}
