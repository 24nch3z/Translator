package ru.s4nchez.translator.presentation.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.s4nchez.translator.R
import ru.s4nchez.translator.presentation.view.translator.TranslatorFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TranslatorFragment())
                    .commit()
        }
    }
}
