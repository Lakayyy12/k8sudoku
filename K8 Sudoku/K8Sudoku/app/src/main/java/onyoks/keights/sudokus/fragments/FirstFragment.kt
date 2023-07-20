package onyoks.keights.sudokus.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import onyoks.keights.sudokus.R
import onyoks.keights.sudokus.SudAdapter
import onyoks.keights.sudokus.SudokuModel
import onyoks.keights.sudokus.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SudAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val data = listOf(
            SudokuModel("Item 1")
        )
        adapter = SudAdapter(data)
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}