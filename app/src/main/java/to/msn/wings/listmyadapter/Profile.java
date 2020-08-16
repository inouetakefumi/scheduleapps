package to.msn.wings.listmyadapter;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject {

    public Profile() {
        Number max = Realm.getDefaultInstance().where(Profile.class)
                .max("id");
        id = max == null ? 1L : (max.longValue() + 1L);
    }

    @PrimaryKey
    public long id;     // スケジュール// を見分けるためのIDが必要
    public String name;
    public String kana;
    public String phonenumber;
    public String mail;
    public String part;

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

    public String getKana() {
        return kana;
    }

    public void setKana(String name) {
        this.kana = kana;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public static void insertOrUpdate(final Profile profile) {
        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(profile);
            }
        });
    }
}