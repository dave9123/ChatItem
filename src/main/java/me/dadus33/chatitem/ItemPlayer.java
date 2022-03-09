package me.dadus33.chatitem;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.dadus33.chatitem.playerversion.PlayerVersionManager;
import me.dadus33.chatitem.utils.Version;

public class ItemPlayer {

	private static final HashMap<UUID, ItemPlayer> PLAYERS = new HashMap<>();
	public static ItemPlayer getPlayer(Player p) {
		return PLAYERS.computeIfAbsent(p.getUniqueId(), ItemPlayer::new);
	}
	public static ItemPlayer getPlayer(UUID uuid) {
		return PLAYERS.computeIfAbsent(uuid, ItemPlayer::new);
	}
	private final UUID uuid;
	private int protocolVersion = 0;
	private Version version = Version.getVersion();
	private String clientName = "unknow";
	
	public ItemPlayer(UUID uuid) {
		this.uuid = uuid;
		Player p = Bukkit.getPlayer(uuid);
		setVersion(PlayerVersionManager.getPlayerVersionAdapter().getPlayerVersion(p));
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public String getClientName() {
		return clientName;
	}
	
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	public int getProtocolVersion() {
		return protocolVersion;
	}
	
	public void setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
		if(version == Version.getVersion()) {// if is default
			version = Version.getVersion(protocolVersion);
			ChatItem.debug("Detected version " + version.name() + " (protocol: " + protocolVersion + ")");
		}
	}
	
	public Version getVersion() {
		return version;
	}
	
	public void setVersion(Version version) {
		this.version = version;
		if(protocolVersion == 0)
			this.protocolVersion = version.MAX_VER;
	}
	
	@Override
	public String toString() {
		return "ItemPlayer[version=" + version.name() + ",protocol=" + protocolVersion + ",client=" + clientName + "]";
	}
}
