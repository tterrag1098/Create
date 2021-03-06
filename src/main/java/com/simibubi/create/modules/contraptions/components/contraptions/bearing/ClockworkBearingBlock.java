package com.simibubi.create.modules.contraptions.components.contraptions.bearing;

import com.simibubi.create.foundation.block.ITE;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class ClockworkBearingBlock extends BearingBlock implements ITE<ClockworkBearingTileEntity> {

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new ClockworkBearingTileEntity();
	}

	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		if (!player.isAllowEdit())
			return false;
		if (player.isSneaking())
			return false;
		if (player.getHeldItem(handIn).isEmpty()) {
			if (!worldIn.isRemote) {
				withTileEntityDo(worldIn, pos, te -> {
					if (te.running) {
						te.disassemble();
						return;
					}
					te.assembleNextTick = true;
				});
			}
			return true;
		}
		return false;
	}

	@Override
	public Class<ClockworkBearingTileEntity> getTileEntityClass() {
		return ClockworkBearingTileEntity.class;
	}

}
