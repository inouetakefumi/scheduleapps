package to.msn.wings.listmyadapter;

import java.util.Date;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Schedule extends RealmObject {
    @PrimaryKey
    public long id;     // 予定を見分けるためのIDが必要
    public String name;
    public Date date;   //予定の日付
    public String work;    // 勤怠
    public int workPosition;    // 勤怠(0:出社、1:テレワーク、2:休み）
    public String detail;   // 予定の詳細

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public int getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(int workPosition) {
        this.workPosition = workPosition;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
