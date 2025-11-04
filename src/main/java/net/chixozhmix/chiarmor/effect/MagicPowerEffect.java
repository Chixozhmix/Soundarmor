package net.chixozhmix.chiarmor.effect;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MagicPowerEffect extends MobEffect {
    public MagicPowerEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAA00FF); // Фиолетовый цвет эффекта
        this.addAttributeModifier(AttributeRegistry.SPELL_POWER.get(),
                "1d10a9a4-5b5f-11ee-8c99-0242ac120002",
                0.05,
                AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE,
                "1d10a9a3-5b5f-11ee-8c10-0242ac120102",
                0.05,
                AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}


