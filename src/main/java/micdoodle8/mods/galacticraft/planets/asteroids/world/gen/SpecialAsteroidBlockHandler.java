package micdoodle8.mods.galacticraft.planets.asteroids.world.gen;

import java.util.ArrayList;
import java.util.Random;

public class SpecialAsteroidBlockHandler
{

    final ArrayList<SpecialAsteroidBlock> asteroidBlocks;

    public SpecialAsteroidBlockHandler(SpecialAsteroidBlock... asteroidBlocks)
    {
        this.asteroidBlocks = new ArrayList<>();
    }

    public SpecialAsteroidBlockHandler()
    {
        this.asteroidBlocks = new ArrayList<>();
    }

    public void addBlock(SpecialAsteroidBlock asteroidBlock)
    {
        for (int i = 0; i < asteroidBlock.probability; i++)
        {
            this.asteroidBlocks.add(asteroidBlock);
        }
    }

    public SpecialAsteroidBlock getBlock(Random rand, int size)
    {
        int s = this.asteroidBlocks.size();
        if (s < 10)
        {
            return this.asteroidBlocks.get(rand.nextInt(s));
        }

        double r = rand.nextDouble();
        int index = (int) (s * Math.pow(r, (size + 5) * 0.05D));
        return this.asteroidBlocks.get(index);
    }

}
