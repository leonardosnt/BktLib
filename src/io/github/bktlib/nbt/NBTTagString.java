package io.github.bktlib.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {

  private String data;

  public NBTTagString() {
    this.data = "";
  }

  public NBTTagString(String data) {
    if (data == null) {
      throw new IllegalArgumentException("Empty string not allowed");
    }
    this.data = data;
  }

  void write(DataOutput output) throws IOException {
    output.writeUTF(this.data);
  }

  void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
    this.data = input.readUTF();
    sizeTracker.read((long) (16 * this.data.length()));
  }

  public byte getId() {
    return (byte) 8;
  }

  public String toString() {
    return "\"" + this.data.replace("\"", "\\\"") + "\"";
  }

  public NBTBase copy() {
    return new NBTTagString(this.data);
  }

  public boolean hasNoTags() {
    return this.data.isEmpty();
  }

  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    } else {
      NBTTagString tagString = (NBTTagString) obj;
      return this.data == null && tagString.data == null || this.data != null && this.data.equals(tagString.data);
    }
  }

  public int hashCode() {
    return super.hashCode() ^ this.data.hashCode();
  }

  public String getString() {
    return this.data;
  }
}
