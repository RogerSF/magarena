[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.Negative("Target Plains or Island"),
                MagicDestroyTargetPicker.Destroy,
                this,
                "Destroy target Plains or Island\$. SN deals 3 damage to that land's controller."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent permanent ->
                game.doAction(new MagicDestroyAction(permanent));
                final MagicDamage damage=new MagicDamage(event.getSource(),permanent.getController(),3);
                game.doAction(new MagicDealDamageAction(damage));
            });
        }
    }
]
