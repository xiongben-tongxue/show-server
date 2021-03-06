/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package one.show.video.thrift.view;

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

public class LiveUserView implements org.apache.thrift.TBase<LiveUserView, LiveUserView._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("LiveUserView");

  private static final org.apache.thrift.protocol.TField LIVEID_FIELD_DESC = new org.apache.thrift.protocol.TField("liveid", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField UID_FIELD_DESC = new org.apache.thrift.protocol.TField("uid", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField SNAPSHOT_FIELD_DESC = new org.apache.thrift.protocol.TField("snapshot", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField VIEWED_FIELD_DESC = new org.apache.thrift.protocol.TField("viewed", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField CREATE_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("createTime", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField VOD_STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("vodStatus", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField STATUS_FIELD_DESC = new org.apache.thrift.protocol.TField("status", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new LiveUserViewStandardSchemeFactory());
    schemes.put(TupleScheme.class, new LiveUserViewTupleSchemeFactory());
  }

  public long liveid; // required
  public long uid; // required
  public String snapshot; // required
  public int viewed; // required
  public int createTime; // required
  public int vodStatus; // required
  public int status; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LIVEID((short)1, "liveid"),
    UID((short)2, "uid"),
    SNAPSHOT((short)3, "snapshot"),
    VIEWED((short)4, "viewed"),
    CREATE_TIME((short)5, "createTime"),
    VOD_STATUS((short)6, "vodStatus"),
    STATUS((short)7, "status");

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
        case 1: // LIVEID
          return LIVEID;
        case 2: // UID
          return UID;
        case 3: // SNAPSHOT
          return SNAPSHOT;
        case 4: // VIEWED
          return VIEWED;
        case 5: // CREATE_TIME
          return CREATE_TIME;
        case 6: // VOD_STATUS
          return VOD_STATUS;
        case 7: // STATUS
          return STATUS;
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
  private static final int __LIVEID_ISSET_ID = 0;
  private static final int __UID_ISSET_ID = 1;
  private static final int __VIEWED_ISSET_ID = 2;
  private static final int __CREATETIME_ISSET_ID = 3;
  private static final int __VODSTATUS_ISSET_ID = 4;
  private static final int __STATUS_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LIVEID, new org.apache.thrift.meta_data.FieldMetaData("liveid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.UID, new org.apache.thrift.meta_data.FieldMetaData("uid", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SNAPSHOT, new org.apache.thrift.meta_data.FieldMetaData("snapshot", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VIEWED, new org.apache.thrift.meta_data.FieldMetaData("viewed", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CREATE_TIME, new org.apache.thrift.meta_data.FieldMetaData("createTime", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VOD_STATUS, new org.apache.thrift.meta_data.FieldMetaData("vodStatus", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STATUS, new org.apache.thrift.meta_data.FieldMetaData("status", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(LiveUserView.class, metaDataMap);
  }

  public LiveUserView() {
  }

  public LiveUserView(
    long liveid,
    long uid,
    String snapshot,
    int viewed,
    int createTime,
    int vodStatus,
    int status)
  {
    this();
    this.liveid = liveid;
    setLiveidIsSet(true);
    this.uid = uid;
    setUidIsSet(true);
    this.snapshot = snapshot;
    this.viewed = viewed;
    setViewedIsSet(true);
    this.createTime = createTime;
    setCreateTimeIsSet(true);
    this.vodStatus = vodStatus;
    setVodStatusIsSet(true);
    this.status = status;
    setStatusIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public LiveUserView(LiveUserView other) {
    __isset_bitfield = other.__isset_bitfield;
    this.liveid = other.liveid;
    this.uid = other.uid;
    if (other.isSetSnapshot()) {
      this.snapshot = other.snapshot;
    }
    this.viewed = other.viewed;
    this.createTime = other.createTime;
    this.vodStatus = other.vodStatus;
    this.status = other.status;
  }

  public LiveUserView deepCopy() {
    return new LiveUserView(this);
  }

  @Override
  public void clear() {
    setLiveidIsSet(false);
    this.liveid = 0;
    setUidIsSet(false);
    this.uid = 0;
    this.snapshot = null;
    setViewedIsSet(false);
    this.viewed = 0;
    setCreateTimeIsSet(false);
    this.createTime = 0;
    setVodStatusIsSet(false);
    this.vodStatus = 0;
    setStatusIsSet(false);
    this.status = 0;
  }

  public long getLiveid() {
    return this.liveid;
  }

  public LiveUserView setLiveid(long liveid) {
    this.liveid = liveid;
    setLiveidIsSet(true);
    return this;
  }

  public void unsetLiveid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LIVEID_ISSET_ID);
  }

  /** Returns true if field liveid is set (has been assigned a value) and false otherwise */
  public boolean isSetLiveid() {
    return EncodingUtils.testBit(__isset_bitfield, __LIVEID_ISSET_ID);
  }

  public void setLiveidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LIVEID_ISSET_ID, value);
  }

  public long getUid() {
    return this.uid;
  }

  public LiveUserView setUid(long uid) {
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

  public String getSnapshot() {
    return this.snapshot;
  }

  public LiveUserView setSnapshot(String snapshot) {
    this.snapshot = snapshot;
    return this;
  }

  public void unsetSnapshot() {
    this.snapshot = null;
  }

  /** Returns true if field snapshot is set (has been assigned a value) and false otherwise */
  public boolean isSetSnapshot() {
    return this.snapshot != null;
  }

  public void setSnapshotIsSet(boolean value) {
    if (!value) {
      this.snapshot = null;
    }
  }

  public int getViewed() {
    return this.viewed;
  }

  public LiveUserView setViewed(int viewed) {
    this.viewed = viewed;
    setViewedIsSet(true);
    return this;
  }

  public void unsetViewed() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIEWED_ISSET_ID);
  }

  /** Returns true if field viewed is set (has been assigned a value) and false otherwise */
  public boolean isSetViewed() {
    return EncodingUtils.testBit(__isset_bitfield, __VIEWED_ISSET_ID);
  }

  public void setViewedIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIEWED_ISSET_ID, value);
  }

  public int getCreateTime() {
    return this.createTime;
  }

  public LiveUserView setCreateTime(int createTime) {
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

  public int getVodStatus() {
    return this.vodStatus;
  }

  public LiveUserView setVodStatus(int vodStatus) {
    this.vodStatus = vodStatus;
    setVodStatusIsSet(true);
    return this;
  }

  public void unsetVodStatus() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VODSTATUS_ISSET_ID);
  }

  /** Returns true if field vodStatus is set (has been assigned a value) and false otherwise */
  public boolean isSetVodStatus() {
    return EncodingUtils.testBit(__isset_bitfield, __VODSTATUS_ISSET_ID);
  }

  public void setVodStatusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VODSTATUS_ISSET_ID, value);
  }

  public int getStatus() {
    return this.status;
  }

  public LiveUserView setStatus(int status) {
    this.status = status;
    setStatusIsSet(true);
    return this;
  }

  public void unsetStatus() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  /** Returns true if field status is set (has been assigned a value) and false otherwise */
  public boolean isSetStatus() {
    return EncodingUtils.testBit(__isset_bitfield, __STATUS_ISSET_ID);
  }

  public void setStatusIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __STATUS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LIVEID:
      if (value == null) {
        unsetLiveid();
      } else {
        setLiveid((Long)value);
      }
      break;

    case UID:
      if (value == null) {
        unsetUid();
      } else {
        setUid((Long)value);
      }
      break;

    case SNAPSHOT:
      if (value == null) {
        unsetSnapshot();
      } else {
        setSnapshot((String)value);
      }
      break;

    case VIEWED:
      if (value == null) {
        unsetViewed();
      } else {
        setViewed((Integer)value);
      }
      break;

    case CREATE_TIME:
      if (value == null) {
        unsetCreateTime();
      } else {
        setCreateTime((Integer)value);
      }
      break;

    case VOD_STATUS:
      if (value == null) {
        unsetVodStatus();
      } else {
        setVodStatus((Integer)value);
      }
      break;

    case STATUS:
      if (value == null) {
        unsetStatus();
      } else {
        setStatus((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LIVEID:
      return Long.valueOf(getLiveid());

    case UID:
      return Long.valueOf(getUid());

    case SNAPSHOT:
      return getSnapshot();

    case VIEWED:
      return Integer.valueOf(getViewed());

    case CREATE_TIME:
      return Integer.valueOf(getCreateTime());

    case VOD_STATUS:
      return Integer.valueOf(getVodStatus());

    case STATUS:
      return Integer.valueOf(getStatus());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LIVEID:
      return isSetLiveid();
    case UID:
      return isSetUid();
    case SNAPSHOT:
      return isSetSnapshot();
    case VIEWED:
      return isSetViewed();
    case CREATE_TIME:
      return isSetCreateTime();
    case VOD_STATUS:
      return isSetVodStatus();
    case STATUS:
      return isSetStatus();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof LiveUserView)
      return this.equals((LiveUserView)that);
    return false;
  }

  public boolean equals(LiveUserView that) {
    if (that == null)
      return false;

    boolean this_present_liveid = true;
    boolean that_present_liveid = true;
    if (this_present_liveid || that_present_liveid) {
      if (!(this_present_liveid && that_present_liveid))
        return false;
      if (this.liveid != that.liveid)
        return false;
    }

    boolean this_present_uid = true;
    boolean that_present_uid = true;
    if (this_present_uid || that_present_uid) {
      if (!(this_present_uid && that_present_uid))
        return false;
      if (this.uid != that.uid)
        return false;
    }

    boolean this_present_snapshot = true && this.isSetSnapshot();
    boolean that_present_snapshot = true && that.isSetSnapshot();
    if (this_present_snapshot || that_present_snapshot) {
      if (!(this_present_snapshot && that_present_snapshot))
        return false;
      if (!this.snapshot.equals(that.snapshot))
        return false;
    }

    boolean this_present_viewed = true;
    boolean that_present_viewed = true;
    if (this_present_viewed || that_present_viewed) {
      if (!(this_present_viewed && that_present_viewed))
        return false;
      if (this.viewed != that.viewed)
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

    boolean this_present_vodStatus = true;
    boolean that_present_vodStatus = true;
    if (this_present_vodStatus || that_present_vodStatus) {
      if (!(this_present_vodStatus && that_present_vodStatus))
        return false;
      if (this.vodStatus != that.vodStatus)
        return false;
    }

    boolean this_present_status = true;
    boolean that_present_status = true;
    if (this_present_status || that_present_status) {
      if (!(this_present_status && that_present_status))
        return false;
      if (this.status != that.status)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(LiveUserView other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    LiveUserView typedOther = (LiveUserView)other;

    lastComparison = Boolean.valueOf(isSetLiveid()).compareTo(typedOther.isSetLiveid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLiveid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.liveid, typedOther.liveid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetSnapshot()).compareTo(typedOther.isSetSnapshot());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSnapshot()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.snapshot, typedOther.snapshot);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetViewed()).compareTo(typedOther.isSetViewed());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetViewed()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.viewed, typedOther.viewed);
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
    lastComparison = Boolean.valueOf(isSetVodStatus()).compareTo(typedOther.isSetVodStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVodStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.vodStatus, typedOther.vodStatus);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatus()).compareTo(typedOther.isSetStatus());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatus()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.status, typedOther.status);
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
    StringBuilder sb = new StringBuilder("LiveUserView(");
    boolean first = true;

    sb.append("liveid:");
    sb.append(this.liveid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("uid:");
    sb.append(this.uid);
    first = false;
    if (!first) sb.append(", ");
    sb.append("snapshot:");
    if (this.snapshot == null) {
      sb.append("null");
    } else {
      sb.append(this.snapshot);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("viewed:");
    sb.append(this.viewed);
    first = false;
    if (!first) sb.append(", ");
    sb.append("createTime:");
    sb.append(this.createTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("vodStatus:");
    sb.append(this.vodStatus);
    first = false;
    if (!first) sb.append(", ");
    sb.append("status:");
    sb.append(this.status);
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

  private static class LiveUserViewStandardSchemeFactory implements SchemeFactory {
    public LiveUserViewStandardScheme getScheme() {
      return new LiveUserViewStandardScheme();
    }
  }

  private static class LiveUserViewStandardScheme extends StandardScheme<LiveUserView> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, LiveUserView struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LIVEID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.liveid = iprot.readI64();
              struct.setLiveidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // UID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.uid = iprot.readI64();
              struct.setUidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SNAPSHOT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.snapshot = iprot.readString();
              struct.setSnapshotIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // VIEWED
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.viewed = iprot.readI32();
              struct.setViewedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATE_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.createTime = iprot.readI32();
              struct.setCreateTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // VOD_STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.vodStatus = iprot.readI32();
              struct.setVodStatusIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // STATUS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.status = iprot.readI32();
              struct.setStatusIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, LiveUserView struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(LIVEID_FIELD_DESC);
      oprot.writeI64(struct.liveid);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(UID_FIELD_DESC);
      oprot.writeI64(struct.uid);
      oprot.writeFieldEnd();
      if (struct.snapshot != null) {
        oprot.writeFieldBegin(SNAPSHOT_FIELD_DESC);
        oprot.writeString(struct.snapshot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(VIEWED_FIELD_DESC);
      oprot.writeI32(struct.viewed);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CREATE_TIME_FIELD_DESC);
      oprot.writeI32(struct.createTime);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VOD_STATUS_FIELD_DESC);
      oprot.writeI32(struct.vodStatus);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(STATUS_FIELD_DESC);
      oprot.writeI32(struct.status);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class LiveUserViewTupleSchemeFactory implements SchemeFactory {
    public LiveUserViewTupleScheme getScheme() {
      return new LiveUserViewTupleScheme();
    }
  }

  private static class LiveUserViewTupleScheme extends TupleScheme<LiveUserView> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, LiveUserView struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetLiveid()) {
        optionals.set(0);
      }
      if (struct.isSetUid()) {
        optionals.set(1);
      }
      if (struct.isSetSnapshot()) {
        optionals.set(2);
      }
      if (struct.isSetViewed()) {
        optionals.set(3);
      }
      if (struct.isSetCreateTime()) {
        optionals.set(4);
      }
      if (struct.isSetVodStatus()) {
        optionals.set(5);
      }
      if (struct.isSetStatus()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetLiveid()) {
        oprot.writeI64(struct.liveid);
      }
      if (struct.isSetUid()) {
        oprot.writeI64(struct.uid);
      }
      if (struct.isSetSnapshot()) {
        oprot.writeString(struct.snapshot);
      }
      if (struct.isSetViewed()) {
        oprot.writeI32(struct.viewed);
      }
      if (struct.isSetCreateTime()) {
        oprot.writeI32(struct.createTime);
      }
      if (struct.isSetVodStatus()) {
        oprot.writeI32(struct.vodStatus);
      }
      if (struct.isSetStatus()) {
        oprot.writeI32(struct.status);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, LiveUserView struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.liveid = iprot.readI64();
        struct.setLiveidIsSet(true);
      }
      if (incoming.get(1)) {
        struct.uid = iprot.readI64();
        struct.setUidIsSet(true);
      }
      if (incoming.get(2)) {
        struct.snapshot = iprot.readString();
        struct.setSnapshotIsSet(true);
      }
      if (incoming.get(3)) {
        struct.viewed = iprot.readI32();
        struct.setViewedIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createTime = iprot.readI32();
        struct.setCreateTimeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.vodStatus = iprot.readI32();
        struct.setVodStatusIsSet(true);
      }
      if (incoming.get(6)) {
        struct.status = iprot.readI32();
        struct.setStatusIsSet(true);
      }
    }
  }

}

