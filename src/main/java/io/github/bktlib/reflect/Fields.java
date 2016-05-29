package io.github.bktlib.reflect;

import com.google.common.base.Preconditions;

import java.util.Optional;

//TODO cache

public class Fields {
  private Object instance;
  private Class<?> instanceClass;

  public static Fields from(Object obj) {
    Preconditions.checkNotNull(obj, "obj");

    return new Fields(obj);
  }

  private Fields(Object instance) {
    this.instance = instance;
    this.instanceClass = instance instanceof Class<?>
            ? (Class<?>) instance
            : instance.getClass();
  }

  public Field find(String fieldName) {
    Preconditions.checkNotNull(fieldName, "fieldName");

    try {
      java.lang.reflect.Field jField = instanceClass.getDeclaredField(fieldName);
      return new Field(jField);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Optional<Field> findOptional(String fieldName) {
    Preconditions.checkNotNull(fieldName, "fieldName");

    try {
      java.lang.reflect.Field jField = instanceClass.getDeclaredField(fieldName);
      return Optional.of(new Field(jField));
    } catch (NoSuchFieldException e) {
      return Optional.empty();
    }
  }

  public class Field {
    private java.lang.reflect.Field jField;

    Field(java.lang.reflect.Field jField) {
      this.jField = jField;
      if (!jField.isAccessible()) {
        jField.setAccessible(true);
      }
    }

    public void setFrom(Object targetObj, Object value) {
      try {
        jField.set(targetObj, value);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    @SuppressWarnings("unchecked")
    public <T> T getFrom(Object targetObj) {
      try {
        return (T) jField.get(targetObj);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      return null;
    }

    public void set(Object value) {
      setFrom(Fields.this.instance, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get() {
      return (T) getFrom(Fields.this.instance);
    }

    public Fields getAsFields() {
      return Fields.from(get());
    }

    public Fields getAsFieldsFrom(Object targetObj) {
      return Fields.from(getFrom(targetObj));
    }

    public java.lang.reflect.Field getJavaField() {
      return jField;
    }
  }
}
