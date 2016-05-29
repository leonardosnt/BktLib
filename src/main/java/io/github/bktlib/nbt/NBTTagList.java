package io.github.bktlib.nbt;

import com.google.common.collect.Lists;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class NBTTagList extends NBTBase {

  private List<NBTBase> tagList = Lists.newArrayList();
  private byte tagType = 0;

  void write(DataOutput output) throws IOException {
    if (!this.tagList.isEmpty()) {
      this.tagType = this.tagList.get(0).getId();
    } else {
      this.tagType = 0;
    }

    output.writeByte(this.tagType);
    output.writeInt(this.tagList.size());

    for (int i = 0; i < this.tagList.size(); ++i) {
      this.tagList.get(i).write(output);
    }
  }

  void read(DataInput input, int depth, NBTReadLimiter readLimiter) throws IOException {
    if (depth > 512) {
      throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
    } else {
      readLimiter.read(8L);
      this.tagType = input.readByte();
      int i = input.readInt();
      this.tagList = Lists.newArrayList();

      for (int j = 0; j < i; ++j) {
        NBTBase nbtBase = NBTBase.createNewByType(this.tagType);
        nbtBase.read(input, depth + 1, readLimiter);
        this.tagList.add(nbtBase);
      }
    }
  }

  public byte getId() {
    return (byte) 9;
  }

  public String toString() {
    StringBuilder builder = new StringBuilder("[");
    for (int i = 0; i < tagList.size(); i++) {
      NBTBase cur = tagList.get(i);
      builder.append(i).append(":").append(cur);
      if ((i + 1) < tagList.size())
        builder.append(", ");
    }
    return builder.append("]").toString();
  }

  public void appendTag(NBTBase nbt) {
    if (this.tagType == 0) {
      this.tagType = nbt.getId();
    }

    this.tagList.add(nbt);
  }

  public void set(int idx, NBTBase nbt) {
    if (idx >= 0 && idx < this.tagList.size()) {
      if (this.tagType == 0) {
        this.tagType = nbt.getId();
      }

      this.tagList.set(idx, nbt);
    }
  }

  public NBTBase removeTag(int i) {
    return this.tagList.remove(i);
  }

  public boolean hasNoTags() {
    return this.tagList.isEmpty();
  }

  public NBTTagCompound getCompoundTagAt(int i) {
    if (i >= 0 && i < this.tagList.size()) {
      NBTBase base = this.tagList.get(i);
      return base.getId() == 10 ? (NBTTagCompound) base : new NBTTagCompound();
    } else {
      return new NBTTagCompound();
    }
  }

  public int[] getIntArray(int i) {
    if (i >= 0 && i < this.tagList.size()) {
      NBTBase base = this.tagList.get(i);
      return base.getId() == 11 ? ((NBTTagIntArray) base).getIntArray() : new int[0];
    } else {
      return new int[0];
    }
  }

  public double getDouble(int i) {
    if (i >= 0 && i < this.tagList.size()) {
      NBTBase base = this.tagList.get(i);
      return base.getId() == 6 ? ((NBTTagDouble) base).getDouble() : 0.0D;
    } else {
      return 0.0D;
    }
  }

  public float getFloat(int i) {
    if (i >= 0 && i < this.tagList.size()) {
      NBTBase base = this.tagList.get(i);
      return base.getId() == 5 ? ((NBTTagFloat) base).getFloat() : 0.0F;
    } else {
      return 0.0F;
    }
  }

  public String getStringTagAt(int i) {
    if (i >= 0 && i < this.tagList.size()) {
      NBTBase base = this.tagList.get(i);
      return base.getId() == 8 ? base.getString() : base.toString();
    } else {
      return "";
    }
  }

  public NBTBase get(int idx) {
    return idx >= 0 && idx < this.tagList.size() ? this.tagList.get(idx) : new NBTTagEnd();
  }

  public int tagCount() {
    return this.tagList.size();
  }

  public NBTBase copy() {
    NBTTagList newList = new NBTTagList();
    newList.tagType = this.tagType;
    Iterator it = this.tagList.iterator();

    while (it.hasNext()) {
      NBTBase cur = (NBTBase) it.next();
      NBTBase curCpy = cur.copy();
      newList.tagList.add(curCpy);
    }

    return newList;
  }

  public boolean equals(Object obj) {
    if (super.equals(obj)) {
      NBTTagList list = (NBTTagList) obj;

      if (this.tagType == list.tagType) {
        return this.tagList.equals(list.tagList);
      }
    }
    return false;
  }

  public int hashCode() {
    return super.hashCode() ^ this.tagList.hashCode();
  }

  public int getTagType() {
    return this.tagType;
  }
}
