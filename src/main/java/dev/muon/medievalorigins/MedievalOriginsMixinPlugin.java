package dev.muon.medievalorigins;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MedievalOriginsMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals("dev.muon.medievalorigins.mixin.client.IcarusClientMixin") ||
           (mixinClassName.equals("dev.muon.medievalorigins.mixin.IcarusHelperMixin"))) {
            return FabricLoader.getInstance().isModLoaded("icarus");
        }
        if (mixinClassName.equals("dev.muon.medievalorigins.mixin.SpellCooldownMixin") ||
                (mixinClassName.equals("dev.muon.medievalorigins.mixin.SpellHelperMixin"))) {
            return FabricLoader.getInstance().isModLoaded("spell_engine");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}