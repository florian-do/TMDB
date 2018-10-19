package com.dof.myapplication.repository

class RepositoryFactory {
    var map : MutableMap<Class<Any>, Repository> = mutableMapOf()

    fun <T> get(c: Class<Any>) : Repository? {
        if (map.containsKey(c)) {
            return map.get(c)
        } else {
            val instance : Repository
            instance = c.newInstance() as Repository
            map.set(c, instance)
            return instance
        }
    }

    fun clear() {

    }
}