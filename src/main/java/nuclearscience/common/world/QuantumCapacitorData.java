package nuclearscience.common.world;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

public class QuantumCapacitorData extends SavedData {
	public static final String DATANAME = "quantumcapacitordata";
	public HashMap<UUID, HashMap<Integer, Double>> powermapping = new HashMap<>();

	public QuantumCapacitorData() {
	}

	@Override
	public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
		ListTag list = new ListTag();
		tag.put("list", list);
		for (Entry<UUID, HashMap<Integer, Double>> en : powermapping.entrySet()) {
			CompoundTag compound = new CompoundTag();
			compound.putUUID("uuid", en.getKey());
			ListTag entrylist = new ListTag();
			compound.put("entrylist", entrylist);
			for (Entry<Integer, Double> entryInside : en.getValue().entrySet()) {
				CompoundTag inside = new CompoundTag();
				inside.putInt("frequency", entryInside.getKey());
				inside.putDouble("joules", entryInside.getValue());
				entrylist.add(inside);
			}
			list.add(compound);
		}
		return tag;
	}

	public static QuantumCapacitorData load(CompoundTag tag, HolderLookup.Provider registries) {
		QuantumCapacitorData data = new QuantumCapacitorData();
		data.powermapping.clear();
		ListTag list = tag.getList("list", 10);
		for (Tag en : list) {
			CompoundTag compound = (CompoundTag) en;
			UUID id = compound.getUUID("uuid");
			ListTag entryList = compound.getList("entrylist", 10);
			HashMap<Integer, Double> info = new HashMap<>();
			for (Tag entryInside : entryList) {
				CompoundTag inside = (CompoundTag) entryInside;
				int frequency = inside.getInt("frequency");
				double joules = inside.getDouble("joules");
				info.put(frequency, joules);
			}
			data.powermapping.put(id, info);
		}
		return data;
	}

	public static QuantumCapacitorData get(Level world) {
		if (world instanceof ServerLevel sl) {
			DimensionDataStorage storage = sl.getDataStorage();
			QuantumCapacitorData instance = storage.computeIfAbsent(new Factory<>(QuantumCapacitorData::new, QuantumCapacitorData::load), DATANAME);
			if (instance == null) {
				instance = new QuantumCapacitorData();
				storage.set(DATANAME, instance);
			}
			return instance;
		}
		return null;
	}

	@Override
	public boolean isDirty() {
		return true;
	}

	public double getJoules(UUID uuid, int frequency) {
		if (powermapping.containsKey(uuid)) {
			HashMap<Integer, Double> value = powermapping.get(uuid);
			if (value.containsKey(frequency)) {
				return value.get(frequency);
			}
			value.put(frequency, (double) 0);
			return 0;
		}
		powermapping.put(uuid, new HashMap<>());
		powermapping.get(uuid).put(frequency, 0.0);
		return 0;
	}

	public void setJoules(UUID uuid, int frequency, double value) {
		getJoules(uuid, frequency); // load
		powermapping.get(uuid).put(frequency, value);
	}

}
