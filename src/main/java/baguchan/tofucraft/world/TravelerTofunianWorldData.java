package baguchan.tofucraft.world;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.UUID;

public class TravelerTofunianWorldData extends WorldSavedData {

	private static final String IDENTIFIER = "tofucraft_travelling_tofunian";
	private World world;
	private int tickCounter;
	private int tofunianSpawnDelay;
	private int tofunianSpawnChance;
	private UUID tofunianID;

	public TravelerTofunianWorldData() {
		super(IDENTIFIER);
	}

	public static TravelerTofunianWorldData get(World world) {
		if (world instanceof ServerWorld) {
			ServerWorld overworld = world.getServer().getLevel(world.dimension());

			DimensionSavedDataManager storage = overworld.getDataStorage();
			TravelerTofunianWorldData data = storage.get(TravelerTofunianWorldData::new, IDENTIFIER);
			if (data != null) {
				data.world = world;
				data.setDirty();
			}
			return data;
		}
		return null;
	}

	public int getDoctorSpawnDelay() {
		return this.tofunianSpawnDelay;
	}

	public void setDoctorSpawnDelay(int delay) {
		this.tofunianSpawnDelay = delay;
	}

	public int getDoctorSpawnChance() {
		return this.tofunianSpawnChance;
	}

	public void setDoctorSpawnChance(int chance) {
		this.tofunianSpawnChance = chance;
	}

	public void setTravelerTofunianID(UUID id) {
		this.tofunianID = id;
	}


	public void tick() {
		++this.tickCounter;
	}

	@Override
	public void load(CompoundNBT nbt) {
		if (nbt.contains("TravelerTofunianSpawnDelay", 99)) {
			this.tofunianSpawnDelay = nbt.getInt("TravelerTofunianSpawnDelay");
		}

		if (nbt.contains("TravelerTofunianSpawnChance", 99)) {
			this.tofunianSpawnChance = nbt.getInt("TravelerTofunianSpawnChance");
		}

		if (nbt.contains("TravelerTofunianId", 8)) {
			this.tofunianID = UUID.fromString(nbt.getString("TravelerTofunianId"));
		}

	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.putInt("TravelerTofunianSpawnDelay", this.tofunianSpawnDelay);
		compound.putInt("TravelerTofunianSpawnChance", this.tofunianSpawnChance);
		if (this.tofunianID != null) {
			compound.putString("TravelerTofunianId", this.tofunianID.toString());
		}
		return compound;
	}
}