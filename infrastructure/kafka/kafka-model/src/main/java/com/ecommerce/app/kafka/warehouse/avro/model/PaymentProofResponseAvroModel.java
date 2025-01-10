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
public class PaymentProofResponseAvroModel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 3867633129177746269L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PaymentProofResponseAvroModel\",\"namespace\":\"com.ecommerce.app.kafka.warehouse.avro.model\",\"fields\":[{\"name\":\"orderId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"updatedAt\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}},{\"name\":\"orderStatus\",\"type\":{\"type\":\"enum\",\"name\":\"OrderStatus\",\"symbols\":[\"AWAITING_PAYMENT\",\"PENDING\",\"PROCESSED\",\"APPROVED\",\"SHIPPED\",\"CONFIRMED\",\"CANCELLING\",\"CANCELLED\"]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.UUIDConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.TimestampMillisConversion());
  }

  private static final BinaryMessageEncoder<PaymentProofResponseAvroModel> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<PaymentProofResponseAvroModel> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<PaymentProofResponseAvroModel> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<PaymentProofResponseAvroModel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<PaymentProofResponseAvroModel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this PaymentProofResponseAvroModel to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a PaymentProofResponseAvroModel from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a PaymentProofResponseAvroModel instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static PaymentProofResponseAvroModel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.util.UUID orderId;
  private java.time.Instant updatedAt;
  private com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus orderStatus;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PaymentProofResponseAvroModel() {}

  /**
   * All-args constructor.
   * @param orderId The new value for orderId
   * @param updatedAt The new value for updatedAt
   * @param orderStatus The new value for orderStatus
   */
  public PaymentProofResponseAvroModel(java.util.UUID orderId, java.time.Instant updatedAt, com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus orderStatus) {
    this.orderId = orderId;
    this.updatedAt = updatedAt.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
    this.orderStatus = orderStatus;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return orderId;
    case 1: return updatedAt;
    case 2: return orderStatus;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      new org.apache.avro.Conversions.UUIDConversion(),
      new org.apache.avro.data.TimeConversions.TimestampMillisConversion(),
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
    case 0: orderId = (java.util.UUID)value$; break;
    case 1: updatedAt = (java.time.Instant)value$; break;
    case 2: orderStatus = (com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'orderId' field.
   * @return The value of the 'orderId' field.
   */
  public java.util.UUID getOrderId() {
    return orderId;
  }


  /**
   * Sets the value of the 'orderId' field.
   * @param value the value to set.
   */
  public void setOrderId(java.util.UUID value) {
    this.orderId = value;
  }

  /**
   * Gets the value of the 'updatedAt' field.
   * @return The value of the 'updatedAt' field.
   */
  public java.time.Instant getUpdatedAt() {
    return updatedAt;
  }


  /**
   * Sets the value of the 'updatedAt' field.
   * @param value the value to set.
   */
  public void setUpdatedAt(java.time.Instant value) {
    this.updatedAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
  }

  /**
   * Gets the value of the 'orderStatus' field.
   * @return The value of the 'orderStatus' field.
   */
  public com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus getOrderStatus() {
    return orderStatus;
  }


  /**
   * Sets the value of the 'orderStatus' field.
   * @param value the value to set.
   */
  public void setOrderStatus(com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus value) {
    this.orderStatus = value;
  }

  /**
   * Creates a new PaymentProofResponseAvroModel RecordBuilder.
   * @return A new PaymentProofResponseAvroModel RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder newBuilder() {
    return new com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder();
  }

  /**
   * Creates a new PaymentProofResponseAvroModel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new PaymentProofResponseAvroModel RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder newBuilder(com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder other) {
    if (other == null) {
      return new com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder();
    } else {
      return new com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder(other);
    }
  }

  /**
   * Creates a new PaymentProofResponseAvroModel RecordBuilder by copying an existing PaymentProofResponseAvroModel instance.
   * @param other The existing instance to copy.
   * @return A new PaymentProofResponseAvroModel RecordBuilder
   */
  public static com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder newBuilder(com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel other) {
    if (other == null) {
      return new com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder();
    } else {
      return new com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder(other);
    }
  }

  /**
   * RecordBuilder for PaymentProofResponseAvroModel instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PaymentProofResponseAvroModel>
    implements org.apache.avro.data.RecordBuilder<PaymentProofResponseAvroModel> {

    private java.util.UUID orderId;
    private java.time.Instant updatedAt;
    private com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus orderStatus;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.orderId)) {
        this.orderId = data().deepCopy(fields()[0].schema(), other.orderId);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.updatedAt)) {
        this.updatedAt = data().deepCopy(fields()[1].schema(), other.updatedAt);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.orderStatus)) {
        this.orderStatus = data().deepCopy(fields()[2].schema(), other.orderStatus);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing PaymentProofResponseAvroModel instance
     * @param other The existing instance to copy.
     */
    private Builder(com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.orderId)) {
        this.orderId = data().deepCopy(fields()[0].schema(), other.orderId);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.updatedAt)) {
        this.updatedAt = data().deepCopy(fields()[1].schema(), other.updatedAt);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.orderStatus)) {
        this.orderStatus = data().deepCopy(fields()[2].schema(), other.orderStatus);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'orderId' field.
      * @return The value.
      */
    public java.util.UUID getOrderId() {
      return orderId;
    }


    /**
      * Sets the value of the 'orderId' field.
      * @param value The value of 'orderId'.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder setOrderId(java.util.UUID value) {
      validate(fields()[0], value);
      this.orderId = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'orderId' field has been set.
      * @return True if the 'orderId' field has been set, false otherwise.
      */
    public boolean hasOrderId() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'orderId' field.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder clearOrderId() {
      orderId = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'updatedAt' field.
      * @return The value.
      */
    public java.time.Instant getUpdatedAt() {
      return updatedAt;
    }


    /**
      * Sets the value of the 'updatedAt' field.
      * @param value The value of 'updatedAt'.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder setUpdatedAt(java.time.Instant value) {
      validate(fields()[1], value);
      this.updatedAt = value.truncatedTo(java.time.temporal.ChronoUnit.MILLIS);
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'updatedAt' field has been set.
      * @return True if the 'updatedAt' field has been set, false otherwise.
      */
    public boolean hasUpdatedAt() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'updatedAt' field.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder clearUpdatedAt() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'orderStatus' field.
      * @return The value.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus getOrderStatus() {
      return orderStatus;
    }


    /**
      * Sets the value of the 'orderStatus' field.
      * @param value The value of 'orderStatus'.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder setOrderStatus(com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus value) {
      validate(fields()[2], value);
      this.orderStatus = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'orderStatus' field has been set.
      * @return True if the 'orderStatus' field has been set, false otherwise.
      */
    public boolean hasOrderStatus() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'orderStatus' field.
      * @return This builder.
      */
    public com.ecommerce.app.kafka.warehouse.avro.model.PaymentProofResponseAvroModel.Builder clearOrderStatus() {
      orderStatus = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PaymentProofResponseAvroModel build() {
      try {
        PaymentProofResponseAvroModel record = new PaymentProofResponseAvroModel();
        record.orderId = fieldSetFlags()[0] ? this.orderId : (java.util.UUID) defaultValue(fields()[0]);
        record.updatedAt = fieldSetFlags()[1] ? this.updatedAt : (java.time.Instant) defaultValue(fields()[1]);
        record.orderStatus = fieldSetFlags()[2] ? this.orderStatus : (com.ecommerce.app.kafka.warehouse.avro.model.OrderStatus) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<PaymentProofResponseAvroModel>
    WRITER$ = (org.apache.avro.io.DatumWriter<PaymentProofResponseAvroModel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<PaymentProofResponseAvroModel>
    READER$ = (org.apache.avro.io.DatumReader<PaymentProofResponseAvroModel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










