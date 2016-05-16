package io.github.bktlib.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase.NBTPrimitive {

  private short data;

  NBTTagShort() {}

  public NBTTagShort(short data) {
    this.data = data;
  }

  void write(DataOutput output) throws IOException {
    output.writeShort(this.data);
  }

  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
    sizeTracker.read(16L);
    this.data = input.readShort();
  }

  public byte getId() {
    return (byte) 2;
  }

  public String toString() {
    return "" + this.data + "s";
  }

  public NBTBase copy() {
    return new NBTTagShort(this.data);
  }

  public boolean equals(Object obj) {
    if (super.equals(obj)) {
      NBTTagShort tagShort = (NBTTagShort) obj;
      return this.data == tagShort.data;
    } else {
      return false;
    }
  }

  public int hashCode() {
    return super.hashCode() ^ this.data;
  }

  public long getLong() {
    return (long) this.data;
  }

  public int getInt() {
    return this.data;
  }

  public short getShort() {
    return this.data;
  }

  public byte getByte() {
    return (byte) (this.data & 255);
  }

  public double getDouble() {
    return (double) this.data;
  }

  public float getFloat() {
    return (float) this.data;
  }
}
