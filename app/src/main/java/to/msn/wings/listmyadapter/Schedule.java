package to.msn.wings.listmyadapter;

import java.util.Date;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Schedule extends RealmObject {
    @PrimaryKey
    public long id;     // ID
    public Date date;   //日付
    public String work;    // 勤怠
    public String task;   // タスク
}

