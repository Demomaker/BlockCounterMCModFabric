package net.demomaker.blockcounter.payload;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ClipboardPayload(String clipboardText) implements CustomPayload {
    public static final Identifier CLIPBOARD_PAYLOAD_ID = Identifier.of("clipboard_payload_id");
    public static final CustomPayload.Id<ClipboardPayload> ID = new CustomPayload.Id<>(CLIPBOARD_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, ClipboardPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING,
            ClipboardPayload::clipboardText,
            ClipboardPayload::new
    );
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
