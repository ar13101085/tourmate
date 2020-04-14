package com.example.tourmate.utility;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

public class DB {
    private static Realm realm;
    public static Realm getRealm(){
        if(realm==null){
            RealmConfiguration realmConfiguration = new RealmConfiguration
                    .Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            try {
                realm = Realm.getInstance(realmConfiguration);
            } catch (RealmMigrationNeededException r) {
                Realm.deleteRealm(realmConfiguration);
                realm = Realm.getInstance(realmConfiguration);
            }
        }
        //Log.d("SIMILAR CHANNEL SIZE6","listItems "+(realm==null));
        return realm;
    }
}
