/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.ecommerce.app.kafka.warehouse.avro.model;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class OrderItemShipped extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6469800327536436938L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OrderItemShipped\",\"namespace\":\"com.ecommerce.app.kafka.warehouse.avro.model\",\"fields\":[{\"name\":\"productId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"quantity\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.UUIDConversion());
  }

  private static final BinaryMessageEncoder<OrderItemShipped> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OrderItemShipped> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<OrderItemShipped> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<OrderItemShipped> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<OrderItemShipped> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this OrderItemShipped to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a OrderItemShipped from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a OrderItemShipped instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static OrderItemShipped fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.util.UUID productId;
  private int quantity;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OrderItemShipped() {}

  /**
   * All-args constructor.
   * @param productId The new value for productId
   * @param quantity The new value for quantity
   */
  public OrderItemShipped(java.util.UUID productId, java.lang.Integer quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return productId;
    case 1: return quantity;
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
    case 0: productId = (java.util.UUID)value$; break;
    case 1: quantity = (java.lang.Integer)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'productId' field.
   * @return The value of the 'productId' field.
   */
  public java.util.UUID getProductId() {
    return productId;
  }


  /**
   * Sets the value of the 'productId' field.
   * @param value the value to set.
   */
  public void setProductId(java.util.UUID value) {
    this.productId = value;
  }

  /**
   * Gets the value of the 'quantity' field.
   * @return The value of the 'quantity' field.
   */
  public int getQuantity() {
    return quantity;
  }


  /**
   * Sets the value of the 'quantity' field.
   * @param value the value to set.
   */
  public void setQuantity(int value) {
    this.quantity = value;
  }

  /**
   * Creates a new OrderItemShipped RecordBuilder.
   * @return A new OrderItemShipped RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder newBuilder() {
    return new com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder();
  }

  /**
   * Creates a new OrderItemShipped RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OrderItemShipped RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder newBuilder(com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder other) {
    if (other == null) {
      return new com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder();
    } else {
      return new com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder(other);
    }
  }

  /**
   * Creates a new OrderItemShipped RecordBuilder by copying an existing OrderItemShipped instance.
   * @param other The existing instance to copy.
   * @return A new OrderItemShipped RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder newBuilder(com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped other) {
    if (other == null) {
      return new com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder();
    } else {
      return new com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder(other);
    }
  }

  /**
   * RecordBuilder for OrderItemShipped instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OrderItemShipped>
    implements org.apache.avro.data.RecordBuilder<OrderItemShipped> {

    private java.util.UUID productId;
    private int quantity;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.productId)) {
        this.productId = data().deepCopy(fields()[0].schema(), other.productId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.quantity)) {
        this.quantity = data().deepCopy(fields()[1].schema(), other.quantity);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing OrderItemShipped instance
     * @param other The existing instance to copy.
     */
    private Builder(com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.productId)) {
        this.productId = data().deepCopy(fields()[0].schema(), other.productId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.quantity)) {
        this.quantity = data().deepCopy(fields()[1].schema(), other.quantity);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'productId' field.
      * @return The value.
      */
    public java.util.UUID getProductId() {
      return productId;
    }


    /**
      * Sets the value of the 'productId' field.
      * @param value The value of 'productId'.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder setProductId(java.util.UUID value) {
      validate(fields()[0], value);
      this.productId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'productId' field has been set.
      * @return True if the 'productId' field has been set, false otherwise.
      */
    public boolean hasProductId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'productId' field.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder clearProductId() {
      productId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'quantity' field.
      * @return The value.
      */
    public int getQuantity() {
      return quantity;
    }


    /**
      * Sets the value of the 'quantity' field.
      * @param value The value of 'quantity'.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder setQuantity(int value) {
      validate(fields()[1], value);
      this.quantity = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'quantity' field has been set.
      * @return True if the 'quantity' field has been set, false otherwise.
      */
    public boolean hasQuantity() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'quantity' field.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.OrderItemShipped.Builder clearQuantity() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OrderItemShipped build() {
      try {
        OrderItemShipped record = new OrderItemShipped();
        record.productId = fieldSetFlags()[0] ? this.productId : (java.util.UUID) defaultValue(fields()[0]);
        record.quantity = fieldSetFlags()[1] ? this.quantity : (java.lang.Integer) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OrderItemShipped>
    WRITER$ = (org.apache.avro.io.DatumWriter<OrderItemShipped>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OrderItemShipped>
    READER$ = (org.apache.avro.io.DatumReader<OrderItemShipped>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










