package io.github.bktlib.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase.NBTPrimitive {

  private int data;

  NBTTagInt() {}

  public NBTTagInt(int data) {
    this.data = data;
  }

  void write(DataOutput output) throws IOException {
    output.writeInt(this.data);
  }

  void read(DataInput input, int depth, NBTReadLimiter readLimiter) throws IOException {
    readLimiter.read(32L);
    this.data = input.readInt();
  }

  public byte getId() {
    return (byte) 3;
  }

  public String toString() {
    return Integer.toString(this.data);
  }

  public NBTBase copy() {
    return new NBTTagInt(this.data);
  }

  public boolean equals(Object obj) {
    if (super.equals(obj)) {
      NBTTagInt tagInt = (NBTTagInt) obj;
      return this.data == tagInt.data;
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
    return (short) (this.data & 65535);
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
