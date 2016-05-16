package io.github.bktlib.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class NBTTagIntArray extends NBTBase {
  private int[] intArray;

  NBTTagIntArray() {}

  public NBTTagIntArray(int[] data) {
    this.intArray = data;
  }

  void write(DataOutput output) throws IOException {
    output.writeInt(this.intArray.length);

    for (int i = 0; i < this.intArray.length; ++i) {
      output.writeInt(this.intArray[i]);
    }
  }

  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
    int i = input.readInt();
    sizeTracker.read((long) (32 * i));
    this.intArray = new int[i];

    for (int j = 0; j < i; ++j) {
      this.intArray[j] = input.readInt();
    }
  }

  public byte getId() {
    return (byte) 11;
  }

  public String toString() {
    return "[" + Arrays.stream(intArray)
            .boxed()
            .map(Object::toString)
            .collect(Collectors.joining(", ")) + "]";
  }

  public NBTBase copy() {
    int[] cpy = new int[this.intArray.length];
    System.arraycopy(this.intArray, 0, cpy, 0, this.intArray.length);
    return new NBTTagIntArray(cpy);
  }

  public boolean equals(Object obj) {
    return super.equals(obj) && Arrays.equals(this.intArray, ((NBTTagIntArray) obj).intArray);
  }

  public int hashCode() {
    return super.hashCode() ^ Arrays.hashCode(this.intArray);
  }

  public int[] getIntArray() {
    return this.intArray;
  }
}
