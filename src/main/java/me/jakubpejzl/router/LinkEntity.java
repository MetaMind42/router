package me.jakubpejzl.router;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "LINK", schema="WKSP_LINKS")
public class LinkEntity {
    @Id
    private int id;
    private String path;
    private String url;

    public LinkEntity() {}

    public LinkEntity(String path, String url) {
        this.path = path;
        this.url = url;
    }

    public int getId() { return id; }
    public String getPath() { return path; }
    public String getUrl() { return url; }
    public void setId(int id) { this.id = id; }
    public void setPath(String path) { this.path = path; }
    public void setUrl(String url) { this.url = url; }
}
