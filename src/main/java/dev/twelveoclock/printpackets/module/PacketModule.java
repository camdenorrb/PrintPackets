package dev.twelveoclock.printpackets.module;

import dev.twelveoclock.printpackets.module.base.PluginModule;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;


// TODO: Store this information in memory and then can be retrieved via commands
// TODO: Use to help generate packet enum in minecraftPackets
// TODO: Be able to provide a minecraft-server.jar to pull from
@SuppressWarnings({"rawtypes", "unchecked"})
public final class PacketModule extends PluginModule {

	private static final String CLASS_TO_DEBUG = "PacketPlayOutViewCentre";

	public PacketModule(final JavaPlugin plugin) {
		super(plugin);
	}

	@Override
	protected void onEnable() {
		plugin.getServer().getScheduler().runTaskLater(plugin, this::printConnectionProtocols, 40L);
	}

	private void printConnectionProtocols() {
		try {
			final Object[] connectionProtocols = Class.forName("net.minecraft.network.EnumProtocol").getEnumConstants();
			for (final Object connectionProtocol : connectionProtocols) {

				System.out.println("Connection Protocol: " + connectionProtocol);

				final Map<?, ?> flows = getFlowsFromConnectionProtocol(connectionProtocol);

				Objects.requireNonNull(flows).forEach((key, val) -> {
					System.out.println(connectionProtocol + " Direction: " + key);
					printPacketSet(val);
				});
			}
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private Map getFlowsFromConnectionProtocol(final Object connectionProtocol) {
		try {

			for (final Field field : connectionProtocol.getClass().getDeclaredFields()) {

				if (!field.getType().getName().equals("java.util.Map")) {
					continue;
				}

				field.setAccessible(true);

				final Map value = (Map) field.get(connectionProtocol);

				if (!value.values().stream().findFirst().get().getClass().getName().equals("net.minecraft.network.EnumProtocol$a")) {
					continue;
				}

				return value;
			}
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	private Object2IntMap getClassToIdFromPacketSet(final Object packetSet) {

		for (final Field declaredField : packetSet.getClass().getDeclaredFields()) {

			if (!declaredField.getType().getName().equals("it.unimi.dsi.fastutil.objects.Object2IntMap")) {
				continue;
			}

			declaredField.setAccessible(true);

			try {
				return (Object2IntMap) declaredField.get(packetSet);
			} catch (final IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

		return null;
	}

	private void printPacketSet(final Object packetSet) {

		// Get class to id
		final Object2IntMap<Object> classToId = getClassToIdFromPacketSet(packetSet);
		final Map<Object, Integer> classToIdJavaMap = new HashMap<>(Objects.requireNonNull(classToId));

		// Print class to id
		classToIdJavaMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach((entry) -> {
			System.out.printf("Class: " + entry.getKey() + " ID: 0x%02x \n", entry.getValue());
		});

		System.out.println();
	}

	private void printInfoAboutPacket(Class<Object> packet) {
		System.out.println("Packet: " + packet.getName());


	}


}


