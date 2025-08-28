package net.chixozhmix.chiarmor.item.client;

import net.chixozhmix.chiarmor.item.custom.BardArmorItem;
import net.minecraft.client.model.HumanoidModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SoundArmorRenderer extends GeoArmorRenderer<BardArmorItem> {
    public SoundArmorRenderer() {
        super(new SoundArmorModel());
    }
}
