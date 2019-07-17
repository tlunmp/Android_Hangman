package com.ted_uy.hangman

import android.text.Editable
import android.util.Log
import android.widget.Toast
import java.lang.StringBuilder

class HangmanModel {
    private val wordsArray = arrayOf("Head Over Heels",
                                     "Down For The Count",
                                     "Roll With the Punches",
                                     "Back To the Drawing Board",
                                     "Shot In the Dark",
                                     "Down And Out",
                                     "Jaws of Death",
                                     "Fit as a Fiddle",
                                     "Go Out On a Limb",
                                     "Raining Cats and Dogs"
                                    )

    private var isValidInput : Boolean = false
    private val arrayParsed = arrayListOf<String>()
    private var underscoreParsed = arrayListOf<String>()
    private var inputWrongList = arrayListOf<String>()
    private var correctCounter = 0
    private var errorCounter = 0
    private var letterCounterForCorrect = 0
    private var newGame = false
    private var allCorrectCounter = 0


    fun getWordsArray():Array<String>{
        return  this.wordsArray
    }


    fun getParsedWord(): ArrayList<String> {
        return this.arrayParsed
    }

    //randomize the array list for wors
    private fun randomizeTheWordsArrayReturnIndex(): Int{
        val randomInteger = (0..wordsArray.size-1).shuffled().first()
        return randomInteger

    }

    //get words from the array
    fun getWordsFromWordsArray() : String{
        val indexPosition = randomizeTheWordsArrayReturnIndex()
       // Log.e("MSG", "${indexPosition}")
        return wordsArray[indexPosition]
    }

    //parse the word from array and add to arrayparsed
    fun parseWordsFromWordsArray(guessWord: String){
        for (i in 0..guessWord.length-1) {
            this.arrayParsed.add(i, "${guessWord[i]}")
            //Log.e("msgs","${arrayParsed[i]}")
        }

    }


    //creating underscore
    fun replaceWordsWithUnderscore(): String{

        val underscoreBuilder = StringBuilder()

        //this function will check the letters and change it _ or space if not letter
        for(i in 0..this.arrayParsed.size-1){
            if (this.arrayParsed[i] >= "a" && this.arrayParsed[i] <= "z" || this.arrayParsed[i] >= "A" && this.arrayParsed[i] <= "Z"){
                underscoreBuilder.append("_ ")
                letterCounterForCorrect++
                this.underscoreParsed.add(i, "_")
             //   Log.e("letter","${underscoreParsed[i]}")
            } else {
                underscoreBuilder.append(" ")
                this.underscoreParsed.add(i, " ")
           //     Log.e("notletter","${underscoreParsed[i]}")
            }
        }

        val stringUnderscore = underscoreBuilder.toString()

        //Log.e("all","${underscoreParsed.toString()}")
        // Log.e("letter","${stringUnderscore}")

        return stringUnderscore
    }



    //checking if it valids is corect
    fun checkIfValidInput(input: String): String {
        val answerBuilder = StringBuilder()

        for(i in 0..this.arrayParsed.size-1){
            if (input == this.arrayParsed[i].toUpperCase() || input == this.arrayParsed[i].toLowerCase()){
                this.underscoreParsed.set(i,"${this.arrayParsed[i]}")
                answerBuilder.append("${underscoreParsed[i]} ")

                allCorrectCounter++
                correctCounter++

            } else {

                answerBuilder.append("${underscoreParsed[i]} ")
            }
        }


        //this will check if all the answer are input correct
        if (correctCounter > 0) {
            this.isValidInput = true

        //    Log.e("msges","all ${allCorrectCounter} between ${letterCounterForCorrect}")
            if(allCorrectCounter == this.letterCounterForCorrect) {
                this.newGame = true
              //  Log.e("MSG", "${this.newGame}")
            }

            correctCounter = 0;
        } else {
            this.isValidInput = false
            errorCounter++;
        }

       // Log.e("all","this is ${this.underscoreParsed}")

        val answerFillTextBox = answerBuilder.toString()
        return answerFillTextBox
    }

    //build the used letters
    fun usedLetters() : String{
        val usedLetterBuilder = StringBuilder()

        for (i in 0 ..inputWrongList.size-1) {

            usedLetterBuilder.append("${inputWrongList[i]} ")
           // Log.e("test", "${i} : ${this.inputWrongList[i]}")
        }

        val usedletter = usedLetterBuilder.toString()

        return usedletter
    }

    //check if the input is duplicated
    fun checkIfDuplicate(duplicate: String): Boolean{

        for (i in 0.. inputWrongList.size-1){
            if (duplicate == inputWrongList[i]){
                //Log.e("msg", "there is duplicate ${inputWrongList[i]}")
                return true
            }
        }

        return false
    }

    fun insertUsedLetter(input : String){
        this.inputWrongList.add(input)
    }

    fun getValidInput(): Boolean{
       return this.isValidInput
    }

    fun getError(): Int {
        return  this.errorCounter
    }

    fun ifPlayerWin() : Boolean{
        return this.newGame
    }

    //clear everything for new game
    fun clearEverything(){
        this.arrayParsed.clear()
        this.underscoreParsed.clear()
        this.inputWrongList.clear()
        errorCounter = 0
        correctCounter = 0
        allCorrectCounter = 0
        letterCounterForCorrect = 0
        newGame = false
    }
}