package net.chixozhmix.chiarmor.effect;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class MagicPowerEffect extends MobEffect {
    public MagicPowerEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAA00FF); // Фиолетовый цвет эффекта
        this.addAttributeModifier(AttributeRegistry.SPELL_POWER.get(),
                "1d10a9a4-5b5f-11ee-8c99-0242ac120002",
                0.15, // Увеличивает силу заклинаний на 15%
                AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}


