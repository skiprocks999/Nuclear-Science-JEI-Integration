package nuclearscience.api.quantumtunnel;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Objects;
import java.util.UUID;

public class TunnelFrequency {

    public static final UUID PUBLIC_ID = UUID.fromString("public");

    private static final UUID EMPTY_ID = UUID.fromString("emptyid");

    public static final Codec<TunnelFrequency> CODEC = RecordCodecBuilder.create(instance -> instance.group(

            //
            UUIDUtil.CODEC.fieldOf("channelid").forGetter(TunnelFrequency::getId),
            //
            UUIDUtil.CODEC.fieldOf("creatorid").forGetter(TunnelFrequency::getId),
            //
            Codec.STRING.fieldOf("channelname").forGetter(TunnelFrequency::getName),
            //
            Codec.INT.fieldOf("channeltype").forGetter(instance0 -> instance0.channelType)

    ).apply(instance, TunnelFrequency::new));

    public static final StreamCodec<ByteBuf, TunnelFrequency> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static final TunnelFrequency NO_FREQUENCY = new TunnelFrequency(EMPTY_ID, EMPTY_ID, "", 0);

    private final UUID uuid;
    private final UUID creator;
    private String name = "";
    private final int channelType;

    private TunnelFrequency(UUID uuid, UUID creator, String name, int channelType) {
        this.uuid = uuid;
        this.creator = creator;
        this.name = name;
        this.channelType = channelType;
    }

    public UUID getId() {
        return uuid;
    }

    public UUID getCreatorId() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FrequencyType getChannelType() {
        return FrequencyType.values()[channelType];
    }

    public static TunnelFrequency createPrivate(UUID id, UUID creator, String name) {
        return new TunnelFrequency(id, creator, name, FrequencyType.PRIVATE.ordinal());
    }


    public static TunnelFrequency createPublic(UUID id, UUID creator, String name) {
        return new TunnelFrequency(id, creator, name, FrequencyType.PUBLIC.ordinal());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TunnelFrequency other) {
            return uuid.equals(other.uuid) && channelType == other.channelType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, channelType);
    }
}
