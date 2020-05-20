package micdoodle8.mods.galacticraft.planets.venus.tile;

import micdoodle8.mods.galacticraft.core.client.sounds.GCSounds;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import micdoodle8.mods.galacticraft.planets.venus.entities.EntitySpiderQueen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.List;

public class TileEntityDungeonSpawnerVenus extends TileEntityDungeonSpawner<EntitySpiderQueen>
{
    public TileEntityDungeonSpawnerVenus()
    {
        super(EntitySpiderQueen.class);
    }

    @Override
    public List<Class<? extends MobEntity>> getDisabledCreatures()
    {
        List<Class<? extends MobEntity>> list = new ArrayList<Class<? extends MobEntity>>();
        list.add(EntityEvolvedSkeleton.class);
        list.add(EntityEvolvedZombie.class);
        list.add(EntityEvolvedSpider.class);
        return list;
    }

    @Override
    public void playSpawnSound(Entity entity)
    {
        this.world.playSound(null, entity.posX, entity.posY, entity.posZ, GCSounds.scaryScape, SoundCategory.AMBIENT, 9.0F, 1.4F);
    }
}
