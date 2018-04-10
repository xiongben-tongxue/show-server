/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package one.show.stat.thrift.view;

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

public class SummaryStatView implements org.apache.thrift.TBase<SummaryStatView, SummaryStatView._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SummaryStatView");

  private static final org.apache.thrift.protocol.TField PV_FIELD_DESC = new org.apache.thrift.protocol.TField("pv", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PLAY_FIELD_DESC = new org.apache.thrift.protocol.TField("play", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField VIDEOS_FIELD_DESC = new org.apache.thrift.protocol.TField("videos", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField SHARE_FIELD_DESC = new org.apache.thrift.protocol.TField("share", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField LOGIN_FIELD_DESC = new org.apache.thrift.protocol.TField("login", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField REG_FIELD_DESC = new org.apache.thrift.protocol.TField("reg", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField GIFT_FIELD_DESC = new org.apache.thrift.protocol.TField("gift", org.apache.thrift.protocol.TType.DOUBLE, (short)7);
  private static final org.apache.thrift.protocol.TField RECHARGE_FIELD_DESC = new org.apache.thrift.protocol.TField("recharge", org.apache.thrift.protocol.TType.DOUBLE, (short)8);
  private static final org.apache.thrift.protocol.TField TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("time", org.apache.thrift.protocol.TType.I32, (short)9);
  private static final org.apache.thrift.protocol.TField LIVE_MAX_FIELD_DESC = new org.apache.thrift.protocol.TField("liveMax", org.apache.thrift.protocol.TType.I32, (short)10);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SummaryStatViewStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SummaryStatViewTupleSchemeFactory());
  }

  public int pv; // required
  public int play; // required
  public int videos; // required
  public int share; // required
  public int login; // required
  public int reg; // required
  public double gift; // required
  public double recharge; // required
  public int time; // required
  public int liveMax; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PV((short)1, "pv"),
    PLAY((short)2, "play"),
    VIDEOS((short)3, "videos"),
    SHARE((short)4, "share"),
    LOGIN((short)5, "login"),
    REG((short)6, "reg"),
    GIFT((short)7, "gift"),
    RECHARGE((short)8, "recharge"),
    TIME((short)9, "time"),
    LIVE_MAX((short)10, "liveMax");

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
        case 1: // PV
          return PV;
        case 2: // PLAY
          return PLAY;
        case 3: // VIDEOS
          return VIDEOS;
        case 4: // SHARE
          return SHARE;
        case 5: // LOGIN
          return LOGIN;
        case 6: // REG
          return REG;
        case 7: // GIFT
          return GIFT;
        case 8: // RECHARGE
          return RECHARGE;
        case 9: // TIME
          return TIME;
        case 10: // LIVE_MAX
          return LIVE_MAX;
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
  private static final int __PV_ISSET_ID = 0;
  private static final int __PLAY_ISSET_ID = 1;
  private static final int __VIDEOS_ISSET_ID = 2;
  private static final int __SHARE_ISSET_ID = 3;
  private static final int __LOGIN_ISSET_ID = 4;
  private static final int __REG_ISSET_ID = 5;
  private static final int __GIFT_ISSET_ID = 6;
  private static final int __RECHARGE_ISSET_ID = 7;
  private static final int __TIME_ISSET_ID = 8;
  private static final int __LIVEMAX_ISSET_ID = 9;
  private short __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PV, new org.apache.thrift.meta_data.FieldMetaData("pv", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PLAY, new org.apache.thrift.meta_data.FieldMetaData("play", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.VIDEOS, new org.apache.thrift.meta_data.FieldMetaData("videos", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.SHARE, new org.apache.thrift.meta_data.FieldMetaData("share", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LOGIN, new org.apache.thrift.meta_data.FieldMetaData("login", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REG, new org.apache.thrift.meta_data.FieldMetaData("reg", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.GIFT, new org.apache.thrift.meta_data.FieldMetaData("gift", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.RECHARGE, new org.apache.thrift.meta_data.FieldMetaData("recharge", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.TIME, new org.apache.thrift.meta_data.FieldMetaData("time", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.LIVE_MAX, new org.apache.thrift.meta_data.FieldMetaData("liveMax", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SummaryStatView.class, metaDataMap);
  }

  public SummaryStatView() {
  }

  public SummaryStatView(
    int pv,
    int play,
    int videos,
    int share,
    int login,
    int reg,
    double gift,
    double recharge,
    int time,
    int liveMax)
  {
    this();
    this.pv = pv;
    setPvIsSet(true);
    this.play = play;
    setPlayIsSet(true);
    this.videos = videos;
    setVideosIsSet(true);
    this.share = share;
    setShareIsSet(true);
    this.login = login;
    setLoginIsSet(true);
    this.reg = reg;
    setRegIsSet(true);
    this.gift = gift;
    setGiftIsSet(true);
    this.recharge = recharge;
    setRechargeIsSet(true);
    this.time = time;
    setTimeIsSet(true);
    this.liveMax = liveMax;
    setLiveMaxIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SummaryStatView(SummaryStatView other) {
    __isset_bitfield = other.__isset_bitfield;
    this.pv = other.pv;
    this.play = other.play;
    this.videos = other.videos;
    this.share = other.share;
    this.login = other.login;
    this.reg = other.reg;
    this.gift = other.gift;
    this.recharge = other.recharge;
    this.time = other.time;
    this.liveMax = other.liveMax;
  }

  public SummaryStatView deepCopy() {
    return new SummaryStatView(this);
  }

  @Override
  public void clear() {
    setPvIsSet(false);
    this.pv = 0;
    setPlayIsSet(false);
    this.play = 0;
    setVideosIsSet(false);
    this.videos = 0;
    setShareIsSet(false);
    this.share = 0;
    setLoginIsSet(false);
    this.login = 0;
    setRegIsSet(false);
    this.reg = 0;
    setGiftIsSet(false);
    this.gift = 0.0;
    setRechargeIsSet(false);
    this.recharge = 0.0;
    setTimeIsSet(false);
    this.time = 0;
    setLiveMaxIsSet(false);
    this.liveMax = 0;
  }

  public int getPv() {
    return this.pv;
  }

  public SummaryStatView setPv(int pv) {
    this.pv = pv;
    setPvIsSet(true);
    return this;
  }

  public void unsetPv() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PV_ISSET_ID);
  }

  /** Returns true if field pv is set (has been assigned a value) and false otherwise */
  public boolean isSetPv() {
    return EncodingUtils.testBit(__isset_bitfield, __PV_ISSET_ID);
  }

  public void setPvIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PV_ISSET_ID, value);
  }

  public int getPlay() {
    return this.play;
  }

  public SummaryStatView setPlay(int play) {
    this.play = play;
    setPlayIsSet(true);
    return this;
  }

  public void unsetPlay() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PLAY_ISSET_ID);
  }

  /** Returns true if field play is set (has been assigned a value) and false otherwise */
  public boolean isSetPlay() {
    return EncodingUtils.testBit(__isset_bitfield, __PLAY_ISSET_ID);
  }

  public void setPlayIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PLAY_ISSET_ID, value);
  }

  public int getVideos() {
    return this.videos;
  }

  public SummaryStatView setVideos(int videos) {
    this.videos = videos;
    setVideosIsSet(true);
    return this;
  }

  public void unsetVideos() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __VIDEOS_ISSET_ID);
  }

  /** Returns true if field videos is set (has been assigned a value) and false otherwise */
  public boolean isSetVideos() {
    return EncodingUtils.testBit(__isset_bitfield, __VIDEOS_ISSET_ID);
  }

  public void setVideosIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __VIDEOS_ISSET_ID, value);
  }

  public int getShare() {
    return this.share;
  }

  public SummaryStatView setShare(int share) {
    this.share = share;
    setShareIsSet(true);
    return this;
  }

  public void unsetShare() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SHARE_ISSET_ID);
  }

  /** Returns true if field share is set (has been assigned a value) and false otherwise */
  public boolean isSetShare() {
    return EncodingUtils.testBit(__isset_bitfield, __SHARE_ISSET_ID);
  }

  public void setShareIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SHARE_ISSET_ID, value);
  }

  public int getLogin() {
    return this.login;
  }

  public SummaryStatView setLogin(int login) {
    this.login = login;
    setLoginIsSet(true);
    return this;
  }

  public void unsetLogin() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LOGIN_ISSET_ID);
  }

  /** Returns true if field login is set (has been assigned a value) and false otherwise */
  public boolean isSetLogin() {
    return EncodingUtils.testBit(__isset_bitfield, __LOGIN_ISSET_ID);
  }

  public void setLoginIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LOGIN_ISSET_ID, value);
  }

  public int getReg() {
    return this.reg;
  }

  public SummaryStatView setReg(int reg) {
    this.reg = reg;
    setRegIsSet(true);
    return this;
  }

  public void unsetReg() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REG_ISSET_ID);
  }

  /** Returns true if field reg is set (has been assigned a value) and false otherwise */
  public boolean isSetReg() {
    return EncodingUtils.testBit(__isset_bitfield, __REG_ISSET_ID);
  }

  public void setRegIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REG_ISSET_ID, value);
  }

  public double getGift() {
    return this.gift;
  }

  public SummaryStatView setGift(double gift) {
    this.gift = gift;
    setGiftIsSet(true);
    return this;
  }

  public void unsetGift() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __GIFT_ISSET_ID);
  }

  /** Returns true if field gift is set (has been assigned a value) and false otherwise */
  public boolean isSetGift() {
    return EncodingUtils.testBit(__isset_bitfield, __GIFT_ISSET_ID);
  }

  public void setGiftIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __GIFT_ISSET_ID, value);
  }

  public double getRecharge() {
    return this.recharge;
  }

  public SummaryStatView setRecharge(double recharge) {
    this.recharge = recharge;
    setRechargeIsSet(true);
    return this;
  }

  public void unsetRecharge() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RECHARGE_ISSET_ID);
  }

  /** Returns true if field recharge is set (has been assigned a value) and false otherwise */
  public boolean isSetRecharge() {
    return EncodingUtils.testBit(__isset_bitfield, __RECHARGE_ISSET_ID);
  }

  public void setRechargeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RECHARGE_ISSET_ID, value);
  }

  public int getTime() {
    return this.time;
  }

  public SummaryStatView setTime(int time) {
    this.time = time;
    setTimeIsSet(true);
    return this;
  }

  public void unsetTime() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TIME_ISSET_ID);
  }

  /** Returns true if field time is set (has been assigned a value) and false otherwise */
  public boolean isSetTime() {
    return EncodingUtils.testBit(__isset_bitfield, __TIME_ISSET_ID);
  }

  public void setTimeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TIME_ISSET_ID, value);
  }

  public int getLiveMax() {
    return this.liveMax;
  }

  public SummaryStatView setLiveMax(int liveMax) {
    this.liveMax = liveMax;
    setLiveMaxIsSet(true);
    return this;
  }

  public void unsetLiveMax() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LIVEMAX_ISSET_ID);
  }

  /** Returns true if field liveMax is set (has been assigned a value) and false otherwise */
  public boolean isSetLiveMax() {
    return EncodingUtils.testBit(__isset_bitfield, __LIVEMAX_ISSET_ID);
  }

  public void setLiveMaxIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LIVEMAX_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PV:
      if (value == null) {
        unsetPv();
      } else {
        setPv((Integer)value);
      }
      break;

    case PLAY:
      if (value == null) {
        unsetPlay();
      } else {
        setPlay((Integer)value);
      }
      break;

    case VIDEOS:
      if (value == null) {
        unsetVideos();
      } else {
        setVideos((Integer)value);
      }
      break;

    case SHARE:
      if (value == null) {
        unsetShare();
      } else {
        setShare((Integer)value);
      }
      break;

    case LOGIN:
      if (value == null) {
        unsetLogin();
      } else {
        setLogin((Integer)value);
      }
      break;

    case REG:
      if (value == null) {
        unsetReg();
      } else {
        setReg((Integer)value);
      }
      break;

    case GIFT:
      if (value == null) {
        unsetGift();
      } else {
        setGift((Double)value);
      }
      break;

    case RECHARGE:
      if (value == null) {
        unsetRecharge();
      } else {
        setRecharge((Double)value);
      }
      break;

    case TIME:
      if (value == null) {
        unsetTime();
      } else {
        setTime((Integer)value);
      }
      break;

    case LIVE_MAX:
      if (value == null) {
        unsetLiveMax();
      } else {
        setLiveMax((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PV:
      return Integer.valueOf(getPv());

    case PLAY:
      return Integer.valueOf(getPlay());

    case VIDEOS:
      return Integer.valueOf(getVideos());

    case SHARE:
      return Integer.valueOf(getShare());

    case LOGIN:
      return Integer.valueOf(getLogin());

    case REG:
      return Integer.valueOf(getReg());

    case GIFT:
      return Double.valueOf(getGift());

    case RECHARGE:
      return Double.valueOf(getRecharge());

    case TIME:
      return Integer.valueOf(getTime());

    case LIVE_MAX:
      return Integer.valueOf(getLiveMax());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PV:
      return isSetPv();
    case PLAY:
      return isSetPlay();
    case VIDEOS:
      return isSetVideos();
    case SHARE:
      return isSetShare();
    case LOGIN:
      return isSetLogin();
    case REG:
      return isSetReg();
    case GIFT:
      return isSetGift();
    case RECHARGE:
      return isSetRecharge();
    case TIME:
      return isSetTime();
    case LIVE_MAX:
      return isSetLiveMax();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SummaryStatView)
      return this.equals((SummaryStatView)that);
    return false;
  }

  public boolean equals(SummaryStatView that) {
    if (that == null)
      return false;

    boolean this_present_pv = true;
    boolean that_present_pv = true;
    if (this_present_pv || that_present_pv) {
      if (!(this_present_pv && that_present_pv))
        return false;
      if (this.pv != that.pv)
        return false;
    }

    boolean this_present_play = true;
    boolean that_present_play = true;
    if (this_present_play || that_present_play) {
      if (!(this_present_play && that_present_play))
        return false;
      if (this.play != that.play)
        return false;
    }

    boolean this_present_videos = true;
    boolean that_present_videos = true;
    if (this_present_videos || that_present_videos) {
      if (!(this_present_videos && that_present_videos))
        return false;
      if (this.videos != that.videos)
        return false;
    }

    boolean this_present_share = true;
    boolean that_present_share = true;
    if (this_present_share || that_present_share) {
      if (!(this_present_share && that_present_share))
        return false;
      if (this.share != that.share)
        return false;
    }

    boolean this_present_login = true;
    boolean that_present_login = true;
    if (this_present_login || that_present_login) {
      if (!(this_present_login && that_present_login))
        return false;
      if (this.login != that.login)
        return false;
    }

    boolean this_present_reg = true;
    boolean that_present_reg = true;
    if (this_present_reg || that_present_reg) {
      if (!(this_present_reg && that_present_reg))
        return false;
      if (this.reg != that.reg)
        return false;
    }

    boolean this_present_gift = true;
    boolean that_present_gift = true;
    if (this_present_gift || that_present_gift) {
      if (!(this_present_gift && that_present_gift))
        return false;
      if (this.gift != that.gift)
        return false;
    }

    boolean this_present_recharge = true;
    boolean that_present_recharge = true;
    if (this_present_recharge || that_present_recharge) {
      if (!(this_present_recharge && that_present_recharge))
        return false;
      if (this.recharge != that.recharge)
        return false;
    }

    boolean this_present_time = true;
    boolean that_present_time = true;
    if (this_present_time || that_present_time) {
      if (!(this_present_time && that_present_time))
        return false;
      if (this.time != that.time)
        return false;
    }

    boolean this_present_liveMax = true;
    boolean that_present_liveMax = true;
    if (this_present_liveMax || that_present_liveMax) {
      if (!(this_present_liveMax && that_present_liveMax))
        return false;
      if (this.liveMax != that.liveMax)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(SummaryStatView other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    SummaryStatView typedOther = (SummaryStatView)other;

    lastComparison = Boolean.valueOf(isSetPv()).compareTo(typedOther.isSetPv());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPv()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pv, typedOther.pv);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlay()).compareTo(typedOther.isSetPlay());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlay()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.play, typedOther.play);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVideos()).compareTo(typedOther.isSetVideos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVideos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.videos, typedOther.videos);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShare()).compareTo(typedOther.isSetShare());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShare()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.share, typedOther.share);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLogin()).compareTo(typedOther.isSetLogin());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLogin()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.login, typedOther.login);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReg()).compareTo(typedOther.isSetReg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reg, typedOther.reg);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGift()).compareTo(typedOther.isSetGift());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGift()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.gift, typedOther.gift);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRecharge()).compareTo(typedOther.isSetRecharge());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecharge()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recharge, typedOther.recharge);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTime()).compareTo(typedOther.isSetTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.time, typedOther.time);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLiveMax()).compareTo(typedOther.isSetLiveMax());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLiveMax()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.liveMax, typedOther.liveMax);
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
    StringBuilder sb = new StringBuilder("SummaryStatView(");
    boolean first = true;

    sb.append("pv:");
    sb.append(this.pv);
    first = false;
    if (!first) sb.append(", ");
    sb.append("play:");
    sb.append(this.play);
    first = false;
    if (!first) sb.append(", ");
    sb.append("videos:");
    sb.append(this.videos);
    first = false;
    if (!first) sb.append(", ");
    sb.append("share:");
    sb.append(this.share);
    first = false;
    if (!first) sb.append(", ");
    sb.append("login:");
    sb.append(this.login);
    first = false;
    if (!first) sb.append(", ");
    sb.append("reg:");
    sb.append(this.reg);
    first = false;
    if (!first) sb.append(", ");
    sb.append("gift:");
    sb.append(this.gift);
    first = false;
    if (!first) sb.append(", ");
    sb.append("recharge:");
    sb.append(this.recharge);
    first = false;
    if (!first) sb.append(", ");
    sb.append("time:");
    sb.append(this.time);
    first = false;
    if (!first) sb.append(", ");
    sb.append("liveMax:");
    sb.append(this.liveMax);
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

  private static class SummaryStatViewStandardSchemeFactory implements SchemeFactory {
    public SummaryStatViewStandardScheme getScheme() {
      return new SummaryStatViewStandardScheme();
    }
  }

  private static class SummaryStatViewStandardScheme extends StandardScheme<SummaryStatView> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SummaryStatView struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PV
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pv = iprot.readI32();
              struct.setPvIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PLAY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.play = iprot.readI32();
              struct.setPlayIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VIDEOS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.videos = iprot.readI32();
              struct.setVideosIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SHARE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.share = iprot.readI32();
              struct.setShareIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // LOGIN
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.login = iprot.readI32();
              struct.setLoginIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // REG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.reg = iprot.readI32();
              struct.setRegIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // GIFT
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.gift = iprot.readDouble();
              struct.setGiftIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // RECHARGE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.recharge = iprot.readDouble();
              struct.setRechargeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.time = iprot.readI32();
              struct.setTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // LIVE_MAX
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.liveMax = iprot.readI32();
              struct.setLiveMaxIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SummaryStatView struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PV_FIELD_DESC);
      oprot.writeI32(struct.pv);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PLAY_FIELD_DESC);
      oprot.writeI32(struct.play);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(VIDEOS_FIELD_DESC);
      oprot.writeI32(struct.videos);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(SHARE_FIELD_DESC);
      oprot.writeI32(struct.share);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LOGIN_FIELD_DESC);
      oprot.writeI32(struct.login);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(REG_FIELD_DESC);
      oprot.writeI32(struct.reg);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(GIFT_FIELD_DESC);
      oprot.writeDouble(struct.gift);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(RECHARGE_FIELD_DESC);
      oprot.writeDouble(struct.recharge);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TIME_FIELD_DESC);
      oprot.writeI32(struct.time);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(LIVE_MAX_FIELD_DESC);
      oprot.writeI32(struct.liveMax);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SummaryStatViewTupleSchemeFactory implements SchemeFactory {
    public SummaryStatViewTupleScheme getScheme() {
      return new SummaryStatViewTupleScheme();
    }
  }

  private static class SummaryStatViewTupleScheme extends TupleScheme<SummaryStatView> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SummaryStatView struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPv()) {
        optionals.set(0);
      }
      if (struct.isSetPlay()) {
        optionals.set(1);
      }
      if (struct.isSetVideos()) {
        optionals.set(2);
      }
      if (struct.isSetShare()) {
        optionals.set(3);
      }
      if (struct.isSetLogin()) {
        optionals.set(4);
      }
      if (struct.isSetReg()) {
        optionals.set(5);
      }
      if (struct.isSetGift()) {
        optionals.set(6);
      }
      if (struct.isSetRecharge()) {
        optionals.set(7);
      }
      if (struct.isSetTime()) {
        optionals.set(8);
      }
      if (struct.isSetLiveMax()) {
        optionals.set(9);
      }
      oprot.writeBitSet(optionals, 10);
      if (struct.isSetPv()) {
        oprot.writeI32(struct.pv);
      }
      if (struct.isSetPlay()) {
        oprot.writeI32(struct.play);
      }
      if (struct.isSetVideos()) {
        oprot.writeI32(struct.videos);
      }
      if (struct.isSetShare()) {
        oprot.writeI32(struct.share);
      }
      if (struct.isSetLogin()) {
        oprot.writeI32(struct.login);
      }
      if (struct.isSetReg()) {
        oprot.writeI32(struct.reg);
      }
      if (struct.isSetGift()) {
        oprot.writeDouble(struct.gift);
      }
      if (struct.isSetRecharge()) {
        oprot.writeDouble(struct.recharge);
      }
      if (struct.isSetTime()) {
        oprot.writeI32(struct.time);
      }
      if (struct.isSetLiveMax()) {
        oprot.writeI32(struct.liveMax);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SummaryStatView struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(10);
      if (incoming.get(0)) {
        struct.pv = iprot.readI32();
        struct.setPvIsSet(true);
      }
      if (incoming.get(1)) {
        struct.play = iprot.readI32();
        struct.setPlayIsSet(true);
      }
      if (incoming.get(2)) {
        struct.videos = iprot.readI32();
        struct.setVideosIsSet(true);
      }
      if (incoming.get(3)) {
        struct.share = iprot.readI32();
        struct.setShareIsSet(true);
      }
      if (incoming.get(4)) {
        struct.login = iprot.readI32();
        struct.setLoginIsSet(true);
      }
      if (incoming.get(5)) {
        struct.reg = iprot.readI32();
        struct.setRegIsSet(true);
      }
      if (incoming.get(6)) {
        struct.gift = iprot.readDouble();
        struct.setGiftIsSet(true);
      }
      if (incoming.get(7)) {
        struct.recharge = iprot.readDouble();
        struct.setRechargeIsSet(true);
      }
      if (incoming.get(8)) {
        struct.time = iprot.readI32();
        struct.setTimeIsSet(true);
      }
      if (incoming.get(9)) {
        struct.liveMax = iprot.readI32();
        struct.setLiveMaxIsSet(true);
      }
    }
  }

}
