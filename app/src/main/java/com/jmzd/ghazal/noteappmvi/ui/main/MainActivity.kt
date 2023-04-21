package com.jmzd.ghazal.noteappmvi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jmzd.ghazal.noteappmvi.databinding.ActivityMainBinding
import com.jmzd.ghazal.noteappmvi.ui.main.note.NoteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //InitViews
        binding?.apply {
            //Toolbar
            setSupportActionBar(notesToolbar)
            //Add
            addNoteBtn.setOnClickListener { NoteFragment().show(supportFragmentManager, NoteFragment().tag) }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}