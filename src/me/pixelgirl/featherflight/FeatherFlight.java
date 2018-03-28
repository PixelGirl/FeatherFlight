package me.jorislight.featherflight;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.UUID;

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
    HashMap<UUID, Boolean> Flying = new HashMap<>();

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
                World world = player.getWorld();
				if(args.length == 0){
					if(player.hasPermission(FlyPermission)) {
                        if (!player.getAllowFlight()){
                            Flying.put(player.getUniqueId(), true);
                            player.setAllowFlight(true);
                            player.setFlying(true);
                            world.playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 1000);
                            world.playSound(player.getLocation(), Sound.LEVEL_UP, 100f, 100f);
                            player.sendMessage(FlightOnSuccess);
                            return true;
                        } else if (player.getAllowFlight()){
                            Flying.put(player.getUniqueId(), false);
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            world.playEffect(player.getLocation(), Effect.EXPLOSION_LARGE, 1000);
                            world.playSound(player.getLocation(), Sound.LEVEL_UP, 100f, 100f);
                            player.sendMessage(FlightOffSuccess);
                            return true;
                        } else {
                            player.sendMessage(FeatherFlightPrefix + error);
                            return false;
                        }
                    }
                    else{
						player.sendMessage(NoPermission);
						return true;
					}
				}else if (args.length == 1){
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null){
						player.sendMessage(OnlinePlayerFindPrefix + CannotFindPlayer + " " + ChatColor.GRAY + args[0]);
						return true;
					}else{
                        World tworld = target.getWorld();
						if(player.hasPermission(FlyPermissionOthers)){
							if(!Flying.get(target.getUniqueId())){
								Flying.put(target.getUniqueId(), true);
								target.setAllowFlight(true);
								target.setFlying(true);
                                tworld.playEffect(target.getLocation(), Effect.EXPLOSION_LARGE, 1000);
                                tworld.playSound(target.getLocation(), Sound.LEVEL_UP, 100f, 100f);
                                world.playSound(player.getLocation(), Sound.LEVEL_UP, 100f, 100f);
								target.sendMessage(FeatherFlightPrefix + ChatColor.GRAY + player.getDisplayName() + ChatColor.RESET + ChatColor.GREEN + " " + "turned on your flight" + ChatColor.RESET);
								player.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "You turned on flight for" + " " + ChatColor.RESET + ChatColor.GRAY + target.getDisplayName() + ChatColor.RESET);
								return true;
							}else if(Flying.get(target.getUniqueId())){
								Flying.put(target.getUniqueId(), false);
								target.setAllowFlight(false);
								target.setFlying(false);
                                tworld.playEffect(target.getLocation(), Effect.EXPLOSION_LARGE, 1000);
                                tworld.playSound(target.getLocation(), Sound.LEVEL_UP, 100f, 100f);
                                world.playSound(player.getLocation(), Sound.LEVEL_UP, 100f, 100f);
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
				}else if(args.length > 1){
					player.sendMessage(TooManyArgs);
					return true;
				}else{
					player.sendMessage(FeatherFlightPrefix + error);
					return false;
				}
			}else{
				if(args.length == 0){
					sender.sendMessage(ConsoleDenied);
					return true;
				}else if(args.length == 1){
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null){
						sender.sendMessage(OnlinePlayerFindConsolePrefix + CannotFindPlayerConsole + " " + args[0]);
						return true;
					}else{
                        World tworld = target.getWorld();
						if(!Flying.get(target.getUniqueId())){
							Flying.put(target.getUniqueId(), true);
							target.setAllowFlight(true);
							target.setFlying(true);
                            tworld.playEffect(target.getLocation(), Effect.EXPLOSION_LARGE, 1000);
                            tworld.playSound(target.getLocation(), Sound.LEVEL_UP, 100f, 100f);
							target.sendMessage(FeatherFlightPrefix + ChatColor.GREEN + "Your flight has been turned on by" + ChatColor.RESET + " " + ChatColor.GRAY + "Console" + ChatColor.RESET);
							sender.sendMessage(FeatherFlightConsolePrefix + "You turned on flight for" + " " + target.getDisplayName());
							return true;
						}else if(Flying.get(target.getUniqueId())){
							Flying.put(target.getUniqueId(), false);
							target.setAllowFlight(false);
							target.setFlying(false);
                            tworld.playEffect(target.getLocation(), Effect.EXPLOSION_LARGE, 1000);
                            tworld.playSound(target.getLocation(), Sound.LEVEL_UP, 100f, 100f);
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
        try {
            if (Flying.get(player.getUniqueId())) {
                player.setAllowFlight(true);
                player.setFlying(true);
            }
        }catch(Exception excep){

        }
	}
}
