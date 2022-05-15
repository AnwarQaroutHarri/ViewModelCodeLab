package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {




    private val _score = MutableLiveData<Int>(0)
    val score : LiveData<Int>
    get() = _score

    private val _currentWordCount = MutableLiveData<Int>(0)
    val currentWordCount : LiveData<Int>
    get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord : LiveData<String>
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
            _currentScrambledWord.value = String(tempWord)
            //println("====== $_currentScrambledWord")
            _currentWordCount.value = _currentWordCount.value?.inc()
            wordsList.add(currentWord)
        }
    }

    fun nextWord() : Boolean {
    return if(_currentWordCount.value!! < MAX_NO_OF_WORDS) {
        getNextWord()
        true
    }
        else
            false
    }

    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
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
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }


}