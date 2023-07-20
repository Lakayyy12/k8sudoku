package onyoks.keights.sudokus

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class SudAdapter(private val items: List<SudokuModel>) :
    RecyclerView.Adapter<SudAdapter.ViewHolder>() {
    private lateinit var firestore: FirebaseFirestore
    private val valuesMap =
        HashMap<Int, Boolean>()
    private val args = Bundle()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sudClicker1: Button = itemView.findViewById(R.id.ckone)
        val sudClicker2: Button = itemView.findViewById(R.id.cktwo)
        val sudClicker3: Button = itemView.findViewById(R.id.ckthree)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sudoku_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        firestore = FirebaseFirestore.getInstance()

        fetchValue(holder, position)

        holder.sudClicker1.setOnClickListener {
            args.putString("title", "webview")
            val navController = Navigation.findNavController(holder.itemView).navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                args
            )
        }

        holder.sudClicker2.setOnClickListener {
            args.putString("title", "webview2")
            val navController = Navigation.findNavController(holder.itemView).navigate(
                R.id.action_FirstFragment_to_SecondFragment,
                args
            )
        }

        holder.sudClicker3.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PlaySudoku::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun fetchValue(holder: ViewHolder, position: Int) {
        firestore.collection("JULY")
            .document("SUDOKU")
            .get()
            .addOnSuccessListener { documentSnapshot: DocumentSnapshot? ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val isValueTrue = documentSnapshot.getBoolean("sudthree") ?: false
                    valuesMap[position] = isValueTrue
                    updateButtonText(holder, position)
                }
            }
            .addOnFailureListener { e ->
            }
    }

    private fun toggleValue(holder: ViewHolder, position: Int) {
        val currentValue = valuesMap[position] ?: false
        val newValue = !currentValue

        firestore.collection("JULY")
            .document("SUDOKU")
            .update("sudthree", newValue)
            .addOnSuccessListener {
                valuesMap[position] = newValue
                updateButtonText(holder, position)
            }
            .addOnFailureListener { e ->
            }
    }


    private fun updateButtonText(holder: ViewHolder, position: Int) {
        val isValueTrue = valuesMap[position] ?: false
        holder.sudClicker1.text = if (isValueTrue) {
            "ĐĂNG KÝ"
        } else {
            holder.itemView.context.getString(R.string.scfirst)
        }
        holder.sudClicker2.text = if (isValueTrue) {
            "ĐĂNG NHẬP"
        } else {
            holder.itemView.context.getString(R.string.scsecond)
        }
        holder.sudClicker3.text = if (isValueTrue) {
            "Chơi Sudoku"
        } else {
            holder.itemView.context.getString(R.string.scthird)
        }
    }
}