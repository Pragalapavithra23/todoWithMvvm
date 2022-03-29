package com.example.todoapplication.ui.main.adaptor

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapplication.R
import com.example.todoapplication.data.model.Todo
import com.example.todoapplication.ui.main.view.DetailViewActivity
import java.util.*

/*
this adaptor extends RecyclerViewAdaptor and sets the item to view and bind the view to recyclerview
 */
class TodoAdaptor(var todos: List<Todo>) : RecyclerView.Adapter<TodoAdaptor.TodoViewHolder>() {
    /**
     * Holds the views for holding title and checkbox
     */
    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * sets the view to display items i.e., inflating the layout file used for toDo to java file
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    /**
    Binds the listItems to widgets such as textView and checkbox view
     */
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.itemView.apply {
            val tvTitle = findViewById<TextView>(R.id.tvTitle)
            val cbDone = findViewById<CheckBox>(R.id.cbDone)
            val dateView = findViewById<TextView>(R.id.dateView)
            val position = holder.absoluteAdapterPosition
            tvTitle.text = todos[position].description
            cbDone.isChecked = todos[position].status.equals("COMPLETED")

            dateView.text = convertDate(todos[position].scheduledDate)
            cbDone.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    if (cbDone.isChecked) {
                        println("size $position ...${todos.size - 1}")
                        moveItem(holder.absoluteAdapterPosition, todos.size - 1)
                    }
                }

            })

            tvTitle.run {
                text = todos[position].description
                cbDone.isChecked = todos[position].status.equals("COMPLETED")

                dateView.text = convertDate(todos[position].scheduledDate)
                cbDone.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View?) {
                        if (!cbDone.isChecked) return
                        moveItem(holder.absoluteAdapterPosition, todos.size - 1)
                    }

                })

                setOnClickListener {
                    Intent(context, DetailViewActivity::class.java).apply {
                        val bundle = Bundle()
                        bundle.putString("description", todos[position].description)
                        bundle.putInt("id", todos[position].id)
                        bundle.putString(
                            "scheduledDate",
                            convertDate(todos[position].scheduledDate)
                        )
                        bundle.putString("status", todos[position].status)
                        this.putExtras(bundle)
                        startActivity(context, this, bundle)

                    }
                }
            }

        }
    }


    private fun convertDate(dateInMilliseconds: String): String {
        return DateFormat.format("MM/dd", dateInMilliseconds.toLong()).toString()
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        Collections.swap(todos, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }
}