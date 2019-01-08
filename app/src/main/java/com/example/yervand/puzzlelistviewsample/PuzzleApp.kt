package com.example.yervand.puzzlelistviewsample

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class PuzzleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .assetFile("Codex.realm")
                .build()
        )
    }
}