package io.github.bktlib.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase.NBTPrimitive {
  private double data;

  NBTTagDouble() {}

  public NBTTagDouble(double data) {
    this.data = data;
  }

  void write(DataOutput output) throws IOException {
    output.writeDouble(this.data);
  }

  void read(DataInput input, int depth, NBTReadLimiter readLimiter) throws IOException {
    readLimiter.read(64L);
    this.data = input.readDouble();
  }

  public byte getId() {
    return (byte) 6;
  }

  public String toString() {
    return "" + this.data + "d";
  }

  public NBTBase copy() {
    return new NBTTagDouble(this.data);
  }

  public boolean equals(Object obj) {
    if (super.equals(obj)) {
      NBTTagDouble tagDouble = (NBTTagDouble) obj;
      return this.data == tagDouble.data;
    } else {
      return false;
    }
  }

  public int hashCode() {
    long var1 = Double.doubleToLongBits(this.data);
    return super.hashCode() ^ (int) (var1 ^ var1 >>> 32);
  }

  public long getLong() {
    return (long) Math.floor(this.data);
  }

  public int getInt() {
    return (int) Math.floor(this.data);
  }

  public short getShort() {
    return (short) (((int) Math.floor(this.data)) & 65535);
  }

  public byte getByte() {
    return (byte) (((int) Math.floor(this.data)) & 255);
  }

  public double getDouble() {
    return this.data;
  }

  public float getFloat() {
    return (float) this.data;
  }
}
