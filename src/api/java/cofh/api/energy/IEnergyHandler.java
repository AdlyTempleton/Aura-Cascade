package cofh.api.energy;

import net.minecraft.util.EnumFacing;


/**
 * Implement this interface on Tile Entities which should handle energy, generally storing it in one or more internal {@link IEnergyStorage} objects.
 * <p>
 * A reference implementation is provided {@link TileEnergyHandler}.
 *
 * @author King Lemming
 *
 */
public interface IEnergyHandler extends IEnergyProvider, IEnergyReceiver {

	// merely a convenience interface (remove these methods in 1.8; provided here for back-compat via compiler doing things)

	/**
	 * Add energy to an IEnergyReceiver, internal distribution is left entirely to the IEnergyReceiver.
	 *
	 * @param from
	 *            Orientation the energy is received from.
	 * @param maxReceive
	 *            Maximum amount of energy to receive.
	 * @param simulate
	 *            If TRUE, the charge will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) received.
	 */
	@Override
	int receiveEnergy(EnumFacing facing, int maxReceive, boolean simulate);

	/**
	 * Remove energy from an IEnergyProvider, internal distribution is left entirely to the IEnergyProvider.
	 *
	 * @param from
	 *            Orientation the energy is extracted from.
	 * @param maxExtract
	 *            Maximum amount of energy to extract.
	 * @param simulate
	 *            If TRUE, the extraction will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) extracted.
	 */
	@Override
	int extractEnergy(EnumFacing facing, int maxExtract, boolean simulate);


	/**
	 * Returns the amount of energy currently stored.
	 */
	@Override
	int getEnergyStored(EnumFacing facing);

	/**
	 * Returns the maximum amount of energy that can be stored.
	 */
	@Override
	int getMaxEnergyStored(EnumFacing facing);

}
