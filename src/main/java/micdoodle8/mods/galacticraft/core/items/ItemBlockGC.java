package micdoodle8.mods.galacticraft.core.items;

import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.block.Block;
import net.minecraft.item.Rarity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockGC extends BlockItem
{
    public ItemBlockGC(Block block)
    {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Rarity getRarity(ItemStack par1ItemStack)
    {
        return ClientProxyCore.galacticraftItem;
    }
}
