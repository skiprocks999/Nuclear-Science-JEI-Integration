package nuclearscience.api.radiation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import nuclearscience.api.radiation.util.*;
import nuclearscience.common.reloadlistener.RadiationShieldingRegister;
import nuclearscience.registers.NuclearScienceAttachmentTypes;
import nuclearscience.registers.NuclearScienceCapabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadiationManager implements IRadiationManager {

    public RadiationManager() {

    }

    @Override
    public List<SimpleRadiationSource> getPermanentSources(Level world) {
        return new ArrayList<>(world.getData(NuclearScienceAttachmentTypes.PERMANENT_RADIATION_SOURCES).values());
    }

    @Override
    public void addRadiationSource(SimpleRadiationSource source, Level world) {
        if (source.isTemporary()) {
            HashMap<BlockPos, TemporaryRadiationSource> sources = world.getData(NuclearScienceAttachmentTypes.TEMPORARY_RADIATION_SOURCES);
            sources.put(source.getSourceLocation(), new TemporaryRadiationSource(source.ticks(), source.getRadiationStrength(), source.getRadiationAmount(), source.shouldLeaveLingeringSource(), source.distance()));
            world.setData(NuclearScienceAttachmentTypes.TEMPORARY_RADIATION_SOURCES, sources);
        } else {
            HashMap<BlockPos, SimpleRadiationSource> sources = world.getData(NuclearScienceAttachmentTypes.PERMANENT_RADIATION_SOURCES);
            sources.put(source.getSourceLocation(), source);
            world.setData(NuclearScienceAttachmentTypes.PERMANENT_RADIATION_SOURCES, sources);
        }
    }

    @Override
    public void setDisipation(double radiationDisipation, Level world) {
        world.setData(NuclearScienceAttachmentTypes.DEFAULT_DISSIPATION, radiationDisipation);
    }

    @Override
    public void setLocalizedDisipation(double disipation, BlockPosVolume area, Level world) {
        HashMap<BlockPosVolume, Double> values = world.getData(NuclearScienceAttachmentTypes.LOCALIZED_DISSIPATIONS);
        values.put(area, disipation);
        world.setData(NuclearScienceAttachmentTypes.LOCALIZED_DISSIPATIONS, values);
    }

    @Override
    public void removeLocalizedDisipation(BlockPosVolume area, Level world) {
        HashMap<BlockPosVolume, Double> values = world.getData(NuclearScienceAttachmentTypes.LOCALIZED_DISSIPATIONS);
        values.remove(area);
        world.setData(NuclearScienceAttachmentTypes.LOCALIZED_DISSIPATIONS, values);
    }

    @Override
    public boolean removeRadiationSource(BlockPos pos, boolean shouldLeaveFadingSource, Level world) {
        HashMap<BlockPos, SimpleRadiationSource> sources = world.getData(NuclearScienceAttachmentTypes.PERMANENT_RADIATION_SOURCES);
        IRadiationSource source = sources.remove(pos);
        world.setData(NuclearScienceAttachmentTypes.PERMANENT_RADIATION_SOURCES, sources);
        if (source == null) {
            return false;
        }
        if (shouldLeaveFadingSource) {
            HashMap<BlockPos, FadingRadiationSource> fadingSources = world.getData(NuclearScienceAttachmentTypes.FADING_RADIATION_SOURCES);
            fadingSources.put(pos, new FadingRadiationSource(source.getDistanceSpread(), source.getRadiationStrength(), source.getRadiationAmount()));
            world.setData(NuclearScienceAttachmentTypes.FADING_RADIATION_SOURCES, fadingSources);
        }
        return true;
    }

    @Override
    public void tick(Level world) {

        /* Apply Radiation */

        BlockPos position;

        SimpleRadiationSource permanentSource;


        for (Map.Entry<BlockPos, SimpleRadiationSource> entry : world.getData(NuclearScienceAttachmentTypes.PERMANENT_RADIATION_SOURCES).entrySet()) {
            position = entry.getKey();
            permanentSource = entry.getValue();

            for (LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, new AABB(position.getX() - permanentSource.distance(), position.getY() - permanentSource.distance(), position.getZ() - permanentSource.distance(), position.getX() + permanentSource.distance() + 1, position.getY() + permanentSource.distance() + 1, position.getZ() + permanentSource.distance() + 1))) {
                IRadiationRecipient capability = entity.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
                if (capability == null) {
                    continue;
                }

                for (int i = 0; i < (int) Math.ceil(entity.getBbHeight()); i++) {

                    capability.recieveRadiation(entity, getAppliedRadiation(world, position, entity.getOnPos().above(i + 1), permanentSource.getRadiationAmount(), permanentSource.getRadiationStrength()), permanentSource.getRadiationStrength());

                }
            }
        }

        TemporaryRadiationSource temporarySource;

        HashMap<BlockPos, TemporaryRadiationSource> temporarySources = world.getData(NuclearScienceAttachmentTypes.TEMPORARY_RADIATION_SOURCES);

        for (Map.Entry<BlockPos, TemporaryRadiationSource> entry : temporarySources.entrySet()) {
            position = entry.getKey();
            temporarySource = entry.getValue();

            for (LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, new AABB(position.getX() - temporarySource.distance, position.getY() - temporarySource.distance, position.getZ() - temporarySource.distance, position.getX() + temporarySource.distance + 1, position.getY() + temporarySource.distance + 1, position.getZ() + temporarySource.distance + 1))) {

                IRadiationRecipient capability = entity.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
                if (capability == null) {
                    continue;
                }

                for (int i = 0; i < (int) Math.ceil(entity.getBbHeight()); i++) {
                    capability.recieveRadiation(entity, getAppliedRadiation(world, position, entity.getOnPos().above(i + 1), temporarySource.radiation, temporarySource.strength), temporarySource.strength);
                }


            }


        }

        FadingRadiationSource fadingSource;

        HashMap<BlockPos, FadingRadiationSource> fadingSources = world.getData(NuclearScienceAttachmentTypes.FADING_RADIATION_SOURCES);

        for (Map.Entry<BlockPos, FadingRadiationSource> entry : fadingSources.entrySet()) {

            position = entry.getKey();
            fadingSource = entry.getValue();

            for (LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class, new AABB(position.getX() - fadingSource.distance, position.getY() - fadingSource.distance, position.getZ() - fadingSource.distance, position.getX() + fadingSource.distance + 1, position.getY() + fadingSource.distance + 1, position.getZ() + fadingSource.distance + 1))) {
                IRadiationRecipient capability = entity.getCapability(NuclearScienceCapabilities.CAPABILITY_RADIATIONRECIPIENT);
                if (capability == null) {
                    continue;
                }

                for (int i = 0; i < (int) Math.ceil(entity.getBbHeight()); i++) {
                    capability.recieveRadiation(entity, getAppliedRadiation(world, position, entity.getOnPos().above(i + 1), fadingSource.radiation, fadingSource.strength), fadingSource.strength);
                }

            }

        }


        /* Handle Temporary Sources */


        ArrayList<BlockPos> toRemove = new ArrayList<>();

        boolean changed = false;

        for (Map.Entry<BlockPos, TemporaryRadiationSource> entry : temporarySources.entrySet()) {
            position = entry.getKey();
            temporarySource = entry.getValue();

            temporarySource.ticks = temporarySource.ticks - 1;
            if (temporarySource.ticks < 0) {
                changed = true;
                toRemove.add(position);
                if (temporarySource.leaveFading) {
                    fadingSources.put(new BlockPos(position), new FadingRadiationSource(temporarySource.distance, temporarySource.strength, temporarySource.radiation));
                }
            }

        }

        for (BlockPos pos : toRemove) {
            temporarySources.remove(pos);
        }

        toRemove.clear();

        if (changed) {
            world.setData(NuclearScienceAttachmentTypes.TEMPORARY_RADIATION_SOURCES, temporarySources);
        }


        /* Handle Fading Sources */

        changed = false;

        double defaultRadiationDisipation = world.getData(NuclearScienceAttachmentTypes.DEFAULT_DISSIPATION);

        HashMap<BlockPosVolume, Double> localizedDissipations = world.getData(NuclearScienceAttachmentTypes.LOCALIZED_DISSIPATIONS);

        for (Map.Entry<BlockPos, FadingRadiationSource> entry : fadingSources.entrySet()) {

            changed = true;

            position = entry.getKey();
            fadingSource = entry.getValue();

            boolean hit = false;

            for (Map.Entry<BlockPosVolume, Double> localized : localizedDissipations.entrySet()) {
                if (localized.getKey().isIn(position)) {
                    fadingSource.radiation = fadingSource.radiation - localized.getValue();
                    hit = true;
                    break;
                }
            }

            if (!hit) {
                fadingSource.radiation = fadingSource.radiation - defaultRadiationDisipation;
            }

            if (fadingSource.radiation <= 0) {
                toRemove.add(position);
            }

        }

        for (BlockPos pos : toRemove) {
            fadingSources.remove(pos);
        }

        toRemove.clear();

        if (changed) {
            world.setData(NuclearScienceAttachmentTypes.FADING_RADIATION_SOURCES, fadingSources);
        }

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

        int maxChecks = (int) magnitude;

        double incX = deltaX / magnitude;
        double incY = deltaY / magnitude;
        double incZ = deltaZ / magnitude;

        double x = 0;
        double y = 0;
        double z = 0;

        BlockPos toCheck = start;

        int i = 0;

        while (i < maxChecks) {

            x += incX;
            y += incY;
            z += incZ;
            toCheck = new BlockPos((int) (start.getX() + x), (int) (start.getY() + y), (int) (start.getZ() + z));
            if (!toCheck.equals(start) && !toCheck.equals(end)) {
                blocks.add(world.getBlockState(toCheck).getBlock());
                //world.setBlockAndUpdate(toCheck, Blocks.COBBLESTONE.defaultBlockState());
            }

            i++;

        }

        return blocks;
    }

    public static double getAppliedRadiation(Level world, BlockPos source, BlockPos entity, double amount, double strength) {

        List<Block> blocks = raycastToBlockPos(world, source, entity);

        if (blocks.isEmpty()) {
            return amount;
        }

        RadiationShielding shielding;

        for (Block block : blocks) {
            shielding = RadiationShieldingRegister.getValue(block);
            if (shielding.level() < strength) {
                continue;
            }
            amount -= shielding.amount();
            if (amount <= 0) {
                return 0;
            }

        }

        return amount;

    }


}
