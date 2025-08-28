package net.chixozhmix.chiarmor.datagen;

import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.chixozhmix.chiarmor.SoundArmor;
import net.chixozhmix.chiarmor.item.ModsItem;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModsItem.SOUND_ARMOR_HELMET.get())
                .pattern("MMM")
                .pattern("MSM")
                .pattern("   ")
                .define('M', ItemRegistry.MAGIC_CLOTH.get())
                .define('S', net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get())
                .unlockedBy("has_sound_rune", has(net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModsItem.SOUND_ARMOR_CHESTPLATE.get())
                .pattern("MSM")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', ItemRegistry.MAGIC_CLOTH.get())
                .define('S', net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get())
                .unlockedBy("has_sound_rune", has(net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModsItem.SOUND_ARMOR_LEGGINGS.get())
                .pattern("MMM")
                .pattern("MSM")
                .pattern("M M")
                .define('M', ItemRegistry.MAGIC_CLOTH.get())
                .define('S', net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get())
                .unlockedBy("has_sound_rune", has(net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModsItem.SOUND_ARMOR_BOOTS.get())
                .pattern("   ")
                .pattern("M M")
                .pattern("MSM")
                .define('M', ItemRegistry.MAGIC_CLOTH.get())
                .define('S', net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get())
                .unlockedBy("has_sound_rune", has(net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModsItem.BARD_HEART.get())
                .pattern("A A")
                .pattern(" S ")
                .pattern("   ")
                .define('A', ItemRegistry.ARCANE_INGOT.get())
                .define('S', net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get())
                .unlockedBy("has_sound_rune", has(net.alshanex.alshanex_familiars.registry.ItemRegistry.SOUND_RUNE.get()))
                .save(consumer);

    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe>
            pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime,
                                     String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime,
                    pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(pFinishedRecipeConsumer,
                    SoundArmor.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
