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

        val startButton: Button = findViewById(R.id.start_reset_button)
        startButton.setOnClickListener {
            initBoard()
        }
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

    fun initBoard() {
        val boardGrid: GridLayout = findViewById(R.id.board_grid)
        boardGrid.removeAllViews()
        boardGrid.alignmentMode = GridLayout.ALIGN_BOUNDS
        boardGrid.columnCount = 8
        boardGrid.rowCount = 8
        for (i in 0..7) {
            for (j in 0..7) {
                val cellButton = Button(this)
                cellButton.text = "$i $j"
                cellButton.textSize = 5f
                boardGrid.addView(cellButton, i * j)
                val param: GridLayout.LayoutParams = GridLayout.LayoutParams()
                param.height = boardGrid.width / 8
                param.width = boardGrid.width / 8
                param.bottomMargin = 1
                param.leftMargin = 0
                param.topMargin = 0
                param.rightMargin = 1
                param.columnSpec = GridLayout.spec(i)
                param.rowSpec = GridLayout.spec(j)
                cellButton.layoutParams = param
                cellButton.setBackgroundResource(R.drawable.b_queen)
            }
        }
    }
}

