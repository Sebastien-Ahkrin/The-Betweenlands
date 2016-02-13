package thebetweenlands.client.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import thebetweenlands.client.model.entity.ModelDreadfulMummy;
import thebetweenlands.entities.mobs.EntityPeatMummy;

@SideOnly(Side.CLIENT)
public class RenderDreadfulMummy extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("thebetweenlands:textures/entity/dreadfulMummy.png");

	public RenderDreadfulMummy() {
		super(new ModelDreadfulMummy(), 0.7F);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
	
	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float pitch) {
		if(((EntityPeatMummy)entity).getSpawningProgress() < 0.1F) {
			return;
		}
        super.doRender(entity, x, y, z, yaw, pitch);
    }
}