package com.example.homework5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var statisticHelper:StatisticDb

    private lateinit var runView: TextView
    private lateinit var swimView: TextView
    private lateinit var calView: TextView
    private lateinit var totalRunView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runView = findViewById<TextView>(R.id.avgRun)
        swimView = findViewById<TextView>(R.id.avgSwim)
        calView = findViewById<TextView>(R.id.avgCal)
        totalRunView = findViewById<TextView>(R.id.totalRun)
        statisticHelper = StatisticDb(this)
        statisticHelper.selectAll()

        loadStatistics()

        findViewById<Button>(R.id.btn).setOnClickListener{
            insertStatistic(
                findViewById<TextInputEditText>(R.id.run).text.toString().toDouble(),
                findViewById<TextInputEditText>(R.id.swim).text.toString().toDouble(),
                findViewById<TextInputEditText>(R.id.cal).text.toString().toDouble())
        }
    }

    fun insertStatistic(runStat: Double, swimStat: Double, calStat: Double){
        if(runStat < 0 || swimStat < 0 || calStat < 0)
            return

        statisticHelper.insertStatistic(statisticHelper.getLatestId() + 1, runStat, swimStat, calStat)
        clear()
        loadStatistics()
    }

    fun clear(){
        findViewById<TextInputEditText>(R.id.run).setText("")
        findViewById<TextInputEditText>(R.id.swim).setText("")
        findViewById<TextInputEditText>(R.id.cal).setText("")
    }
    fun loadStatistics(){
        runView.setText(statisticHelper.getAvgRun().toString())
        swimView.setText(statisticHelper.getAvgSwim().toString())
        calView.setText(statisticHelper.getAvgCal().toString())
        totalRunView.setText(statisticHelper.getTotalRun().toString())
    }

}