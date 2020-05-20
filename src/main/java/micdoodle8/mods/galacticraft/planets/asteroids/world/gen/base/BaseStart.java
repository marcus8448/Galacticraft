package micdoodle8.mods.galacticraft.planets.asteroids.world.gen.base;

import com.google.common.collect.Lists;

import net.minecraft.util.Direction;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.feature.StructurePiece;

import java.util.List;
import java.util.Random;

public class BaseStart extends BaseDeck
{
    public List<StructurePiece> attachedComponents = Lists.newArrayList();
    public List<MutableBoundingBox> componentBounds = Lists.newArrayList();

    public BaseStart()
    {
    }

    public BaseStart(BaseConfiguration configuration, Random rand, int blockPosX, int blockPosZ, Direction direction)
    {
        super(configuration, rand, blockPosX, configuration.getYPosition(), blockPosZ, 1, direction);
    }

    @Override
    public void buildComponent(StructurePiece componentIn, List<StructurePiece> listIn, Random rand)
    {
        attachedComponents.clear();
        componentBounds.clear();
        componentBounds.add(this.boundingBox);
        listIn.clear();
        listIn.add(this);
        List<Piece> rooms = getRooms(0, this, rand);
        for(Piece next : rooms)
        {
            listIn.add(next);
            attachedComponents.add(next);
            componentBounds.add(next.getBoundingBox());
        }

        //TODO  applyAsteroidDamage();

        super.buildComponent(componentIn, listIn, rand);
    }
}