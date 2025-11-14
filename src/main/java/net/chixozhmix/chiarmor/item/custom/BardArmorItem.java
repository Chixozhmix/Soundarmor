package net.chixozhmix.chiarmor.item.custom;

import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.item.armor.ArmorCapeProvider;
import io.redspace.ironsspellbooks.item.armor.ImbuableChestplateArmorItem;
import net.chixozhmix.chiarmor.item.BardArmor;
import net.chixozhmix.chiarmor.item.client.SoundArmorModel;
import net.chixozhmix.chiarmor.item.client.SoundArmorRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.List;
import java.util.function.Consumer;

public class BardArmorItem extends ImbuableChestplateArmorItem implements ArmorCapeProvider {

    public BardArmorItem(Type pType, Item.Properties settings) {
        super(BardArmor.SOUND, pType, settings);
    }

    @Override
    public ResourceLocation getCapeResourceLocation() {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public GeoArmorRenderer<?> supplyRenderer() {
        return new GenericCustomArmorRenderer(new SoundArmorModel());
    }
}
