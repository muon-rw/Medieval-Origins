package dev.muon.medievalorigins.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class MirroringEnchantment extends Enchantment {
    public MirroringEnchantment(Rarity weight, EnchantmentCategory type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }
    @Override
    public boolean isTreasureOnly() {
        return true;
    }
    @Override
    public int getMinLevel() {
        return 1;
    }
    @Override
    public int getMaxLevel() {
        return 1;
    }
}
