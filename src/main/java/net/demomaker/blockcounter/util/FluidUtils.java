package net.demomaker.blockcounter.util;

import net.minecraft.fluid.Fluid;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidUtils {
  public static Text getFluidName(Fluid fluid) {
    // Get the fluid's registry name
    Identifier fluidId = Registry.FLUID.getId(fluid);
    // Create a translation key using the fluid's registry name
    return new TranslatableText("fluid." + fluidId.getNamespace() + "." + fluidId.getPath());
  }

  public static String getFluidNameAsString(Fluid fluid) {
    // Get the translated name as a string
    return getFluidName(fluid).getString();
  }

}
