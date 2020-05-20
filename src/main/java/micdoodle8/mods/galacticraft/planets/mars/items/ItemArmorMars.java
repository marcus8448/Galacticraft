package micdoodle8.mods.galacticraft.planets.mars.items;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.entity.Entity;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorMars extends ArmorItem implements ISortableItem
{
    private final ArmorMaterial material;

    public ItemArmorMars(ArmorMaterial par2EnumArmorMaterial, int par3, EquipmentSlotType par4)
    {
        super(par2EnumArmorMaterial, par3, par4);
        this.material = par2EnumArmorMaterial;
    }

    /*@Override
    public Item setUnlocalizedName(String par1Str)
    {
//        super.setTextureName(par1Str);
        super.setUnlocalizedName(par1Str);
        return this;
    }*/

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type)
    {
        if (this.material == MarsItems.ARMORDESH)
        {
            if (stack.getItem() == MarsItems.deshHelmet)
            {
                return GalacticraftPlanets.TEXTURE_PREFIX + "textures/model/armor/desh_1.png";
            }
            else if (stack.getItem() == MarsItems.deshChestplate || stack.getItem() == MarsItems.deshBoots)
            {
                return GalacticraftPlanets.TEXTURE_PREFIX + "textures/model/armor/desh_2.png";
            }
            else if (stack.getItem() == MarsItems.deshLeggings)
            {
                return GalacticraftPlanets.TEXTURE_PREFIX + "textures/model/armor/desh_3.png";
            }
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemGroup getCreativeTab()
    {
        return GalacticraftCore.galacticraftItemsTab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Rarity getRarity(ItemStack par1ItemStack)
    {
        return ClientProxyCore.galacticraftItem;
    }

    @Override
    public EnumSortCategoryItem getCategory(int meta)
    {
        return EnumSortCategoryItem.ARMOR;
    }
    
    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == MarsItems.marsItemBasic && repair.getItemDamage() == 2;
    }
}
