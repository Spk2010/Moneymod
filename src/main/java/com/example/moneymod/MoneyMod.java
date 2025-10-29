package com.example.moneymod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import java.util.HashMap;
import java.util.UUID;

public class MoneyMod implements ModInitializer {
    public static final String MOD_ID = "moneymod";
    private static final HashMap<UUID, Integer> BALANCES = new HashMap<>();

    @Override
    public void onInitialize() {
        System.out.println("💰 Money Mod loaded!");
    }

    public static int getBalance(ServerPlayerEntity player) {
        return BALANCES.getOrDefault(player.getUuid(), 0);
    }

    public static void addMoney(ServerPlayerEntity player, int amount) {
        BALANCES.put(player.getUuid(), getBalance(player) + amount);
        player.sendMessage(Text.literal("You now have $" + getBalance(player)), false);
    }

    public static boolean buyItem(ServerPlayerEntity player, int cost) {
        if (getBalance(player) >= cost) {
            BALANCES.put(player.getUuid(), getBalance(player) - cost);
            player.sendMessage(Text.literal("You spent $" + cost), false);
            return true;
        } else {
            player.sendMessage(Text.literal("You don't have enough money!"), false);
            return false;
        }
    }
}
