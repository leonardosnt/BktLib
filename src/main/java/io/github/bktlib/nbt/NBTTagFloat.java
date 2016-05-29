package io.github.bktlib.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class NBTTagFloat extends NBTBase.NBTPrimitive {

  private float data;

  NBTTagFloat() {}

  public NBTTagFloat(float data) {
    this.data = data;
  }

  void write(DataOutput output) throws IOException {
    output.writeFloat(this.data);
  }

  void read(DataInput input, int depth, NBTReadLimiter readLimiter) throws IOException {
    readLimiter.read(32L);
    this.data = input.readFloat();
  }

  public byte getId() {
    return (byte) 5;
  }

  public String toString() {
    return "" + this.data + "f";
  }

  public NBTBase copy() {
    return new NBTTagFloat(this.data);
  }

  public boolean equals(Object obj) {
    if (super.equals(obj)) {
      NBTTagFloat tagFloat = (NBTTagFloat) obj;
      return this.data == tagFloat.data;
    } else {
      return false;
    }
  }

  public int hashCode() {
    return super.hashCode() ^ Float.floatToIntBits(this.data);
  }

  public long getLong() {
    return (long) this.data;
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
    return (double) this.data;
  }

  public float getFloat() {
    return this.data;
  }
}
