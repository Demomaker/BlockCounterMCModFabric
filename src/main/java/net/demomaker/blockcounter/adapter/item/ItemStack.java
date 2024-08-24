package net.demomaker.blockcounter.adapter.item;

import net.demomaker.blockcounter.adapter.servercommand.ServerCommandContext;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.WritableBookContentComponent;

public record ItemStack(net.minecraft.item.ItemStack itemStack) {

  public Item getItem() {
    return new Item(this.itemStack().getItem());
  }

  public WritableBookContentComponent getBookContent(
      ComponentType<WritableBookContentComponent> writableBookContent) {
    return itemStack().get(writableBookContent);
  }

  public void setBookContent(ComponentType<WritableBookContentComponent> writableBookContent,
      WritableBookContentComponent writableBookContentComponent) {
    itemStack().set(writableBookContent, writableBookContentComponent);
  }

  public static ItemStackArgument getArgument(ServerCommandContext commandContext, String name) {
    return ItemStackArgumentType.getItemStackArgument(commandContext.commandContext(), name);
  }
}
