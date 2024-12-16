package nuclearscience.client.render.event.levelstage;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import electrodynamics.client.render.event.levelstage.AbstractLevelStageHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

import java.util.*;

public class HandlerCloudChamber extends AbstractLevelStageHandler {

    public static final HandlerCloudChamber INSTANCE = new HandlerCloudChamber();

    private HashSet<BlockPos> locations = new HashSet<>();

    @Override
    public boolean shouldRender(RenderLevelStageEvent.Stage stage) {
        return stage == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS;
    }

    @Override
    public void render(Camera camera, Frustum frustum, LevelRenderer levelRenderer, PoseStack poseStack, Matrix4f matrix4f, Minecraft minecraft, int renderTick, DeltaTracker deltaTracker) {

        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        VertexConsumer builder = buffer.getBuffer(RenderType.LINES);
        Vec3 camPos = camera.getPosition();

        locations.forEach(source -> {

            AABB outline = new AABB(source);
            poseStack.pushPose();
            poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
            LevelRenderer.renderLineBox(poseStack, builder, outline, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();

        });

        buffer.endBatch(RenderType.LINES);

        locations.clear();

    }

    @Override
    public void clear() {
        locations.clear();
    }

    public static void addSources(List<BlockPos> sources) {
        INSTANCE.locations.addAll(sources);
    }
}
