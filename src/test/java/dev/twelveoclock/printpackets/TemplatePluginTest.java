package dev.twelveoclock.printpackets;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Sound;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

final class TemplatePluginTest {

    private static ServerMock server;

    private static PrintPackets plugin;


    @BeforeAll
    static void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(PrintPackets.class);
    }


    @Test
    void join() {
        final PlayerMock player = server.addPlayer();
        player.assertSoundHeard(Sound.ENTITY_CAT_PURR);
    }

    // TODO: Add more tests, unfortunately MockBukkit seems pretty limited


    @AfterAll
    static void tearDown() {
        MockBukkit.unmock();
    }

}
