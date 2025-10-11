package net.chixozhmix.chiarmor.item.custom;

import net.chixozhmix.chiarmor.effect.ModEffect;
import net.chixozhmix.chiarmor.sounds.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BardHeart extends Item {
    public BardHeart(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if(!pLevel.isClientSide) {
            AABB area = new AABB(pPlayer.blockPosition()).inflate(10);
            List<Player> players = pLevel.getEntitiesOfClass(Player.class, area);

            for (Player nearbyPlayer : players) {
                nearbyPlayer.addEffect(new MobEffectInstance(
                        ModEffect.SPELL_POWER.get(),
                        1200,
                        5,
                        false,
                        true,
                        true
                ));
            }

            pPlayer.getCooldowns().addCooldown(this, 1200);
            stack.hurtAndBreak(1, pPlayer, (p) -> {
                p.broadcastBreakEvent(pUsedHand);
            });
        } else {
            pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    ModSounds.HEATR_MUSIC.get(), SoundSource.PLAYERS,
                    1.0F, 1.0F);
        }

        return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide());
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
