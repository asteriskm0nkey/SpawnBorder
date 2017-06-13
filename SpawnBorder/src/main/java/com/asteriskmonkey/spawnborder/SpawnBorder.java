package com.asteriskmonkey.spawnborder;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommand;
import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommandBuilder;
import com.asteriskmonkey.spawnborder.BorderCommand.BorderCommandExecutor;
import com.asteriskmonkey.spawnborder.BorderStrategies.Completion.ColumnCompletionStrategy;
import com.asteriskmonkey.spawnborder.BorderStrategies.Locations.SimpleBorderLocationStrategy;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidArgumentException;
import com.asteriskmonkey.spawnborder.Exceptions.InvalidOptionException;

public class SpawnBorder extends JavaPlugin {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		// TODO commands: border, undoborder?

		if (cmd.getName().equalsIgnoreCase("border")) {

			boolean usageTextRequired = false;

			if (sender.getName() == null) {
				getLogger().info(cmd.getName() + " called by unknown sender");
				throw new RuntimeException("Unknown sender");
			}

			Options possibleOpts = BorderCommand.getCLIOptions();

			// HashMap of the options and the values that are to be passed to the builder
			Map<String, String> optionMap = new HashMap<>();

			try {

				CommandLineParser parser = new DefaultParser();
				CommandLine cmnd = parser.parse(possibleOpts, args);

				for (Option o : cmnd.getOptions()) {
					String optName = o.getLongOpt();
					if (cmnd.hasOption(optName)) {
						String optStr = cmnd.getOptionValue(optName);
						if (!optionMap.containsKey(optName)) {
							optionMap.put(optName, optStr);
						} else {
							throw new InvalidArgumentException("Duplicate argument " + optName);
						}
					}
				}
				
				// If the command was called by a player and no "center" argument was provided, use the player's location
				if (!cmnd.hasOption("center") && sender.getName() != "CONSOLE") {
					if (sender instanceof Player) {
						Player p = (Player)sender;
						Location pLoc = p.getLocation();
						World w = p.getWorld();
						Environment env = w.getEnvironment();
						
						optionMap.put("center", pLoc.getX() + ","  + pLoc.getY() + "," + pLoc.getZ());
						optionMap.put("world", w.getName());
						optionMap.put("environment", env.name());
					}
				}
				
				if (cmnd.hasOption("help")) {
					usageTextRequired = true;
				}

			} catch (ParseException e1) {
				sender.sendMessage("ParseException: " + e1.getMessage());
				e1.printStackTrace();
				usageTextRequired = true;

			} catch (InvalidArgumentException e2) {
				sender.sendMessage(e2.getMessage());
				usageTextRequired = true;
			}

			//optionMap.forEach((k, v) -> getLogger().info("k: " + k + ", v: " + v));

			
			
			try {
				BorderCommandBuilder commandBuilder = new BorderCommandBuilder(optionMap);
				BorderCommand bc = commandBuilder.build();
				// Output the description of the bordercommand
				sender.sendMessage(bc.toString());
				
				// Execute the border command, affecting the world with the defined strategies
				BorderCommandExecutor bce = new BorderCommandExecutor(
						new SimpleBorderLocationStrategy(),
						new ColumnCompletionStrategy());
				bce.execute(bc);
				
			} catch (InvalidArgumentException e) {
				sender.sendMessage(e.getMessage());
				usageTextRequired = true;
			} catch (InvalidOptionException e) {
				sender.sendMessage(e.getMessage());
				usageTextRequired = true;
			}  catch (Exception e) {
				sender.sendMessage(e.getMessage());
				e.printStackTrace();
			}

			if (usageTextRequired) {
				// TODO pretty print the usage text. Modify the getUsageText to format it nicer. 
				sender.sendMessage(BorderCommand.getUsageText());
			}

		}

		return true;
	}

}
