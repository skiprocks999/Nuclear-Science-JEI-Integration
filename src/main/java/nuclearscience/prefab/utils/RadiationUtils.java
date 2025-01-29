package nuclearscience.prefab.utils;

import java.util.List;

import electrodynamics.api.gas.GasStack;
import electrodynamics.api.gas.GasTank;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.api.radiation.SimpleRadiationSource;
import nuclearscience.api.radiation.util.RadioactiveObject;
import nuclearscience.common.reloadlistener.RadioactiveFluidRegister;
import nuclearscience.common.reloadlistener.RadioactiveGasRegister;
import nuclearscience.common.reloadlistener.RadioactiveItemRegister;

public class RadiationUtils {

    public static void handleRadioactiveGases(GenericTile tile, ComponentGasHandlerMulti multi, int radius, boolean isTemp, int ticks, boolean shouldLinger) {
        handleRadioactiveGases(tile, multi.getInputTanks(), radius, isTemp, ticks, shouldLinger);
        handleRadioactiveGases(tile, multi.getOutputTanks(), radius, isTemp, ticks, shouldLinger);
    }

    public static void handleRadioactiveGases(GenericTile tile, ComponentGasHandlerSimple simple, int radius, boolean isTemp, int ticks, boolean shouldLinger) {
        handleRadioactiveGases(tile, simple.getInputTanks(), radius, isTemp, ticks, shouldLinger);
    }

    public static void handleRadioactiveGases(GenericTile tile, GasTank[] tanks, int radius, boolean isTemp, int ticks, boolean shouldLinger) {

        double totRadiation = 0;
        double totStrength = 0;

        for(GasTank tank : tanks) {

            if(tank.isEmpty()) {
                continue;
            }

            GasStack gas = tank.getGas();

            RadioactiveObject rads = RadioactiveGasRegister.getValue(gas.getGas());

            if(rads.amount() <= 0) {
                continue;
            }

            totRadiation += (rads.amount() * gas.getAmount() * gas.getPressure());

            totStrength = Math.max(totStrength, rads.strength());

        }

        if(totRadiation <= 0) {
            return;
        }

        RadiationSystem.addRadiationSource(tile.getLevel(), new SimpleRadiationSource(totRadiation, totStrength, radius, isTemp, ticks, tile.getBlockPos(), shouldLinger));

    }

    public static void handleRadioactiveFluids(GenericTile tile, ComponentFluidHandlerMulti multi, int radius, boolean isTemp, int ticks, boolean shouldLinger) {
        handleRadioactiveFluids(tile, multi.getInputTanks(), radius, isTemp, ticks, shouldLinger);
        handleRadioactiveFluids(tile, multi.getOutputTanks(), radius, isTemp, ticks, shouldLinger);
    }

    public static void handleRadioactiveFluids(GenericTile tile, ComponentFluidHandlerSimple simple, int radius, boolean isTemp, int ticks, boolean shouldLinger) {
        handleRadioactiveFluids(tile, simple.getInputTanks(), radius, isTemp, ticks, shouldLinger);
    }

    public static void handleRadioactiveFluids(GenericTile tile, FluidTank[] tanks, int radius, boolean isTemp, int ticks, boolean shouldLinger) {

        double totRadiation = 0;
        double totStrength = 0;

        for(FluidTank tank : tanks) {

            if(tank.isEmpty()) {
                continue;
            }

            FluidStack fluid = tank.getFluid();

            RadioactiveObject rads = RadioactiveFluidRegister.getValue(fluid.getFluid());

            if(rads.amount() <= 0) {
                continue;
            }

            totRadiation += (rads.amount() * fluid.getAmount());

            totStrength = Math.max(totStrength, rads.strength());

        }

        if(totRadiation <= 0) {
            return;
        }

        RadiationSystem.addRadiationSource(tile.getLevel(), new SimpleRadiationSource(totRadiation, totStrength, radius, isTemp, ticks, tile.getBlockPos(), shouldLinger));

    }

    public static void handleRadioactiveItems(GenericTile tile, ComponentInventory inv, int radius, boolean isTemp, int ticks, boolean shouldLinger) {
        handleRadioactiveItems(tile, inv.getInputContents(), radius, isTemp, ticks, shouldLinger);
        handleRadioactiveItems(tile, inv.getOutputContents(), radius, isTemp, ticks, shouldLinger);
    }

    public static void handleRadioactiveItems(GenericTile tile, List<ItemStack> items, int radius, boolean isTemp, int ticks, boolean shouldLinger) {

        double totRadiation = 0;
        double totStrength = 0;

        for(ItemStack item : items) {

            if(item.isEmpty()) {
                continue;
            }

            RadioactiveObject rads = RadioactiveItemRegister.getValue(item.getItem());

            if(rads.amount() <= 0) {
                continue;
            }

            totRadiation += (rads.amount() * item.getCount());

            totStrength = Math.max(totStrength, rads.strength());

        }

        if(totRadiation <= 0) {
            return;
        }

        RadiationSystem.addRadiationSource(tile.getLevel(), new SimpleRadiationSource(totRadiation, totStrength, radius, isTemp, ticks, tile.getBlockPos(), shouldLinger));

    }


}
