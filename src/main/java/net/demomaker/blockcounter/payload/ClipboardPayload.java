package net.demomaker.blockcounter.payload;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ClipboardPayload {
    @NotNull
    public static final Identifier CLIPBOARD_PAYLOAD_ID = Objects.requireNonNull(Identifier.of("blockcounter", "clipboard_payload_id"));
}
