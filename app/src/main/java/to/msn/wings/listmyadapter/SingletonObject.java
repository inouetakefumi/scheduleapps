package to.msn.wings.listmyadapter;

public class SingletonObject {
    private static SingletonObject instance;

    private SingletonObject() {}

    public static SingletonObject getInstance() {
        if (instance == null) {
            instance = new SingletonObject();
        }
        return instance;
    }


}
