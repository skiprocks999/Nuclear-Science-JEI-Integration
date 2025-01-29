package nuclearscience.registers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import nuclearscience.References;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.api.radiation.RadiationManager;
import nuclearscience.api.radiation.SimpleRadiationSource;
import nuclearscience.api.radiation.util.BlockPosVolume;
import nuclearscience.api.radiation.util.IRadiationManager;
import nuclearscience.common.settings.Constants;

public class NuclearScienceAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, References.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<BlockPos, SimpleRadiationSource>>> PERMANENT_RADIATION_SOURCES = ATTACHMENT_TYPES.register("permanentradiationsources", () -> AttachmentType.builder(() -> new HashMap<BlockPos, SimpleRadiationSource>()).serialize(new IAttachmentSerializer<CompoundTag, HashMap<BlockPos, SimpleRadiationSource>>() {
        @Override
        public HashMap<BlockPos, SimpleRadiationSource> read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
            HashMap<BlockPos, SimpleRadiationSource> data = new HashMap<>();

            int size = tag.getInt("size");
            for (int i = 0; i < size; i++) {

                CompoundTag stored = tag.getCompound("" + i);
                data.put(BlockPos.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("pos"))).result().get(), SimpleRadiationSource.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.getCompound("radiation"))).getOrThrow());
            }


            return data;
        }

        @Override
        public @Nullable CompoundTag write(HashMap<BlockPos, SimpleRadiationSource> attachment, HolderLookup.Provider provider) {
            CompoundTag data = new CompoundTag();
            int size = attachment.size();
            data.putInt("size", size);
            int i = 0;
            for (Map.Entry<BlockPos, SimpleRadiationSource> entry : attachment.entrySet()) {
                CompoundTag store = new CompoundTag();
                BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).ifSuccess(tag -> store.put("pos", tag));
                SimpleRadiationSource.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue()).ifSuccess(tag -> store.put("radiation", tag));
                data.put(i + "", store);
                i++;
            }
            return data;
        }
    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource>>> TEMPORARY_RADIATION_SOURCES = ATTACHMENT_TYPES.register("temporaryradiationsources", () -> AttachmentType.builder(() -> new HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource>()).serialize(new IAttachmentSerializer<CompoundTag, HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource>>() {
        @Override
        public HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource> read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
            HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource> data = new HashMap<>();

            int size = tag.getInt("size");
            for (int i = 0; i < size; i++) {

                CompoundTag stored = tag.getCompound("" + i);
                data.put(BlockPos.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("pos"))).result().get(), IRadiationManager.TemporaryRadiationSource.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.getCompound("radiation"))).getOrThrow());
            }


            return data;
        }

        @Override
        public @Nullable CompoundTag write(HashMap<BlockPos, IRadiationManager.TemporaryRadiationSource> attachment, HolderLookup.Provider provider) {
            CompoundTag data = new CompoundTag();
            int size = attachment.size();
            data.putInt("size", size);
            int i = 0;
            for (Map.Entry<BlockPos, IRadiationManager.TemporaryRadiationSource> entry : attachment.entrySet()) {
                CompoundTag store = new CompoundTag();
                BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).ifSuccess(tag -> store.put("pos", tag));
                IRadiationManager.TemporaryRadiationSource.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue()).ifSuccess(tag -> store.put("radiation", tag));
                data.put(i + "", store);
                i++;
            }
            return data;
        }
    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<BlockPos, IRadiationManager.FadingRadiationSource>>> FADING_RADIATION_SOURCES = ATTACHMENT_TYPES.register("fadingradiationsources", () -> AttachmentType.builder(() -> new HashMap<BlockPos, IRadiationManager.FadingRadiationSource>()).serialize(new IAttachmentSerializer<CompoundTag, HashMap<BlockPos, IRadiationManager.FadingRadiationSource>>() {
        @Override
        public HashMap<BlockPos, IRadiationManager.FadingRadiationSource> read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
            HashMap<BlockPos, IRadiationManager.FadingRadiationSource> data = new HashMap<>();

            int size = tag.getInt("size");
            for (int i = 0; i < size; i++) {

                CompoundTag stored = tag.getCompound("" + i);
                data.put(BlockPos.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("pos"))).result().get(), IRadiationManager.FadingRadiationSource.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.getCompound("radiation"))).getOrThrow());
            }


            return data;
        }

        @Override
        public @Nullable CompoundTag write(HashMap<BlockPos, IRadiationManager.FadingRadiationSource> attachment, HolderLookup.Provider provider) {
            CompoundTag data = new CompoundTag();
            int size = attachment.size();
            data.putInt("size", size);
            int i = 0;
            for (Map.Entry<BlockPos, IRadiationManager.FadingRadiationSource> entry : attachment.entrySet()) {
                CompoundTag store = new CompoundTag();
                BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).ifSuccess(tag -> store.put("pos", tag));
                IRadiationManager.FadingRadiationSource.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue()).ifSuccess(tag -> store.put("radiation", tag));
                data.put(i + "", store);
                i++;
            }
            return data;
        }
    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<BlockPosVolume, Double>>> LOCALIZED_DISSIPATIONS = ATTACHMENT_TYPES.register("localizeddissipations", () -> AttachmentType.builder(() -> new HashMap<BlockPosVolume, Double>()).serialize(new IAttachmentSerializer<CompoundTag, HashMap<BlockPosVolume, Double>>() {
        @Override
        public HashMap<BlockPosVolume, Double> read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
            HashMap<BlockPosVolume, Double> data = new HashMap<>();

            int size = tag.getInt("size");
            for (int i = 0; i < size; i++) {

                CompoundTag stored = tag.getCompound("" + i);
                data.put(BlockPosVolume.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("pos"))).result().get(), stored.getDouble("amount"));
            }


            return data;
        }

        @Override
        public @Nullable CompoundTag write(HashMap<BlockPosVolume, Double> attachment, HolderLookup.Provider provider) {
            CompoundTag data = new CompoundTag();
            int size = attachment.size();
            data.putInt("size", size);
            int i = 0;
            for (Map.Entry<BlockPosVolume, Double> entry : attachment.entrySet()) {
                CompoundTag store = new CompoundTag();
                BlockPosVolume.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).ifSuccess(tag -> store.put("pos", tag));
                store.putDouble("amount", entry.getValue());
                data.put(i + "", store);
                i++;
            }
            return data;
        }
    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> DEFAULT_DISSIPATION = ATTACHMENT_TYPES.register("defaultdissipation", () -> AttachmentType.builder(() -> Constants.BACKROUND_RADIATION_DISSIPATION).serialize(Codec.DOUBLE).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<RadiationManager>> RADIATION_MANAGER = ATTACHMENT_TYPES.register("radiationmanager", () -> AttachmentType.builder(RadiationManager::new).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> RECIEVED_RADIATIONAMOUNT = ATTACHMENT_TYPES.register("recievedradiationamount", () -> AttachmentType.builder(() -> Double.valueOf(0.0)).serialize(Codec.DOUBLE).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> RECIEVED_RADIATIONSTRENGTH = ATTACHMENT_TYPES.register("recievedradiationstrength", () -> AttachmentType.builder(() -> Double.valueOf(0.0)).serialize(Codec.DOUBLE).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> OLD_RECIEVED_RADIATIONAMOUNT = ATTACHMENT_TYPES.register("oldrecievedradiationamount", () -> AttachmentType.builder(() -> Double.valueOf(0.0)).serialize(Codec.DOUBLE).build());
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> OLD_RECIEVED_RADIATIONSTRENGTH = ATTACHMENT_TYPES.register("oldrecievedradiationstrength", () -> AttachmentType.builder(() -> Double.valueOf(0.0)).serialize(Codec.DOUBLE).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<UUID, HashSet<TunnelFrequency>>>> CHANNEL_MAP = ATTACHMENT_TYPES.register("channelmap", () -> AttachmentType.builder(() -> new HashMap<UUID, HashSet<TunnelFrequency>>()).serialize(new IAttachmentSerializer<CompoundTag, HashMap<UUID, HashSet<TunnelFrequency>>>() {
        @Override
        public HashMap<UUID, HashSet<TunnelFrequency>> read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
            HashMap<UUID, HashSet<TunnelFrequency>> data = new HashMap<>();

            int size = tag.getInt("size");
            for (int i = 0; i < size; i++) {

                CompoundTag stored = tag.getCompound("" + i);

                UUID id = UUIDUtil.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("id"))).result().get();
                HashSet<TunnelFrequency> set = new HashSet<>();

                for(int j = 0; j < stored.getInt("setsize"); j++) {
                    set.add(TunnelFrequency.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("" + j))).result().get());
                }

                data.put(id, set);
            }


            return data;
        }

        @Override
        public @Nullable CompoundTag write(HashMap<UUID, HashSet<TunnelFrequency>> attachment, HolderLookup.Provider provider) {
            CompoundTag data = new CompoundTag();
            int size = attachment.size();
            data.putInt("size", size);
            int i = 0;
            for (Map.Entry<UUID, HashSet<TunnelFrequency>> entry : attachment.entrySet()) {
                CompoundTag store = new CompoundTag();
                UUIDUtil.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).ifSuccess(tag -> store.put("id", tag));
                store.putInt("setsize", entry.getValue().size());
                int j = 0;
                for(TunnelFrequency freq : entry.getValue()) {
                    int finalJ = j;
                    TunnelFrequency.CODEC.encodeStart(NbtOps.INSTANCE, freq).ifSuccess(tag -> store.put("" + finalJ, tag));
                    j++;
                }
                data.put(i + "", store);
                i++;
            }
            return data;
        }
    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<HashMap<TunnelFrequency, TunnelFrequencyBuffer>>> TUNNEL_MAP = ATTACHMENT_TYPES.register("tunnelmap", () -> AttachmentType.builder(() -> new HashMap<TunnelFrequency, TunnelFrequencyBuffer>()).serialize(new IAttachmentSerializer<CompoundTag, HashMap<TunnelFrequency, TunnelFrequencyBuffer>>() {
        @Override
        public HashMap<TunnelFrequency, TunnelFrequencyBuffer> read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
            HashMap<TunnelFrequency, TunnelFrequencyBuffer> data = new HashMap<>();

            int size = tag.getInt("size");
            for (int i = 0; i < size; i++) {

                CompoundTag stored = tag.getCompound("" + i);

                data.put(TunnelFrequency.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("id"))).result().get(), TunnelFrequencyBuffer.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, stored.get("buffer"))).result().get());
            }


            return data;
        }

        @Override
        public @Nullable CompoundTag write(HashMap<TunnelFrequency, TunnelFrequencyBuffer> attachment, HolderLookup.Provider provider) {
            CompoundTag data = new CompoundTag();
            int size = attachment.size();
            data.putInt("size", size);
            int i = 0;
            for (Map.Entry<TunnelFrequency, TunnelFrequencyBuffer> entry : attachment.entrySet()) {
                CompoundTag store = new CompoundTag();
                TunnelFrequency.CODEC.encodeStart(NbtOps.INSTANCE, entry.getKey()).ifSuccess(tag -> store.put("id", tag));
                TunnelFrequencyBuffer.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue()).ifSuccess(tag -> store.put("buffer", tag));
                data.put(i + "", store);
                i++;
            }
            return data;
        }
    }).build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> ANTIMATTER_TIMEONGROUND = ATTACHMENT_TYPES.register("timeonground", () -> AttachmentType.builder(() -> Constants.ANTIMATTER_TICKS_ON_GROUND).serialize(Codec.INT).build());


}
