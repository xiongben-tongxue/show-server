package one.show.common;

public class Ip
{
    private long min;
    private long max;
    private int nettype;
    private int country;

    public long getMin()
    {
        return this.min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return this.max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public int getNettype() {
        return this.nettype;
    }

    public void setNettype(int nettype) {
        this.nettype = nettype;
    }

    public int getCountry() {
        return this.country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getRe() {
        return this.nettype + "," + this.country;
    }

    public String toString()
    {
        return "min=" + this.min + "; max=" + this.max + "; re=" + getRe();
    }
}