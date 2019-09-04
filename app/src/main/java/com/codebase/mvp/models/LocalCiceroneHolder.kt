package com.bss.codebase.models

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import java.util.HashMap

class LocalCiceroneHolder {
    private val containers: MutableMap<String, Cicerone<Router>> = HashMap()

    fun getCicerone(containerTag: String): Cicerone<Router>? {
        if (!containers.containsKey(containerTag)) {
            containers[containerTag] = Cicerone.create()
        }
        return containers[containerTag]
    }
}