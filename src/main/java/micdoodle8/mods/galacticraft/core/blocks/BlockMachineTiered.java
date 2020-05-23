package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.tile.IMachineSides;
import micdoodle8.mods.galacticraft.core.tile.IMachineSidesProperties;
import micdoodle8.mods.galacticraft.core.tile.TileEntityElectricFurnace;
import micdoodle8.mods.galacticraft.core.tile.TileEntityEnergyStorageModule;
import net.minecraft.block.BlockState;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockMachineTiered extends BlockMachineBase
{
    public static final EnumProperty<EnumTieredMachineType> TYPE = EnumProperty.create("type", EnumTieredMachineType.class);
    public static IMachineSidesProperties MACHINESIDES_RENDERTYPE = IMachineSidesProperties.TWOFACES_HORIZ;
    public static final PropertyEnum SIDES = MACHINESIDES_RENDERTYPE.asProperty;
    public static final IntegerProperty FILL_VALUE = IntegerProperty.create("fill_value", 0, 33);

    public enum EnumTieredMachineType implements EnumMachineBase, IStringSerializable
    {
        STORAGE_MODULE(0, "energy_storage", TileEntityEnergyStorageModule::new, "tile.energy_storage_module_tier1.description", "tile.machine.1"),
        ELECTRIC_FURNACE(4, "electric_furnace", TileEntityElectricFurnace::new, "tile.electric_furnace_tier1.description", "tile.machine.2"),
        STORAGE_CLUSTER(8, "cluster_storage", TileEntityEnergyStorageModule::new, "tile.energy_storage_module_tier2.description", "tile.machine.8"),
        ARC_FURNACE(12, "arc_furnace", TileEntityElectricFurnace::new, "tile.electric_furnace_tier2.description", "tile.machine.7");

        private final int meta;
        private final String name;
        private TileConstructor tile;
        private final String shiftDescriptionKey;
        private final String blockName;

        EnumTieredMachineType(int meta, String name, TileConstructor tc, String key, String blockName)
        {
            this.meta = meta;
            this.name = name;
            this.tile = tc;
            this.shiftDescriptionKey = key;
            this.blockName = blockName;
        }

        @Override
        public int getMetadata()
        {
            return this.meta;
        }

        private final static EnumTieredMachineType[] values = values();
        @Override
        public EnumMachineBase fromMetadata(int meta)
        {
            return values[(meta / 4) % values.length];
        }
        
        @Override
        public String getName()
        {
            return this.name;
        }
        
        @Override
        public TileEntity tileConstructor()
        {
            int tier = this.meta / 8 + 1;
            return this.tile.create(tier);
        }
        
        @FunctionalInterface
        private static interface TileConstructor
        {
              TileEntity create(int tier);  //Note this variant picks up the new TileEntityStorageModule(int tier) constructor, and forces the ::new above to that
        }

        @Override
        public String getShiftDescriptionKey()
        {
            return this.shiftDescriptionKey;
        }

        @Override
        public String getUnlocalizedName()
        {
            return this.blockName;
        }
    }

    public BlockMachineTiered(Properties builder)
    {
        super(builder);
    }

    @Override
    protected void initialiseTypes()
    {
        this.types = EnumTieredMachineType.values;
        this.typeBase = EnumTieredMachineType.values[0];
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
//        TileEntity tile = super.createTileEntity(state, world);
//        tile.setWorld(world); TODO Needed?
        return super.createTileEntity(state, world);
    }

    @Override
    public BlockState getStateFromMeta(int meta)
    {
        Direction enumfacing = Direction.getHorizontal(meta % 4);
        EnumTieredMachineType type = (EnumTieredMachineType) typeBase.fromMetadata(meta);
        return this.getDefaultState().with(FACING, enumfacing).with(TYPE, type);
    }

    @Override
    public int getMetaFromState(BlockState state)
    {
        return ((Direction) state.get(FACING)).getHorizontalIndex() + ((EnumTieredMachineType) state.get(TYPE)).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE, FILL_VALUE, SIDES);
    }

    @Override
    public BlockState getActualState(BlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        TileEntity tile = worldIn.getTileEntity(pos);
        state = IMachineSides.addPropertyForTile(state, tile, MACHINESIDES_RENDERTYPE, SIDES);

        if (!(tile instanceof TileEntityEnergyStorageModule))
        {
            return state.with(FILL_VALUE, 0);
        }
        int energyLevel = ((TileEntityEnergyStorageModule) tile).scaledEnergyLevel;
        if (state.get(TYPE) == EnumTieredMachineType.STORAGE_CLUSTER) energyLevel += 17;
        return state.with(FILL_VALUE, energyLevel);
    }
}
