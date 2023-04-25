package com.jmzd.ghazal.noteappmvi.ui.main.note

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmzd.ghazal.noteappmvi.data.model.NoteEntity
import com.jmzd.ghazal.noteappmvi.databinding.FragmentNoteBinding
import com.jmzd.ghazal.noteappmvi.utils.*
import com.jmzd.ghazal.noteappmvi.viewmodel.note.DetailIntent
import com.jmzd.ghazal.noteappmvi.viewmodel.note.DetailState
import com.jmzd.ghazal.noteappmvi.viewmodel.note.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {

    //binding
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var entity: NoteEntity

    //Other
    private val viewModel: DetailViewModel by viewModels()
    private var category = ""
    private var priority = ""
    private var noteId = 0
    private var type = ""
    private val categoriesList: MutableList<String> = mutableListOf()
    private val prioritiesList: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Bundle
        noteId = arguments?.getInt(BUNDLE_ID) ?: 0
        //Type
        type = if (noteId > 0) EDIT else NEW
        //init views
        binding?.apply {
            //Close
            closeImg.setOnClickListener { dismiss() }
            //Data
            lifecycleScope.launch {
                //Send
                viewModel.detailIntent.send(DetailIntent.SpinnersList)
                //Note detail
                if (type == EDIT) {
                    viewModel.detailIntent.send(DetailIntent.GetNoteDetail(noteId))
                }
                //Get data
                viewModel.state.collect { state : DetailState ->
                    when (state) {
                        is DetailState.Idle -> {}
                        is DetailState.SpinnersData -> {
                            //Category
                            categoriesList.addAll(state.categories)
                            categoriesSpinner.setupListWithAdapter(state.categories) {
                                category = it
                            }
                            //Priority
                            prioritiesList.addAll(state.priorities)
                            prioritySpinner.setupListWithAdapter(state.priorities) {
                                priority = it
                            }
                        }
                        is DetailState.SaveNote -> {
                            Log.d(TAG, "dismisssss ")
                            dismiss()
                        }
                        is DetailState.Error -> {
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                        is DetailState.ShowNoteDetail -> {
                            titleEdt.setText(state.enitiy.title)
                            descEdt.setText(state.enitiy.desc)
                            categoriesSpinner.setSelection(categoriesList.getIndexFromList(state.enitiy.category))
                            prioritySpinner.setSelection(prioritiesList.getIndexFromList(state.enitiy.priority))
                        }
                        is DetailState.NoteUpdated -> {
                            dismiss()
                        }
                    }
                }

            }
            //Save
            saveNote.setOnClickListener {
                val title = titleEdt.text.toString()
                val desc = descEdt.text.toString()
                entity.id = noteId
                entity.title = title
                entity.desc = desc
                entity.category = category
                entity.priority = priority

                lifecycleScope.launch {
                    if (type == NEW) {
                        viewModel.detailIntent.send(DetailIntent.SaveNote(entity))
                    } else {
                        viewModel.detailIntent.send(DetailIntent.UpdateNote(entity))
                    }
                }
            }

        }
    }

    override fun onStop() {
        super.onStop()
        _binding = null
    }

}