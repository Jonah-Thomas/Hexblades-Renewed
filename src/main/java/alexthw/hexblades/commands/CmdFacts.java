package alexthw.hexblades.commands;

import alexthw.hexblades.commands.args.FactArgumentType;
import alexthw.hexblades.deity.HexFacts;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import elucent.eidolon.util.KnowledgeUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class CmdFacts {

    private static final CmdFacts CMD = new CmdFacts();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("facts")
                .requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("give")
                                .then(Commands.argument("fact", new FactArgumentType())
                                        .executes(ctx -> CMD.giveFact(EntityArgument.getPlayer(ctx, "player"), ctx.getArgument("fact", String.class)))
                                )
                        )
                        .then(Commands.literal("has")
                                .then(Commands.argument("fact", new FactArgumentType())
                                        .executes(ctx -> CMD.hasFact(ctx, EntityArgument.getPlayer(ctx, "player"), ctx.getArgument("fact", String.class)))
                                )
                        )
                );
    }

    public int giveFact(Player player, String factName) {
        ResourceLocation fact = switch (factName) {
            case "awakening_ritual" -> HexFacts.AWAKENING_RITUAL;
            case "evolution_ritual" -> HexFacts.EVOLVE_RITUAL;
            case "elemental_summoning" -> HexFacts.ELEMENTAL_SUMMON;
            case "villager_sacrifice" -> HexFacts.ELEMENTAL_SUMMON;
            default -> null;
        };
        if (fact == null) return 0;

        if (!KnowledgeUtil.knowsFact(player, fact)) {
            KnowledgeUtil.grantFact(player, fact);
        }

        return Command.SINGLE_SUCCESS;
    }

    public int hasFact(CommandContext<CommandSourceStack> ctx, Player player, String factName) {
        ResourceLocation fact = switch (factName) {
            case "awakening_ritual" -> HexFacts.AWAKENING_RITUAL;
            case "evolution_ritual" -> HexFacts.EVOLVE_RITUAL;
            case "star_infusion" -> HexFacts.ELEMENTAL_SUMMON;
            case "villager_sacrifice" -> HexFacts.ELEMENTAL_SUMMON;
            default -> null;
        };
        if (fact == null) return 0;

        if (KnowledgeUtil.knowsFact(player, fact)) {
            ctx.getSource().sendSuccess(() -> Component.literal("true"), false);
        } else {
            ctx.getSource().sendFailure(Component.literal("false"));
        }
        return Command.SINGLE_SUCCESS;
    }
}
