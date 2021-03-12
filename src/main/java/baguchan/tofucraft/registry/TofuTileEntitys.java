package baguchan.tofucraft.registry;

import baguchan.tofucraft.TofuCraftReload;
import baguchan.tofucraft.tileentity.TofuBedTileEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftReload.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuTileEntitys {
	public static final TileEntityType<TofuBedTileEntity> TOFUBED = register("tofucraft:tofubed", TileEntityType.Builder.of(TofuBedTileEntity::new, TofuBlocks.TOFUBED));

	private static <T extends TileEntity> TileEntityType<T> register(String p_200966_0_, TileEntityType.Builder<T> p_200966_1_) {
		Type<?> type = Util.fetchChoiceType(TypeReferences.BLOCK_ENTITY, p_200966_0_);
		return p_200966_1_.build(type);
	}

	@SubscribeEvent
	public static void registerTileEntityType(RegistryEvent.Register<TileEntityType<?>> registry) {
		registry.getRegistry().register(TOFUBED.setRegistryName("tofubed"));
	}
}
