[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.Negative("target artifact or land"),
                MagicDestroyTargetPicker.Destroy,
                this,
                "Destroy target artifact or land\$. Its controller loses 1 life."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent creature ->
                final MagicPlayer controller=creature.getController();
                game.doAction(new MagicDestroyAction(creature));
                game.doAction(new MagicChangeLifeAction(controller,-1));
            });
        }
    }
]
