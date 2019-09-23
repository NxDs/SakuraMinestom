package fr.themode.minestom.net.packet.client.handler;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import fr.themode.minestom.net.packet.client.ClientPacket;

public class ClientPacketsHandler {

    private static final int SIZE = 0xFF;

    private ConstructorAccess[] constructorAccesses = new ConstructorAccess[SIZE];

    public void register(int id, Class<? extends ClientPacket> packet) {
        constructorAccesses[id] = ConstructorAccess.get(packet);
    }

    public ClientPacket getPacketInstance(int id) {
        if (id > SIZE)
            throw new IllegalStateException("Packet ID 0x" + Integer.toHexString(id) + " has been tried to be parsed, debug needed");

        ConstructorAccess<? extends ClientPacket> constructorAccess = constructorAccesses[id];
        if (constructorAccess == null)
            throw new IllegalStateException("Packet id 0x" + Integer.toHexString(id) + " isn't registered!");

        ClientPacket packet = constructorAccess.newInstance();
        return packet;
    }

}
