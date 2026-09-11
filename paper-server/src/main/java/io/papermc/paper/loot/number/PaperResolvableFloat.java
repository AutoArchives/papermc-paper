package io.papermc.paper.loot.number;

import org.bukkit.craftbukkit.util.Handleable;
import org.bukkit.loot.LootContext;
import org.jspecify.annotations.NonNull;

public record PaperResolvableFloat(
    net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat impl
) implements ResolvableFloat, Handleable<net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat> {
    @Override
    public float resolve(final LootContext context, final float defaultValue) {
        return this.impl.get(toVanilla(context), defaultValue);
    }

    net.minecraft.world.level.storage.loot.LootContext toVanilla(LootContext context) {
        return null; // TODO - snapshot - convert to a vanilla loot context
    }

    @Override
    public net.minecraft.world.level.storage.loot.providers.number.floats.@NonNull ResolvableFloat getHandle() {
        return this.impl;
    }

    public static ResolvableFloat fromVanilla(net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat vanilla) {
        if (vanilla instanceof net.minecraft.world.level.storage.loot.providers.number.floats.ResolvableFloat.Constant constant) {
            return new PaperResolvableFloat.Constant(constant.getValue());
        }
        return new PaperResolvableFloat(vanilla);
    }

    record Constant(float value) implements ResolvableFloat.Constant {
        @Override
        public float getValue() {
            return this.value;
        }
    }
}
