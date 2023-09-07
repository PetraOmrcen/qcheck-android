package com.example.myapplication

import android.app.Application
import java.util.*

class Global : Application() {
    companion object {
        var fragmentStack: Stack<Int> = Stack()
    }
}