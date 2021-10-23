package com.ferri.arnus.enderhopper.blocks;

import javax.annotation.Nullable;

import com.ferri.arnus.enderhopper.blockentities.BlockEntityRegistry;
import com.ferri.arnus.enderhopper.blockentities.EnderHopperBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;

public class EnderHopper extends Block implements EntityBlock{
	public static final DirectionProperty FACING = BlockStateProperties.FACING_HOPPER;
	public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
	private static final VoxelShape TOP = Block.box(0.0D, 10.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape FUNNEL = Block.box(4.0D, 4.0D, 4.0D, 12.0D, 10.0D, 12.0D);
	private static final VoxelShape CONVEX_BASE = Shapes.or(FUNNEL, TOP);
	private static final VoxelShape BASE = Shapes.join(CONVEX_BASE, Hopper.INSIDE, BooleanOp.ONLY_FIRST);
	private static final VoxelShape DOWN_SHAPE = Shapes.or(BASE, Block.box(6.0D, 0.0D, 6.0D, 10.0D, 4.0D, 10.0D));
	private static final VoxelShape EAST_SHAPE = Shapes.or(BASE, Block.box(12.0D, 4.0D, 6.0D, 16.0D, 8.0D, 10.0D));
	private static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, Block.box(6.0D, 4.0D, 0.0D, 10.0D, 8.0D, 4.0D));
	private static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, Block.box(6.0D, 4.0D, 12.0D, 10.0D, 8.0D, 16.0D));
	private static final VoxelShape WEST_SHAPE = Shapes.or(BASE, Block.box(0.0D, 4.0D, 6.0D, 4.0D, 8.0D, 10.0D));
	private static final VoxelShape DOWN_INTERACTION_SHAPE = Hopper.INSIDE;
	private static final VoxelShape EAST_INTERACTION_SHAPE = Shapes.or(Hopper.INSIDE, Block.box(12.0D, 8.0D, 6.0D, 16.0D, 10.0D, 10.0D));
	private static final VoxelShape NORTH_INTERACTION_SHAPE = Shapes.or(Hopper.INSIDE, Block.box(6.0D, 8.0D, 0.0D, 10.0D, 10.0D, 4.0D));
	private static final VoxelShape SOUTH_INTERACTION_SHAPE = Shapes.or(Hopper.INSIDE, Block.box(6.0D, 8.0D, 12.0D, 10.0D, 10.0D, 16.0D));
	private static final VoxelShape WEST_INTERACTION_SHAPE = Shapes.or(Hopper.INSIDE, Block.box(0.0D, 8.0D, 6.0D, 4.0D, 10.0D, 10.0D));
	
	
	public EnderHopper() {
		super(Properties.of(Material.HEAVY_METAL));
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN).setValue(ENABLED, Boolean.valueOf(true)));
	}
	
	@Override
	public VoxelShape getShape(BlockState p_54105_, BlockGetter p_54106_, BlockPos p_54107_, CollisionContext p_54108_) {
		switch((Direction)p_54105_.getValue(FACING)) {
		case DOWN:
			return DOWN_SHAPE;
		case NORTH:
			return NORTH_SHAPE;
		case SOUTH:
			return SOUTH_SHAPE;
		case WEST:
			return WEST_SHAPE;
		case EAST:
			return EAST_SHAPE;
		default:
			return BASE;
		}
	}
	
	@Override
	public VoxelShape getInteractionShape(BlockState p_54099_, BlockGetter p_54100_, BlockPos p_54101_) {
		switch((Direction)p_54099_.getValue(FACING)) {
		case DOWN:
			return DOWN_INTERACTION_SHAPE;
		case NORTH:
			return NORTH_INTERACTION_SHAPE;
		case SOUTH:
			return SOUTH_INTERACTION_SHAPE;
		case WEST:
			return WEST_INTERACTION_SHAPE;
		case EAST:
			return EAST_INTERACTION_SHAPE;
		default:
			return Hopper.INSIDE;
		}
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_54041_) {
		Direction direction = p_54041_.getClickedFace().getOpposite();
		return this.defaultBlockState().setValue(FACING, direction.getAxis() == Direction.Axis.Y ? Direction.DOWN : direction).setValue(ENABLED, Boolean.valueOf(true));
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<T> p_153214_) {
		return p_153212_.isClientSide ? null : createTickerHelper(p_153214_, BlockEntityRegistry.ENDERHOPPER.get(), (l,p,s,e) -> EnderHopperBE.tick(l, p, s, e));
	}
	
	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
		return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return BlockEntityRegistry.ENDERHOPPER.get().create(p_153215_, p_153216_);
	}
	
	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand,
			BlockHitResult pHit) {
		if (pLevel.isClientSide) {
	         return InteractionResult.SUCCESS;
	      } else {
	         BlockEntity blockentity = pLevel.getBlockEntity(pPos);
	         if (blockentity instanceof EnderHopperBE hopper) {
//	            pPlayer.openMenu(new MenuProvider() {
//					
//					@Override
//					public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
//						// TODO Auto-generated method stub
//						return null;
//					}
//					
//					@Override
//					public Component getDisplayName() {
//						// TODO Auto-generated method stub
//						return null;
//					}
//				});
	         }
	         return InteractionResult.CONSUME;
	      }
	}
	
	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (!pState.is(pNewState.getBlock())) {
			BlockEntity blockentity = pLevel.getBlockEntity(pPos);
			if (blockentity instanceof EnderHopperBE hopper) {
				hopper.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, pState.getValue(FACING)).ifPresent(cap -> {
					NonNullList<ItemStack> stacks = NonNullList.withSize(5, ItemStack.EMPTY);
					for (int i = 0; i < cap.getSlots(); i++) {
						stacks.set(i, cap.extractItem(i, cap.getStackInSlot(i).getCount(), false));
					}
					Containers.dropContents(pLevel, pPos, stacks);
				});
				pLevel.updateNeighbourForOutputSignal(pPos, this);
			}
			super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
		}
	}
	
	@Override
	public void onPlace(BlockState p_54110_, Level p_54111_, BlockPos p_54112_, BlockState p_54113_, boolean p_54114_) {
		if (!p_54113_.is(p_54110_.getBlock())) {
			this.checkPoweredState(p_54111_, p_54112_, p_54110_);
		}
	}
	
	private void checkPoweredState(Level p_54045_, BlockPos p_54046_, BlockState p_54047_) {
		boolean flag = !p_54045_.hasNeighborSignal(p_54046_);
		if (flag != p_54047_.getValue(ENABLED)) {
			p_54045_.setBlock(p_54046_, p_54047_.setValue(ENABLED, Boolean.valueOf(flag)), 4);
		}
		
	}
	
	@Override
	public RenderShape getRenderShape(BlockState p_54103_) {
		return RenderShape.MODEL;
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState p_54055_) {
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState p_54062_, Level p_54063_, BlockPos p_54064_) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(p_54063_.getBlockEntity(p_54064_));
	}
	
	@Override
	public BlockState rotate(BlockState p_54094_, Rotation p_54095_) {
		return p_54094_.setValue(FACING, p_54095_.rotate(p_54094_.getValue(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState p_54091_, Mirror p_54092_) {
		return p_54091_.rotate(p_54092_.getRotation(p_54091_.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54097_) {
		p_54097_.add(FACING, ENABLED);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_54057_, BlockGetter p_54058_, BlockPos p_54059_, PathComputationType p_54060_) {
		return false;
	}
	
	@Override
	public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
		BlockEntity blockentity = pLevel.getBlockEntity(pPos);
		if (blockentity instanceof EnderHopperBE hopper) {
			EnderHopperBE.entityInside(pLevel, pPos, pState, pEntity, hopper);
		}
	}
}
