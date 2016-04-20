/**
 * This file is part of XtraPunish, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016 - 2016 XtraStudio <https://github.com/XtraStudio>
 * Copyright (c) Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.awesomeman.xtrapunish.punish;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.type.GridInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.awesomeman.xtrapunish.util.CmdUtil;
import com.awesomeman.xtrapunish.util.CommandBase;

public class PlayerSponge implements CommandBase {

    private List<PlayerSpongeStore> history = new ArrayList<>();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> optional = args.<Player>getOne("player");
        if (!optional.isPresent()) {
            src.sendMessage(Text.of(TextColors.RED, "Player argument not specified! Correct usage: /punish sponge <player>"));
            return CommandResult.empty();
        }
        Player player = optional.get();

        Inventory inv = player.getInventory().query(GridInventory.class);
        Inventory hotbar = player.getInventory().query(Hotbar.class);

        List<ItemStack> items = new ArrayList<>();
        List<Inventory> slots = new ArrayList<>();

        iterateSlots(inv, items, slots);
        iterateSlots(hotbar, items, slots);

        history.add(new PlayerSpongeStore(items, slots));
        return CommandResult.success();
    }

    public void iterateSlots(Inventory inv, List<ItemStack> items, List<Inventory> slots) {
        ItemStack sponge = ItemStack.builder().itemType(ItemTypes.SPONGE).build();
        for (Inventory slot : inv.slots()) {
            Optional<ItemStack> stack = slot.poll();
            if (stack.isPresent()) {
                items.add(stack.get());
            } else {
                items.add(ItemStack.of(ItemTypes.NONE, 0));
            }
            slots.add(slot);
            slot.set(sponge);
        }
    }

    @Override
    public String description() {
        return "Sets everything in a player's inventory to sponge.";
    }

    @Override
    public String[] command() {
        return new String[] {"sponge"};
    }

    @Override
    public CommandSpec commandSpec() {
        return CommandSpec.builder()
                .permission("xtrapunish.sponge")
                .description(Text.of(description()))
                .arguments(GenericArguments.optional(GenericArguments
                        .onlyOne(GenericArguments.player(Text.of("player")))))
                .executor(this)
                .build();
    }

    @Override
    public CmdUtil.UndoSuccess undoRecent() {
        PlayerSpongeStore store = history.get(history.size() - 1);
        for (int i = 0; i < store.items.size(); i++) {
            store.slots.get(i).set(store.items.get(i));
        }
        history.remove(store);
        return CmdUtil.UndoSuccess.SUCCESS;
    }

    @Override
    public boolean supportsUndo() {
        return true;
    }

    public class PlayerSpongeStore {

        public List<ItemStack> items;
        public List<Inventory> slots;

        public PlayerSpongeStore(List<ItemStack> items, List<Inventory> slots) {
            this.items = items;
            this.slots = slots;
        }
    }
}
