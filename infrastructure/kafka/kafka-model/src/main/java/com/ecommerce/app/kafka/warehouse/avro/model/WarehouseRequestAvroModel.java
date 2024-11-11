/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.ecommerce.app.kafka.warehouse.avro.model;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class WarehouseRequestAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2362591346236818656L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"WarehouseRequestAvroModel\",\"namespace\":\"com.ecommerce.app.kafka.warehouse.avro.model\",\"fields\":[{\"name\":\"warehouseId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.UUIDConversion());
  }

  private static final BinaryMessageEncoder<WarehouseRequestAvroModel> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<WarehouseRequestAvroModel> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<WarehouseRequestAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<WarehouseRequestAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<WarehouseRequestAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this WarehouseRequestAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a WarehouseRequestAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a WarehouseRequestAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static WarehouseRequestAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.util.UUID warehouseId;
  private java.lang.String name;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public WarehouseRequestAvroModel() {}

  /**
   * All-args constructor.
   * @param warehouseId The new value for warehouseId
   * @param name The new value for name
   */
  public WarehouseRequestAvroModel(java.util.UUID warehouseId, java.lang.String name) {
    this.warehouseId = warehouseId;
    this.name = name;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return warehouseId;
    case 1: return name;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      new org.apache.avro.Conversions.UUIDConversion(),
      null,
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: warehouseId = (java.util.UUID)value$; break;
    case 1: name = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'warehouseId' field.
   * @return The value of the 'warehouseId' field.
   */
  public java.util.UUID getWarehouseId() {
    return warehouseId;
  }


  /**
   * Sets the value of the 'warehouseId' field.
   * @param value the value to set.
   */
  public void setWarehouseId(java.util.UUID value) {
    this.warehouseId = value;
  }

  /**
   * Gets the value of the 'name' field.
   * @return The value of the 'name' field.
   */
  public java.lang.String getName() {
    return name;
  }


  /**
   * Sets the value of the 'name' field.
   * @param value the value to set.
   */
  public void setName(java.lang.String value) {
    this.name = value;
  }

  /**
   * Creates a new WarehouseRequestAvroModel RecordBuilder.
   * @return A new WarehouseRequestAvroModel RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder newBuilder() {
    return new com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder();
  }

  /**
   * Creates a new WarehouseRequestAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new WarehouseRequestAvroModel RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder newBuilder(com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder other) {
    if (other == null) {
      return new com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder();
    } else {
      return new com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new WarehouseRequestAvroModel RecordBuilder by copying an existing WarehouseRequestAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new WarehouseRequestAvroModel RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder newBuilder(com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel other) {
    if (other == null) {
      return new com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder();
    } else {
      return new com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for WarehouseRequestAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<WarehouseRequestAvroModel>
    implements org.apache.avro.data.RecordBuilder<WarehouseRequestAvroModel> {

    private java.util.UUID warehouseId;
    private java.lang.String name;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.warehouseId)) {
        this.warehouseId = data().deepCopy(fields()[0].schema(), other.warehouseId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing WarehouseRequestAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.warehouseId)) {
        this.warehouseId = data().deepCopy(fields()[0].schema(), other.warehouseId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.name)) {
        this.name = data().deepCopy(fields()[1].schema(), other.name);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'warehouseId' field.
      * @return The value.
      */
    public java.util.UUID getWarehouseId() {
      return warehouseId;
    }


    /**
      * Sets the value of the 'warehouseId' field.
      * @param value The value of 'warehouseId'.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder setWarehouseId(java.util.UUID value) {
      validate(fields()[0], value);
      this.warehouseId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'warehouseId' field has been set.
      * @return True if the 'warehouseId' field has been set, false otherwise.
      */
    public boolean hasWarehouseId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'warehouseId' field.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder clearWarehouseId() {
      warehouseId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'name' field.
      * @return The value.
      */
    public java.lang.String getName() {
      return name;
    }


    /**
      * Sets the value of the 'name' field.
      * @param value The value of 'name'.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder setName(java.lang.String value) {
      validate(fields()[1], value);
      this.name = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'name' field has been set.
      * @return True if the 'name' field has been set, false otherwise.
      */
    public boolean hasName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'name' field.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.WarehouseRequestAvroModel.Builder clearName() {
      name = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public WarehouseRequestAvroModel build() {
      try {
        WarehouseRequestAvroModel record = new WarehouseRequestAvroModel();
        record.warehouseId = fieldSetFlags()[0] ? this.warehouseId : (java.util.UUID) defaultValue(fields()[0]);
        record.name = fieldSetFlags()[1] ? this.name : (java.lang.String) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<WarehouseRequestAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<WarehouseRequestAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<WarehouseRequestAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<WarehouseRequestAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










