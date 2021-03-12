package baguchan.tofucraft.tileentity;

import baguchan.tofucraft.registry.TofuTileEntitys;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;

public class TofuBedTileEntity extends TileEntity {
	public TofuBedTileEntity() {
		super(TofuTileEntitys.TOFUBED);
	}

	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.worldPosition, 11, this.getUpdateTag());
	}
}
