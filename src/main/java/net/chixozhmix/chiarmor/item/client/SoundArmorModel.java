package net.chixozhmix.chiarmor.item.client;

import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.item.custom.BardArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class SoundArmorModel extends DefaultedItemGeoModel<BardArmorItem> {
    public SoundArmorModel() {
        super(new ResourceLocation(SoundArmor.MOD_ID, ""));
    }

    @Override
    public ResourceLocation getModelResource(BardArmorItem bardArmorItem) {
        return new ResourceLocation(SoundArmor.MOD_ID, "geo/sound_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BardArmorItem bardArmorItem) {
        return new ResourceLocation(SoundArmor.MOD_ID, "textures/armor/custom_sound_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BardArmorItem bardArmorItem) {
        return new ResourceLocation(SoundArmor.MOD_ID, "animations/sound_armor.animation.json");
    }
}
