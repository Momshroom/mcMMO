package com.gmail.nossr50.commands.player;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.nossr50.commands.CommandHelper;
import com.gmail.nossr50.datatypes.McMMOPlayer;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.locale.LocaleLoader;
import com.gmail.nossr50.skills.utilities.SkillType;
import com.gmail.nossr50.util.Misc;
import com.gmail.nossr50.util.Permissions;
import com.gmail.nossr50.util.Users;

public class InspectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String usage = LocaleLoader.getString("Commands.Usage.1", "inspect", "<" + LocaleLoader.getString("Commands.Usage.Player") + ">");

        if (CommandHelper.noCommandPermissions(sender, "mcmmo.commands.inspect")) {
            return true;
        }

        switch (args.length) {
        case 1:
            McMMOPlayer mcmmoPlayer = Users.getPlayer(args[0]);

            if (mcmmoPlayer != null) {
                Player target = mcmmoPlayer.getPlayer();

                if (sender instanceof Player && !Misc.isNear(((Player) sender).getLocation(), target.getLocation(), 5.0) && !Permissions.inspectFar((Player) sender)) {
                    sender.sendMessage(LocaleLoader.getString("Inspect.TooFar"));
                    return true;
                }

                PlayerProfile profile = mcmmoPlayer.getProfile();

                sender.sendMessage(LocaleLoader.getString("Inspect.Stats", target.getName()));
                CommandHelper.printGatheringSkills(target, profile, sender);
                CommandHelper.printCombatSkills(target, profile, sender);
                CommandHelper.printMiscSkills(target, profile, sender);
                sender.sendMessage(LocaleLoader.getString("Commands.PowerLevel", mcmmoPlayer.getPowerLevel()));

                return true;
            }

            if (sender instanceof Player && !Permissions.inspectOffline((Player) sender)) {
                sender.sendMessage(LocaleLoader.getString("Inspect.Offline"));
                return true;
            }

            PlayerProfile profile = new PlayerProfile(args[0], false); //Temporary Profile

            if (!profile.isLoaded()) {
                sender.sendMessage(LocaleLoader.getString("Commands.DoesNotExist"));
                return true;
            }

            sender.sendMessage(LocaleLoader.getString("Inspect.OfflineStats", args[0]));

            sender.sendMessage(LocaleLoader.getString("Stats.Header.Gathering"));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Excavation.Listener"), profile.getSkillLevel(SkillType.EXCAVATION), profile.getSkillXpLevel(SkillType.EXCAVATION), profile.getXpToLevel(SkillType.EXCAVATION)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Fishing.Listener"), profile.getSkillLevel(SkillType.FISHING), profile.getSkillXpLevel(SkillType.FISHING), profile.getXpToLevel(SkillType.FISHING)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Herbalism.Listener"), profile.getSkillLevel(SkillType.HERBALISM), profile.getSkillXpLevel(SkillType.HERBALISM), profile.getXpToLevel(SkillType.HERBALISM)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Mining.Listener"), profile.getSkillLevel(SkillType.MINING), profile.getSkillXpLevel(SkillType.MINING), profile.getXpToLevel(SkillType.MINING)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Woodcutting.Listener"), profile.getSkillLevel(SkillType.WOODCUTTING), profile.getSkillXpLevel(SkillType.WOODCUTTING), profile.getXpToLevel(SkillType.WOODCUTTING)));

            sender.sendMessage(LocaleLoader.getString("Stats.Header.Combat"));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Axes.Listener"), profile.getSkillLevel(SkillType.AXES), profile.getSkillXpLevel(SkillType.AXES), profile.getXpToLevel(SkillType.AXES)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Archery.Listener"), profile.getSkillLevel(SkillType.ARCHERY), profile.getSkillXpLevel(SkillType.ARCHERY), profile.getXpToLevel(SkillType.ARCHERY)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Swords.Listener"), profile.getSkillLevel(SkillType.SWORDS), profile.getSkillXpLevel(SkillType.SWORDS), profile.getXpToLevel(SkillType.SWORDS)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Taming.Listener"), profile.getSkillLevel(SkillType.TAMING), profile.getSkillXpLevel(SkillType.TAMING), profile.getXpToLevel(SkillType.TAMING)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Unarmed.Listener"), profile.getSkillLevel(SkillType.UNARMED), profile.getSkillXpLevel(SkillType.UNARMED), profile.getXpToLevel(SkillType.UNARMED)));

            sender.sendMessage(LocaleLoader.getString("Stats.Header.Misc"));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Acrobatics.Listener"), profile.getSkillLevel(SkillType.ACROBATICS), profile.getSkillXpLevel(SkillType.ACROBATICS), profile.getXpToLevel(SkillType.ACROBATICS)));
            sender.sendMessage(LocaleLoader.getString("Skills.Stats", LocaleLoader.getString("Repair.Listener"), profile.getSkillLevel(SkillType.REPAIR), profile.getSkillXpLevel(SkillType.REPAIR), profile.getXpToLevel(SkillType.REPAIR)));

            return true;

        default:
            sender.sendMessage(usage);
            return true;
        }
    }
}
