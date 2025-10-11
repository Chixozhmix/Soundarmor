package net.chixozhmix.chiarmor.spells.custom;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.alshanex.alshanex_familiars.registry.AFSchoolRegistry;
import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.effect.ModEffect;
import net.chixozhmix.chiarmor.sounds.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class BardInspirationSpell extends AbstractSpell {

    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(SoundArmor.MOD_ID, "bard_inspiration");

    public BardInspirationSpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 30;
        this.baseManaCost = 50;
    }


    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", "60s")
        );
    }

    private DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(AFSchoolRegistry.SOUND_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(60)
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
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.HEATR_MUSIC.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {

        AABB area = new AABB(entity.blockPosition()).inflate(10);
        List<Player> players = level.getEntitiesOfClass(Player.class, area);

        for (Player nearbyPlayer : players) {
            nearbyPlayer.addEffect(new MobEffectInstance(
                    ModEffect.SPELL_POWER.get(),
                    1200,
                    getAmplifier(spellLevel),
                    false,
                    true,
                    true
            ));
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public int getAmplifier(int spellLevel) {
        return -1 + spellLevel;
    }
}
