package baguchan.tofucraft.registry;

import baguchan.tofucraft.TofuCraftReload;
import baguchan.tofucraft.block.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftReload.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuBlocks {
	public static final Block SOYMILK = new TofuFluidBlock(TofuFluids.SOYMILK, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
	public static final Block SOYMILK_HELL = new TofuFluidBlock(TofuFluids.SOYMILK_HELL, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());
	public static final Block SOYMILK_SOUL = new TofuFluidBlock(TofuFluids.SOYMILK_SOUL, Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops());

	public static final Block SOYBEAN = new SoybeanCropsBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
	public static final Block SOYBEAN_NETHER = new SoybeanNetherCropsBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));
	public static final Block SOYBEAN_SOUL = new SoybeanSoulCropsBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP));

	public static final Block KINUTOFU = new KinuTofuBlock(AbstractBlock.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.1F, 0.2F).sound(SoundType.SNOW));
	public static final Block MOMENTOFU = new TofuBlock(AbstractBlock.Properties.create(TofuMaterial.TOFU).tickRandomly().harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));
	public static final Block ISHITOFU = new TofuBlock(AbstractBlock.Properties.create(Material.ROCK).tickRandomly().harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE));
	public static final Block METALTOFU = new Block(AbstractBlock.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(1).hardnessAndResistance(4.0F, 7.5F).sound(SoundType.METAL));
	public static final Block DIAMONDTOFU = new Block(AbstractBlock.Properties.create(Material.IRON, MaterialColor.LIGHT_BLUE).harvestTool(ToolType.PICKAXE).setRequiresTool().harvestLevel(2).hardnessAndResistance(5.0F, 8.0F).sound(SoundType.METAL));
	public static final Block HELLTOFU = new Block(AbstractBlock.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));
	public static final Block SOULTOFU = new Block(AbstractBlock.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));

	public static final Block SALTPAN = new SaltPanBlock(AbstractBlock.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(2.0F, 3.0F).notSolid().sound(SoundType.WOOD));

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> registry) {
		registry.getRegistry().register(SOYMILK.setRegistryName("soymilk"));
		registry.getRegistry().register(SOYMILK_HELL.setRegistryName("soymilk_hell"));
		registry.getRegistry().register(SOYMILK_SOUL.setRegistryName("soymilk_soul"));

		registry.getRegistry().register(SOYBEAN.setRegistryName("soybean"));
		registry.getRegistry().register(SOYBEAN_NETHER.setRegistryName("soybean_nether"));
		registry.getRegistry().register(SOYBEAN_SOUL.setRegistryName("soybean_soul"));

		registry.getRegistry().register(KINUTOFU.setRegistryName("blocktofukinu"));
		registry.getRegistry().register(MOMENTOFU.setRegistryName("blocktofumomen"));
		registry.getRegistry().register(ISHITOFU.setRegistryName("blocktofuishi"));
		registry.getRegistry().register(METALTOFU.setRegistryName("blocktofumetal"));
		registry.getRegistry().register(DIAMONDTOFU.setRegistryName("blocktofudiamond"));
		registry.getRegistry().register(HELLTOFU.setRegistryName("blocktofuhell"));
		registry.getRegistry().register(SOULTOFU.setRegistryName("blocktofusoul"));

		registry.getRegistry().register(SALTPAN.setRegistryName("blocksaltpan"));
	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {
		TofuItems.register(registry, new BlockItem(KINUTOFU, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(MOMENTOFU, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(ISHITOFU, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(METALTOFU, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(DIAMONDTOFU, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(HELLTOFU, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(SOULTOFU, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));

		TofuItems.register(registry, new BlockItem(SALTPAN, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
	}
}
