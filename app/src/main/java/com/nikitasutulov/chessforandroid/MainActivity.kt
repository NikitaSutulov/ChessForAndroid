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
        }
        catch (e: NullPointerException) {
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

        val controller = Controller(this)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val boardGrid: GridLayout = findViewById(R.id.board_grid)
        val drawableResource = when (parent!!.getItemAtPosition(position).toString()) {
            "1" -> R.drawable.board_1
            "2" -> R.drawable.board_2
            "3" -> R.drawable.board_3
            else -> R.drawable.board_4
        }
        boardGrid.setBackgroundResource(drawableResource)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}

