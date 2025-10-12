package net.chixozhmix.chiarmor.spells;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.spells.custom.BardInspirationSpell;
import net.chixozhmix.chiarmor.spells.custom.ManaRegenSpell;
import net.chixozhmix.chiarmor.spells.custom.ViciousMockerySpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SpellRegistry {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(io.redspace.ironsspellbooks.api.registry.SpellRegistry.SPELL_REGISTRY_KEY, SoundArmor.MOD_ID);

    public static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell) {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    public static final RegistryObject<AbstractSpell> BARD_INSPIRATION = registerSpell(new BardInspirationSpell());
    public static final RegistryObject<AbstractSpell> MANA_REGEN = registerSpell(new ManaRegenSpell());
    public static final RegistryObject<AbstractSpell> VICIOUS_MOCKERY = registerSpell(new ViciousMockerySpell());


    public static void register(IEventBus eventBus) {
        SPELLS.register(eventBus);
    }
}
