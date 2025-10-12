package net.chixozhmix.chiarmor.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffect {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "soundarmor");

    public static final RegistryObject<MobEffect> SPELL_POWER =
            EFFECTS.register("magic_power", MagicPowerEffect::new);

    public static final RegistryObject<MobEffect> MANA_REGEN_EFFECT =
            EFFECTS.register("mana_regen_effect", ManaRegen::new);

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
