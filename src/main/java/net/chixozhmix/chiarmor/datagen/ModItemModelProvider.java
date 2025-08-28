package net.chixozhmix.chiarmor.datagen;

import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.item.ModsItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SoundArmor.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModsItem.SOUND_ARMOR_BOOTS);
        simpleItem(ModsItem.SOUND_ARMOR_LEGGINGS);
        simpleItem(ModsItem.SOUND_ARMOR_CHESTPLATE);
        simpleItem(ModsItem.SOUND_ARMOR_HELMET);
        simpleItem(ModsItem.BARD_HEART);

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(SoundArmor.MOD_ID, "item/" + item.getId().getPath()));
    }
}
