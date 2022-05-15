package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {




    private var _score = 0
    val score : Int
    get() = _score

    private var _currentWordCount = 0
    val currentWordCount : Int
    get() = _currentWordCount

    private lateinit var _currentScrambledWord : String
    val currentScrambledWord : String
    get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf()
    lateinit var currentWord: String

    init {
        Log.d("GameFragment","GameModelView created!")
        getNextWord()
    }




    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment","GameModelView cleared!")
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        println(currentWord)

        if(wordsList.contains(currentWord))
            getNextWord()

        else {
            val tempWord = currentWord.toCharArray()
            //println("temp word ... ${String(tempWord)} current word == $currentWord")
            while (String(tempWord).equals(currentWord, false))
                tempWord.shuffle()
            _currentScrambledWord = String(tempWord)
            //println("====== $_currentScrambledWord")
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun nextWord() : Boolean {
    return if(_currentWordCount < MAX_NO_OF_WORDS) {
        getNextWord()
        true
    }
        else
            false
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect (userWord: String) : Boolean {
        return if(userWord.equals(currentWord,true)){
            increaseScore()
            true
        }
        else
            false

    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }


}