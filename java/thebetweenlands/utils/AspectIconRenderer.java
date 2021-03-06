package thebetweenlands.utils;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class AspectIconRenderer {
	public static final ResourceLocation ASPECT_MAP = new ResourceLocation("thebetweenlands:textures/items/strictlyHerblore/misc/aspectMap.png");

	public static final int TEX_WIDTH = 64, TEX_HEIGHT = 64, ICON_WIDTH = 16, ICON_HEIGHT = 16, ICONS_H = TEX_WIDTH / ICON_WIDTH;

	private static final double REL_ICON_WIDTH = (double) ICON_WIDTH / (double) TEX_WIDTH, REL_ICON_HEIGHT = (double) ICON_HEIGHT / (double) TEX_HEIGHT;

	public static void renderIcon(int x, int y, int width, int height, int id) {
		int iconH = id % ICONS_H;
		int iconV = id / ICONS_H;
		double u = iconH * REL_ICON_WIDTH;
		double v = iconV * REL_ICON_WIDTH;
		Minecraft.getMinecraft().renderEngine.bindTexture(ASPECT_MAP);
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		GL11.glDisable(GL11.GL_BLEND);
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.addVertexWithUV(0, 0, 0, u, v);
		Tessellator.instance.addVertexWithUV(0, height, 0, u, v+REL_ICON_HEIGHT);
		Tessellator.instance.addVertexWithUV(width, height, 0, u+REL_ICON_WIDTH, v+REL_ICON_HEIGHT);
		Tessellator.instance.addVertexWithUV(width, 0, 0, u+REL_ICON_WIDTH, v);
		Tessellator.instance.draw();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}
