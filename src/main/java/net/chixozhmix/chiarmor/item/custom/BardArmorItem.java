package net.chixozhmix.chiarmor.item.custom;

import net.chixozhmix.chiarmor.item.BardArmor;
import net.chixozhmix.chiarmor.item.client.SoundArmorRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.List;
import java.util.function.Consumer;

public class BardArmorItem extends ArmorItem implements GeoItem {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);


    public BardArmorItem(Type pType, Item.Properties settings) {
        super(BardArmor.SOUND, pType, settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private SoundArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new SoundArmorRenderer();

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState animationState) {
        animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return this.type.getSlot();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, tooltip, pIsAdvanced);

        // Получаем тип брони (шлем, нагрудник и т.д.)
        Type armorType = this.type;

        // Добавляем информацию о бонусах в зависимости от типа брони
        switch (armorType) {
            case HELMET -> {
                tooltip.add(Component.literal(""));
                tooltip.add(Component.translatable("tooltip.chiarmor.sound_power", "+10%").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.chiarmor.max_mana", "+125").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.chiarmor.spell_power", "+5%").withStyle(ChatFormatting.BLUE));
            }
            case CHESTPLATE -> {
                tooltip.add(Component.literal(""));
                tooltip.add(Component.translatable("tooltip.chiarmor.sound_power", "+10%").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.chiarmor.max_mana", "+125").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.chiarmor.spell_power", "+5%").withStyle(ChatFormatting.BLUE));
            }
            case LEGGINGS -> {
                tooltip.add(Component.literal(""));
                tooltip.add(Component.translatable("tooltip.chiarmor.sound_power", "+10%").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.chiarmor.max_mana", "+125").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.chiarmor.spell_power", "+5%").withStyle(ChatFormatting.BLUE));
            }
            case BOOTS -> {
                tooltip.add(Component.literal(""));
                tooltip.add(Component.translatable("tooltip.chiarmor.sound_power", "+10%").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.chiarmor.max_mana", "+125").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.translatable("tooltip.chiarmor.spell_power", "+5%").withStyle(ChatFormatting.BLUE));
            }
        }
    }
}
