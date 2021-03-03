package baguchan.tofucraft.registry;

import baguchan.tofucraft.api.BitternRecipes;
import baguchan.tofucraft.api.HardenRecipes;

public class TofuRecipes {
	public static void register() {
		//Example recipe
		//BitternRecipes.addRecipe(Fluids.LAVA, Blocks.MAGMA_BLOCK);

		BitternRecipes.addRecipe(TofuFluids.SOYMILK, TofuBlocks.KINUTOFU);
		BitternRecipes.addRecipe(TofuFluids.SOYMILK_HELL, TofuBlocks.HELLTOFU);
		BitternRecipes.addRecipe(TofuFluids.SOYMILK_SOUL, TofuBlocks.SOULTOFU);

		HardenRecipes.addRecipe(TofuBlocks.MOMENTOFU, TofuBlocks.ISHITOFU);
		HardenRecipes.addRecipe(TofuBlocks.ISHITOFU, TofuBlocks.METALTOFU);
	}
}
