package com.example.homework5

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.homework5.Statistic
import java.lang.Exception

class StatisticDb(context: Context): SQLiteOpenHelper(context, "Db", null, 1) {

    companion object{
        const val SQL_CREATE_TABLE = "create table ${Statistic.TABLE_NAME} (" +
                "${Statistic.StatisticColumns.ID} integer primary key, "+
                "${Statistic.StatisticColumns.RUN_DISTANCE} real, " +
                "${Statistic.StatisticColumns.SWIM_DISTANCE} real, " +
                "${Statistic.StatisticColumns.CALORIES} real)"

        const val SQL_DROP_TABLE = "drop table if exists ${Statistic.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP_TABLE)
        onCreate(db)
    }

    fun insertStatistic(id: Int, runDistance: Double, swimDistance: Double, calories: Double){
        val cv = ContentValues().apply {
            put(Statistic.StatisticColumns.ID, id)
            put(Statistic.StatisticColumns.RUN_DISTANCE, runDistance)
            put(Statistic.StatisticColumns.SWIM_DISTANCE, swimDistance)
            put(Statistic.StatisticColumns.CALORIES, calories)
        }

        try{
            writableDatabase.insert(Statistic.TABLE_NAME, null, cv)
        }
        catch (exception: Exception){
            var x = exception
            val y = 2
        }
    }



    @SuppressLint("Range")
    fun selectAll(){
        val columns = arrayOf(
            Statistic.StatisticColumns.RUN_DISTANCE,
            Statistic.StatisticColumns.SWIM_DISTANCE,
            Statistic.StatisticColumns.CALORIES
        )

        val cursor = readableDatabase.query(Statistic.TABLE_NAME, columns, null, null,
            null, null, null)

        while (cursor.moveToNext()){
            val runDistance = cursor
                .getDouble(cursor.getColumnIndex(Statistic.StatisticColumns.RUN_DISTANCE))

            Log.d("x", "runDistance: ${runDistance}")

            val swimDistance = cursor
                .getDouble(cursor.getColumnIndex(Statistic.StatisticColumns.SWIM_DISTANCE))

            Log.d("x", "swimDistance: ${swimDistance}")

            val calories = cursor
                .getDouble(cursor.getColumnIndex(Statistic.StatisticColumns.CALORIES))

            Log.d("x", "calories: ${calories}")
        }

        cursor.close()
    }

    @SuppressLint("Range")
    fun getLatestId(): Int{
        val cursor = readableDatabase.rawQuery("select max(Id) from ${Statistic.TABLE_NAME}", null)

        cursor.moveToNext()
        val id = cursor.getInt(0)
        cursor.close()

        return id
    }

    fun getAvgRun(): Double{
        val cursor = readableDatabase.rawQuery("select round(avg(RunDistance), 2) as 'avg' from ${Statistic.TABLE_NAME}", null)

        if(!cursor.moveToNext())
            return 0.0

        val avg = cursor.getDouble(0)
        cursor.close()
        return avg
    }

    fun getAvgSwim(): Double{
        val cursor = readableDatabase.rawQuery("select round(avg(SwimDistance), 2) as 'avg' from ${Statistic.TABLE_NAME}", null)

        cursor.moveToNext()

        val avg = cursor.getDouble(0)
        cursor.close()
        return avg
    }

    fun getAvgCal(): Double{
        val cursor = readableDatabase.rawQuery("select round(avg(Calories), 2) as 'avg' from ${Statistic.TABLE_NAME}", null)

        cursor.moveToNext()

        val avg = cursor.getDouble(0)
        cursor.close()
        return avg
    }

    fun getTotalRun(): Double{
        val cursor = readableDatabase.rawQuery("select round(sum(RunDistance), 2) as 'total' from ${Statistic.TABLE_NAME}", null)

        cursor.moveToNext()

        val avg = cursor.getDouble(0)
        cursor.close()
        return avg
    }

}