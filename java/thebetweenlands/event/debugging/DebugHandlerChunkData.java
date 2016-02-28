package thebetweenlands.event.debugging;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import thebetweenlands.world.storage.chunk.BetweenlandsChunkData;

public class DebugHandlerChunkData {
	public static final DebugHandlerChunkData INSTANCE = new DebugHandlerChunkData();

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent event) {
		for(int cx = -6; cx < 6; cx++) {
			for(int cz = -6; cz < 6; cz++) {
				int cpx = (int)(Minecraft.getMinecraft().thePlayer.posX / 16 + cx);
				int cpz = (int)(Minecraft.getMinecraft().thePlayer.posZ / 16 + cz);
				Chunk chunk = Minecraft.getMinecraft().theWorld.getChunkFromChunkCoords(cpx, cpz);
				if(chunk != null) {
					BetweenlandsChunkData data = BetweenlandsChunkData.forChunk(chunk);
					if(data.hasData) {
						//GL11.glTranslated(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);
						//GL11.glTranslated(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);


						GL11.glPushMatrix();
						
						//System.out.println("TEST");

						//GL11.glTranslated(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ);

						GL11.glTranslated(cpx * 16 - RenderManager.renderPosX, 90 - RenderManager.renderPosY, cpz * 16 - RenderManager.renderPosZ);

						GL11.glDisable(GL11.GL_TEXTURE_2D);
						GL11.glDisable(GL11.GL_BLEND);
						GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
						GL11.glColor4f(1, 1, 1, 1);
						GL11.glDisable(GL11.GL_CULL_FACE);

						Sphere s = new Sphere();
						s.draw(1, 10, 10);

						GL11.glPopMatrix();

						GL11.glEnable(GL11.GL_TEXTURE_2D);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onBlockPlace(PlaceEvent event) {
		Chunk chunk = event.world.getChunkFromChunkCoords(event.x / 16, event.z / 16);
		if(chunk != null && !event.world.isRemote) {
			BetweenlandsChunkData data = BetweenlandsChunkData.forChunk(chunk);
			data.hasData ^= true;
			data.markDirty();
		}
	}
}