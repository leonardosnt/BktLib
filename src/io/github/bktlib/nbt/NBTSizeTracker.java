package io.github.bktlib.nbt;

public class NBTSizeTracker {

  public static final NBTSizeTracker INFINITE = new NBTSizeTracker(0L) {
    public void read(long bits) {}
  };

  private final long max;

  public NBTSizeTracker(long max) {
    this.max = max;
  }

  public void read(long bits) {
    long read = bits / 8L;

    if (read > this.max) {
      throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + read + "bytes where max allowed: " + this.max);
    }
  }
}
