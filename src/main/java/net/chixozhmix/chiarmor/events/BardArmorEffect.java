package net.chixozhmix.chiarmor.events;


import net.alshanex.alshanex_familiars.registry.AttributeRegistry;
import net.chixozhmix.chiarmor.item.BardArmor;
import net.chixozhmix.chiarmor.item.custom.BardArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class BardArmorEffect {
    // Храним последнее корректное состояние брони
    private static final Map<UUID, boolean[]> armorStates = new HashMap<>();
    // Флаг для отслеживания необходимости обновления
    private static final Set<UUID> needsUpdate = new HashSet<>();

    private static class ArmorModifiers {
        final List<AttributeModifierInfo> modifiers;

        ArmorModifiers(AttributeModifierInfo... modifiers) {
            this.modifiers = Arrays.asList(modifiers);
        }
    }

    private static class AttributeModifierInfo {
        final Supplier<Attribute> attributeSupplier;
        final UUID modifierId;
        final String name;
        final double amount;
        final AttributeModifier.Operation operation;

        AttributeModifierInfo(Supplier<Attribute> attributeSupplier, UUID modifierId, String name, double amount, AttributeModifier.Operation operation) {
            this.attributeSupplier = attributeSupplier;
            this.modifierId = modifierId;
            this.name = name;
            this.amount = amount;
            this.operation = operation;
        }

        Attribute getAttribute() {
            return attributeSupplier.get();
        }

        AttributeModifier createModifier() {
            return new AttributeModifier(modifierId, name, amount, operation);
        }
    }

    private static final Map<Integer, ArmorModifiers> ARMOR_MODIFIERS = new HashMap<>();
    static {
        // Шлем
        ARMOR_MODIFIERS.put(0, new ArmorModifiers(
                new AttributeModifierInfo(
                        io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA,
                        UUID.fromString("a1b2c3d4-e5f6-7890-a1b2-c3d4e5f67892"),
                        "bard_helmet_mana",
                        125.0,
                        AttributeModifier.Operation.ADDITION
                ),
                new AttributeModifierInfo(
                        io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER,
                        UUID.fromString("a1b2c3d4-e5f6-7890-a1b2-c3d4e5f67893"),
                        "bard_helmet_spell_power",
                        0.05,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                ),
                new AttributeModifierInfo(
                        AttributeRegistry.SOUND_SPELL_POWER,
                        UUID.fromString("a1b2c3d4-e5f6-7890-a1b2-c3d4e5f67894"),
                        "bard_helmet_sound_spell_power",
                        0.1,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                )
        ));

        // Нагрудник
        ARMOR_MODIFIERS.put(1, new ArmorModifiers(
                new AttributeModifierInfo(
                        AttributeRegistry.SOUND_SPELL_POWER,
                        UUID.fromString("b2c3d4e5-f6a7-8901-b2c3-d4e5f6a78902"),
                        "bard_chestplate_sound_power",
                        0.1,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                ),
                new AttributeModifierInfo(
                        io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA,
                        UUID.fromString("b2c3d4e5-f6a7-8901-b2c3-d4e5f6a78903"),
                        "bard_chestplate_mana",
                        125.0,
                        AttributeModifier.Operation.ADDITION
                ),
                new AttributeModifierInfo(
                        io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER,
                        UUID.fromString("b2c3d4e5-f6a7-8901-b2c3-d4e5f6a78904"),
                        "bard_chestplate_spell_power",
                        0.05,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                )
        ));

        // Поножи
        ARMOR_MODIFIERS.put(2, new ArmorModifiers(
                new AttributeModifierInfo(
                        AttributeRegistry.SOUND_SPELL_POWER,
                        UUID.fromString("c3d4e5f6-a7b8-9012-c3d4-e5f6a7b89013"),
                        "bard_leggings_sound_power",
                        0.1,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                ),
                new AttributeModifierInfo(
                        io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA,
                        UUID.fromString("c3d4e5f6-a7b8-9012-c3d4-e5f6a7b89014"),
                        "bard_leggings_mana",
                        125,
                        AttributeModifier.Operation.ADDITION
                ),
                new AttributeModifierInfo(
                        io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER,
                        UUID.fromString("c3d4e5f6-a7b8-9012-c3d4-e5f6a7b89015"),
                        "bard_leggings_spell_power",
                        0.05,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                )
        ));

        // Ботинки
        ARMOR_MODIFIERS.put(3, new ArmorModifiers(
                new AttributeModifierInfo(
                        AttributeRegistry.SOUND_SPELL_POWER,
                        UUID.fromString("d4e5f6a7-b8c9-0123-d4e5-f6a7b8c90124"),
                        "bard_boots_sound_power",
                        0.1,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                ),
                new AttributeModifierInfo(
                        io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA,
                        UUID.fromString("d4e5f6a7-b8c9-0123-d4e5-f6a7b8c90125"),
                        "bard_boots_mana",
                        125,
                        AttributeModifier.Operation.ADDITION
                ),
                new AttributeModifierInfo(
                        io.redspace.ironsspellbooks.api.registry.AttributeRegistry.SPELL_POWER,
                        UUID.fromString("d4e5f6a7-b8c9-0123-d4e5-f6a7b8c90126"),
                        "bard_boots_spell_power",
                        0.05,
                        AttributeModifier.Operation.MULTIPLY_TOTAL
                )
        ));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) return;

        Player player = event.player;
        UUID playerId = player.getUUID();

        // Проверяем, нужно ли обновить атрибуты
        if (needsUpdate.contains(playerId)) {
            updatePlayerAttributes(player);
            needsUpdate.remove(playerId);
            return;
        }

        // Проверяем изменения в броне
        boolean[] currentState = armorStates.computeIfAbsent(playerId, k -> new boolean[4]);
        boolean[] newState = getCurrentArmorState(player);

        if (!Arrays.equals(currentState, newState)) {
            System.arraycopy(newState, 0, currentState, 0, 4);
            needsUpdate.add(playerId);
        }
    }

    private static void updatePlayerAttributes(Player player) {
        // Сначала удаляем все модификаторы
        removeAllBardModifiers(player);

        // Затем применяем актуальные модификаторы
        boolean[] armorState = armorStates.get(player.getUUID());
        if (armorState != null) {
            for (int i = 0; i < 4; i++) {
                if (armorState[i]) {
                    applyArmorModifiers(player, i);
                }
            }
        }
    }

    private static boolean[] getCurrentArmorState(Player player) {
        return new boolean[] {
                isWearingBardArmor(player, EquipmentSlot.HEAD),
                isWearingBardArmor(player, EquipmentSlot.CHEST),
                isWearingBardArmor(player, EquipmentSlot.LEGS),
                isWearingBardArmor(player, EquipmentSlot.FEET)
        };
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        UUID playerId = player.getUUID();

        // Устанавливаем текущее состояние брони
        boolean[] armorState = getCurrentArmorState(player);
        armorStates.put(playerId, armorState);

        // Помечаем для обновления атрибутов
        needsUpdate.add(playerId);
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID playerId = event.getEntity().getUUID();
        armorStates.remove(playerId);
        needsUpdate.remove(playerId);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        // Откладываем обновление до следующего тика
        needsUpdate.add(event.getEntity().getUUID());
    }

    private static void removeAllBardModifiers(Player player) {
        for (int i = 0; i < 4; i++) {
            ArmorModifiers modifiers = ARMOR_MODIFIERS.get(i);
            if (modifiers != null) {
                for (AttributeModifierInfo modifierInfo : modifiers.modifiers) {
                    Attribute attribute = modifierInfo.getAttribute();
                    if (attribute != null) {
                        var attributeInstance = player.getAttribute(attribute);
                        if (attributeInstance != null) {
                            attributeInstance.removeModifier(modifierInfo.modifierId);
                        }
                    }
                }
            }
        }
    }

    private static boolean isWearingBardArmor(Player player, EquipmentSlot slot) {
        ItemStack armorPiece = player.getItemBySlot(slot);
        return armorPiece.getItem() instanceof BardArmorItem &&
                ((BardArmorItem) armorPiece.getItem()).getMaterial() == BardArmor.SOUND;
    }

    private static void applyArmorModifiers(Player player, int armorType) {
        ArmorModifiers modifiers = ARMOR_MODIFIERS.get(armorType);
        if (modifiers != null) {
            for (AttributeModifierInfo modifierInfo : modifiers.modifiers) {
                Attribute attribute = modifierInfo.getAttribute();
                if (attribute != null) {
                    var attributeInstance = player.getAttribute(attribute);
                    if (attributeInstance != null) {
                        // Удаляем старый модификатор (если есть)
                        attributeInstance.removeModifier(modifierInfo.modifierId);
                        // Добавляем новый
                        attributeInstance.addPermanentModifier(modifierInfo.createModifier());
                    }
                }
            }
        }
    }
}
