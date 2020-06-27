package to.msn.wings.listmyadapter;

class ListItem {
    private long id = 0;
    private String title = null;
    private String tag = null;

    long getId() { return id; }
    String getTitle() { return title; }
    String getTag() { return tag; }

    void setId(long id) { this.id = id; }
    void setTitle(String title) { this.title = title; }
    void setTag(String tag) { this.tag = tag; }
}
