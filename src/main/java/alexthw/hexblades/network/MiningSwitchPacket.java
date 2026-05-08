package alexthw.hexblades.network;

import alexthw.hexblades.common.items.tier1.EarthHammer1;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MiningSwitchPacket {
    public MiningSwitchPacket() {
    }

    public static void encode(MiningSwitchPacket object, FriendlyByteBuf buffer) {
    }

    public static MiningSwitchPacket decode(FriendlyByteBuf buffer) {
        return new MiningSwitchPacket();
    }

    public static void consume(MiningSwitchPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = ctx.get().getSender();

            if (player != null && !player.level().isClientSide()) {
                ItemStack IStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (IStack.getItem() instanceof EarthHammer1 hexblade) {
                    hexblade.switchMining(IStack);
                    hexblade.recalculatePowers(IStack, player.level(), player);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
