package io.github.bktlib.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase.NBTPrimitive {
  private long data;

  NBTTagLong() {}

  public NBTTagLong(long data) {
    this.data = data;
  }

  void write(DataOutput output) throws IOException {
    output.writeLong(this.data);
  }

  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
    sizeTracker.read(64L);
    this.data = input.readLong();
  }

  public byte getId() {
    return (byte) 4;
  }

  public String toString() {
    return this.data + "L";
  }

  public NBTBase copy() {
    return new NBTTagLong(this.data);
  }

  public boolean equals(Object obj) {
    if (super.equals(obj)) {
      NBTTagLong tagLong = (NBTTagLong) obj;
      return this.data == tagLong.data;
    } else {
      return false;
    }
  }

  public int hashCode() {
    return super.hashCode() ^ (int) (this.data ^ this.data >>> 32);
  }

  public long getLong() {
    return this.data;
  }

  public int getInt() {
    return (int) (this.data);
  }

  public short getShort() {
    return (short) ((int) (this.data & 65535L));
  }

  public byte getByte() {
    return (byte) ((int) (this.data & 255L));
  }

  public double getDouble() {
    return (double) this.data;
  }

  public float getFloat() {
    return (float) this.data;
  }
}
