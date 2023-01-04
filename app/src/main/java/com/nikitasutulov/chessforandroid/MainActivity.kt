package com.nikitasutulov.chessforandroid

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }

        val spinner: Spinner = findViewById(R.id.board_designs_spinner)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.board_designs,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val boardImageView: ImageView = findViewById(R.id.board_image_view)
        val drawableResource = when (parent!!.getItemAtPosition(position).toString()) {
            "1" -> R.drawable.board_plain_01
            "2" -> R.drawable.board_plain_02
            "3" -> R.drawable.board_plain_03
            else -> R.drawable.board_plain_04
        }
        boardImageView.setImageResource(drawableResource)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}

