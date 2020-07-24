//package micdoodle8.mods.galacticraft.planets.mars.client.render.entity;
//
//import com.google.common.collect.ImmutableList;
//import com.mojang.blaze3d.systems.RenderSystem;
//import micdoodle8.mods.galacticraft.core.util.ClientUtil;
//import micdoodle8.mods.galacticraft.planets.GalacticraftPlanets;
//import micdoodle8.mods.galacticraft.planets.mars.client.model.ModelBalloonParachute;
//import micdoodle8.mods.galacticraft.planets.mars.entities.EntityLandingBalloons;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.RenderHelper;
//import net.minecraft.client.renderer.entity.EntityRenderer;
//import net.minecraft.client.renderer.entity.EntityRendererManager;
//import net.minecraft.client.renderer.model.IBakedModel;
//import net.minecraft.client.renderer.texture.AtlasTexture;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.client.model.ModelLoader;
//import org.lwjgl.opengl.GL11;
//
//import java.io.IOException;
//
//@OnlyIn(Dist.CLIENT)
//public class RenderLandingBalloons extends EntityRenderer<EntityLandingBalloons>
//{
//    private static IBakedModel balloonModel;
//    protected ModelBalloonParachute parachuteModel = new ModelBalloonParachute();
//
//    public RenderLandingBalloons(EntityRendererManager manager)
//    {
//        super(manager);
//        this.shadowSize = 1.2F;
//    }
//
//    public static void updateModels(ModelLoader modelLoader)
//    {
//        try
//        {
//            balloonModel = ClientUtil.modelFromOBJ(modelLoader, new ResourceLocation(GalacticraftPlanets.ASSET_PREFIX, "landing_balloon.obj"), ImmutableList.of("Sphere"));
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected ResourceLocation getEntityTexture(EntityLandingBalloons entity)
//    {
//        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
//    }
//
//    @Override
//    public void doRender(EntityLandingBalloons entity, double x, double y, double z, float entityYaw, float partialTicks)
//    {
//        float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
//        RenderSystem.disableRescaleNormal();
//        RenderSystem.pushMatrix();
//        RenderSystem.translatef((float) x, (float) y + 0.8F, (float) z);
//        RenderSystem.rotatef(entityYaw, 0.0F, 1.0F, 0.0F);
//        RenderSystem.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
//        RenderSystem.rotatef(pitch, 0.0F, 0.0F, 1.0F);
//
//        this.bindEntityTexture(entity);
//
//        if (Minecraft.isAmbientOcclusionEnabled())
//        {
//            RenderSystem.shadeModel(7425);
//        }
//        else
//        {
//            RenderSystem.shadeModel(7424);
//        }
//
//        RenderSystem.scalef(-1.0F, -1.0F, 1.0F);
//        RenderSystem.scalef(0.5F, 0.5F, 0.5F);
//        ClientUtil.drawBakedModel(balloonModel);
//        RenderSystem.popMatrix();
//
//        if (entity.getPosY() >= 500.0F)
//        {
//            RenderSystem.pushMatrix();
//            RenderSystem.translatef((float) x - 1.25F, (float) y - 0.93F, (float) z - 0.3F);
//            RenderSystem.scalef(2.5F, 3.0F, 2.5F);
//            this.parachuteModel.renderAll();
//            RenderSystem.popMatrix();
//        }
//        RenderHelper.enableStandardItemLighting();
//    }
//
//    @Override
//    public boolean shouldRender(EntityLandingBalloons lander, ICamera camera, double camX, double camY, double camZ)
//    {
//        AxisAlignedBB axisalignedbb = lander.getBoundingBox().grow(2D, 1D, 2D);
//        return lander.isInRangeToRender3d(camX, camY, camZ) && camera.isBoundingBoxInFrustum(axisalignedbb);
//    }
//}
