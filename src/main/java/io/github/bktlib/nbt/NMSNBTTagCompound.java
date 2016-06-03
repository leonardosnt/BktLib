package io.github.bktlib.nbt;

import io.github.bktlib.lazy.LazyInitField;
import io.github.bktlib.lazy.LazyInitMethod;
import io.github.bktlib.reflect.util.ReflectUtil;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

class NMSNBTTagCompound extends NBTTagCompound {

  private Object handle; //nms compound

  private static final LazyInitMethod NMS_COMPOUND_WRITE_METHOD = new LazyInitMethod(
      ReflectUtil.resolveName("{nms}.NBTTagCompound"), "write", DataOutput.class
  );
  private static final LazyInitMethod NMS_COMPOUND_LOAD_METHOD = new LazyInitMethod(
      ReflectUtil.resolveName("{nms}.NBTTagCompound"), "load", DataInput.class, int.class,
      ReflectUtil.getClass("{nms}.NBTReadLimiter")
  );
  private static final LazyInitField INFINITE_READ_LIMITER = new LazyInitField(
      ReflectUtil.resolveName("{nms}.NBTReadLimiter"), "a"
  );

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
      NMS_COMPOUND_LOAD_METHOD.get().invoke(handle, dataInput, 0, INFINITE_READ_LIMITER.get().get(null));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
