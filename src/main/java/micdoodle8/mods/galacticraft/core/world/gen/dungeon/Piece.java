package micdoodle8.mods.galacticraft.core.world.gen.dungeon;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public abstract class Piece extends StructurePiece
{
    protected DungeonConfiguration configuration;

    public Piece()
    {
    }

    public Piece(DungeonConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    protected void writeStructureToNBT(CompoundNBT tagCompound)
    {
        this.configuration.writeToNBT(tagCompound);
    }

    @Override
    protected void readStructureFromNBT(CompoundNBT tagCompound, TemplateManager manager)
    {
        if (this.configuration == null)
        {
            this.configuration = new DungeonConfiguration();
            this.configuration.readFromNBT(tagCompound);
        }
    }

    protected MutableBoundingBox getExtension(Direction direction, int length, int width)
    {
        int blockX, blockZ, sizeX, sizeZ;
        switch (direction)
        {
        case NORTH:
            sizeX = width;
            sizeZ = length;
            blockX = this.boundingBox.minX + (this.boundingBox.maxX - this.boundingBox.minX) / 2 - sizeX / 2;
            blockZ = this.boundingBox.minZ - sizeZ;
            break;
        case EAST:
            sizeX = length;
            sizeZ = width;
            blockX = this.boundingBox.maxX;
            blockZ = this.boundingBox.minZ + (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 - sizeZ / 2;
            break;
        case SOUTH:
            sizeX = width;
            sizeZ = length;
            blockX = this.boundingBox.minX + (this.boundingBox.maxX - this.boundingBox.minX) / 2 - sizeX / 2;
            blockZ = this.boundingBox.maxZ;
            break;
        case WEST:
        default:
            sizeX = length;
            sizeZ = width;
            blockX = this.boundingBox.minX - sizeX;
            blockZ = this.boundingBox.minZ + (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 - sizeZ / 2;
            break;
        }
        return new MutableBoundingBox(blockX, this.configuration.getYPosition(), blockZ, blockX + sizeX, this.configuration.getYPosition() + this.configuration.getHallwayHeight(),blockZ + sizeZ);
    }

    public Piece getNextPiece(DungeonStart startPiece, Random rand)
    {
        return null;
    }
}