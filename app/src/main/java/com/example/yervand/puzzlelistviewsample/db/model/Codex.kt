package com.example.yervand.puzzlelistviewsample.db.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Codex(
    @PrimaryKey var Id: Int = 0,
    var CreatedAt: Date = Date(),
    var Root: CodexEntity? = null,
    var NumberOfObjects: Long = 0
) : RealmObject()