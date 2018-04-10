/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package one.show.pay.thrift.view;

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

public class StockLogListView implements org.apache.thrift.TBase<StockLogListView, StockLogListView._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StockLogListView");

  private static final org.apache.thrift.protocol.TField STOCK_LOG_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("stockLogList", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("count", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StockLogListViewStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StockLogListViewTupleSchemeFactory());
  }

  public List<one.show.pay.thrift.view.StockLogView> stockLogList; // required
  public int count; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    STOCK_LOG_LIST((short)1, "stockLogList"),
    COUNT((short)2, "count");

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
        case 1: // STOCK_LOG_LIST
          return STOCK_LOG_LIST;
        case 2: // COUNT
          return COUNT;
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
  private static final int __COUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.STOCK_LOG_LIST, new org.apache.thrift.meta_data.FieldMetaData("stockLogList", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, one.show.pay.thrift.view.StockLogView.class))));
    tmpMap.put(_Fields.COUNT, new org.apache.thrift.meta_data.FieldMetaData("count", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StockLogListView.class, metaDataMap);
  }

  public StockLogListView() {
  }

  public StockLogListView(
    List<one.show.pay.thrift.view.StockLogView> stockLogList,
    int count)
  {
    this();
    this.stockLogList = stockLogList;
    this.count = count;
    setCountIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StockLogListView(StockLogListView other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetStockLogList()) {
      List<one.show.pay.thrift.view.StockLogView> __this__stockLogList = new ArrayList<one.show.pay.thrift.view.StockLogView>();
      for (one.show.pay.thrift.view.StockLogView other_element : other.stockLogList) {
        __this__stockLogList.add(new one.show.pay.thrift.view.StockLogView(other_element));
      }
      this.stockLogList = __this__stockLogList;
    }
    this.count = other.count;
  }

  public StockLogListView deepCopy() {
    return new StockLogListView(this);
  }

  @Override
  public void clear() {
    this.stockLogList = null;
    setCountIsSet(false);
    this.count = 0;
  }

  public int getStockLogListSize() {
    return (this.stockLogList == null) ? 0 : this.stockLogList.size();
  }

  public java.util.Iterator<one.show.pay.thrift.view.StockLogView> getStockLogListIterator() {
    return (this.stockLogList == null) ? null : this.stockLogList.iterator();
  }

  public void addToStockLogList(one.show.pay.thrift.view.StockLogView elem) {
    if (this.stockLogList == null) {
      this.stockLogList = new ArrayList<one.show.pay.thrift.view.StockLogView>();
    }
    this.stockLogList.add(elem);
  }

  public List<one.show.pay.thrift.view.StockLogView> getStockLogList() {
    return this.stockLogList;
  }

  public StockLogListView setStockLogList(List<one.show.pay.thrift.view.StockLogView> stockLogList) {
    this.stockLogList = stockLogList;
    return this;
  }

  public void unsetStockLogList() {
    this.stockLogList = null;
  }

  /** Returns true if field stockLogList is set (has been assigned a value) and false otherwise */
  public boolean isSetStockLogList() {
    return this.stockLogList != null;
  }

  public void setStockLogListIsSet(boolean value) {
    if (!value) {
      this.stockLogList = null;
    }
  }

  public int getCount() {
    return this.count;
  }

  public StockLogListView setCount(int count) {
    this.count = count;
    setCountIsSet(true);
    return this;
  }

  public void unsetCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COUNT_ISSET_ID);
  }

  /** Returns true if field count is set (has been assigned a value) and false otherwise */
  public boolean isSetCount() {
    return EncodingUtils.testBit(__isset_bitfield, __COUNT_ISSET_ID);
  }

  public void setCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COUNT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case STOCK_LOG_LIST:
      if (value == null) {
        unsetStockLogList();
      } else {
        setStockLogList((List<one.show.pay.thrift.view.StockLogView>)value);
      }
      break;

    case COUNT:
      if (value == null) {
        unsetCount();
      } else {
        setCount((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case STOCK_LOG_LIST:
      return getStockLogList();

    case COUNT:
      return Integer.valueOf(getCount());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case STOCK_LOG_LIST:
      return isSetStockLogList();
    case COUNT:
      return isSetCount();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StockLogListView)
      return this.equals((StockLogListView)that);
    return false;
  }

  public boolean equals(StockLogListView that) {
    if (that == null)
      return false;

    boolean this_present_stockLogList = true && this.isSetStockLogList();
    boolean that_present_stockLogList = true && that.isSetStockLogList();
    if (this_present_stockLogList || that_present_stockLogList) {
      if (!(this_present_stockLogList && that_present_stockLogList))
        return false;
      if (!this.stockLogList.equals(that.stockLogList))
        return false;
    }

    boolean this_present_count = true;
    boolean that_present_count = true;
    if (this_present_count || that_present_count) {
      if (!(this_present_count && that_present_count))
        return false;
      if (this.count != that.count)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(StockLogListView other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    StockLogListView typedOther = (StockLogListView)other;

    lastComparison = Boolean.valueOf(isSetStockLogList()).compareTo(typedOther.isSetStockLogList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStockLogList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stockLogList, typedOther.stockLogList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCount()).compareTo(typedOther.isSetCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.count, typedOther.count);
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
    StringBuilder sb = new StringBuilder("StockLogListView(");
    boolean first = true;

    sb.append("stockLogList:");
    if (this.stockLogList == null) {
      sb.append("null");
    } else {
      sb.append(this.stockLogList);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("count:");
    sb.append(this.count);
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

  private static class StockLogListViewStandardSchemeFactory implements SchemeFactory {
    public StockLogListViewStandardScheme getScheme() {
      return new StockLogListViewStandardScheme();
    }
  }

  private static class StockLogListViewStandardScheme extends StandardScheme<StockLogListView> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StockLogListView struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // STOCK_LOG_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.stockLogList = new ArrayList<one.show.pay.thrift.view.StockLogView>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  one.show.pay.thrift.view.StockLogView _elem2; // required
                  _elem2 = new one.show.pay.thrift.view.StockLogView();
                  _elem2.read(iprot);
                  struct.stockLogList.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setStockLogListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.count = iprot.readI32();
              struct.setCountIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StockLogListView struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.stockLogList != null) {
        oprot.writeFieldBegin(STOCK_LOG_LIST_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.stockLogList.size()));
          for (one.show.pay.thrift.view.StockLogView _iter3 : struct.stockLogList)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(COUNT_FIELD_DESC);
      oprot.writeI32(struct.count);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StockLogListViewTupleSchemeFactory implements SchemeFactory {
    public StockLogListViewTupleScheme getScheme() {
      return new StockLogListViewTupleScheme();
    }
  }

  private static class StockLogListViewTupleScheme extends TupleScheme<StockLogListView> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StockLogListView struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetStockLogList()) {
        optionals.set(0);
      }
      if (struct.isSetCount()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetStockLogList()) {
        {
          oprot.writeI32(struct.stockLogList.size());
          for (one.show.pay.thrift.view.StockLogView _iter4 : struct.stockLogList)
          {
            _iter4.write(oprot);
          }
        }
      }
      if (struct.isSetCount()) {
        oprot.writeI32(struct.count);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StockLogListView struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.stockLogList = new ArrayList<one.show.pay.thrift.view.StockLogView>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            one.show.pay.thrift.view.StockLogView _elem7; // required
            _elem7 = new one.show.pay.thrift.view.StockLogView();
            _elem7.read(iprot);
            struct.stockLogList.add(_elem7);
          }
        }
        struct.setStockLogListIsSet(true);
      }
      if (incoming.get(1)) {
        struct.count = iprot.readI32();
        struct.setCountIsSet(true);
      }
    }
  }

}
