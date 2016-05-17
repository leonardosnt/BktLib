package io.github.bktlib.nbt;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import io.github.bktlib.reflect.util.ReflectUtil;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NBTTagCompound extends NBTBase {

  protected Map<String, NBTBase> dataMap = Maps.newHashMap();

  public static NBTTagCompound fromNMSCompound(Object nmsCompound) {
    if (nmsCompound.getClass() != ReflectUtil.getClass("{nms}.NBTTagCompound")) {
      throw new IllegalArgumentException(nmsCompound + " is not of type " +
              ReflectUtil.getClass("{nms}.NBTTagCompound").getName());
    }
    return new NMSNBTTagCompound(nmsCompound);
  }

  public Object asNMSCompound() {
    if (this instanceof NMSNBTTagCompound) {
      NMSNBTTagCompound thiz = (NMSNBTTagCompound) this;
      thiz.saveToHandle();
      return thiz.getHandle();
    }
    Object newInst = ReflectUtil.instantiate("{nms}.NBTTagCompound");
    NMSNBTTagCompound newCompound = new NMSNBTTagCompound(newInst);
    newCompound.merge(this);
    newCompound.saveToHandle();
    return newInst;
  }

  public Set<String> getKeySet() {
    return this.dataMap.keySet();
  }

  public byte getId() {
    return (byte) 10;
  }

  public void setTag(String key, NBTBase value) {
    this.dataMap.put(key, value);
  }

  public void setByte(String key, byte value) {
    this.dataMap.put(key, new NBTTagByte(value));
  }

  public void setShort(String key, short value) {
    this.dataMap.put(key, new NBTTagShort(value));
  }

  public void setInteger(String key, int value) {
    this.dataMap.put(key, new NBTTagInt(value));
  }

  public void setLong(String key, long value) {
    this.dataMap.put(key, new NBTTagLong(value));
  }

  public void setFloat(String key, float value) {
    this.dataMap.put(key, new NBTTagFloat(value));
  }

  public void setDouble(String key, double value) {
    this.dataMap.put(key, new NBTTagDouble(value));
  }

  public void setString(String key, String value) {
    this.dataMap.put(key, new NBTTagString(value));
  }

  public void setByteArray(String key, byte[] value) {
    this.dataMap.put(key, new NBTTagByteArray(value));
  }

  public void setIntArray(String key, int[] value) {
    this.dataMap.put(key, new NBTTagIntArray(value));
  }

  public void setBoolean(String key, boolean value) {
    this.setByte(key, (byte) (value ? 1 : 0));
  }

  public NBTBase getTag(String key) {
    return this.dataMap.get(key);
  }

  public byte getTagType(String key) {
    NBTBase val =  this.dataMap.get(key);
    return val != null ? val.getId() : 0;
  }

  public boolean hasKey(String key) {
    return this.dataMap.containsKey(key);
  }

  public boolean hasKey(String key, int type) {
    byte b = this.getTagType(key);

    return b == type || type == 99 && (b == 1 || b == 2 ||
           b == 3 || b == 4 || b == 5 || b == 6);
  }

  public byte getByte(String key) {
    try {
      return !this.hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive) this.dataMap.get(key)).getByte();
    } catch (ClassCastException e) {
      return (byte) 0;
    }
  }

  public short getShort(String key) {
    try {
      return !this.hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive) this.dataMap.get(key)).getShort();
    } catch (ClassCastException e) {
      return (short) 0;
    }
  }

  public int getInteger(String key) {
    try {
      return !this.hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive) this.dataMap.get(key)).getInt();
    } catch (ClassCastException e) {
      return 0;
    }
  }

  public long getLong(String key) {
    try {
      return !this.hasKey(key, 99) ? 0L : ((NBTBase.NBTPrimitive) this.dataMap.get(key)).getLong();
    } catch (ClassCastException e) {
      return 0L;
    }
  }

  public float getFloat(String key) {
    try {
      return !this.hasKey(key, 99) ? 0.0F : ((NBTBase.NBTPrimitive) this.dataMap.get(key)).getFloat();
    } catch (ClassCastException e) {
      return 0.0F;
    }
  }

  public double getDouble(String key) {
    try {
      return !this.hasKey(key, 99) ? 0.0D : ((NBTBase.NBTPrimitive) this.dataMap.get(key)).getDouble();
    } catch (ClassCastException e) {
      return 0.0D;
    }
  }

  public String getString(String key) {
    try {
      return !this.hasKey(key, 8) ? "" : this.dataMap.get(key).getString();
    } catch (ClassCastException e) {
      return "";
    }
  }

  public byte[] getByteArray(String key) {
    return !this.hasKey(key, 7) ? new byte[0] : ((NBTTagByteArray) this.dataMap.get(key)).getByteArray();
  }

  public int[] getIntArray(String key) {
    return !this.hasKey(key, 11) ? new int[0] : ((NBTTagIntArray) this.dataMap.get(key)).getIntArray();
  }

  public NBTTagCompound getCompoundTag(String key) {
    return !this.hasKey(key, 10) ? new NBTTagCompound() : (NBTTagCompound) this.dataMap.get(key);
  }

  public NBTTagList getTagList(String key, int type) {
    if (this.getTagType(key) != 9) {
      return new NBTTagList();
    } else {
      NBTTagList list = (NBTTagList) this.dataMap.get(key);
      return list.tagCount() > 0 && list.getTagType() != type ? new NBTTagList() : list;
    }
  }

  public boolean getBoolean(String key) {
    return this.getByte(key) != 0;
  }

  public void removeTag(String key) {
    this.dataMap.remove(key);
  }

  public String toString() {
    return "{" + dataMap.entrySet().stream()
        .map((k) -> k.getKey() + ": " + k.getValue())
        .collect(Collectors.joining(", ")) + "}";
  }

  public boolean hasNoTags() {
    return this.dataMap.isEmpty();
  }

  public NBTBase copy() {
    NBTTagCompound newComp = new NBTTagCompound();
    Iterator it = this.dataMap.keySet().iterator();

    while (it.hasNext()) {
      String cur = (String) it.next();
      newComp.setTag(cur, this.dataMap.get(cur).copy());
    }

    return newComp;
  }

  public boolean equals(Object obj) {
    if (super.equals(obj)) {
      NBTTagCompound tagCompound = (NBTTagCompound) obj;
      return this.dataMap.entrySet().equals(tagCompound.dataMap.entrySet());
    } else {
      return false;
    }
  }

  public int hashCode() {
    return super.hashCode() ^ this.dataMap.hashCode();
  }

  private static void writeEntry(String name, NBTBase data, DataOutput output) throws IOException {
    output.writeByte(data.getId());

    if (data.getId() != 0) {
      output.writeUTF(name);
      data.write(output);
    }
  }

  private static byte readType(DataInput input, NBTReadLimiter readLimiter) throws IOException {
    return input.readByte();
  }

  private static String readKey(DataInput input, NBTReadLimiter readLimiter) throws IOException {
    return input.readUTF();
  }

  static NBTBase readNBT(byte id, String key, DataInput input, int depth, NBTReadLimiter readLimiter) {
    NBTBase base = NBTBase.createNewByType(id);

    try {
      base.read(input, depth, readLimiter);
      return base;
    } catch (IOException e) {
      Throwables.propagate(e);
    }
    return null;
  }

  public void merge(NBTTagCompound other) {
    Iterator it = other.dataMap.keySet().iterator();

    while (it.hasNext()) {
      String curKey = (String) it.next();
      NBTBase curVal = other.dataMap.get(curKey);

      if (curVal.getId() == 10) {
        if (this.hasKey(curKey, 10)) {
          NBTTagCompound compoundTag = this.getCompoundTag(curKey);
          compoundTag.merge((NBTTagCompound) curVal);
        } else {
          this.setTag(curKey, curVal.copy());
        }
      } else {
        this.setTag(curKey, curVal.copy());
      }
    }
  }

  void write(DataOutput output) throws IOException {
    Iterator it = this.dataMap.keySet().iterator();

    while (it.hasNext()) {
      String k = (String) it.next();
      NBTBase v = this.dataMap.get(k);
      writeEntry(k, v, output);
    }

    output.writeByte(0);
  }

  void read(DataInput input, int depth, NBTReadLimiter readLimiter) throws IOException {
    if (depth > 512) {
      throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
    } else {
      this.dataMap.clear();
      byte b;

      while ((b = readType(input, readLimiter)) != 0) {
        String key = readKey(input, readLimiter);
        readLimiter.read((long) (16 * key.length()));
        NBTBase nbtBase = readNBT(b, key, input, depth + 1, readLimiter);
        this.dataMap.put(key, nbtBase);
      }
    }
  }
}
