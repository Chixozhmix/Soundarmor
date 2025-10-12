package net.chixozhmix.chiarmor.spells.custom;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.alshanex.alshanex_familiars.registry.AFSchoolRegistry;
import net.chixozhmix.chiarmor.SoundArmor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class ViciousMockerySpell extends AbstractSpell {
    private static final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(SoundArmor.MOD_ID, "vicious_mockery");

    public ViciousMockerySpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 30;
        this.baseManaCost = 20;
    }

    private DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(AFSchoolRegistry.SOUND_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(35)
            .build();

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.EVOKER_PREPARE_ATTACK);
    }

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", "10s"),
                Component.translatable("ui.irons_spellbooks.damage", new Object[]{Utils.stringTruncation((double)
                        this.getDamage(spellLevel, caster), 2)})
        );
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, 0.35F);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        ICastData target = playerMagicData.getAdditionalCastData();

        if (target instanceof TargetEntityCastData castTargetingData) {
            LivingEntity target1 = castTargetingData.getTarget((ServerLevel)level);
            if (target1 != null) {
                DamageSources.applyDamage(target1, getDamage(spellLevel, entity), getDamageSource(target1, entity));

                target1.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, getAmplifier(spellLevel)));
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private int getAmplifier(int spellLevel) {
        return -1 + spellLevel;
    }

    private float getDamage(int spellLevel, LivingEntity entity) {
        return this.getSpellPower(spellLevel, entity) * 0.5F;
    }
}
