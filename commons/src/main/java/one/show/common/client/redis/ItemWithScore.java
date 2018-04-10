package one.show.common.client.redis;

/**
 * Created by Haliaeetus leucocephalus on 18/1/27.
 */
public class ItemWithScore<T> {
    public T item;
    Double score;

    public ItemWithScore(T item, Double score) {
        this.item = item;
        this.score = score;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public ItemWithScore(byte[] bytes, Double score) {
        this.item = (T) SerializeUtil.unserialize(bytes);
        this.score = score;
    }

    public T getItem() {

        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }
}
