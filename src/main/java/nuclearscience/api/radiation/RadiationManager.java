package nuclearscience.api.radiation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import nuclearscience.api.radiation.util.BlockPosVolume;
import nuclearscience.api.radiation.util.IRadiationManager;
import nuclearscience.api.radiation.util.IRadiationRecipient;
import nuclearscience.api.radiation.util.IRadiationSource;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceAttachmentTypes;
import nuclearscience.registers.NuclearScienceCapabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadiationManager implements IRadiationManager {

    private Map<BlockPos, SimpleRadiationSource> permanentSources = new HashMap<>();
    private Map<BlockPos, TemporaryRadiationSource> temporarySources = new HashMap<>();
    private Map<BlockPos, FadingRadiationSource> fadingSources = new HashMap<>();
    private Map<BlockPosVolume, Double> localizedDisipations = new HashMap<>();

    private double defaultRadiationDisipation = Constants.BACKROUND_RADIATION_DISSIPATION;


    public static final Codec<RadiationManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(

                    Codec.unboundedMap(BlockPos.CODEC, SimpleRadiationSource.CODEC).fieldOf("permanentsources").forGetter(instance0 -> instance0.permanentSources),
                    Codec.unboundedMap(BlockPos.CODEC, TemporaryRadiationSource.CODEC).fieldOf("temporarysources").forGetter(instance0 -> instance0.temporarySources),
                    Codec.unboundedMap(BlockPos.CODEC, FadingRadiationSource.CODEC).fieldOf("fadingsources").forGetter(instance0 -> instance0.fadingSources),
                    Codec.unboundedMap(BlockPosVolume.CODEC, Codec.DOUBLE).fieldOf("localizeddissipations").forGetter(instance0 -> instance0.localizedDisipations),
                    Codec.DOUBLE.fieldOf("dissipation").forGetter(instance0 -> instance0.defaultRadiationDisipation)

            ).apply(instance, RadiationManager::new)
    );

    public RadiationManager() {

    }

    private RadiationManager(Map<BlockPos, SimpleRadiationSource> permanentSources, Map<BlockPos, TemporaryRadiationSource> temporarySources, Map<BlockPos, FadingRadiationSource> fadingSources, Map<BlockPosVolume, Double> localizedDisipation, double defaultRadiationDisipation) {
        this.permanentSources = permanentSources;
        this.temporarySources = temporarySources;
        this.fadingSources = fadingSources;
        this.localizedDisipations = localizedDisipation;
        this.defaultRadiationDisipation = defaultRadiationDisipation;
    }


    @Override
    public List<SimpleRadiationSource> getPermanentSources() {
        return new ArrayList<>(permanentSources.values());
    }

    @Override
    public void addRadiationSource(SimpleRadiationSource source) {
        if (source.isTemporary()) {
            temporarySources.put(source.getSourceLocation(), new TemporaryRadiationSource(source.ticks(), source.getRadiationStrength(), source.getRadiationAmount(), source.shouldLeaveLingeringSource(), source.distance()));
        } else {
            permanentSources.put(source.getSourceLocation(), source);
        }
    }

    @Override
    public void setDisipation(double radiationDisipation) {
        this.defaultRadiationDisipation = radiationDisipation;
    }

    @Override
    public void setLocalizedDisipation(double disipation, BlockPosVolume area) {
        localizedDisipations.put(area, disipation);

    }

    @Override
    public void removeLocalizedDisipation(BlockPosVolume area) {
        localizedDisipations.remove(area);

    }

    @Override
    public boolean removeRadiationSource(BlockPos pos, boolean shouldLeaveFadingSource) {
        IRadiationSource source = permanentSources.remove(pos);
        if (source == null) {
            return false;
        }
        if (shouldLeaveFadingSource) {
            fadingSources.put(pos, new FadingRadiationSource(source.getDistanceSpread(), source.getRadiationStrength(), source.getRadiationAmount()));
        }
        return true;
    }

    @Override
    public void tick(Level world) {

        /* Apply Radiation */

        permanentSources.forEach((pos, source) -> {

            AABB area = new AABB(pos).inflate(source.getDistanceSpread());
            List<LivingEntity> entites = world.getEntitiesOfClass(LivingEntity.class, area);

            entites.forEach(entity -> {

                IRadiationRecipient capability = entity.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
                if (capability == null) {
                    return;
                }


                capability.recieveRadiation(entity, getAppliedRadiation(world, pos, entity.getOnPos(), source.getRadiationAmount(), source.getRadiationStrength()), source.getRadiationStrength());


            });


        });

        temporarySources.forEach((pos, source) -> {

            AABB area = new AABB(pos).inflate(source.distance);
            List<LivingEntity> entites = world.getEntitiesOfClass(LivingEntity.class, area);

            entites.forEach(entity -> {

                IRadiationRecipient capability = entity.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
                if (capability == null) {
                    return;
                }

                capability.recieveRadiation(entity, getAppliedRadiation(world, pos, entity.getOnPos(), source.radiation, source.strength), source.strength);


            });


        });

        fadingSources.forEach((pos, source) -> {

            AABB area = new AABB(pos).inflate(source.distance);
            List<LivingEntity> entites = world.getEntitiesOfClass(LivingEntity.class, area);

            entites.forEach(entity -> {

                IRadiationRecipient capability = entity.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
                if (capability == null) {
                    return;
                }

                capability.recieveRadiation(entity, getAppliedRadiation(world, pos, entity.getOnPos(), source.radiation, source.strength), source.strength);


            });


        });



        /* Handle Temporary Sources */


        ArrayList<BlockPos> toRemove = new ArrayList<>();

        temporarySources.forEach((pos, source) -> {
            source.ticks = source.ticks - 1;
            if (source.ticks < 0) {
                toRemove.add(pos);
                if (source.leaveFading) {
                    fadingSources.put(new BlockPos(pos), new FadingRadiationSource(source.distance, source.strength, source.radiation));
                }
            }
        });

        toRemove.forEach(pos -> temporarySources.remove(pos));

        toRemove.clear();


        /* Handle Fading Sources */


        fadingSources.forEach((pos, source) -> {

            boolean hit = false;

            for (Map.Entry<BlockPosVolume, Double> entry : localizedDisipations.entrySet()) {
                if (entry.getKey().isIn(pos)) {
                    source.radiation = source.radiation - entry.getValue();
                    hit = true;
                    break;
                }
            }

            if (!hit) {
                source.radiation = source.radiation - defaultRadiationDisipation;
            }

            if (source.radiation <= 0) {
                toRemove.add(pos);
            }


        });

        toRemove.forEach(pos -> fadingSources.remove(pos));

        world.setData(NuclearScienceAttachmentTypes.RADIATION_MANAGER, this);


    }

    public static boolean isWithinRange(BlockPos start, BlockPos end, int distance) {
        if (Math.abs(end.getX() - start.getX()) > distance || Math.abs(end.getY() - start.getY()) > distance || Math.abs(end.getZ() - start.getZ()) > distance) {
            return false;
        } else {
            return true;
        }
    }

    public static List<Block> raycastToBlockPos(Level world, BlockPos start, BlockPos end) {

        List<Block> blocks = new ArrayList<>();

        int deltaX = end.getX() - start.getX();
        int deltaY = end.getY() - start.getY();
        int deltaZ = end.getZ() - start.getZ();

        double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);

        double incX = deltaX / magnitude;
        double incY = deltaY / magnitude;
        double incZ = deltaZ / magnitude;

        double x = 0;
        double y = 0;
        double z = 0;

        BlockPos toCheck;

        while (Math.abs(x) < Math.abs(deltaX) && Math.abs(y) < Math.abs(deltaY) && Math.abs(z) < Math.abs(deltaZ)) {

            x += incX;
            y += incY;
            z += incZ;
            toCheck = new BlockPos((int) Math.ceil(start.getX() + x), (int) Math.ceil(start.getY() + y), (int) Math.ceil(start.getZ() + z));
            if (!toCheck.equals(start) && !toCheck.equals(end)) {
                blocks.add(world.getBlockState(toCheck).getBlock());
            }


        }

        return blocks;
    }

    public static double getAppliedRadiation(Level world, BlockPos source, BlockPos entity, double amount, double strength) {

        List<Block> shielding = raycastToBlockPos(world, source, entity);

        //TODO implement radiation shielding register

        for (Block block : shielding) {

        }


        return 0;

    }


}
