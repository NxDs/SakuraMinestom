package net.minestom.server.event.player;

import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.ItemEvent;
import net.minestom.server.event.trait.PlayerInstanceEvent;
import net.minestom.server.instance.block.BlockFace;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Used when a player is clicking on a block with an item (but is not a block in item form).
 */
public class PlayerUseItemOnBlockEvent implements PlayerInstanceEvent, ItemEvent, CancellableEvent {

    private final Player player;
    private final Player.Hand hand;
    private final ItemStack itemStack;
    private final Point position;
    private final Point cursorPosition;
    private final BlockFace blockFace;

    private boolean cancelled = false;

    public PlayerUseItemOnBlockEvent(@NotNull Player player, @NotNull Player.Hand hand,
                                     @NotNull ItemStack itemStack,
                                     @NotNull Point position, @NotNull Point cursorPosition,
                                     @NotNull BlockFace blockFace) {
        this.player = player;
        this.hand = hand;
        this.itemStack = itemStack;
        this.position = position;
        this.cursorPosition = cursorPosition;
        this.blockFace = blockFace;
    }

    /**
     * Gets the position of the interacted block.
     *
     * @return the block position
     */
    public @NotNull Point getPosition() {
        return position;
    }

    /**
     * Gets the position of the cursor on the interacted block.
     *
     * @return the cursor position
     */
    public @NotNull Point getCursorPosition() {
        return cursorPosition;
    }

    /**
     * Gets which face the player has interacted with.
     *
     * @return the block face
     */
    public @NotNull BlockFace getBlockFace() {
        return blockFace;
    }

    /**
     * Gets which hand the player used to interact with the block.
     *
     * @return the hand
     */
    public @NotNull Player.Hand getHand() {
        return hand;
    }

    /**
     * Gets with which item the player has interacted with the block.
     *
     * @return the item
     */
    @Override
    public @NotNull ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public @NotNull Player getPlayer() {
        return player;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
