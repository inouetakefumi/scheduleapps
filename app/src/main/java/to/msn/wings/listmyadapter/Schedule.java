package to.msn.wings.listmyadapter;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Schedule extends RealmObject {
    @PrimaryKey
    public long id;     // 予定を見分けるためのIDが必要
    public String name;
    public Date date;   //予定の日付
    public String work;    // 勤怠
    public int workPosition;     //
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

    public int getWorkPosition () {
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


    public static List<Schedule> findAll() {
        return Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(Schedule.class).findAll());
}

    public static List<Schedule> findAllBy(String name) {
        return Realm.getDefaultInstance().copyFromRealm(Realm.getDefaultInstance().where(Schedule.class).equalTo("name", name).findAll());
    }

    public static Schedule findBy(long id) {
        Schedule result = Realm.getDefaultInstance().where(Schedule.class).equalTo("id", id).findFirst();
        if (result == null) {
            return null;
        }
        return Realm.getDefaultInstance().copyFromRealm(result);
    }

    public static void insertOrUpdate(final Schedule schedule) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(schedule);
            }
        });
    }

    public static void insertOrUpdate(final List<Schedule> list) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Schedule schedule : list) {
                    realm.insertOrUpdate(schedule);
                }
            }
        });
    }
}
