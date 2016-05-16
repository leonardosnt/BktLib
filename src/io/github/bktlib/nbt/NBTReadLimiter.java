package io.github.bktlib.nbt;

public class NBTReadLimiter {

  public static final NBTReadLimiter INFINITE = new NBTReadLimiter(0L) {
    public void read(long bits) {}
  };

  private final long max;

  public NBTReadLimiter(long max) {
    this.max = max;
  }

  public void read(long bits) {
    long read = bits / 8L;

    if (read > this.max) {
      throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + read + "bytes where max allowed: " + this.max);
    }
  }
}
