package nuclearscience.common.entity;

import java.util.Optional;

import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import nuclearscience.api.radiation.SimpleRadiationSource;
import nuclearscience.common.block.states.NuclearScienceBlockStates;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import org.joml.Vector3f;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.block.states.facing.FacingDirection;
import nuclearscience.common.tile.accelerator.TileParticleInjector;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceEntities;

public class EntityParticle extends Entity {

    private static final EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> TICKS_ALIVE = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<BlockPos> SOURCE = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.BLOCK_POS);
    private static final EntityDataAccessor<Boolean> PASSED_THROUGH_GATEWAY = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PASSED_THROUGH_SWITCH = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Direction> SWITCH_DIRECTION = SynchedEntityData.defineId(EntityParticle.class, EntityDataSerializers.DIRECTION);


    public static final float STARTING_SPEED = 0.02F;
    public static final float STRAIGHT_SPEED_INCREMENT = 0.01F / 3.0F;
    public static final float TURN_SPEED_PENALTY = 0.95F;
    public static final float INVERT_SPEED_INCREMENT = -0.02F;

    public static final float MAX_SPEED = 2F;

    private Direction facingDirection = Direction.UP;
    private Direction switchDirection = Direction.UP;
    public float speed = STARTING_SPEED;
    public BlockPos source = BlockEntityUtils.OUT_OF_REACH;
    public boolean passedThroughGate = false;
    private int ticksAlive = 0;
    private boolean passedThroughSwitch = false;

    public EntityParticle(EntityType<?> entityTypeIn, Level worldIn) {
        super(NuclearScienceEntities.ENTITY_PARTICLE.get(), worldIn);
    }

    public EntityParticle(Direction direction, Level worldIn, Location startPos, BlockPos ownerPos) {
        this(NuclearScienceEntities.ENTITY_PARTICLE.get(), worldIn);

        setPos(new Vec3(startPos.x(), startPos.y(), startPos.z()));

        this.facingDirection = direction;

        noCulling = true;

        source = ownerPos;

        if (worldIn.isClientSide) {

            setViewScale(4);

        }

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        if (facingDirection == null) {
            facingDirection = Direction.UP;
        }
        if (source == null) {
            source = BlockEntityUtils.OUT_OF_REACH;
        }
        if (switchDirection == null) {
            switchDirection = Direction.UP;
        }
        builder.define(DIRECTION, facingDirection);
        builder.define(SPEED, speed);
        builder.define(TICKS_ALIVE, ticksAlive);
        builder.define(SOURCE, source);
        builder.define(PASSED_THROUGH_GATEWAY, passedThroughGate);
        builder.define(PASSED_THROUGH_SWITCH, passedThroughSwitch);
        builder.define(SWITCH_DIRECTION, switchDirection);
    }

    @Override
    public void tick() {

        Level level = level();
        boolean isClientside = level.isClientSide();
        boolean isServerside = !isClientside;

        if (isServerside) {
            //Electrodynamics.LOGGER.info(speed);
            ticksAlive++;
        }

        if (ticksAlive > 1000) {
            removeAfterChangingDimensions();
        }

        if (facingDirection == null || facingDirection == Direction.UP) {
            return;
        }

        BlockEntity blockEntity = level.getBlockEntity(source);

        if (!(blockEntity instanceof TileParticleInjector)) {
            if (isServerside) {
                remove(RemovalReason.DISCARDED);
            }
            return;
        }

        TileParticleInjector injector = (TileParticleInjector) blockEntity;

        injector.addParticle(this);

        if (injector.handleCollision()) {
            return;
        }

        if (isServerside) {

            if (facingDirection == null) {
                facingDirection = Direction.UP;
            }
            entityData.set(DIRECTION, facingDirection);
            entityData.set(SPEED, speed);

            RadiationSystem.addRadiationSource(level, new SimpleRadiationSource(1000, 1, 2, true, 0, blockPosition(), false));

        } else {

            facingDirection = entityData.get(DIRECTION);
            speed = entityData.get(SPEED);

        }

        if (facingDirection == null || facingDirection == Direction.UP || facingDirection == Direction.DOWN) {
            return;
        }


        int i = 0;
        int checks = (int) (Math.ceil(speed) * 2.0);
        float localSpeed = speed / checks;

        while (i < checks) {

            i++;

            BlockState startOfLoopState = level.getBlockState(blockPosition());

            boolean startOfLoopStateIsBooster = isBooster(startOfLoopState);
            boolean startofLoopStateIsDiode = isDiode(startOfLoopState);

            // Handle speed increase if we are in a booster

            if (startOfLoopStateIsBooster) {

                Direction boosterFacing = startOfLoopState.getValue(ElectrodynamicsBlockStates.FACING).getOpposite();

                FacingDirection boosterOrientation = startOfLoopState.getValue(NuclearScienceBlockStates.FACINGDIRECTION);

                if (boosterOrientation == FacingDirection.RIGHT) {

                    boosterFacing = boosterFacing.getClockWise();

                } else if (boosterOrientation == FacingDirection.LEFT) {

                    boosterFacing = boosterFacing.getCounterClockWise();

                }

                if (boosterFacing == facingDirection) {

                    setSpeed(speed += STRAIGHT_SPEED_INCREMENT);

                } else if (boosterFacing == facingDirection.getOpposite()) {

                    setSpeed(speed += INVERT_SPEED_INCREMENT);

                } else {

                    setSpeed(speed *= TURN_SPEED_PENALTY);

                    facingDirection = boosterFacing;

                    BlockPos floor = blockPosition();

                    setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);

                }

            }

            // If the speed is negative flip the particles direction

            if (speed < 0) {

                setSpeed(speed *= -1);

                facingDirection = facingDirection.getOpposite();

            }

            // Move the particle

            setPos(getX() + facingDirection.getStepX() * localSpeed, getY(), getZ() + facingDirection.getStepZ() * localSpeed);

            // If we were in a booster, check if we are now in a switch

            if ((startOfLoopStateIsBooster || startofLoopStateIsDiode) && !passedThroughSwitch) {

                BlockPos positionNow = blockPosition();

                if (isSwitch(level.getBlockState(positionNow))) {


                    if (injector.particles[0].getUUID().equals(getUUID())) {

                        if (injector.particles[1] != null && injector.particles[1].passedThroughSwitch) {

                            Direction otherSwitchDirection = injector.particles[1].switchDirection;

                            Direction clockwise = facingDirection.getClockWise();
                            Direction counterClockwise = facingDirection.getCounterClockWise();

                            if (facingDirection != otherSwitchDirection && level.getBlockState(positionNow.relative(facingDirection)).isAir()) {
                                switchDirection = facingDirection;
                                passedThroughSwitch = true;
                            } else if (clockwise != otherSwitchDirection && level.getBlockState(positionNow.relative(clockwise)).isAir()) {
                                switchDirection = clockwise;
                                passedThroughSwitch = true;
                            } else if (counterClockwise != otherSwitchDirection && level.getBlockState(positionNow.relative(counterClockwise)).isAir()) {
                                switchDirection = counterClockwise;
                                passedThroughSwitch = true;
                            } else {
                                if (isServerside) {
                                    level.explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);

                                    removeAfterChangingDimensions();
                                }
                                return;
                            }


                        } else {
                            Direction clockwise = facingDirection.getClockWise();
                            Direction counterClockwise = facingDirection.getCounterClockWise();

                            if (level.getBlockState(positionNow.relative(facingDirection)).isAir()) {
                                switchDirection = facingDirection;
                                passedThroughSwitch = true;
                            } else if (level.getBlockState(positionNow.relative(clockwise)).isAir()) {
                                switchDirection = clockwise;
                                passedThroughSwitch = true;
                            } else if (level.getBlockState(positionNow.relative(counterClockwise)).isAir()) {
                                switchDirection = counterClockwise;
                                passedThroughSwitch = true;
                            } else {
                                if (isServerside) {
                                    level.explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);

                                    removeAfterChangingDimensions();
                                }
                                return;
                            }
                        }

                    } else if (injector.particles[1].getUUID().equals(getUUID())) {

                        if (!injector.particles[0].passedThroughSwitch) {
                            if (isServerside) {
                                level.explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);

                                removeAfterChangingDimensions();
                            }

                            return;
                        }

                        Direction otherSwitchDirection = injector.particles[0].switchDirection;

                        Direction clockwise = facingDirection.getClockWise();
                        Direction counterClockwise = facingDirection.getCounterClockWise();

                        if (facingDirection != otherSwitchDirection && level.getBlockState(positionNow.relative(facingDirection)).isAir()) {
                            switchDirection = facingDirection;
                            passedThroughSwitch = true;
                        } else if (clockwise != otherSwitchDirection && level.getBlockState(positionNow.relative(clockwise)).isAir()) {
                            switchDirection = clockwise;
                            passedThroughSwitch = true;
                        } else if (counterClockwise != otherSwitchDirection && level.getBlockState(positionNow.relative(counterClockwise)).isAir()) {
                            switchDirection = counterClockwise;
                            passedThroughSwitch = true;
                        } else {
                            if (isServerside) {
                                level.explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);

                                removeAfterChangingDimensions();
                            }
                            return;
                        }


                    } else {

                        if (isServerside) {
                            level.explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);

                            removeAfterChangingDimensions();
                        }

                        return;

                    }

                    if (switchDirection != facingDirection) {

                        facingDirection = switchDirection;

                        setPos(positionNow.getX() + 0.5, positionNow.getY() + 0.5, positionNow.getZ() + 0.5);
                    }

                }

            }

            if (isClientside) {

                continue;

            }

            /* Server-side only code */

            BlockPos postMovePos = blockPosition();

            BlockState postMoveState = level.getBlockState(postMovePos);

            //Check if we are now moved into air, a switch, or a gateway

            if (postMoveState.isAir() || isSwitch(postMoveState) || isGateway(postMoveState) || isDiode(postMoveState)) {

                int amount = 0;

                // Check the number of valid electromagnets around us

                for (Direction dir : Direction.values()) {

                    if (level.getBlockState(postMovePos.relative(dir)).is(NuclearScienceTags.Blocks.PARTICLE_CONTAINMENT)) {

                        amount++;

                    }
                }

                // If the particle is not contained properly, explode it

                if (amount < 4) {

                    level.explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);

                    removeAfterChangingDimensions();

                    break;
                }

                // Get the state in front of us

                BlockPos inFrontOfUs = postMovePos.relative(facingDirection);

                BlockState inFrontOfUsState = level.getBlockState(inFrontOfUs);

                BlockPos relative;

                boolean canPassThroughGateway = canPassThroughGateway(level.getBlockEntity(inFrontOfUs), inFrontOfUsState);

                if (canPassThroughGateway && !passedThroughGate) {
                    level.playSound(null, blockPosition(), SoundEvents.IRON_TRAPDOOR_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                    passedThroughGate = true;
                }

                // Check if we cant move through the block in front of us

                if (inFrontOfUsState.is(NuclearScienceTags.Blocks.PARTICLE_CONTAINMENT) && !isBooster(inFrontOfUsState) && !isSwitch(inFrontOfUsState) && !canPassThroughGateway && !canPassThroughDiode(inFrontOfUsState, facingDirection)) {

                    // Check to the right

                    Direction checkRot = facingDirection.getClockWise();

                    relative = postMovePos.relative(checkRot);

                    inFrontOfUsState = level.getBlockState(relative);

                    // Check if we can go through the block to the right

                    if (inFrontOfUsState.isAir() || (isBooster(inFrontOfUsState) && !passedThroughSwitch) || isSwitch(inFrontOfUsState) || canPassThroughGateway(level.getBlockEntity(relative), inFrontOfUsState) || canPassThroughDiode(inFrontOfUsState, checkRot)) {

                        BlockPos floor = blockPosition();

                        facingDirection = checkRot;

                        setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);

                        if (!passedThroughGate && !passedThroughSwitch) {
                            setSpeed(speed *= TURN_SPEED_PENALTY);
                        }

                    } else {

                        // Otherwise check if we can go through the block to the left

                        checkRot = facingDirection.getCounterClockWise();

                        relative = postMovePos.relative(checkRot);

                        inFrontOfUsState = level.getBlockState(relative);

                        // If we can't, explode the particle

                        if (!inFrontOfUsState.isAir() && (!isBooster(inFrontOfUsState) && !passedThroughSwitch) && !isSwitch(inFrontOfUsState) && !canPassThroughGateway(level.getBlockEntity(relative), inFrontOfUsState) && !canPassThroughDiode(inFrontOfUsState, checkRot)) {

                            level.explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);

                            removeAfterChangingDimensions();

                            break;
                        }

                        BlockPos floor = blockPosition();

                        facingDirection = checkRot;

                        if (!passedThroughGate && !passedThroughSwitch) {
                            setSpeed(speed *= TURN_SPEED_PENALTY);
                        }

                        setPos(floor.getX() + 0.5, floor.getY() + 0.5, floor.getZ() + 0.5);

                    }

                }

                // If we are not in the checked states above, check if the block we started in and the block we are in now are boosters

            } else {

                boolean checkIsBooster = isBooster(postMoveState) && startOfLoopStateIsBooster;

                boolean explode = false;

                if (checkIsBooster) {

                    Direction oldDir = startOfLoopState.getValue(ElectrodynamicsBlockStates.FACING);

                    Direction nextDir = postMoveState.getValue(ElectrodynamicsBlockStates.FACING);

                    if (oldDir != nextDir) {

                        FacingDirection face = startOfLoopState.getValue(NuclearScienceBlockStates.FACINGDIRECTION);

                        if (face == FacingDirection.RIGHT) {

                            oldDir = oldDir.getClockWise();

                        } else if (face == FacingDirection.LEFT) {

                            oldDir = oldDir.getCounterClockWise();

                        }

                        if (oldDir != nextDir) {

                            explode = true;

                        }

                    }


                }

                if (explode) {

                    level.explode(this, getX(), getY(), getZ(), speed, ExplosionInteraction.BLOCK);

                    removeAfterChangingDimensions();

                    break;
                }

            }

        }

		/*



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
					injector.handleCollision();
				} else {
					level().addParticle(new DustParticleOptions(new Vector3f(1, 1, 1), 5), getX(), getY(), getZ(), 0, 0, 0);
				}
				BlockPos next = blockPosition();
				BlockState oldState = level().getBlockState(next);
				boolean isBooster = false;
				if (oldState.getBlock() == NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get()) {
					Direction dir = oldState.getValue(ElectrodynamicsBlockStates.FACING).getOpposite();
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
							Direction oldDir = oldState.getValue(ElectrodynamicsBlockStates.FACING);
							Direction nextDir = nextState.getValue(ElectrodynamicsBlockStates.FACING);
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

		 */
    }

    public boolean isBooster(BlockState state) {
        return state.is(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER);
    }

    public boolean isSwitch(BlockState state) {
        return state.is(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH);
    }

    public boolean isGateway(BlockState state) {
        return state.is(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY);
    }

    public boolean isDiode(BlockState state) {
        return state.is(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE);
    }

    public boolean canPassThroughGateway(BlockEntity entity, BlockState state) {
        return isGateway(state) && entity instanceof TileElectromagneticGateway gateway && gateway.mayPassThrough(speed);
    }

    public boolean canPassThroughDiode(BlockState state, Direction facing) {
        return isDiode(state) && state.getValue(ElectrodynamicsBlockStates.FACING) == facing;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        Optional<BlockPos> optional = NbtUtils.readBlockPos(compound, "injector");
        optional.ifPresent(pos -> source = pos);
        passedThroughGate = compound.getBoolean("passedthroughgate");
        ticksAlive = compound.getInt("ticksalive");
        facingDirection = Direction.values()[compound.getInt("facing")];
        switchDirection = Direction.values()[compound.getInt("switch")];
        speed = compound.getFloat("speed");
        passedThroughSwitch = compound.getBoolean("passedthroughswitch");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.put("injector", NbtUtils.writeBlockPos(source));
        compound.putBoolean("passedthroughgate", passedThroughGate);
        compound.putInt("ticksalive", ticksAlive);
        compound.putInt("facing", facingDirection.ordinal());
        compound.putFloat("speed", speed);
        compound.putInt("switch", switchDirection.ordinal());
        compound.putBoolean("passedthroughswitch", passedThroughSwitch);
    }

    @Override
    protected Component getTypeName() {
        return Component.translatable("entity.particle");
    }

    public void setSpeed(float speed) {

        float sign = Math.signum(speed);

        if (speed > MAX_SPEED || speed < -MAX_SPEED) {

            speed = MAX_SPEED * sign;

        }

        this.speed = speed;
    }


}