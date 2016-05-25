package io.github.bktlib.nbt;

import io.github.bktlib.lazy.LazyInitVar;
import io.github.bktlib.reflect.util.ReflectUtil;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class NMSNBTTagCompound extends NBTTagCompound {

  private Object handle; //nms compound
  private static final LazyInitVar<Method> NMS_COMPOUND_WRITE_METHOD = new LazyInitVar<Method>() {
    @Override
    public Method init() {
      Class<?> nbtCompoundClass = ReflectUtil.getClass("{nms}.NBTTagCompound");
      if (nbtCompoundClass == null) {
        throw new RuntimeException("Could not find net.minecraft" +
                ".server.<version>.NBTTagCompound class.");
      }
      try {
        Method writeMethod = nbtCompoundClass.getDeclaredMethod("write", DataOutput.class);
        writeMethod.setAccessible(true);
        return writeMethod;
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      return null;
    }
  };
  private final static LazyInitVar<Method> NMS_COMPOUND_LOAD_METHOD = new LazyInitVar<Method>() {
    @Override
    public Method init() {
      Class<?> nbtCompoundClass = ReflectUtil.getClass("{nms}.NBTTagCompound");
      if (nbtCompoundClass == null) {
        throw new RuntimeException("Could not find net.minecraft" +
                ".server.<version>.NBTTagCompound class.");
      }
      try {
        Method writeMethod = nbtCompoundClass.getDeclaredMethod("load", DataInput.class,
            int.class, ReflectUtil.getClass("{nms}.NBTReadLimiter"));
        writeMethod.setAccessible(true);
        return writeMethod;
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      return null;
    }
  };
  /**
   * NMS NBTReadLimiter.INFINITE;
   */
  private static final LazyInitVar<Object> INFINITE_READ_LIMITER = new LazyInitVar<Object>() {
    @Override
    public Object init() {
      try {
        return ReflectUtil.getClass("{nms}.NBTReadLimiter").getDeclaredField("a").get(null);
      } catch (IllegalAccessException | NoSuchFieldException e) {
        e.printStackTrace();
      }
      return null;
    }
  };

  public NMSNBTTagCompound(Object handle) {
    this.handle = handle;

    /*
        Read data from handle
    */

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream das = new DataOutputStream(baos);
    try {
      NMS_COMPOUND_WRITE_METHOD.get().invoke(handle, das);
      ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(baos.toByteArray());
      DataInputStream dataInput = new DataInputStream(byteArrayInput);
      this.read(dataInput, 0, NBTReadLimiter.INFINITE);
    } catch (IOException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  Object getHandle() {
    return handle;
  }

  void saveToHandle() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dataOutput = new DataOutputStream(baos);
    try {
      this.write(dataOutput);
      ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(baos.toByteArray());
      DataInputStream dataInput = new DataInputStream(byteArrayInput);
      NMS_COMPOUND_LOAD_METHOD.get().invoke(handle, dataInput, 0, INFINITE_READ_LIMITER.get());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
