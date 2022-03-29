package com.example.todoapplication.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.todoapplication.R

class DetailViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)

        val bundle = intent.extras
        val description = bundle?.getString("description")
        val date = bundle?.getString("scheduledDate")
        val status = bundle?.getString("status")

        val result = "Task is $description to be completed on $date , currently it is  in $status status"

        val detailView = findViewById<TextView>(R.id.detailView)
        detailView.text = result
    }
}