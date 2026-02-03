package org.example.pojos;

public class Root{
    public String id;
    public String name;
    public String desc;
    public Object descData;
    public boolean closed;
    public String idOrganization;
    public Object idEnterprise;
    public boolean pinned;
    public String url;
    public String shortUrl;
    public Prefs prefs;
    public LabelNames labelNames;
    public Limits limits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
