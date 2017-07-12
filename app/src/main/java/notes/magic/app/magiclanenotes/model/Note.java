package notes.magic.app.magiclanenotes.model;

/**
 * Created by pankaj on 7/12/17.
 */

public class Note {
    private String title;
    private String note;
    private String imgUrl;

    private long creationTime = 0;
    private long updateTime = 0;

    public Note(String title, String note) {
        setTitle(title);
        setNote(note);
        setImgUrl(imgUrl);
        setCreationAndUpdateTime();
    }

    public Note(String title, String note, long creationTime) {
        setTitle(title);
        setNote(note);
        setImgUrl(imgUrl);
        setCreationTime(creationTime);
        setCreationAndUpdateTime();
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationAndUpdateTime() {
        if (getCreationTime() == 0) {
            this.creationTime = System.currentTimeMillis();
        } else {
            updateTime = System.currentTimeMillis();
        }
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
