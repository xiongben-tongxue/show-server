/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package one.show.manage.thrift.view;

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

public class AdminRoleFuncView implements org.apache.thrift.TBase<AdminRoleFuncView, AdminRoleFuncView._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AdminRoleFuncView");

  private static final org.apache.thrift.protocol.TField ROLE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("roleId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField FUNC_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("funcId", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AdminRoleFuncViewStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AdminRoleFuncViewTupleSchemeFactory());
  }

  public int roleId; // required
  public String funcId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ROLE_ID((short)1, "roleId"),
    FUNC_ID((short)2, "funcId");

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
        case 1: // ROLE_ID
          return ROLE_ID;
        case 2: // FUNC_ID
          return FUNC_ID;
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
  private static final int __ROLEID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ROLE_ID, new org.apache.thrift.meta_data.FieldMetaData("roleId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.FUNC_ID, new org.apache.thrift.meta_data.FieldMetaData("funcId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AdminRoleFuncView.class, metaDataMap);
  }

  public AdminRoleFuncView() {
  }

  public AdminRoleFuncView(
    int roleId,
    String funcId)
  {
    this();
    this.roleId = roleId;
    setRoleIdIsSet(true);
    this.funcId = funcId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AdminRoleFuncView(AdminRoleFuncView other) {
    __isset_bitfield = other.__isset_bitfield;
    this.roleId = other.roleId;
    if (other.isSetFuncId()) {
      this.funcId = other.funcId;
    }
  }

  public AdminRoleFuncView deepCopy() {
    return new AdminRoleFuncView(this);
  }

  @Override
  public void clear() {
    setRoleIdIsSet(false);
    this.roleId = 0;
    this.funcId = null;
  }

  public int getRoleId() {
    return this.roleId;
  }

  public AdminRoleFuncView setRoleId(int roleId) {
    this.roleId = roleId;
    setRoleIdIsSet(true);
    return this;
  }

  public void unsetRoleId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ROLEID_ISSET_ID);
  }

  /** Returns true if field roleId is set (has been assigned a value) and false otherwise */
  public boolean isSetRoleId() {
    return EncodingUtils.testBit(__isset_bitfield, __ROLEID_ISSET_ID);
  }

  public void setRoleIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ROLEID_ISSET_ID, value);
  }

  public String getFuncId() {
    return this.funcId;
  }

  public AdminRoleFuncView setFuncId(String funcId) {
    this.funcId = funcId;
    return this;
  }

  public void unsetFuncId() {
    this.funcId = null;
  }

  /** Returns true if field funcId is set (has been assigned a value) and false otherwise */
  public boolean isSetFuncId() {
    return this.funcId != null;
  }

  public void setFuncIdIsSet(boolean value) {
    if (!value) {
      this.funcId = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ROLE_ID:
      if (value == null) {
        unsetRoleId();
      } else {
        setRoleId((Integer)value);
      }
      break;

    case FUNC_ID:
      if (value == null) {
        unsetFuncId();
      } else {
        setFuncId((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ROLE_ID:
      return Integer.valueOf(getRoleId());

    case FUNC_ID:
      return getFuncId();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ROLE_ID:
      return isSetRoleId();
    case FUNC_ID:
      return isSetFuncId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AdminRoleFuncView)
      return this.equals((AdminRoleFuncView)that);
    return false;
  }

  public boolean equals(AdminRoleFuncView that) {
    if (that == null)
      return false;

    boolean this_present_roleId = true;
    boolean that_present_roleId = true;
    if (this_present_roleId || that_present_roleId) {
      if (!(this_present_roleId && that_present_roleId))
        return false;
      if (this.roleId != that.roleId)
        return false;
    }

    boolean this_present_funcId = true && this.isSetFuncId();
    boolean that_present_funcId = true && that.isSetFuncId();
    if (this_present_funcId || that_present_funcId) {
      if (!(this_present_funcId && that_present_funcId))
        return false;
      if (!this.funcId.equals(that.funcId))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(AdminRoleFuncView other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    AdminRoleFuncView typedOther = (AdminRoleFuncView)other;

    lastComparison = Boolean.valueOf(isSetRoleId()).compareTo(typedOther.isSetRoleId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRoleId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.roleId, typedOther.roleId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFuncId()).compareTo(typedOther.isSetFuncId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFuncId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.funcId, typedOther.funcId);
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
    StringBuilder sb = new StringBuilder("AdminRoleFuncView(");
    boolean first = true;

    sb.append("roleId:");
    sb.append(this.roleId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("funcId:");
    if (this.funcId == null) {
      sb.append("null");
    } else {
      sb.append(this.funcId);
    }
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

  private static class AdminRoleFuncViewStandardSchemeFactory implements SchemeFactory {
    public AdminRoleFuncViewStandardScheme getScheme() {
      return new AdminRoleFuncViewStandardScheme();
    }
  }

  private static class AdminRoleFuncViewStandardScheme extends StandardScheme<AdminRoleFuncView> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AdminRoleFuncView struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ROLE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.roleId = iprot.readI32();
              struct.setRoleIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FUNC_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.funcId = iprot.readString();
              struct.setFuncIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, AdminRoleFuncView struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ROLE_ID_FIELD_DESC);
      oprot.writeI32(struct.roleId);
      oprot.writeFieldEnd();
      if (struct.funcId != null) {
        oprot.writeFieldBegin(FUNC_ID_FIELD_DESC);
        oprot.writeString(struct.funcId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AdminRoleFuncViewTupleSchemeFactory implements SchemeFactory {
    public AdminRoleFuncViewTupleScheme getScheme() {
      return new AdminRoleFuncViewTupleScheme();
    }
  }

  private static class AdminRoleFuncViewTupleScheme extends TupleScheme<AdminRoleFuncView> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AdminRoleFuncView struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetRoleId()) {
        optionals.set(0);
      }
      if (struct.isSetFuncId()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetRoleId()) {
        oprot.writeI32(struct.roleId);
      }
      if (struct.isSetFuncId()) {
        oprot.writeString(struct.funcId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AdminRoleFuncView struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.roleId = iprot.readI32();
        struct.setRoleIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.funcId = iprot.readString();
        struct.setFuncIdIsSet(true);
      }
    }
  }

}
