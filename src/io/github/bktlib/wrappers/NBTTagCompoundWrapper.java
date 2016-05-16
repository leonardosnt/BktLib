/* 
  ISSO FOI AUTOMATICAMENTE GERADO POR UMA
  GAMBIARRA FEITA POR leonardosnt.

  Classe: net.minecraft.server.v1_8_R3.NBTTagCompound
  Data: 2016-05-16T03:14:03.374Z
*/
package io.github.bktlib.wrappers;

import java.lang.reflect.*;

public final class NBTTagCompoundWrapper {
  private Object nBTTagCompoundInstance;
  private Class<?> nBTTagCompoundClass;
  private Method setByteMethod;
  private Method setShortMethod;
  private Method setIntMethod;
  private Method setFloatMethod;
  private Method setStringMethod;
  private Method setByteArrayMethod;
  private Method setIntArrayMethod;
  private Method setBooleanMethod;
  private Method getMethod;
  private Method hasKeyMethod;
  private Method getByteMethod;
  private Method getShortMethod;
  private Method getIntMethod;
  private Method getFloatMethod;
  private Method getStringMethod;
  private Method getByteArrayMethod;
  private Method getIntArrayMethod;
  private Method getBooleanMethod;
  private Method removeMethod;
  private Method isEmptyMethod;

  public static NBTTagCompoundWrapper of(Object instance) {
    return new NBTTagCompoundWrapper(instance);
  }

  private NBTTagCompoundWrapper(Object instance) {
    this.nBTTagCompoundInstance = instance;
    this.nBTTagCompoundClass = instance.getClass();
  }

  public void setByte(java.lang.String param1, byte param2) {
    /* Simple (non thread-safe) lazy initialization */
    if (setByteMethod == null) {
      setByteMethod = getMethod("setByte", java.lang.String.class, byte.class);
    }
    invokeMethod(setByteMethod, param1, param2);
  }

  public void setShort(java.lang.String param1, short param2) {
    /* Simple (non thread-safe) lazy initialization */
    if (setShortMethod == null) {
      setShortMethod = getMethod("setShort", java.lang.String.class, short.class);
    }
    invokeMethod(setShortMethod, param1, param2);
  }

  public void setInt(java.lang.String param1, int param2) {
    /* Simple (non thread-safe) lazy initialization */
    if (setIntMethod == null) {
      setIntMethod = getMethod("setInt", java.lang.String.class, int.class);
    }
    invokeMethod(setIntMethod, param1, param2);
  }

  public void setFloat(java.lang.String param1, float param2) {
    /* Simple (non thread-safe) lazy initialization */
    if (setFloatMethod == null) {
      setFloatMethod = getMethod("setFloat", java.lang.String.class, float.class);
    }
    invokeMethod(setFloatMethod, param1, param2);
  }

  public void setString(java.lang.String param1, java.lang.String param2) {
    /* Simple (non thread-safe) lazy initialization */
    if (setStringMethod == null) {
      setStringMethod = getMethod("setString", java.lang.String.class, java.lang.String.class);
    }
    invokeMethod(setStringMethod, param1, param2);
  }

  public void setByteArray(java.lang.String param1, byte[] param2) {
    /* Simple (non thread-safe) lazy initialization */
    if (setByteArrayMethod == null) {
      setByteArrayMethod = getMethod("setByteArray", java.lang.String.class, byte[].class);
    }
    invokeMethod(setByteArrayMethod, param1, param2);
  }

  public void setIntArray(java.lang.String param1, int[] param2) {
    /* Simple (non thread-safe) lazy initialization */
    if (setIntArrayMethod == null) {
      setIntArrayMethod = getMethod("setIntArray", java.lang.String.class, int[].class);
    }
    invokeMethod(setIntArrayMethod, param1, param2);
  }

  public void setBoolean(java.lang.String param1, boolean param2) {
    /* Simple (non thread-safe) lazy initialization */
    if (setBooleanMethod == null) {
      setBooleanMethod = getMethod("setBoolean", java.lang.String.class, boolean.class);
    }
    invokeMethod(setBooleanMethod, param1, param2);
  }

  public Object get(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getMethod == null) {
      getMethod = getMethod("get", java.lang.String.class);
    }
    return invokeMethod(getMethod, param1);
  }

  public boolean hasKey(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (hasKeyMethod == null) {
      hasKeyMethod = getMethod("hasKey", java.lang.String.class);
    }
    return (boolean) invokeMethod(hasKeyMethod, param1);
  }

  public byte getByte(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getByteMethod == null) {
      getByteMethod = getMethod("getByte", java.lang.String.class);
    }
    return (byte) invokeMethod(getByteMethod, param1);
  }

  public short getShort(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getShortMethod == null) {
      getShortMethod = getMethod("getShort", java.lang.String.class);
    }
    return (short) invokeMethod(getShortMethod, param1);
  }

  public int getInt(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getIntMethod == null) {
      getIntMethod = getMethod("getInt", java.lang.String.class);
    }
    return (int) invokeMethod(getIntMethod, param1);
  }

  public float getFloat(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getFloatMethod == null) {
      getFloatMethod = getMethod("getFloat", java.lang.String.class);
    }
    return (float) invokeMethod(getFloatMethod, param1);
  }

  public java.lang.String getString(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getStringMethod == null) {
      getStringMethod = getMethod("getString", java.lang.String.class);
    }
    return invokeMethod(getStringMethod, param1);
  }

  public byte[] getByteArray(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getByteArrayMethod == null) {
      getByteArrayMethod = getMethod("getByteArray", java.lang.String.class);
    }
    return invokeMethod(getByteArrayMethod, param1);
  }

  public int[] getIntArray(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getIntArrayMethod == null) {
      getIntArrayMethod = getMethod("getIntArray", java.lang.String.class);
    }
    return invokeMethod(getIntArrayMethod, param1);
  }

  public boolean getBoolean(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (getBooleanMethod == null) {
      getBooleanMethod = getMethod("getBoolean", java.lang.String.class);
    }
    return (boolean) invokeMethod(getBooleanMethod, param1);
  }

  public void remove(java.lang.String param1) {
    /* Simple (non thread-safe) lazy initialization */
    if (removeMethod == null) {
      removeMethod = getMethod("remove", java.lang.String.class);
    }
    invokeMethod(removeMethod, param1);
  }

  public boolean isEmpty() {
    /* Simple (non thread-safe) lazy initialization */
    if (isEmptyMethod == null) {
      isEmptyMethod = getMethod("isEmpty");
    }
    return (boolean) invokeMethod(isEmptyMethod);
  }

  /* UTILS */

  private Method getMethod(String methodName, Class<?> ... params) {
    try {
      return nBTTagCompoundClass.getDeclaredMethod(methodName, params);
    } catch(Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  private <T> T invokeMethod(Method method, Object ... args) {
    try {
      return (T) method.invoke(nBTTagCompoundInstance, args);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }
}