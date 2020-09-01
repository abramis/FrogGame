package com.example.froggame

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    var score: Int=0
    var highscore:Int = 0
    var imageArray= ArrayList<ImageView>()
    var handler: Handler=Handler()
    var runnable: Runnable= Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences= getSharedPreferences("HIGH_SC", Context.MODE_PRIVATE)
        //highscore=sharedPreferences.getInt("HIGH_SCORE",0)
            score = 0
            imageArray = arrayListOf(
                imageView1,
                imageView2,
                imageView3,
                imageView4,
                imageView5,
                imageView6,
                imageView7,
                imageView8,
                imageView9
            )

            hideImages()
            object : CountDownTimer(10000, 1000) {
                override fun onFinish() {
                    timeText.text = "Time' s off"
                    handler.removeCallbacks(runnable)
                    for (image in imageArray) {
                        image.visibility = View.INVISIBLE
                    }
                    buttonPlay.visibility = View.VISIBLE
                    if (score>highscore) {
                        highscore = score
                        highSCtext.text = "HighScore: " + highscore
                        highSCtext.visibility = View.VISIBLE
                        val editor = sharedPreferences.edit()
                        editor.putInt("HighScore", highscore)
                        editor.apply()
                    }
                    else {
                        highSCtext.text = "HighScore: " + highscore
                        highSCtext.visibility = View.VISIBLE
                    }

                    buttonPlay.setOnClickListener {
                        val i = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)
                        i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(i)
                    }
                }

                override fun onTick(millisUntilFinished: Long) {
                    timeText.text = "Time: " + millisUntilFinished / 1000
                }

            }.start()

    }

    fun hideImages(){
        runnable = object : Runnable{
            override fun run() {
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val random= Random()
                val index= random.nextInt(8-0)
                imageArray[index].visibility= View.VISIBLE

                handler.postDelayed(runnable,300)
            }

        }
        handler.post(runnable)

    }

    fun increaseScore(view: View) {
        score++
        scoreText.text="Score: "+score
    }
}