package nuclearscience.common.entity;

import java.util.HashSet;

import nuclearscience.api.radiation.SimpleRadiationSource;
import org.joml.Vector3f;

import electrodynamics.prefab.block.GenericEntityBlock;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.block.BlockElectromagneticBooster;
import nuclearscience.common.block.facing.FacingDirection;
import nuclearscience.common.tile.TileElectromagneticSwitch;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceEntities;

public class EntityParticle extends Entity {
	private static final EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.DIRECTION);
	private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.FLOAT);
	private Direction direction = Direction.UP;
	public float speed = 0.02f;
	public BlockPos source = BlockPos.ZERO;

	public EntityParticle(EntityType<?> entityTypeIn, Level worldIn) {
		super(NuclearScienceEntities.ENTITY_PARTICLE.get(), worldIn);
	}

	public EntityParticle(Direction direction, Level worldIn, Location pos) {
		this(NuclearScienceEntities.ENTITY_PARTICLE.get(), worldIn);
		setPos(new Vec3(pos.x(), pos.y(), pos.z()));
		this.direction = direction;
		noCulling = true;
		if (worldIn.isClientSide) {
			setViewScale(4);
		}
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		if (direction == null) {
			direction = Direction.UP;
		}
		builder.define(DIRECTION, direction);
		builder.define(SPEED, speed);
	}

	@Override
	public void tick() {
		BlockEntity tile = level().getBlockEntity(source);
		if (!level().isClientSide) {
			if (!(tile instanceof TileParticleInjector)) {
				remove(RemovalReason.DISCARDED);
				return;
			}
			((TileParticleInjector) tile).addParticle(this);
			if (direction == null) {
				direction = Direction.UP;
			}
			entityData.set(DIRECTION, direction);
			entityData.set(SPEED, speed);
		} else {
			direction = entityData.get(DIRECTION);
			speed = entityData.get(SPEED);
		}
		RadiationSystem.addRadiationSource(level(), new SimpleRadiationSource(1000, 1, 2, true, 0, blockPosition(), false));
		if (direction != null && direction != Direction.UP) {
			int checks = (int) (Math.ceil(speed) * 2);
			float localSpeed = speed / checks;
			for (int i = 0; i < checks; i++) {
				if (!level().isClientSide) {
					TileParticleInjector injector = (TileParticleInjector) tile;
					injector.checkCollision();
				} else {
					level().addParticle(new DustParticleOptions(new Vector3f(1, 1, 1), 5), getX(), getY(), getZ(), 0, 0, 0);
				}
				BlockPos next = blockPosition();
				BlockState oldState = level().getBlockState(next);
				boolean isBooster = false;
				if (oldState.getBlock() == NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get()) {
					Direction dir = oldState.getValue(GenericEntityBlock.FACING).getOpposite();
					FacingDirection face = oldState.getValue(BlockElectromagneticBooster.FACINGDIRECTION);
					if (face == FacingDirection.RIGHT) {
						dir = dir.getClockWise();
					} else if (face == FacingDirection.LEFT) {
						dir = dir.getCounterClockWise();
					}
					if (dir == direction) {
						speed += 0.01 / 3;
					} else if (dir == direction.getOpposite()) {
						speed -= 0.02;
					} else {
						speed += 0.01 / 6;
						direction = dir;
						BlockPos floor = blockPosition();
						setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
					}
					isBooster = true;
				}
				if (speed < 0) {
					speed *= -1;
					direction = direction.getOpposite();
				}
				setPos(getX() + direction.getStepX() * localSpeed, getY(), getZ() + direction.getStepZ() * localSpeed);
				if (isBooster) {
					BlockPos positionNow = blockPosition();
					if (level().getBlockState(positionNow).getBlock() == NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get()) {
						HashSet<Direction> directions = new HashSet<>();
						for (Direction dir : Direction.values()) {
							if (dir != Direction.UP && dir != Direction.DOWN && dir != direction.getOpposite() && level().getBlockState(positionNow.relative(dir)).getBlock() == Blocks.AIR) {
								directions.add(dir);
							}
						}
						BlockEntity te = level().getBlockEntity(positionNow);
						if (te instanceof TileElectromagneticSwitch switchte) {
							directions.remove(switchte.lastDirection);
							if (directions.size() > (switchte.lastDirection == null ? 2 : 1)) {
								level().explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);
								removeAfterChangingDimensions();
								break;
							}
							for (Direction dir : directions) {
								switchte.lastDirection = dir;
								direction = dir;
								setPos(positionNow.getX() + 0.5, positionNow.getY() + 0.5, positionNow.getZ() + 0.5);
							}
						}
					}
				}
				if (!level().isClientSide) {
					BlockPos getPos = blockPosition();
					BlockState nextState = level().getBlockState(getPos);
					if (nextState.getBlock() == Blocks.AIR || nextState.getBlock() == NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get()) {
						int amount = 0;
						for (Direction of : Direction.values()) {
							if (level().getBlockState(blockPosition().relative(of)).getBlock() instanceof IElectromagnet) {
								amount++;
							}
						}
						if (amount < 4) {
							level().explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);
							removeAfterChangingDimensions();
							break;
						}
						BlockState testNextBlock = level().getBlockState(getPos.relative(direction));
						if (testNextBlock.getBlock() instanceof IElectromagnet && testNextBlock.getBlock() != NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get()) {
							Direction checkRot = direction.getClockWise();
							testNextBlock = level().getBlockState(getPos.relative(checkRot));
							if (testNextBlock.getBlock() == Blocks.AIR || testNextBlock.getBlock() == NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get()) {
								BlockPos floor = blockPosition();
								direction = checkRot;
								setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
							} else {
								checkRot = direction.getClockWise().getOpposite();
								testNextBlock = level().getBlockState(getPos.relative(checkRot));
								if ((testNextBlock.getBlock() != Blocks.AIR) && (testNextBlock.getBlock() != NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get())) {
									level().explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);
									removeAfterChangingDimensions();
									break;
								}
								BlockPos floor = blockPosition();
								direction = checkRot;
								setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);
							}
						}
					} else {
						boolean checkIsBooster = nextState.getBlock() == NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get() && oldState.getBlock() == NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get();
						boolean explode = false;
						if (checkIsBooster) {
							Direction oldDir = oldState.getValue(GenericEntityBlock.FACING);
							Direction nextDir = nextState.getValue(GenericEntityBlock.FACING);
							if (oldDir != nextDir) {
								FacingDirection face = oldState.getValue(BlockElectromagneticBooster.FACINGDIRECTION);
								if (face == FacingDirection.RIGHT) {
									oldDir = oldDir.getClockWise();
								} else if (face == FacingDirection.LEFT) {
									oldDir = oldDir.getCounterClockWise();
								}
								if (oldDir != nextDir) {
									explode = true;
								}
							}
						} else if (nextState.getBlock() != NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get()) {
							explode = true;
						}
						if (explode) {
							level().explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);
							removeAfterChangingDimensions();
							break;
						}
					}
				}
			}
			speed = Math.min(speed, 1.999f);
		} else if (tickCount > 100) {
			removeAfterChangingDimensions();
		}
	}


	@Override
	public boolean isNoGravity() {
		return true;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compound) {
		source = new BlockPos(compound.getInt("sourceX"), compound.getInt("sourceY"), compound.getInt("sourceZ"));
	}

	@Override
	public Component getName() {
		return Component.translatable("entity.particle");
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compound) {
		compound.putInt("sourceX", source.getX());
		compound.putInt("sourceY", source.getY());
		compound.putInt("sourceZ", source.getZ());

	}

}