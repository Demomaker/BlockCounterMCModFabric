package net.demomaker.blockcounter.adapter.argumentbuilder;

import net.demomaker.blockcounter.adapter.servercommand.ServerCommand;

class CommandArgument<T> {

  protected String name;
  protected T type;
  protected ServerCommand serverCommand = null;
}
