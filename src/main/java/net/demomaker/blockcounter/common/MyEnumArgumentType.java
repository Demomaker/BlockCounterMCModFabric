package net.demomaker.blockcounter.common;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.arguments.ArgumentType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class MyEnumArgumentType<T extends Enum<T>> implements ArgumentType {
  private final Collection<String> examples;
  private final Class<T> enumClass;

  public MyEnumArgumentType(Class<T> enumClass) {
    this.enumClass = enumClass;
    this.examples = Arrays.stream(enumClass.getEnumConstants()).map(Enum::name)
        .collect(Collectors.toList());
  }

  public static MyEnumArgumentType enumArgument(Class enumClass) {
    return new MyEnumArgumentType(enumClass);
  }

  public static <T extends Enum<T>> T getEnum(Class<T> clazz, String name) {
    try {
      return Enum.valueOf(clazz, name.toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public T parse(StringReader reader) throws CommandSyntaxException {
    String name = reader.readString();
    T value = getEnum(enumClass, name);
    if (value == null) {
      throw new CommandSyntaxException(
          CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
          () -> "argument.invalid.enum : " + name);
    }
    return value;
  }

  @Override
  public Collection<String> getExamples() {
    return examples;
  }
}

