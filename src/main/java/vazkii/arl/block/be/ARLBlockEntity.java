/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Jan 21, 2014, 9:18:28 PM (GMT)]
 */
package vazkii.arl.block.be;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.arl.util.VanillaPacketDispatcher;

public abstract class ARLBlockEntity extends BlockEntity {

	public ARLBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}

	@Nonnull
	@Override
	public CompoundTag save(CompoundTag par1nbtTagCompound) {
		CompoundTag nbt = super.save(par1nbtTagCompound);

		writeSharedNBT(par1nbtTagCompound);
		return nbt;
	}

	@Override
	public void load(CompoundTag p_230337_2_) {
		super.load(p_230337_2_);

		readSharedNBT(p_230337_2_);
	}

	public void writeSharedNBT(CompoundTag cmp) {
		// NO-OP
	}

	public void readSharedNBT(CompoundTag cmp) {
		// NO-OP
	}
	
	public void sync() {
		VanillaPacketDispatcher.dispatchTEToNearbyPlayers(this);
	}
	
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		CompoundTag cmp = new CompoundTag();
		writeSharedNBT(cmp);
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
		super.onDataPacket(net, packet);
		readSharedNBT(packet.getTag());
	}

}
