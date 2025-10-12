package net.chixozhmix.chiarmor.effect;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ManaRegen extends MobEffect {
    public ManaRegen() {
        super(MobEffectCategory.BENEFICIAL, 0x0B87EC);
        this.addAttributeModifier(AttributeRegistry.MANA_REGEN.get(),
                "1d10a9a4-5b1f-12ee-4c90-0422ac121002",
                0.5F,
                AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
