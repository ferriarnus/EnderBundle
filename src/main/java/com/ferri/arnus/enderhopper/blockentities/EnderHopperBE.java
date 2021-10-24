package com.ferri.arnus.enderhopper.blockentities;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import com.ferri.arnus.enderhopper.EnderHoppers;
import com.ferri.arnus.enderhopper.blocks.EnderHopper;
import com.ferri.arnus.enderhopper.capability.EnderStorageProvider;
import com.ferri.arnus.enderhopper.capability.ExtractWrapper;
import com.ferri.arnus.enderhopper.capability.InsertWrapper;
import com.ferri.arnus.enderhopper.items.ItemRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class EnderHopperBE extends BlockEntity {
	private HopperStackHandler handler = new HopperStackHandler();
	private LazyOptional<IItemHandler> extract = LazyOptional.of(() -> new ExtractWrapper(handler));
	private LazyOptional<IItemHandler> insert = LazyOptional.of(() -> new InsertWrapper(handler));
	private UUID uuid;
	private Component name = TextComponent.EMPTY;
	private boolean bound = false;
	
	public EnderHopperBE(BlockPos p_155550_, BlockState p_155551_) {
		super(BlockEntityRegistry.ENDERHOPPER.get(), p_155550_, p_155551_);
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public ItemStackHandler getHandler() {
		return handler;
	}
	
	public void setCustomName(Component hoverName) {
		this.name = hoverName;
	}
	
	public Component getName() {
		if (this.name.getString().isEmpty()) {
			return new TranslatableComponent("container.enderhopper.enderhopper");
		}
		return name;
	}
	
	public void setBound(boolean bound) {
		this.bound = bound;
	}
	
	public boolean isBound() {
		return bound;
	}
	
	private VoxelShape getSuckShape() {
		return Hopper.SUCK;
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		extract.invalidate();
		insert.invalidate();
		if (this.getLevel() instanceof ServerLevel server) {
			ForgeChunkManager.forceChunk(server, EnderHoppers.MODID, this.getBlockPos(), level.getChunkAt(this.getBlockPos()).getPos().x, level.getChunkAt(this.getBlockPos()).getPos().z, false, false);
		}
	}
	
	@Override
	public void invalidateCaps() {
		extract.invalidate();
		insert.invalidate();
		super.invalidateCaps();
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, EnderHopperBE hopper) {
		if (level.getGameTime() %7 == 0) {
			pullItem(level, pos, hopper);
		}
		if (level.getGameTime() %7 == 3) {
			pushItem(level, pos, state.getValue(EnderHopper.FACING), hopper);
		}
		if (level instanceof ServerLevel server && hopper.isBound()) {
			ForgeChunkManager.forceChunk(server, EnderHoppers.MODID, pos, level.getChunkAt(pos).getPos().x, level.getChunkAt(pos).getPos().z, true, false);
		}
		if (level instanceof ServerLevel server && !hopper.isBound()) {
			ForgeChunkManager.forceChunk(server, EnderHoppers.MODID, pos, level.getChunkAt(pos).getPos().x, level.getChunkAt(pos).getPos().z, false, false);
		}
	}
	
	public static void entityInside(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity, EnderHopperBE pBlockEntity) {
		if (pEntity instanceof ItemEntity item && Shapes.joinIsNotEmpty(Shapes.create(pEntity.getBoundingBox().move((double)(-pPos.getX()), (double)(-pPos.getY()), (double)(-pPos.getZ()))), pBlockEntity.getSuckShape(), BooleanOp.AND)) {
			pBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(cap -> {
				for (int i=0; i< cap.getSlots(); i++) {
					if (!ItemStack.matches(item.getItem(), cap.insertItem(i, item.getItem(), true))) {
						cap.insertItem(i,item.getItem().copy() ,false);
						item.discard();
						break;
					}
				}
			});
		}
	}
	
	public static boolean playerClose(Level level, BlockPos pos, EnderHopperBE hopper) {
		List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(7, 7, 7), (p) -> {
			ItemStack stack = p.getMainHandItem();
			AtomicBoolean b = new AtomicBoolean(false);
			if (stack.is(ItemRegistry.ENDER_BUNDEL.get())) {
				stack.getCapability(EnderStorageProvider.ENDERSTORAGE).ifPresent(cap -> {
					if (cap.getUUID().equals(hopper.getUuid())) {
						b.set(true);
					}
				});
			}
			return b.get();
		});
		if (!players.isEmpty()) {
			return true;
		}
		return false;
	}

	private static void pullItem(Level level, BlockPos pos, EnderHopperBE hopper) {
		level.updateNeighborsAt(pos.above(), level.getBlockState(pos.above()).getBlock());
		BlockEntity be =  level.getBlockEntity(pos.above());
		if (be == null) { return;}
		be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).ifPresent(behandler -> {
			hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).ifPresent(hopperhandler -> {
				for (int i=0; i< behandler.getSlots(); i++) {
					ItemStack extractItem = behandler.extractItem(i, 1, true);
					if (extractItem != ItemStack.EMPTY) {
						for (int j=0; j< hopperhandler.getSlots(); j++) {
							if (!ItemStack.matches(extractItem, hopperhandler.insertItem(j, extractItem, true))) {
								extractItem = behandler.extractItem(i, 1, false);
								hopperhandler.insertItem(j, extractItem, false);
								break;
							}
						}
						break;
					}
				}
			});
		});
	}
	
	private static void pushItem(Level level, BlockPos pos, Direction side, EnderHopperBE hopper) {
		level.updateNeighborsAt(pos.relative(side), level.getBlockState(pos.relative(side)).getBlock());
		BlockEntity be =  level.getBlockEntity(pos.relative(side));
		if (be == null) { return;}
		hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side).ifPresent(hopperhandler -> {
			be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side.getOpposite()).ifPresent(behandler -> {
				for (int i=0; i< hopperhandler.getSlots(); i++) {
					ItemStack extractItem = hopperhandler.extractItem(i, 1, true);
					if (extractItem != ItemStack.EMPTY) {
						for (int j=0; j< behandler.getSlots(); j++) {
							if (!ItemStack.matches(extractItem, behandler.insertItem(j, extractItem, true))) {
								extractItem = hopperhandler.extractItem(i, 1, false);
								behandler.insertItem(j, extractItem, false);
								break;
							}
						}
						break;
					}
				}
			});
		});
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (side.equals(Direction.UP) && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.getBlockState().getValue(EnderHopper.ENABLED)) {
			return this.insert.cast();
		}
		if (this.getBlockState().getValue(EnderHopper.FACING).equals(side) && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && this.getBlockState().getValue(EnderHopper.ENABLED)) {
			return this.extract.cast();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	public CompoundTag save(CompoundTag pTag) {
		super.save(pTag);
		try {
			pTag.putUUID("stack", uuid);
		} catch (Exception e) {
			pTag.putUUID("stack", UUID.randomUUID());
		}
		pTag.put("Storage", handler.serializeNBT());
		pTag.putString("name", this.name.getString());
		return pTag;
	}
	
	@Override
	public void load(CompoundTag pTag) {
		try {
			this.uuid = pTag.getUUID("stack");
		} catch (Exception e) {
			this.uuid = UUID.randomUUID();
		}
		handler.deserializeNBT(pTag.getCompound("Storage"));
		this.name = new TextComponent(pTag.getString("name"));
		super.load(pTag);
	}
	
	@Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }
    
	class HopperStackHandler extends ItemStackHandler {
		
		public HopperStackHandler() {
			stacks = NonNullList.withSize(5, ItemStack.EMPTY);
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	}
}
