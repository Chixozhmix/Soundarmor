package net.chixozhmix.chiarmor.item;

import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.item.custom.BardArmorItem;
import net.chixozhmix.chiarmor.item.custom.BardHeart;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModsItem {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SoundArmor.MOD_ID);

    public static final RegistryObject<Item> SOUND_ARMOR_HELMET = ITEMS.register("sound_armor_helmet",
            () -> new BardArmorItem(BardArmor.SOUND, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SOUND_ARMOR_CHESTPLATE = ITEMS.register("sound_armor_chestplate",
            () -> new BardArmorItem(BardArmor.SOUND, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> SOUND_ARMOR_LEGGINGS = ITEMS.register("sound_armor_leggings",
            () -> new BardArmorItem(BardArmor.SOUND, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> SOUND_ARMOR_BOOTS = ITEMS.register("sound_armor_boots",
            () -> new BardArmorItem(BardArmor.SOUND, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> BARD_HEART = ITEMS.register("bard_heart",
            () -> new BardHeart(new Item.Properties()
                    .defaultDurability(5)
                    .rarity(Rarity.UNCOMMON)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
