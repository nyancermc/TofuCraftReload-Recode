package baguchan.tofucraft.registry;

import baguchan.tofucraft.TofuCraftReload;
import baguchan.tofucraft.item.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TofuCraftReload.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TofuItems {
	public static final Item TOFUKINU = new Item((new Item.Properties()).food(TofuFoods.TOFU).group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUMOMEN = new Item((new Item.Properties()).food(TofuFoods.TOFU).group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUISHI = new Item((new Item.Properties()).food(TofuFoods.ISHITOFU).group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUMETAL = new Item((new Item.Properties()).group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUDIAMOND = new Item((new Item.Properties()).group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUHELL = new Item((new Item.Properties()).food(TofuFoods.TOFUHELL).group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUSOUL = new Item((new Item.Properties()).group(TofuItemGroup.TOFUCRAFT));

	public static final Item BITTERN = new BitternItem((new Item.Properties()).group(TofuItemGroup.TOFUCRAFT));
	public static final Item SALT = new BitternItem((new Item.Properties()).group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUSCOOP = new TofuScoopItem((new Item.Properties()).group(TofuItemGroup.TOFUCRAFT));
	public static final Item FUKUMAME = new FukumameItem(new Item.Properties().maxStackSize(1).maxDamage(64).group(TofuItemGroup.TOFUCRAFT));
	public static final Item NETHER_FUKUMAME = new NetherFukumameItem(new Item.Properties().maxStackSize(1).maxDamage(64).group(TofuItemGroup.TOFUCRAFT), false);
	public static final Item INFERNO_NETHER_FUKUMAME = new NetherFukumameItem(new Item.Properties().maxStackSize(1).maxDamage(64).group(TofuItemGroup.TOFUCRAFT), true);
	public static final Item SOUL_FUKUMAME = new SoulFukumameItem(new Item.Properties().maxStackSize(1).maxDamage(64).group(TofuItemGroup.TOFUCRAFT));


	public static final Item SEEDS_SOYBEANS = new BlockNamedItem(TofuBlocks.SOYBEAN, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item SEEDS_SOYBEANS_NETHER = new BlockNamedItem(TofuBlocks.SOYBEAN_NETHER, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item SEEDS_SOYBEANS_SOUL = new BlockNamedItem(TofuBlocks.SOYBEAN_SOUL, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item SOYBEAN_PARCHED = new BlockNamedItem(TofuBlocks.SOYBEAN_SOUL, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item EDAMAME = new Item(new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item BUCKET_SOYMILK = new BucketItem(() -> TofuFluids.SOYMILK, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
	public static final Item BUCKET_SOYMILK_NETHER = new BucketItem(() -> TofuFluids.SOYMILK_HELL, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));
	public static final Item BUCKET_SOYMILK_SOUL = new BucketItem(() -> TofuFluids.SOYMILK_SOUL, (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(TofuItemGroup.TOFUCRAFT));

	public static final Item KINUSWORD = new SwordItem(TofuItemTier.KINU, 0, -2.2F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item KINUAXE = new AxeItem(TofuItemTier.KINU, 0.0F, -2.25F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item KINUPICKAXE = new PickaxeItem(TofuItemTier.KINU, 0, -2.2F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item KINUSHOVEL = new ShovelItem(TofuItemTier.KINU, 0, -2.2F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item MOMENSWORD = new SwordItem(TofuItemTier.MOMEN, 0, -2.2F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item MOMENAXE = new AxeItem(TofuItemTier.MOMEN, 1.0F, -2.5F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item MOMENPICKAXE = new PickaxeItem(TofuItemTier.MOMEN, 0, -2.25F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item MOMENSHOVEL = new ShovelItem(TofuItemTier.MOMEN, 0, -2.25F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item SOLIDSWORD = new SwordItem(TofuItemTier.SOLID, 3, -2.3F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item SOLIDAXE = new AxeItem(TofuItemTier.SOLID, 7.0F, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item SOLIDPICKAXE = new PickaxeItem(TofuItemTier.SOLID, 1, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item SOLIDSHOVEL = new ShovelItem(TofuItemTier.SOLID, 1.5F, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item METALSWORD = new SwordItem(TofuItemTier.METAL, 3, -2.3F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item METALAXE = new AxeItem(TofuItemTier.METAL, 6.0F, -3.0F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item METALPICKAXE = new PickaxeItem(TofuItemTier.METAL, 1, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item METALSHOVEL = new ShovelItem(TofuItemTier.METAL, 1.5F, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item TOFUDIAMONDSWORD = new SwordItem(TofuItemTier.TOFUDIAMOND, 3, -2.3F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUDIAMONDAXE = new AxeItem(TofuItemTier.TOFUDIAMOND, 6.0F, -3.0F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUDIAMONDPICKAXE = new PickaxeItem(TofuItemTier.TOFUDIAMOND, 1, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item TOFUDIAMONDSHOVEL = new ShovelItem(TofuItemTier.TOFUDIAMOND, 1.5F, -2.9F, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item ARMOR_KINUHELMET = new ArmorItem(TofuArmorMaterial.KINU, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_KINUCHESTPLATE = new ArmorItem(TofuArmorMaterial.KINU, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_KINULEGGINS = new ArmorItem(TofuArmorMaterial.KINU, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_KINUBOOTS = new ArmorItem(TofuArmorMaterial.KINU, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item ARMOR_MOMENHELMET = new ArmorItem(TofuArmorMaterial.MOMEN, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_MOMENCHESTPLATE = new ArmorItem(TofuArmorMaterial.MOMEN, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_MOMENLEGGINS = new ArmorItem(TofuArmorMaterial.MOMEN, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_MOMENBOOTS = new ArmorItem(TofuArmorMaterial.MOMEN, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item ARMOR_SOLIDHELMET = new ArmorItem(TofuArmorMaterial.SOLID, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_SOLIDCHESTPLATE = new ArmorItem(TofuArmorMaterial.SOLID, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_SOLIDLEGGINS = new ArmorItem(TofuArmorMaterial.SOLID, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_SOLIDBOOTS = new ArmorItem(TofuArmorMaterial.SOLID, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item ARMOR_METALHELMET = new ArmorItem(TofuArmorMaterial.METAL, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_METALCHESTPLATE = new ArmorItem(TofuArmorMaterial.METAL, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_METALLEGGINS = new ArmorItem(TofuArmorMaterial.METAL, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_METALBOOTS = new ArmorItem(TofuArmorMaterial.METAL, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));

	public static final Item ARMOR_DIAMONDHELMET = new ArmorItem(TofuArmorMaterial.DIAMOND, EquipmentSlotType.HEAD, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_DIAMONDCHESTPLATE = new ArmorItem(TofuArmorMaterial.DIAMOND, EquipmentSlotType.CHEST, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_DIAMONDLEGGINS = new ArmorItem(TofuArmorMaterial.DIAMOND, EquipmentSlotType.LEGS, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));
	public static final Item ARMOR_DIAMONDBOOTS = new ArmorItem(TofuArmorMaterial.DIAMOND, EquipmentSlotType.FEET, new Item.Properties().group(TofuItemGroup.TOFUCRAFT));


	public static void register(RegistryEvent.Register<Item> registry, Item item, String id) {
		if (item instanceof BlockItem) {
			Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
		}

		item.setRegistryName(new ResourceLocation(TofuCraftReload.MODID, id));

		registry.getRegistry().register(item);
	}

	public static void register(RegistryEvent.Register<Item> registry, Item item) {

		if (item instanceof BlockItem && item.getRegistryName() == null) {
			item.setRegistryName(((BlockItem) item).getBlock().getRegistryName());

			Item.BLOCK_TO_ITEM.put(((BlockItem) item).getBlock(), item);
		}

		registry.getRegistry().register(item);
	}


	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> registry) {
		register(registry, TOFUKINU, "tofukinu");
		register(registry, TOFUMOMEN, "tofumomen");
		register(registry, TOFUISHI, "tofuishi");
		register(registry, TOFUMETAL, "tofumetal");
		register(registry, TOFUDIAMOND, "tofudiamond");
		register(registry, TOFUHELL, "tofuhell");
		register(registry, TOFUSOUL, "tofusoul");

		register(registry, BITTERN, "bittern_bottle");
		register(registry, SALT, "salt");
		register(registry, TOFUSCOOP, "tofuscoop");
		register(registry, FUKUMAME, "fukumame");
		register(registry, NETHER_FUKUMAME, "nether_fukumame");
		register(registry, INFERNO_NETHER_FUKUMAME, "inferno_nether_fukumame");
		register(registry, SOUL_FUKUMAME, "soul_fukumame");

		register(registry, SEEDS_SOYBEANS, "seeds_soybeans");
		register(registry, SEEDS_SOYBEANS_NETHER, "seeds_soybeans_nether");
		register(registry, SEEDS_SOYBEANS_SOUL, "seeds_soybeans_soul");
		register(registry, SOYBEAN_PARCHED, "soybeans_parched");
		register(registry, EDAMAME, "edamame");

		register(registry, BUCKET_SOYMILK, "bucket_soymilk");
		register(registry, BUCKET_SOYMILK_NETHER, "bucket_soymilk_nether");
		register(registry, BUCKET_SOYMILK_SOUL, "bucket_soymilk_soul");

		register(registry, KINUSWORD, "swordkinu");
		register(registry, KINUAXE, "toolkinuaxe");
		register(registry, KINUPICKAXE, "toolkinupickaxe");
		register(registry, KINUSHOVEL, "toolkinushovel");

		register(registry, MOMENSWORD, "swordmomen");
		register(registry, MOMENAXE, "toolmomenaxe");
		register(registry, MOMENPICKAXE, "toolmomenpickaxe");
		register(registry, MOMENSHOVEL, "toolmomenshovel");

		register(registry, SOLIDSWORD, "swordsolid");
		register(registry, SOLIDAXE, "toolsolidaxe");
		register(registry, SOLIDPICKAXE, "toolsolidpickaxe");
		register(registry, SOLIDSHOVEL, "toolsolidshovel");

		register(registry, METALSWORD, "swordmetal");
		register(registry, METALAXE, "toolmetalaxe");
		register(registry, METALPICKAXE, "toolmetalpickaxe");
		register(registry, METALSHOVEL, "toolmetalshovel");

		register(registry, TOFUDIAMONDSWORD, "sworddiamond");
		register(registry, TOFUDIAMONDAXE, "tooldiamondaxe");
		register(registry, TOFUDIAMONDPICKAXE, "tooldiamondpickaxe");
		register(registry, TOFUDIAMONDSHOVEL, "tooldiamondshovel");

		register(registry, ARMOR_KINUHELMET, "armorkinuhelmet");
		register(registry, ARMOR_KINUCHESTPLATE, "armorkinuchestplate");
		register(registry, ARMOR_KINULEGGINS, "armorkinuleggins");
		register(registry, ARMOR_KINUBOOTS, "armorkinuboots");

		register(registry, ARMOR_MOMENHELMET, "armormomenhelmet");
		register(registry, ARMOR_MOMENCHESTPLATE, "armormomenchestplate");
		register(registry, ARMOR_MOMENLEGGINS, "armormomenleggins");
		register(registry, ARMOR_MOMENBOOTS, "armormomenboots");

		register(registry, ARMOR_SOLIDHELMET, "armorsolidhelmet");
		register(registry, ARMOR_SOLIDCHESTPLATE, "armorsolidchestplate");
		register(registry, ARMOR_SOLIDLEGGINS, "armorsolidleggins");
		register(registry, ARMOR_SOLIDBOOTS, "armorsolidboots");

		register(registry, ARMOR_METALHELMET, "armormetalhelmet");
		register(registry, ARMOR_METALCHESTPLATE, "armormetalchestplate");
		register(registry, ARMOR_METALLEGGINS, "armormetalleggins");
		register(registry, ARMOR_METALBOOTS, "armormetalboots");

		register(registry, ARMOR_DIAMONDHELMET, "armordiamondhelmet");
		register(registry, ARMOR_DIAMONDCHESTPLATE, "armordiamondchestplate");
		register(registry, ARMOR_DIAMONDLEGGINS, "armordiamondleggins");
		register(registry, ARMOR_DIAMONDBOOTS, "armordiamondboots");
	}
}
