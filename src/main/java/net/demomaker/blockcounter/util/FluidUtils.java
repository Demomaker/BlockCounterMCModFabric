package net.demomaker.blockcounter.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;

public class FluidUtils {
  public static Text getFluidName(Fluid fluid) {
    // Create a translation key using the fluid's registry name
    return Text.of(FluidVariantAttributes.getName(FluidVariant.of(fluid)));
  }

  public static String getFluidNameAsString(Fluid fluid) {
    // Get the translated name as a string
    return getFluidName(fluid).getString();
  }

}
