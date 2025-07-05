package net.night.grasses.block.blockEntity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.night.grasses.block.blockEntity.DyeingStationBlockEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static net.night.grasses.block.station.DyeingStationBlock.FACING;

public class DyeingStationBlockEntityRenderer implements BlockEntityRenderer<DyeingStationBlockEntity> {

    public DyeingStationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }


    @Override
    public void render(DyeingStationBlockEntity dyeingStationBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        List<List<Float>> poseStackPosition;

        poseStackPosition = Arrays.asList(
                Arrays.asList(0.5f, 0.5f, 0.90f, 0f),  //0 output
                Arrays.asList(0.35f, 0.95f, 0.35f, 0f),   //1 bucket
                Arrays.asList(0.5f, 0.95f, 0.65f, 0f),    //2 tool
                Arrays.asList(0.65f, 1f, 0.35f, 0f)     //3 ingredient
        );

        if (dyeingStationBlockEntity.getBlockState().getValue(FACING) == Direction.WEST) {

            poseStackPosition = Arrays.asList(
                    Arrays.asList(0.1f, 0.5f, 0.5f, 90f),  //0 output
                    Arrays.asList(0.65f, 0.95f, 0.35f, 90f),   //1 bucket
                    Arrays.asList(0.35f, 0.95f, 0.5f, 90f),    //2 tool
                    Arrays.asList(0.65f, 1f, 0.65f, 90f)     //3 ingredient
            );

        } else if (dyeingStationBlockEntity.getBlockState().getValue(FACING) == Direction.EAST) {

            poseStackPosition = Arrays.asList(
                    Arrays.asList(0.9f, 0.5f, 0.5f, 90f),  //0 output
                    Arrays.asList(0.35f, 0.95f, 0.65f, 90f),   //1 bucket
                    Arrays.asList(0.65f, 0.95f, 0.5f, 90f),    //2 tool
                    Arrays.asList(0.35f, 1f, 0.35f, 90f)     //3 ingredient
            );

        } else if (dyeingStationBlockEntity.getBlockState().getValue(FACING) == Direction.NORTH) {

            poseStackPosition = Arrays.asList(
                    Arrays.asList(0.5f, 0.5f, 0.10f, 0f),  //0 output
                    Arrays.asList(0.65f, 0.95f, 0.65f, 0f),   //1 bucket
                    Arrays.asList(0.5f, 0.95f, 0.35f, 0f),    //2 tool
                    Arrays.asList(0.35f, 1f, 0.65f, 0f)     //3 ingredient
            );
        }

        ItemStack itemStackInSlot;
        for (int i = 0; i < 4; i++){
            switch (i) {
                case 1: itemStackInSlot = dyeingStationBlockEntity.getRenderStackInSlot1();
                break;
                case 2: itemStackInSlot = dyeingStationBlockEntity.getRenderStackInSlot2();
                break;
                case 3: itemStackInSlot = dyeingStationBlockEntity.getRenderStackInSlot3();
                break;
                default: itemStackInSlot = dyeingStationBlockEntity.getRenderStackInSlot0();
            }

            poseStack.pushPose();
            poseStack.translate (poseStackPosition.get(i).get(0), poseStackPosition.get(i).get(1), poseStackPosition.get(i).get(2));
            poseStack.scale(0.25f, 0.25f, 0.25f);
            poseStack.mulPose(Axis.YP.rotationDegrees(poseStackPosition.get(i).get(3)));

            itemRenderer.renderStatic(itemStackInSlot, ItemDisplayContext.FIXED, getLightLevel(Objects.requireNonNull(dyeingStationBlockEntity.getLevel()), dyeingStationBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, dyeingStationBlockEntity.getLevel(), 1);
            poseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos blockPos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, blockPos);
        int sLight = level.getBrightness(LightLayer.SKY, blockPos);

        return LightTexture.pack(bLight, sLight);
    }

}
