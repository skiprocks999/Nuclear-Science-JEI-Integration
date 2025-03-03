package nuclearscience.prefab;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import com.mojang.serialization.Codec;

import electrodynamics.prefab.properties.PropertyType;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.ByteBufCodecs;
import nuclearscience.api.quantumtunnel.TunnelFrequency;

public class NuclearPropertyTypes {

    public static final PropertyType<TunnelFrequency, ByteBuf> TUNNEL_FREQUENCY = new PropertyType<>(
            //
            Objects::equals,
            //
            TunnelFrequency.STREAM_CODEC,
            //
            writer -> writer.tag().put(writer.prop().getName(), TunnelFrequency.CODEC.encode(writer.prop().get(), NbtOps.INSTANCE, new CompoundTag()).getOrThrow()),
            //
            reader -> TunnelFrequency.CODEC.decode(NbtOps.INSTANCE, reader.tag().getCompound(reader.prop().getName())).getOrThrow().getFirst()
            //
    );

    public static final PropertyType<List<Integer>, ByteBuf> INTEGER_LIST = new PropertyType<>(
            //
            (thisSet, otherSet) -> {
                if (thisSet.size() != otherSet.size()) {
                    return false;
                }
                int a, b;
                for (int i = 0; i < thisSet.size(); i++) {
                    a = thisSet.get(i);
                    b = otherSet.get(i);
                    if (a != b) {
                        return false;
                    }
                }
                return true;
            },
            //
            ByteBufCodecs.fromCodec(Codec.INT.listOf()),
            //
            writer -> {
                List<Integer> list = writer.prop().get();
                CompoundTag data = new CompoundTag();
                data.putInt("size", list.size());
                for (int i = 0; i < list.size(); i++) {
                    data.putInt("" + i, list.get(i));
                }
                writer.tag().put(writer.prop().getName(), data);
            },
            //
            reader -> {
                List<Integer> list = new ArrayList<>();
                CompoundTag data = reader.tag().getCompound(reader.prop().getName());
                int size = data.getInt("size");
                for (int i = 0; i < size; i++) {
                    list.add(data.getInt("" + i));
                }
                return list;
            }
            //
    );

    public static final PropertyType<HashSet<Integer>, ByteBuf> INTEGER_SET = new PropertyType<>(
            //
            (thisSet, otherSet) -> {
                if (thisSet.size() != otherSet.size()) {
                    return false;
                }
                return thisSet.equals(otherSet);
            },
            //
            ByteBufCodecs.fromCodec(Codec.INT.listOf().xmap(HashSet::new, ArrayList::new)),
            //
            writer -> {
                HashSet<Integer> list = writer.prop().get();
                CompoundTag data = new CompoundTag();
                data.putInt("size", list.size());
                int i = 0;
                for(int integer : list) {
                    data.putInt("" + i, integer);
                    i++;
                }
                writer.tag().put(writer.prop().getName(), data);
            },
            //
            reader -> {
                HashSet<Integer> list = new HashSet<>();
                CompoundTag data = reader.tag().getCompound(reader.prop().getName());
                int size = data.getInt("size");
                for (int i = 0; i < size; i++) {
                    list.add(data.getInt("" + i));
                }
                return list;
            }
            //
    );
}
