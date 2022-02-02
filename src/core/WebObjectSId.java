package core;

import java.io.Serializable;

public abstract class WebObjectSId implements Serializable, IWebObjectSId, Cloneable {
    public static String ID = "id";

    private Long id;

    public WebObjectSId() {
        super();
    }

    public WebObjectSId(Long id) {
        this.id = id;
    }

    public WebObjectSId(int id) {
        this(Long.valueOf(id));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Class<? extends WebObjectSId> getTip() {
        return getClass();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WebObjectSId other = (WebObjectSId) obj;
        if (getId() == null)
            return super.equals(obj);
        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId() == null ? super.hashCode() : getId().hashCode();
    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer(super.toString());
        buff.append("id: ").append(getId());
        return buff.toString();
    }

}
