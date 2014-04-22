[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_ENCHANTMENT,
                MagicDestroyTargetPicker.Destroy,
                this,
                "Destroy target enchantment\$ and all other enchantments " +
                "with the same name as that enchantment."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game, {
                final MagicPermanent targetPermanent ->
                final MagicTargetFilter<MagicPermanent> targetFilter =
                    new MagicNameTargetFilter(targetPermanent.getName());
                final Collection<MagicPermanent> targets =
                    game.filterPermanents(event.getPlayer(),targetFilter);
                for (final MagicPermanent permanent : targets) {
                    if (permanent.isEnchantment()) {
                        game.doAction(new MagicDestroyAction(permanent));
                    }
                }
            });
        }
    }
]
