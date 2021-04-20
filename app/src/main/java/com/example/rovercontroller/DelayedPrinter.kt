package com.example.rovercontroller

import android.widget.TextView
import kotlin.random.Random


// эффект печати текста
object DelayedPrinter
{
    fun printText(word: Word, textView: TextView)
    {
        val random = Random(System.currentTimeMillis())
        val currentRandOffset: Int = random.nextInt(word.offset)
        val addOrSubtract: Boolean = random.nextBoolean()
        var finalDelay = if (addOrSubtract) word.delayBetweenSymbols + currentRandOffset else word.delayBetweenSymbols - currentRandOffset
        if (finalDelay < 0) finalDelay = 0

        textView.postDelayed(Runnable {
            val charAt = word.word[word.currentCharacterIndex].toString()
            word.currentCharacterIndex++
            textView.text = textView.text.toString() + charAt
            if (word.currentCharacterIndex >= word.word.length) return@Runnable
            printText(word, textView)
        }, finalDelay)
    }

    class Word(val delayBetweenSymbols: Long, var word: String)
    {
        var offset = 0
        var currentCharacterIndex = 0

        init
        {
            require(delayBetweenSymbols >= 0) { "Delay can't be < 0" }
        }
    }
}