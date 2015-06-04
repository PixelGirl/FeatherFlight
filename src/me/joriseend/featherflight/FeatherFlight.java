package me.joriseend.featherflight;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
public class FeatherFlight extends JavaPlugin implements Listener{
	
	String Name = "FeatherFlight";
	String FeatherFlightPrefix = ChatColor.DARK_GRAY + "Flight>" + ChatColor.RESET + " ";
	String FeatherFlightConsolePrefix = "[" + Name + "]" + " ";
	String OnlinePlayerFindPrefix = ChatColor.DARK_GRAY + "Online Player Search>" + ChatColor.RESET + " ";
	String OnlinePlayerFindConsolePrefix = "[Online Player Search]" + " ";
	String CannotFindPlayer = ChatColor.RED + "Cannot find player" + ChatColor.RESET;
	String CannotFindPlayerConsole = "Cannot find player";
	String error = ChatColor.RED + "Something went wrong!";
	String NoPermission = FeatherFlightPrefix + ChatColor.RED + "Many permissions, such denied, much no. Wow.";
	String ConsoleDenied = "Such console, so denied. Wow.";
	String Enabled = FeatherFlightConsolePrefix + Name + " is now levitating!";
	String Disabled = FeatherFlightConsolePrefix + Name + " is now slowly sinking to the ground.";
	String FlightOnSuccess = FeatherFlightPrefix + ChatColor.GREEN + "Flight is now turned on";
	String FlightOffSuccess = FeatherFlightPrefix + ChatColor.GREEN + "Flight is now turned off";
	String FlightChangeSuccess = FeatherFlightPrefix + ChatColor.GREEN + "Your flight has been changed";
	String TooManyArgs = FeatherFlightPrefix + ChatColor.RED + "Too many arguments! Please specify 1 player at the time!";
	String FlyPermission = "featherflight.fly";
	String FlyPermissionOthers = "featherflight.fly.others";
	public String ConfigArgs = ".Flying";
	String ConfigNameArg = ".username";
	boolean Flying = false;
	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
		System.out.println(Enabled);
	}
	public void onDisable(){
		System.out.println(Disabled);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(command.getName().equalsIgnoreCase("fly")){
			if((sender instanceof Player)){
				Player player = (Player) sender;
				if(args.length == 0){
					if(player.hasPermission(FlyPermission)){
						if(getConfig().getBoolean(player.getUniqueId().toString() + ConfigArgs) == false){
							getConfig().set(player.getUniqueId().toString() + ConfigArgs, true);
							getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
							saveConfig();
							player.setAllowFlight(true);
							player.setFlying(true);
							player.sendMessage(FlightOnSuccess);
							return true;
						}else if(getConfig().getBoolean(player.getUniqueId().toString() + ConfigArgs) == true){
							getConfig().set(player.getUniqueId().toString() + ConfigArgs, false);
							getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
							saveConfig();
							player.setAllowFlight(false);
							player.setFlying(false);
							player.sendMessage(FlightOffSuccess);
							return true;
						}else{
							player.sendMessage(FeatherFlightPrefix + error);
							return false;
						}
					}else{
						player.sendMessage(NoPermission);
						return true;
					}
				}else if (args.length == 1){
					if(args[0] == "true"){
						if(player.hasPermission(FlyPermission)){
							getConfig().set(player.getUniqueId().toString() + ConfigArgs, true);
							getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
							saveConfig();
							player.setAllowFlight(true);
							player.setFlying(true);
							player.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "Flight of" + ChatColor.RESET + " " + ChatColor.GRAY + player.getDisplayName() +  ChatColor.RESET + " " + ChatColor.GREEN + "=" + ChatColor.RESET + " " + ChatColor.YELLOW + getConfig().getBoolean(player.getUniqueId().toString() + ConfigArgs));
						}else{
							player.sendMessage(NoPermission);
						}
					}else if(args[0] == "false"){
						if(player.hasPermission(FlyPermission)){
							getConfig().set(player.getUniqueId().toString() + ConfigArgs, false);
							getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
							saveConfig();
							player.setAllowFlight(false);
							player.setFlying(false);
							player.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "Flight of" + ChatColor.RESET + " " + ChatColor.GRAY + player.getDisplayName() + ChatColor.RESET + " " + ChatColor.GREEN + "=" + ChatColor.RESET + " " + ChatColor.YELLOW + getConfig().getBoolean(player.getUniqueId().toString() + ConfigArgs));
						}else{
							player.sendMessage(NoPermission);
						}
					}else{
						Player target = Bukkit.getPlayer(args[0]);
						if(target == null){
							player.sendMessage(OnlinePlayerFindPrefix + CannotFindPlayer + " " + ChatColor.GRAY + args[0]);
							return true;
						}else{
							if(player.hasPermission(FlyPermissionOthers)){
								if(getConfig().getBoolean(target.getUniqueId().toString() + ConfigArgs) == false){
									getConfig().set(target.getUniqueId().toString() + ConfigArgs, true);
									getConfig().set(target.getUniqueId().toString() + ConfigNameArg, target.getDisplayName());
									getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
									saveConfig();
									target.setAllowFlight(true);
									target.setFlying(true);
									target.sendMessage(FeatherFlightPrefix + ChatColor.GRAY + player.getDisplayName() + ChatColor.RESET + ChatColor.GREEN + " " + "turned on your flight" + ChatColor.RESET);
									player.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "You turned on flight for" + " " + ChatColor.RESET + ChatColor.GRAY + target.getDisplayName() + ChatColor.RESET);
									return true;
								}else if(getConfig().getBoolean(target.getUniqueId().toString() + ConfigArgs) == true){
									getConfig().set(target.getUniqueId().toString() + ConfigArgs, false);
									getConfig().set(target.getUniqueId().toString() + ConfigNameArg, target.getDisplayName());
									getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
									saveConfig();
									target.setAllowFlight(false);
									target.setFlying(false);
									target.sendMessage(FeatherFlightPrefix + ChatColor.GRAY + player.getDisplayName() + ChatColor.RESET + ChatColor.GREEN + " " + "turned off your flight" + ChatColor.RESET);
									player.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "You turned off flight for" + " " + ChatColor.RESET + ChatColor.GRAY + target.getDisplayName() + ChatColor.RESET);
									return true;
								}else{
									player.sendMessage(FeatherFlightPrefix + error);
									return false;
								}
							}else{
								player.sendMessage(NoPermission);
								return true;
							}
						}
					}
				}else if(args.length == 2){
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null){
						player.sendMessage(OnlinePlayerFindPrefix + CannotFindPlayer + " " + ChatColor.GRAY + args[0]);
						return true;
					}else{
						if(player.hasPermission(FlyPermissionOthers)){
							if(args[1] == "true"){
								getConfig().set(target.getUniqueId().toString() + ConfigArgs, true);
								getConfig().set(target.getUniqueId().toString() + ConfigNameArg, target.getDisplayName());
								getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
								saveConfig();
								target.setAllowFlight(true);
								target.setFlying(true);
								target.sendMessage(FlightChangeSuccess);
								player.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "Flight of" + ChatColor.RESET + " " + ChatColor.GRAY + target.getDisplayName() + ChatColor.RESET + " " + ChatColor.GREEN + "=" + ChatColor.RESET + " " + ChatColor.YELLOW + getConfig().getBoolean(target.getUniqueId().toString() + ConfigArgs));
								return true;
							}else if(args[1] == "false"){
								getConfig().set(target.getUniqueId().toString() + ConfigArgs, false);
								getConfig().set(target.getUniqueId().toString() + ConfigNameArg, target.getDisplayName());
								getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
								saveConfig();
								target.setAllowFlight(false);
								target.setFlying(false);
								target.sendMessage(FlightChangeSuccess);
								player.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "Flight of" + ChatColor.RESET + " " + ChatColor.GRAY + target.getDisplayName() + ChatColor.RESET + " " + ChatColor.GREEN + "=" + ChatColor.RESET + " " + ChatColor.YELLOW + getConfig().getBoolean(target.getUniqueId().toString() + ConfigArgs));
								return true;
							}
						}else{
							player.sendMessage(NoPermission);
							return true;
						}
					}
				}else if(args.length > 2){
					player.sendMessage(TooManyArgs);
					return true;
				}else{
					player.sendMessage(FeatherFlightPrefix + error);
					return false;
				}
			}else{
				if(args.length == 0){
					sender.sendMessage(ConsoleDenied);
				}else if(args.length == 1){
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null){
						sender.sendMessage(OnlinePlayerFindConsolePrefix + CannotFindPlayerConsole + " " + args[0]);
						return true;
					}else{
						if(getConfig().getBoolean(target.getUniqueId().toString() + ConfigArgs) == false){
							getConfig().set(target.getUniqueId().toString() + ConfigArgs, true);
							getConfig().set(target.getUniqueId().toString() + ConfigNameArg, target.getDisplayName());
							saveConfig();
							target.setAllowFlight(true);
							target.setFlying(true);
							target.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "Your flight has been turned on by" + ChatColor.RESET + " " + ChatColor.GRAY + "Console" + ChatColor.RESET);
							sender.sendMessage(FeatherFlightConsolePrefix + "You turned on flight for" + " " + target.getDisplayName());
							return true;
						}else if(getConfig().getBoolean(target.getUniqueId().toString() + ConfigArgs) == true){
							getConfig().set(target.getUniqueId().toString() + ConfigArgs, false);
							getConfig().set(target.getUniqueId().toString() + ConfigNameArg, target.getDisplayName());
							target.setAllowFlight(false);
							target.setFlying(false);
							target.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "Your flight has been turned off by" + ChatColor.RESET + " " + ChatColor.GRAY + "Console" + ChatColor.RESET);
							sender.sendMessage(FeatherFlightConsolePrefix + "You turned off flight for" + " " + target.getDisplayName());
							return true;
						}else{
							sender.sendMessage(FeatherFlightConsolePrefix + error);
							return false;
						}
					}
				}
			}
		}
		return false;
	}
	@EventHandler
	public void onLogin(PlayerJoinEvent e){
		Player player = e.getPlayer();
		getConfig().set(player.getUniqueId().toString() + ConfigNameArg, player.getDisplayName());
		if(getConfig().getBoolean(player.getUniqueId().toString() + ConfigArgs) == true){
			player.setAllowFlight(true);
			player.setFlying(true);
		}
	}
}
