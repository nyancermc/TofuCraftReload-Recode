package baguchan.tofucraft.registry;

import baguchan.tofucraft.TofuCraftReload;
import baguchan.tofucraft.block.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
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
	public static final Block GRILLEDTOFU = new Block(AbstractBlock.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));
	public static final Block ZUNDATOFU = new Block(AbstractBlock.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));
	//Building
	public static final Block ISHITOFU_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	public static final Block ISHITOFU_SMOOTH_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	public static final Block ISHITOFU_CHISELED_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	public static final Block ZUNDATOFU_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	public static final Block ZUNDATOFU_SMOOTHBRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	public static final Block HELLTOFU_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	public static final Block HELLTOFU_SMOOTHBRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	public static final Block SOULTOFU_BRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	public static final Block SOULTOFU_SMOOTHBRICK = new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.6F, 6.5F).sound(SoundType.STONE));
	//Stair
	public static final Block TOFUSTAIR_KINU = new StairsBlock(KINUTOFU::getDefaultState, Block.Properties.from(KINUTOFU));
	public static final Block TOFUSTAIR_MOMEN = new StairsBlock(MOMENTOFU::getDefaultState, Block.Properties.from(MOMENTOFU));
	public static final Block TOFUSTAIR_ISHI = new StairsBlock(ISHITOFU::getDefaultState, Block.Properties.from(ISHITOFU));
	public static final Block TOFUSTAIR_METAL = new StairsBlock(METALTOFU::getDefaultState, Block.Properties.from(METALTOFU));
	//public static final Block TOFUSTAIR_ZUNDA = new StairsBlock(ZUNDATOFU::getDefaultState, Block.Properties.from(ZUNDATOFU));
	public static final Block TOFUSTAIR_ZUNDABRICK = new StairsBlock(ZUNDATOFU_BRICK::getDefaultState, Block.Properties.from(ZUNDATOFU_BRICK));
	public static final Block TOFUSTAIR_ISHIBRICK = new StairsBlock(ISHITOFU_BRICK::getDefaultState, Block.Properties.from(ISHITOFU_BRICK));
	public static final Block TOFUSTAIR_HELLBRICK = new StairsBlock(HELLTOFU_BRICK::getDefaultState, Block.Properties.from(HELLTOFU_BRICK));
	public static final Block TOFUSTAIR_SOULBRICK = new StairsBlock(SOULTOFU_BRICK::getDefaultState, Block.Properties.from(SOULTOFU_BRICK));
	//Slab
	public static final Block TOFUSLAB_KINU = new SlabBlock(Block.Properties.from(KINUTOFU));
	public static final Block TOFUSLAB_MOMEN = new SlabBlock(Block.Properties.from(MOMENTOFU));
	public static final Block TOFUSLAB_ISHI = new SlabBlock(Block.Properties.from(ISHITOFU));
	public static final Block TOFUSLAB_METAL = new SlabBlock(Block.Properties.from(METALTOFU));
	//public static final Block TOFUSLAB_ZUNDA = new SlabBlock(Block.Properties.from(ZUNDATOFU));
	public static final Block TOFUSLAB_ZUNDABRICK = new SlabBlock(Block.Properties.from(ZUNDATOFU_BRICK));
	public static final Block TOFUSLAB_ISHIBRICK = new SlabBlock(Block.Properties.from(ISHITOFU_BRICK));
	public static final Block TOFUSLAB_HELLBRICK = new SlabBlock(Block.Properties.from(HELLTOFU_BRICK));
	public static final Block TOFUSLAB_SOULBRICK = new SlabBlock(Block.Properties.from(SOULTOFU_BRICK));
	//torch
	public static final Block TOFUTORCH_KINU = new TorchBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F, 0.5F).setLightLevel((state) -> {
		return 14;
	}).doesNotBlockMovement().notSolid().sound(SoundType.SNOW), ParticleTypes.FLAME);
	public static final Block TOFUTORCH_MOMEN = new TorchBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F, 0.5F).setLightLevel((state) -> {
		return 14;
	}).doesNotBlockMovement().notSolid().sound(SoundType.SNOW), ParticleTypes.FLAME);
	public static final Block TOFUTORCH_ISHI = new TorchBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F, 6.0F).setLightLevel((state) -> {
		return 14;
	}).doesNotBlockMovement().notSolid().sound(SoundType.STONE), ParticleTypes.FLAME);
	public static final Block TOFUTORCH_METAL = new TorchBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F, 7.5F).setLightLevel((state) -> {
		return 14;
	}).doesNotBlockMovement().notSolid().sound(SoundType.METAL), ParticleTypes.FLAME);
	public static final Block WALLTOFUTORCH_KINU = new WallTorchBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F, 0.5F).setLightLevel((state) -> {
		return 14;
	}).doesNotBlockMovement().notSolid().sound(SoundType.SNOW).lootFrom(TOFUTORCH_KINU), ParticleTypes.FLAME);
	public static final Block WALLTOFUTORCH_MOMEN = new WallTorchBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F, 0.5F).setLightLevel((state) -> {
		return 14;
	}).doesNotBlockMovement().notSolid().sound(SoundType.SNOW).lootFrom(TOFUTORCH_MOMEN), ParticleTypes.FLAME);
	public static final Block WALLTOFUTORCH_ISHI = new WallTorchBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F, 6.0F).setLightLevel((state) -> {
		return 14;
	}).doesNotBlockMovement().notSolid().sound(SoundType.STONE).lootFrom(TOFUTORCH_ISHI), ParticleTypes.FLAME);
	public static final Block WALLTOFUTORCH_METAL = new WallTorchBlock(Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0.0F, 7.5F).setLightLevel((state) -> {
		return 14;
	}).doesNotBlockMovement().notSolid().sound(SoundType.METAL).lootFrom(TOFUTORCH_METAL), ParticleTypes.FLAME);

	//Ladder
	public static final Block TOFULADDER_KINU = new LadderBlock(Block.Properties.from(KINUTOFU));
	public static final Block TOFULADDER_MOMEN = new LadderBlock(Block.Properties.from(MOMENTOFU));
	public static final Block TOFULADDER_ISHI = new LadderBlock(Block.Properties.from(ISHITOFU));
	public static final Block TOFULADDER_METAL = new LadderBlock(Block.Properties.from(METALTOFU));
	//public static final Block TOFULADDER_ZUNDABRICK = new TofuLadderBlock(Block.Properties.from(ZUNDATOFU_BRICK));
	public static final Block TOFULADDER_ISHIBRICK = new LadderBlock(Block.Properties.from(ISHITOFU_BRICK));
	//FENCE
	public static final Block TOFUFENCE_KINU = new WallBlock(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.1F, 0.2F).sound(SoundType.SNOW));
	public static final Block TOFUFENCE_MOMEN = new WallBlock(Block.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.35F, 0.5F).sound(SoundType.SNOW));
	public static final Block TOFUFENCE_ISHI = new WallBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.5F, 6.0F).sound(SoundType.STONE));
	public static final Block TOFUFENCE_METAL = new WallBlock(Block.Properties.from(METALTOFU));


	//terrain
	public static final Block TOFU_TERRAIN = new TofuTerrainBlock(AbstractBlock.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.5F, 1.0F).sound(SoundType.SNOW));
	public static final Block ZUNDATOFU_TERRAIN = new TofuTerrainBlock(AbstractBlock.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.5F, 1.0F).sound(SoundType.SNOW));
	public static final Block TOFU_FLOWER = new TofuFlowerBlock(Effects.SATURATION, 2, AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.PLANT));
	public static final Block LEEK = new TofuBushBlock(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.PLANT));
	public static final Block LEEK_GREEN_STEM = new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.STEM));
	public static final Block LEEK_STEM = new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.STEM));
	//misc
	public static final Block POTTED_TOFU_FLOWER = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> TOFU_FLOWER, AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().doesNotBlockMovement().sound(SoundType.STONE));
	public static final Block TOFU_FARMLAND = new TofuFarmlandBlock(AbstractBlock.Properties.create(TofuMaterial.TOFU).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.5F, 1.0F).sound(SoundType.SNOW));
	public static final Block SALTPAN = new SaltPanBlock(AbstractBlock.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(2.0F, 3.0F).notSolid().sound(SoundType.WOOD));
	public static final TofuPortalBlock TOFU_PORTAL = new TofuPortalBlock(Block.Properties.create(Material.PORTAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(-1.0F).sound(SoundType.GLASS).setLightLevel((state) -> {
		return 11;
	}).noDrops());


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
		registry.getRegistry().register(GRILLEDTOFU.setRegistryName("blocktofugrilled"));
		registry.getRegistry().register(ZUNDATOFU.setRegistryName("blocktofuzunda"));

		//Building
		registry.getRegistry().register(ISHITOFU_BRICK.setRegistryName("tofuishi_brick"));
		registry.getRegistry().register(ISHITOFU_SMOOTH_BRICK.setRegistryName("tofuishi_smooth_brick"));
		registry.getRegistry().register(ISHITOFU_CHISELED_BRICK.setRegistryName("tofuishi_chiseled_brick"));
		registry.getRegistry().register(ZUNDATOFU_BRICK.setRegistryName("tofuzunda_brick"));
		registry.getRegistry().register(ZUNDATOFU_SMOOTHBRICK.setRegistryName("tofuzunda_smooth_brick"));
		registry.getRegistry().register(HELLTOFU_BRICK.setRegistryName("tofuhell_brick"));
		registry.getRegistry().register(HELLTOFU_SMOOTHBRICK.setRegistryName("tofuhell_smooth_brick"));
		registry.getRegistry().register(SOULTOFU_BRICK.setRegistryName("tofusoul_brick"));
		registry.getRegistry().register(SOULTOFU_SMOOTHBRICK.setRegistryName("tofusoul_smooth_brick"));
		//Stair
		registry.getRegistry().register(TOFUSTAIR_KINU.setRegistryName("tofustair_kinu"));
		registry.getRegistry().register(TOFUSTAIR_MOMEN.setRegistryName("tofustair_momen"));
		registry.getRegistry().register(TOFUSTAIR_ISHI.setRegistryName("tofustair_ishi"));
		registry.getRegistry().register(TOFUSTAIR_METAL.setRegistryName("tofustair_metal"));
		//registry.getRegistry().register(TOFUSTAIR_ZUNDA.setRegistryName("tofustair_zunda"));
		registry.getRegistry().register(TOFUSTAIR_ZUNDABRICK.setRegistryName("tofustair_zundabrick"));
		registry.getRegistry().register(TOFUSTAIR_ISHIBRICK.setRegistryName("tofustair_ishibrick"));
		registry.getRegistry().register(TOFUSTAIR_HELLBRICK.setRegistryName("tofustair_hellbrick"));
		registry.getRegistry().register(TOFUSTAIR_SOULBRICK.setRegistryName("tofustair_soulbrick"));
		//Slab
		registry.getRegistry().register(TOFUSLAB_KINU.setRegistryName("tofuslab_kinu"));
		registry.getRegistry().register(TOFUSLAB_MOMEN.setRegistryName("tofuslab_momen"));
		registry.getRegistry().register(TOFUSLAB_ISHI.setRegistryName("tofuslab_ishi"));
		registry.getRegistry().register(TOFUSLAB_METAL.setRegistryName("tofuslab_metal"));
		registry.getRegistry().register(TOFUSLAB_ISHIBRICK.setRegistryName("tofuslab_ishibrick"));
		//registry.getRegistry().register(TOFUSLAB_ZUNDA.setRegistryName("tofuslab_zunda"));
		registry.getRegistry().register(TOFUSLAB_ZUNDABRICK.setRegistryName("tofuslab_zundabrick"));
		registry.getRegistry().register(TOFUSLAB_HELLBRICK.setRegistryName("tofuslab_hellbrick"));
		registry.getRegistry().register(TOFUSLAB_SOULBRICK.setRegistryName("tofuslab_soulbrick"));
		//Torch
		registry.getRegistry().register(TOFUTORCH_KINU.setRegistryName("tofutorch_kinu"));
		registry.getRegistry().register(TOFUTORCH_MOMEN.setRegistryName("tofutorch_momen"));
		registry.getRegistry().register(TOFUTORCH_ISHI.setRegistryName("tofutorch_ishi"));
		registry.getRegistry().register(TOFUTORCH_METAL.setRegistryName("tofutorch_metal"));
		registry.getRegistry().register(WALLTOFUTORCH_KINU.setRegistryName("walltofutorch_kinu"));
		registry.getRegistry().register(WALLTOFUTORCH_MOMEN.setRegistryName("walltofutorch_momen"));
		registry.getRegistry().register(WALLTOFUTORCH_ISHI.setRegistryName("walltofutorch_ishi"));
		registry.getRegistry().register(WALLTOFUTORCH_METAL.setRegistryName("walltofutorch_metal"));
		//Ladder
		registry.getRegistry().register(TOFULADDER_KINU.setRegistryName("tofuladder_kinu"));
		registry.getRegistry().register(TOFULADDER_MOMEN.setRegistryName("tofuladder_momen"));
		registry.getRegistry().register(TOFULADDER_ISHI.setRegistryName("tofuladder_ishi"));
		registry.getRegistry().register(TOFULADDER_ISHIBRICK.setRegistryName("tofuladder_ishibrick"));
		registry.getRegistry().register(TOFULADDER_METAL.setRegistryName("tofuladder_metal"));
		//FENCE
		registry.getRegistry().register(TOFUFENCE_KINU.setRegistryName("tofufence_kinu"));
		registry.getRegistry().register(TOFUFENCE_MOMEN.setRegistryName("tofufence_momen"));
		registry.getRegistry().register(TOFUFENCE_ISHI.setRegistryName("tofufence_ishi"));
		registry.getRegistry().register(TOFUFENCE_METAL.setRegistryName("tofufence_metal"));

		registry.getRegistry().register(TOFU_TERRAIN.setRegistryName("tofu_terrain"));
		registry.getRegistry().register(ZUNDATOFU_TERRAIN.setRegistryName("zundatofu_terrain"));
		registry.getRegistry().register(TOFU_FLOWER.setRegistryName("tofuflower"));
		registry.getRegistry().register(LEEK.setRegistryName("leek"));
		registry.getRegistry().register(LEEK_GREEN_STEM.setRegistryName("leek_green_stem"));
		registry.getRegistry().register(LEEK_STEM.setRegistryName("leek_green_stem"));

		registry.getRegistry().register(POTTED_TOFU_FLOWER.setRegistryName("potted_tofuflower"));
		registry.getRegistry().register(TOFU_FARMLAND.setRegistryName("tofu_farmland"));
		registry.getRegistry().register(SALTPAN.setRegistryName("blocksaltpan"));
		registry.getRegistry().register(TOFU_PORTAL.setRegistryName("tofuportal"));

		((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(TOFU_FLOWER.getRegistryName(), () -> POTTED_TOFU_FLOWER);
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
		TofuItems.register(registry, new BlockItem(GRILLEDTOFU, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));

		//Building
		TofuItems.register(registry, new BlockItem(ISHITOFU_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(ISHITOFU_SMOOTH_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(ISHITOFU_CHISELED_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(ZUNDATOFU_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(ZUNDATOFU_SMOOTHBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(HELLTOFU_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(HELLTOFU_SMOOTHBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(SOULTOFU_BRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(SOULTOFU_SMOOTHBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		//TofuItems.register(registry, new BlockItem(CONCRETE_TOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		//TofuItems.register(registry, new BlockItem(CONCRETE_POWDER_TOFU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		//TofuItems.register(registry, new BlockItem(MOMEN_TOFUNAMAKO_WALL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		//Stair
		TofuItems.register(registry, new BlockItem(TOFUSTAIR_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSTAIR_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSTAIR_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSTAIR_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		//TofuItems.register(registry, new BlockItem(TOFUSTAIR_ZUNDA, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSTAIR_ZUNDABRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSTAIR_ISHIBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSTAIR_HELLBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSTAIR_SOULBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));


		TofuItems.register(registry, new BlockItem(TOFUSLAB_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSLAB_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSLAB_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSLAB_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		//TofuItems.register(registry, new BlockItem(TOFUSLAB_ZUNDA, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSLAB_ZUNDABRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSLAB_ISHIBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSLAB_HELLBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUSLAB_SOULBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

		TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_KINU, WALLTOFUTORCH_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_MOMEN, WALLTOFUTORCH_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_ISHI, WALLTOFUTORCH_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new WallOrFloorItem(TOFUTORCH_METAL, WALLTOFUTORCH_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

		TofuItems.register(registry, new BlockItem(TOFULADDER_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFULADDER_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFULADDER_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFULADDER_ISHIBRICK, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFULADDER_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

		TofuItems.register(registry, new BlockItem(TOFUFENCE_KINU, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUFENCE_MOMEN, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUFENCE_ISHI, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFUFENCE_METAL, (new Item.Properties()).group(TofuItemGroup.TOFUCRAFT)));

		TofuItems.register(registry, new BlockItem(TOFU_TERRAIN, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(ZUNDATOFU_TERRAIN, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(TOFU_FLOWER, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(LEEK_GREEN_STEM, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
		TofuItems.register(registry, new BlockItem(LEEK_STEM, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));

		TofuItems.register(registry, new BlockItem(TOFU_FARMLAND, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));

		TofuItems.register(registry, new BlockItem(SALTPAN, new Item.Properties().group(TofuItemGroup.TOFUCRAFT)));
	}
}
