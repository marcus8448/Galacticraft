package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.tile.TileEntityCargoLoader;
import micdoodle8.mods.galacticraft.core.tile.TileEntityCargoUnloader;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockCargoLoader extends BlockAdvancedTile implements IShiftDescription, ISortableBlock
{
    private enum EnumLoaderType implements IStringSerializable
    {
        CARGO_LOADER(METADATA_CARGO_LOADER, "cargo_loader"),
        CARGO_UNLOADER(METADATA_CARGO_UNLOADER, "cargo_unloader");

        private final int meta;
        private final String name;

        EnumLoaderType(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta()
        {
            return this.meta;
        }

        @Override
        public String getName()
        {
            return this.name;
        }
    }

    public static final EnumProperty<EnumLoaderType> TYPE = EnumProperty.create("type", EnumLoaderType.class);
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public static final int METADATA_CARGO_LOADER = 0;
    public static final int METADATA_CARGO_UNLOADER = 4;

    public BlockCargoLoader(Properties builder)
    {
        super(builder);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(ItemGroup tab, NonNullList<ItemStack> list)
    {
        list.add(new ItemStack(this, 1, BlockCargoLoader.METADATA_CARGO_LOADER));
        list.add(new ItemStack(this, 1, BlockCargoLoader.METADATA_CARGO_UNLOADER));
    }

    @Override
    public ItemGroup getCreativeTabToDisplayOn()
    {
        return GalacticraftCore.galacticraftBlocksTab;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, BlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);

        TileEntity tileEntity = worldIn.getTileEntity(pos);

        if (tileEntity != null)
        {
            if (tileEntity instanceof TileEntityCargoLoader)
            {
                ((TileEntityCargoLoader) tileEntity).checkForCargoEntity();
            }
            else if (tileEntity instanceof TileEntityCargoUnloader)
            {
                ((TileEntityCargoUnloader) tileEntity).checkForCargoEntity();
            }
        }
    }

    @Override
    public boolean onMachineActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, ItemStack heldItem, BlockRayTraceResult hit)
    {
        playerIn.openGui(GalacticraftCore.instance, -1, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        if (state.get(TYPE) == EnumLoaderType.CARGO_LOADER)
        {
            return new TileEntityCargoLoader();
        }
        else
        {
            return new TileEntityCargoUnloader();
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        final int angle = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
//        int change = EnumFacing.getHorizontal(angle).getOpposite().getHorizontalIndex();
//
//        if (stack.getItemDamage() >= METADATA_CARGO_UNLOADER)
//        {
//            change += METADATA_CARGO_UNLOADER;
//        }
//        else if (stack.getItemDamage() >= METADATA_CARGO_LOADER)
//        {
//            change += METADATA_CARGO_LOADER;
//        }

        worldIn.setBlockState(pos, state.with(FACING, Direction.getHorizontal(angle).getOpposite()), 3);
        WorldUtil.markAdjacentPadForUpdate(worldIn, pos);
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, BlockState state)
    {
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
        WorldUtil.markAdjacentPadForUpdate(worldIn, pos);
    }

    @Override
    public int damageDropped(BlockState state)
    {
        return state.get(TYPE).getMeta();
    }

    @Override
    public String getShiftDescription(int meta)
    {
        switch (meta)
        {
        case METADATA_CARGO_LOADER:
            return GCCoreUtil.translate("tile.cargo_loader.description");
        case METADATA_CARGO_UNLOADER:
            return GCCoreUtil.translate("tile.cargo_unloader.description");
        }
        return "";
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }

    @Override
    public BlockState getStateFromMeta(int meta)
    {
        Direction enumfacing = Direction.getHorizontal(meta % 4);
        EnumLoaderType type = meta >= METADATA_CARGO_UNLOADER ? EnumLoaderType.CARGO_UNLOADER : EnumLoaderType.CARGO_LOADER;

        return this.getDefaultState().with(FACING, enumfacing).with(TYPE, type);
    }

    @Override
    public int getMetaFromState(BlockState state)
    {
        return state.get(FACING).getHorizontalIndex() + state.get(TYPE).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    public EnumSortCategoryBlock getCategory(int meta)
    {
        return EnumSortCategoryBlock.MACHINE;
    }
}
