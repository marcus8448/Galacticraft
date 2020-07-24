package micdoodle8.mods.galacticraft.core.inventory;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import java.util.List;

public class SlotRocketBench extends Slot
{
    private final int index;
    //    private final BlockPos pos;
    private final PlayerEntity player;

    public SlotRocketBench(IInventory par2IInventory, int par3, int par4, int par5, PlayerEntity player)
    {
        super(par2IInventory, par3, par4, par5);
        this.index = par3;
//        this.pos = pos;
        this.player = player;
    }

    @Override
    public void onSlotChanged()
    {
        if (this.player instanceof ServerPlayerEntity)
        {
//            DimensionType dimID = GCCoreUtil.getDimensionID(this.player.world);
//            GCCoreUtil.sendToAllAround(new PacketSimple(EnumSimplePacket.C_SPAWN_SPARK_PARTICLES, dimID, new Object[] { this.pos }), this.player.world, dimID, this.pos, 20); TODO Spark particles
        }
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {

        List<INasaWorkbenchRecipe> recipes = GalacticraftRegistry.getRocketT1Recipes();
        for (INasaWorkbenchRecipe recipe : recipes)
        {
            if (ItemStack.areItemsEqual(par1ItemStack, recipe.getRecipeInput().get(this.index)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as
     * getInventoryStackLimit(), but 1 in the case of armor slots)
     */
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}
