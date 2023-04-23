package com.jmzd.ghazal.noteappmvi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jmzd.ghazal.noteappmvi.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvi.databinding.ActivityMainBinding
import com.jmzd.ghazal.noteappmvi.ui.main.note.NoteFragment
import com.jmzd.ghazal.noteappmvi.viewmodel.main.MainIntent
import com.jmzd.ghazal.noteappmvi.viewmodel.main.MainState
import com.jmzd.ghazal.noteappmvi.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var noteEntity: NoteEntity

    //Other
    private val viewModel: MainViewModel by viewModels()
    private var selectedItem = 0

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
            //Call intent
            viewModel.handleIntent(MainIntent.LoadAllNotes)
            //Data
            lifecycleScope.launch {
                //Get
                viewModel.state.collect { state : MainState ->
                    when (state) {
                        is MainState.Empty -> {
                            //emptyLay.visibility = View.VISIBLE
                            emptyLay.isVisible = true //visible
                            noteList.isVisible = false //gone
                        }
                        is MainState.LoadNotes -> {
                            emptyLay.isVisible = false
                            noteList.isVisible = true

                            noteAdapter.setData(state.list)
                            noteList.apply {
                                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                                adapter = noteAdapter
                            }

                        /*    noteAdapter.setOnItemClickListener { entity, type ->
                                when (type) {
                                    EDIT -> {
                                        viewModel.handleIntent(MainIntent.ClickToDetail(entity.id))
                                    }
                                    DELETE -> {
                                        noteEntity.id = entity.id
                                        noteEntity.title = entity.title
                                        noteEntity.desc = entity.desc
                                        noteEntity.category = entity.category
                                        noteEntity.priority = entity.priority
                                        viewModel.handleIntent(MainIntent.DeleteNote(noteEntity))
                                    }
                                }
                            }
                        }*/
                    /*    is MainState.DeleteNote -> {
                            //For example show toast, snack bar and more ...
                        }
                        is MainState.GoToDetail -> {
                            val noteFragment = NoteFragment()
                            val bundle = Bundle()
                            bundle.putInt(BUNDLE_ID, state.id)
                            noteFragment.arguments = bundle
                            noteFragment.show(supportFragmentManager, NoteFragment().tag)
                        }*/
                    }
                }
            }
        }
    }
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}