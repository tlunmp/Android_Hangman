package com.ted_uy.hangman

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.gameover_dialog.*
import kotlinx.android.synthetic.main.gameover_dialog.view.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val bitmap: Bitmap = Bitmap.createBitmap(1000, 2000, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)
        var shapeDrawable: ShapeDrawable

        val hangmanModel = HangmanModel()

        hangmanModel.parseWordsFromWordsArray(hangmanModel.getWordsFromWordsArray())


        guessText.setText("${hangmanModel.replaceWordsWithUnderscore()}")



        quitButton.setOnClickListener {

            //AlertDialogBuilder
            val quitDialog = AlertDialog.Builder(this)
                .setMessage("Are you sure you want to quit?")

                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })

                .setNegativeButton("Cancel", null)
                val alert = quitDialog.show()

        }

        enterButton.setOnClickListener {

            //cannot be empty  only letters
            if (letterText.text.length > 1 || letterText.text.isEmpty()){
                letterText.setText("")
                Toast.makeText(this, "Must be only one letter and not empty", Toast.LENGTH_SHORT).show()
            } else {

                //if the duplication not found
                if(hangmanModel.checkIfDuplicate(letterText.text.toString()) == false){

                    hangmanModel.insertUsedLetter(letterText.text.toString())

                    val stringReturn = hangmanModel.checkIfValidInput(letterText.text.toString())

                    if (hangmanModel.getValidInput() == true) {
                        guessText.setText("${stringReturn}")


                        letterText.setText("")
                        submitHistory.setText(hangmanModel.usedLetters())

                        // wehen there is something wrong
                    } else {

                        //show the history
                        submitHistory.setText(hangmanModel.usedLetters())
                        letterText.setText("")
                        var left = 0
                        var top = 0
                        var right = 0
                        var bottom = 0

                        //draw the shape
                        if (hangmanModel.getError() == 1) {
                            // rectangle positions
                            left = 425
                            top = 250
                            right = 625
                            bottom = 570

                            // draw oval shape to canvas
                            shapeDrawable = ShapeDrawable(OvalShape())
                            shapeDrawable.setBounds(left, top, right, bottom)
                            shapeDrawable.getPaint().setColor(Color.parseColor("#009191"))
                            shapeDrawable.draw(canvas)


                        }

                        if (hangmanModel.getError() == 2) {
                            // body position
                            left = 480
                            top = 550
                            right = 580
                            bottom = 959


                            // draw rectangle shape to canvas
                            shapeDrawable = ShapeDrawable(RectShape())
                            shapeDrawable.setBounds(left, top, right, bottom)
                            shapeDrawable.getPaint().setColor(Color.parseColor("#009944"))
                            shapeDrawable.draw(canvas)
                        }

                        if (hangmanModel.getError() == 3) {
                            // left arm
                            left = 320
                            top = 550
                            right = 490
                            bottom = 650


                            // draw rectangle shape to canvas
                            shapeDrawable = ShapeDrawable(RectShape())
                            shapeDrawable.setBounds(left, top, right, bottom)
                            shapeDrawable.getPaint().setColor(Color.parseColor("#000000"))
                            shapeDrawable.draw(canvas)

                        }

                        if (hangmanModel.getError() == 4) {

                            // right arm
                            left = 580
                            top = 550
                            right = 750
                            bottom = 650


                            // draw rectangle shape to canvas
                            shapeDrawable = ShapeDrawable(RectShape())
                            shapeDrawable.setBounds(left, top, right, bottom)
                            shapeDrawable.getPaint().setColor(Color.parseColor("#000000"))
                            shapeDrawable.draw(canvas)
                        }

                        if (hangmanModel.getError() == 5) {

                            // left leg position
                            left = 410
                            top = 900
                            right = 480
                            bottom = 1250


                            // draw rectangle shape to canvas
                            shapeDrawable = ShapeDrawable(RectShape())
                            shapeDrawable.setBounds(left, top, right, bottom)
                            shapeDrawable.getPaint().setColor(Color.parseColor("blue"))
                            shapeDrawable.draw(canvas)
                        }

                        if (hangmanModel.getError() == 6) {
                            // right leg position
                            left = 580
                            top = 900
                            right = 650
                            bottom = 1250


                            // draw rectangle shape to canvas
                            shapeDrawable = ShapeDrawable(RectShape())
                            shapeDrawable.setBounds(left, top, right, bottom)
                            shapeDrawable.getPaint().setColor(Color.parseColor("blue"))
                            shapeDrawable.draw(canvas)


                            //if gameover show dialog box with 2 buttons if you want to play again or quit
                            val mDialogView = LayoutInflater.from(this).inflate(R.layout.gameover_dialog, null)

                            //AlertDialogBuilder
                            val mBuilder = AlertDialog.Builder(this)
                                .setView(mDialogView)
                                .setTitle("Game Over")


                            //set the gameover text the answer
                            mDialogView.gameover.setText("${hangmanModel.getWordsFromWordsArray()}")
                            //show dialog
                            val mAlertDialog = mBuilder.show()


                            //login button click of custom layout
                            mDialogView.continueButton.setOnClickListener {
                                hangmanModel.clearEverything()
                                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                                canvas.setBitmap(bitmap)
                                submitHistory.setText("")
                                 hangmanModel.parseWordsFromWordsArray(hangmanModel.getWordsFromWordsArray())
                                guessText.setText("${hangmanModel.replaceWordsWithUnderscore()}")
                                //dismiss dialog
                                mAlertDialog.dismiss()
                                //get text from EditTexts of custom layout
                            }


                            //quit button go back to main menu
                            mAlertDialog.quitButtons.setOnClickListener {
                                finish()
                                mAlertDialog.dismiss()
                            }
                        }

                        imageView.background = BitmapDrawable(getResources(), bitmap)
                    }
                } else {
                    //show if it is duplicated input
                    letterText.setText("")
                    Toast.makeText(this, "Duplicated Input", Toast.LENGTH_SHORT).show()
                }
            }

            //if player wins reset everything for new game
            if(hangmanModel.ifPlayerWin() == true){
                hangmanModel.clearEverything()
                submitHistory.setText("")
                hangmanModel.parseWordsFromWordsArray(hangmanModel.getWordsFromWordsArray())
                guessText.setText("${hangmanModel.replaceWordsWithUnderscore()}")
                canvas.drawColor(0, PorterDuff.Mode.CLEAR)
            }
        }
    }

    //alaerm notification when resume let it stuff
    override fun onResume() {
        super.onResume()
        val intentAlarm = Intent(this, AlarmReceiver::class.java)
        intentAlarm.putExtra("something", "off")
        println("calling Alarm receiver ")
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //set the notification to repeat every fifteen minutes
        val startTime = (1 * 60 * 1000).toLong() // 2 min
        // set unique id to the pending item, so we can call it when needed
        val pi = PendingIntent.getBroadcast(this, MainActivity.ALARM_ID, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            SystemClock.elapsedRealtime() + startTime, (60 * 1000).toLong(), pi
        )


    }

    //alarm when the program stop
    override fun onStop() {
        super.onStop()
        val intentAlarm = Intent(this, AlarmReceiver::class.java)
        intentAlarm.putExtra("something", "on")
        println("calling Alarm receiver ")
        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //set the notification to repeat every fifteen minutes
        val startTime = (1 * 60 * 1000).toLong() // 2 min
        // set unique id to the pending item, so we can call it when needed
        val pi = PendingIntent.getBroadcast(this, MainActivity.ALARM_ID, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            SystemClock.elapsedRealtime() + startTime, (60 * 1000).toLong(), pi
        )


    }
}
