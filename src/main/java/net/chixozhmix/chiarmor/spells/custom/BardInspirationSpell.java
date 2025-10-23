package net.chixozhmix.chiarmor.spells.custom;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import io.redspace.ironsspellbooks.spells.TargetAreaCastData;
import net.alshanex.alshanex_familiars.registry.AFSchoolRegistry;
import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.effect.ModEffect;
import net.chixozhmix.chiarmor.sounds.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

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
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        super.onServerPreCast(level, spellLevel, entity, playerMagicData);

        if (playerMagicData != null) {
            TargetedAreaEntity targetedAreaEntity = TargetedAreaEntity.createTargetAreaEntity(level, entity.position(), 10.0F, 16239960, entity);
            playerMagicData.setAdditionalCastData(new TargetAreaCastData(entity.position(), targetedAreaEntity));
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {

        highlightSpellArea(level, entity);

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

    private void highlightSpellArea(Level level, LivingEntity caster) {
        if (level.isClientSide) {
            return; // Работаем только на серверной стороне
        }

        double radius = 10.0; // Радиус области
        Vec3 center = caster.position().add(0, caster.getEyeHeight() * 0.5, 0);

        // Создаем частицы по окружности
        int particles = 50; // Количество частиц
        for (int i = 0; i < particles; i++) {
            double angle = 2 * Math.PI * i / particles;
            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);

            // Создаем частицы на разных высотах
            for (int yOffset = 0; yOffset <= 3; yOffset++) {
                double y = center.y + yOffset;
                level.addParticle(
                        ParticleTypes.GLOW,
                        x, y, z,
                        0, 0, 0
                );

                // Добавляем дополнительные эффектные частицы
                if (i % 5 == 0) {
                    level.addParticle(
                            ParticleTypes.NOTE,
                            x, y + 0.5, z,
                            (Math.random() - 0.5) * 0.1,
                            Math.random() * 0.1,
                            (Math.random() - 0.5) * 0.1
                    );
                }
            }
        }

        // Добавляем частицы, поднимающиеся от земли
        for (int i = 0; i < 20; i++) {
            double angle = 2 * Math.PI * Math.random();
            double distance = radius * Math.random();
            double x = center.x + distance * Math.cos(angle);
            double z = center.z + distance * Math.sin(angle);

            level.addParticle(
                    ParticleTypes.ELECTRIC_SPARK,
                    x, center.y, z,
                    0, 0.1 + Math.random() * 0.2, 0
            );
        }
    }
}
