package io.github.bktlib.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {

  private byte[] data;

  NBTTagByteArray() {}

  public NBTTagByteArray(byte[] data) {
    this.data = data;
  }

  void write(DataOutput output) throws IOException {
    output.writeInt(this.data.length);
    output.write(this.data);
  }

  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
    int i = input.readInt();
    sizeTracker.read(8 * i);
    this.data = new byte[i];
    input.readFully(this.data);
  }

  public byte getId() {
    return (byte) 7;
  }

  public String toString() {
    return "[" + this.data.length + " bytes]";
  }

  public NBTBase copy() {
    byte[] buf = new byte[this.data.length];
    System.arraycopy(this.data, 0, buf, 0, this.data.length);
    return new NBTTagByteArray(buf);
  }

  public boolean equals(Object obj) {
    return super.equals(obj) && Arrays.equals(this.data, ((NBTTagByteArray) obj).data);
  }

  public int hashCode() {
    return super.hashCode() ^ Arrays.hashCode(this.data);
  }

  public byte[] getByteArray() {
    return this.data;
  }
}
