package dev.muon.medievalorigins.condition.item;

import dev.muon.medievalorigins.condition.ModItemConditionTypes;
import io.github.apace100.apoli.condition.ConditionConfiguration;
import io.github.apace100.apoli.condition.type.ItemConditionType;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IsSummonWeaponConditionType extends ItemConditionType {
    @Override
    public boolean test(Level world, ItemStack stack) {
        return stack.getItem() instanceof BowItem ||
                stack.getItem() instanceof DiggerItem ||
                stack.getItem() instanceof SwordItem;
    }

    @Override
    public @NotNull ConditionConfiguration<?> getConfig() {
        return ModItemConditionTypes.IS_SUMMON_WEAPON;
    }
}