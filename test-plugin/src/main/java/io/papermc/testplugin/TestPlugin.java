package io.papermc.testplugin;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.BrewingFuel;
import io.papermc.paper.datacomponent.item.CookingFuel;
import io.papermc.paper.datacomponent.item.SignText;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);

        // io.papermc.testplugin.brigtests.Registration.registerViaOnEnable(this);

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(Commands.literal("test")
                .executes((ctx) -> {
                    Player p = ctx.getSource().getPlayerOrThrow();
                    p.sendPlainMessage("Test");
                    ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
                    CookingFuel cookingFuel = itemInMainHand.getData(DataComponentTypes.COOKING_FUEL);
                    p.sendPlainMessage("cooking fuel " + cookingFuel.burnTime().resolve(null, 0) + " " + cookingFuel.speedMultiplier().resolve(null, 0));
                    BrewingFuel brewingFuel = itemInMainHand.getData(DataComponentTypes.BREWING_FUEL);
                    p.sendPlainMessage("brewing fuel " + brewingFuel.uses().resolve(null, 0) + " " + brewingFuel.speedMultiplier().resolve(null, 0));
                    itemInMainHand.setData(DataComponentTypes.COOKING_FUEL, CookingFuel.cookingFuel().burnTime(1).speedMultiplier(1000).build());
                    itemInMainHand.setData(DataComponentTypes.BREWING_FUEL, BrewingFuel.brewingFuel().uses(1).speedMultiplier(1000).build());
                    return 1;
                }).build()
            );
        });
    }
}
