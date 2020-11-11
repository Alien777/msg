package pl.lasota.msg;


import java.io.Serializable;

/**
 * Online message wrapper
 * @param <M>
 */
public class Message<M> implements Serializable {

    private M m;
    private String from;
    private String[] to;

    public Message() {
    }

    public Message(M m, String from) {
        this(m, from, new String[0]);
    }

    public Message(M m, String from, String... to) {
        this.m = m;
        this.from = from;
        this.to = to;
    }

    public M getM() {
        return m;
    }

    public String getFrom() {
        return from;
    }

    public String[] getTo() {
        return to;
    }

    public void setM(M m) {
        this.m = m;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String[] to) {
        this.to = to;
    }
}
