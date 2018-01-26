package io.areguig.kxj

import org.jooq.DSLContext
import org.jooq.impl.DSL




data class Character(val first_name:String, val last_name: String, val show_name: String)

fun getCharacters(dsl: DSLContext):List<Character>{

    return  dsl.select(   DSL.field("first_name"), DSL.field("last_name"), DSL.field("show_name"))
                .from(DSL.table("characters"))
            .fetch()
            .map {

                    Character(it["first_name"].toString(),it["last_name"].toString(),it["show_name"].toString())
                }
}
