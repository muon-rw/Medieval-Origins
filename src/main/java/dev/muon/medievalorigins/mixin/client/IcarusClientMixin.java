package dev.muon.medievalorigins.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.cammiescorner.icarus.client.IcarusClient;
import dev.cammiescorner.icarus.util.IcarusHelper;
import dev.muon.medievalorigins.enchantment.ModEnchantments;
import dev.muon.medievalorigins.power.IcarusWingsPowerType;
import dev.muon.medievalorigins.power.PixieWingsPowerType;
import dev.muon.medievalorigins.util.ItemDataUtil;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.type.PowerType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(IcarusClient.class)
public abstract class IcarusClientMixin {

    /*
     * TODO: Rewrite to be less invasive
     *  Use ModifyExpressionValue, target getArmorValue
     */
    @ModifyVariable(method = "onPlayerTick(Lnet/minecraft/world/entity/player/Player;)V",
            at = @At(value = "STORE", opcode = Opcodes.FSTORE),
            ordinal = 0)
    private static float modifyArmorModifier(float modifier, Player player) {
        var cfg = IcarusHelper.getConfigValues(player);
        int armorValueSum = 0;
        Iterable<ItemStack> armorItems = player.getArmorSlots();
        for (ItemStack armorItem : armorItems) {
            if (armorItem.isEmpty() || ItemDataUtil.getEnchantmentLevel(armorItem, ModEnchantments.FEATHERWEIGHT, player.level()) > 0) {
                continue;
            }
            if (armorItem.getItem() instanceof ArmorItem armor) {
                armorValueSum += armor.getDefense();
            }
        }
        return cfg.armorSlows() ? Math.max(1.0F, armorValueSum / 20.0F * cfg.maxSlowedMultiplier()) : 1.0F;
    }

    @ModifyReturnValue(method = "getWingsForRendering", at = @At(value = "RETURN"))
    private static ItemStack renderOriginWings(ItemStack original, LivingEntity entity) {
        var powerHolder = PowerHolderComponent.getOptional(entity);
        if (powerHolder.isEmpty()) return original;

        if (original.isEmpty()) {
            var icarusWings = powerHolder.get().getPowerTypes(IcarusWingsPowerType.class).stream()
                    .filter(PowerType::isActive)
                    .findFirst();
            if (icarusWings.isPresent()) {
                return icarusWings.get().getWingsType();
            }
        } else {
            var pixieWings = powerHolder.get().getPowerTypes(PixieWingsPowerType.class).stream()
                    .filter(PowerType::isActive)
                    .findFirst();
            if (pixieWings.isPresent()) {
                return new ItemStack(Items.AIR);
            }
        }
        return original;
    }
}