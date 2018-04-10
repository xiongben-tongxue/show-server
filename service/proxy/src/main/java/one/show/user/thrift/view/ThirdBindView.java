/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package one.show.user.thrift.view;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThirdBindView implements org.apache.thrift.TBase<ThirdBindView, ThirdBindView._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ThirdBindView");

  private static final org.apache.thrift.protocol.TField UID_FIELD_DESC = new org.apache.thrift.protocol.TField("uid", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField TID_FIELD_DESC = new org.apache.thrift.protocol.TField("tid", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TOKEN_FIELD_DESC = new org.apache.thrift.protocol.TField("token", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PUBLIC_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("publicStatus", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ThirdBindViewStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ThirdBindViewTupleSchemeFactory());
  }

  public long uid; // required
  public String tid; // required
  public String token; // required
  public int publicStatus; // required
  public String type; // required
  public int createTime; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    UID((short)1, "uid"),
    TID((short)2, "tid"),
    TOKEN((short)3, "token"),
    PUBLIC_STATUS((short)4, "publicStatus"),
    TYPE((short)5, "type"),
    CREATE_TIME((short)6, "createTime");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // UID
          return UID;
        case 2: // TID
          return TID;
        case 3: // TOKEN
          return TOKEN;
        case 4: // PUBLIC_STATUS
          return PUBLIC_STATUS;
        case 5: // TYPE
          return TYPE;
        case 6: // CREATE_TIME
          return CREATE_TIME;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __UID_ISSET_ID = 0;
  private static final int __PUBLICSTATUS_ISSET_ID = 1;
  private static final int __CREATETIME_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.UID, new org.apache.thrift.meta_data.FieldMetaData("uid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TID, new org.apache.thrift.meta_data.FieldMetaData("tid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TOKEN, new org.apache.thrift.meta_data.FieldMetaData("token", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PUBLIC_STATUS, new org.apache.thrift.meta_data.FieldMetaData("publicStatus", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ThirdBindView.class, metaDataMap);
  }

  public ThirdBindView() {
  }

  public ThirdBindView(
    long uid,
    String tid,
    String token,
    int publicStatus,
    String type,
    int createTime)
  {
    this();
    this.uid = uid;
    setUidIsSet(true);
    this.tid = tid;
    this.token = token;
    this.publicStatus = publicStatus;
    setPublicStatusIsSet(true);
    this.type = type;
    this.createTime = createTime;
    setCreateTimeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ThirdBindView(ThirdBindView other) {
    __isset_bitfield = other.__isset_bitfield;
    this.uid = other.uid;
    if (other.isSetTid()) {
      this.tid = other.tid;
    }
    if (other.isSetToken()) {
      this.token = other.token;
    }
    this.publicStatus = other.publicStatus;
    if (other.isSetType()) {
      this.type = other.type;
    }
    this.createTime = other.createTime;
  }

  public ThirdBindView deepCopy() {
    return new ThirdBindView(this);
  }

  @Override
  public void clear() {
    setUidIsSet(false);
    this.uid = 0;
    this.tid = null;
    this.token = null;
    setPublicStatusIsSet(false);
    this.publicStatus = 0;
    this.type = null;
    setCreateTimeIsSet(false);
    this.createTime = 0;
  }

  public long getUid() {
    return this.uid;
  }

  public ThirdBindView setUid(long uid) {
    this.uid = uid;
    setUidIsSet(true);
    return this;
  }

  public void unsetUid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UID_ISSET_ID);
  }

  /** Returns true if field uid is set (has been assigned a value) and false otherwise */
  public boolean isSetUid() {
    return EncodingUtils.testBit(__isset_bitfield, __UID_ISSET_ID);
  }

  public void setUidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UID_ISSET_ID, value);
  }

  public String getTid() {
    return this.tid;
  }

  public ThirdBindView setTid(String tid) {
    this.tid = tid;
    return this;
  }

  public void unsetTid() {
    this.tid = null;
  }

  /** Returns true if field tid is set (has been assigned a value) and false otherwise */
  public boolean isSetTid() {
    return this.tid != null;
  }

  public void setTidIsSet(boolean value) {
    if (!value) {
      this.tid = null;
    }
  }

  public String getToken() {
    return this.token;
  }

  public ThirdBindView setToken(String token) {
    this.token = token;
    return this;
  }

  public void unsetToken() {
    this.token = null;
  }

  /** Returns true if field token is set (has been assigned a value) and false otherwise */
  public boolean isSetToken() {
    return this.token != null;
  }

  public void setTokenIsSet(boolean value) {
    if (!value) {
      this.token = null;
    }
  }

  public int getPublicStatus() {
    return this.publicStatus;
  }

  public ThirdBindView setPublicStatus(int publicStatus) {
    this.publicStatus = publicStatus;
    setPublicStatusIsSet(true);
    return this;
  }

  public void unsetPublicStatus() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PUBLICSTATUS_ISSET_ID);
  }

  /** Returns true if field publicStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetPublicStatus() {
    return EncodingUtils.testBit(__isset_bitfield, __PUBLICSTATUS_ISSET_ID);
  }

  public void setPublicStatusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PUBLICSTATUS_ISSET_ID, value);
  }

  public String getType() {
    return this.type;
  }

  public ThirdBindView setType(String type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public int getCreateTime() {
    return this.createTime;
  }

  public ThirdBindView setCreateTime(int createTime) {
    this.createTime = createTime;
    setCreateTimeIsSet(true);
    return this;
  }

  public void unsetCreateTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CREATETIME_ISSET_ID);
  }

  /** Returns true if field createTime is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTime() {
    return EncodingUtils.testBit(__isset_bitfield, __CREATETIME_ISSET_ID);
  }

  public void setCreateTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CREATETIME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case UID:
      if (value == null) {
        unsetUid();
      } else {
        setUid((Long)value);
      }
      break;

    case TID:
      if (value == null) {
        unsetTid();
      } else {
        setTid((String)value);
      }
      break;

    case TOKEN:
      if (value == null) {
        unsetToken();
      } else {
        setToken((String)value);
      }
      break;

    case PUBLIC_STATUS:
      if (value == null) {
        unsetPublicStatus();
      } else {
        setPublicStatus((Integer)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((String)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case UID:
      return Long.valueOf(getUid());

    case TID:
      return getTid();

    case TOKEN:
      return getToken();

    case PUBLIC_STATUS:
      return Integer.valueOf(getPublicStatus());

    case TYPE:
      return getType();

    case CREATE_TIME:
      return Integer.valueOf(getCreateTime());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case UID:
      return isSetUid();
    case TID:
      return isSetTid();
    case TOKEN:
      return isSetToken();
    case PUBLIC_STATUS:
      return isSetPublicStatus();
    case TYPE:
      return isSetType();
    case CREATE_TIME:
      return isSetCreateTime();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ThirdBindView)
      return this.equals((ThirdBindView)that);
    return false;
  }

  public boolean equals(ThirdBindView that) {
    if (that == null)
      return false;

    boolean this_present_uid = true;
    boolean that_present_uid = true;
    if (this_present_uid || that_present_uid) {
      if (!(this_present_uid && that_present_uid))
        return false;
      if (this.uid != that.uid)
        return false;
    }

    boolean this_present_tid = true && this.isSetTid();
    boolean that_present_tid = true && that.isSetTid();
    if (this_present_tid || that_present_tid) {
      if (!(this_present_tid && that_present_tid))
        return false;
      if (!this.tid.equals(that.tid))
        return false;
    }

    boolean this_present_token = true && this.isSetToken();
    boolean that_present_token = true && that.isSetToken();
    if (this_present_token || that_present_token) {
      if (!(this_present_token && that_present_token))
        return false;
      if (!this.token.equals(that.token))
        return false;
    }

    boolean this_present_publicStatus = true;
    boolean that_present_publicStatus = true;
    if (this_present_publicStatus || that_present_publicStatus) {
      if (!(this_present_publicStatus && that_present_publicStatus))
        return false;
      if (this.publicStatus != that.publicStatus)
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_createTime = true;
    boolean that_present_createTime = true;
    if (this_present_createTime || that_present_createTime) {
      if (!(this_present_createTime && that_present_createTime))
        return false;
      if (this.createTime != that.createTime)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(ThirdBindView other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    ThirdBindView typedOther = (ThirdBindView)other;

    lastComparison = Boolean.valueOf(isSetUid()).compareTo(typedOther.isSetUid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uid, typedOther.uid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTid()).compareTo(typedOther.isSetTid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tid, typedOther.tid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetToken()).compareTo(typedOther.isSetToken());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetToken()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.token, typedOther.token);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPublicStatus()).compareTo(typedOther.isSetPublicStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPublicStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.publicStatus, typedOther.publicStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, typedOther.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTime()).compareTo(typedOther.isSetCreateTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTime, typedOther.createTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ThirdBindView(");
    boolean first = true;

    sb.append("uid:");
    sb.append(this.uid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("tid:");
    if (this.tid == null) {
      sb.append("null");
    } else {
      sb.append(this.tid);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("token:");
    if (this.token == null) {
      sb.append("null");
    } else {
      sb.append(this.token);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("publicStatus:");
    sb.append(this.publicStatus);
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("createTime:");
    sb.append(this.createTime);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ThirdBindViewStandardSchemeFactory implements SchemeFactory {
    public ThirdBindViewStandardScheme getScheme() {
      return new ThirdBindViewStandardScheme();
    }
  }

  private static class ThirdBindViewStandardScheme extends StandardScheme<ThirdBindView> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ThirdBindView struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // UID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.uid = iprot.readI64();
              struct.setUidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tid = iprot.readString();
              struct.setTidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOKEN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.token = iprot.readString();
              struct.setTokenIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PUBLIC_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.publicStatus = iprot.readI32();
              struct.setPublicStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.type = iprot.readString();
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTime = iprot.readI32();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ThirdBindView struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(UID_FIELD_DESC);
      oprot.writeI64(struct.uid);
      oprot.writeFieldEnd();
      if (struct.tid != null) {
        oprot.writeFieldBegin(TID_FIELD_DESC);
        oprot.writeString(struct.tid);
        oprot.writeFieldEnd();
      }
      if (struct.token != null) {
        oprot.writeFieldBegin(TOKEN_FIELD_DESC);
        oprot.writeString(struct.token);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PUBLIC_STATUS_FIELD_DESC);
      oprot.writeI32(struct.publicStatus);
      oprot.writeFieldEnd();
      if (struct.type != null) {
        oprot.writeFieldBegin(TYPE_FIELD_DESC);
        oprot.writeString(struct.type);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
      oprot.writeI32(struct.createTime);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ThirdBindViewTupleSchemeFactory implements SchemeFactory {
    public ThirdBindViewTupleScheme getScheme() {
      return new ThirdBindViewTupleScheme();
    }
  }

  private static class ThirdBindViewTupleScheme extends TupleScheme<ThirdBindView> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ThirdBindView struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetUid()) {
        optionals.set(0);
      }
      if (struct.isSetTid()) {
        optionals.set(1);
      }
      if (struct.isSetToken()) {
        optionals.set(2);
      }
      if (struct.isSetPublicStatus()) {
        optionals.set(3);
      }
      if (struct.isSetType()) {
        optionals.set(4);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetUid()) {
        oprot.writeI64(struct.uid);
      }
      if (struct.isSetTid()) {
        oprot.writeString(struct.tid);
      }
      if (struct.isSetToken()) {
        oprot.writeString(struct.token);
      }
      if (struct.isSetPublicStatus()) {
        oprot.writeI32(struct.publicStatus);
      }
      if (struct.isSetType()) {
        oprot.writeString(struct.type);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeI32(struct.createTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ThirdBindView struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.uid = iprot.readI64();
        struct.setUidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.tid = iprot.readString();
        struct.setTidIsSet(true);
      }
      if (incoming.get(2)) {
        struct.token = iprot.readString();
        struct.setTokenIsSet(true);
      }
      if (incoming.get(3)) {
        struct.publicStatus = iprot.readI32();
        struct.setPublicStatusIsSet(true);
      }
      if (incoming.get(4)) {
        struct.type = iprot.readString();
        struct.setTypeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.createTime = iprot.readI32();
        struct.setCreateTimeIsSet(true);
      }
    }
  }

}
