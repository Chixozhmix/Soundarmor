package net.chixozhmix.chiarmor.spells.custom;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import net.alshanex.alshanex_familiars.registry.AFSchoolRegistry;
import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.effect.ModEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

@AutoSpellConfig
public class ManaRegenSpell extends AbstractSpell {

    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(SoundArmor.MOD_ID, "mana_regen");

    public ManaRegenSpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 20;
        this.baseManaCost = 40;
    }

    private DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(AFSchoolRegistry.SOUND_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(60)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", "30s")
        );
    }

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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {

        AABB area = new AABB(entity.blockPosition()).inflate(10);
        List<Player> players = level.getEntitiesOfClass(Player.class, area);

        for (Player nearbyPlayer : players) {
            nearbyPlayer.addEffect(new MobEffectInstance(
                    ModEffect.MANA_REGEN_EFFECT.get(),
                    600,
                    getAmplifier(spellLevel),
                    false,
                    true,
                    true
            ));
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private int getAmplifier(int spellLevel) {
        return -1 + spellLevel;
    }
}
