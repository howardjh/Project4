package com.example.project3

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import kotlin.math.sqrt

class FragmentQuestions : Fragment() {
    private var numCorrect = 0
    private var ref1 = 0
    private var ref2 = 0
    private lateinit var ansText: EditText
    private lateinit var correctSound: MediaPlayer
    private lateinit var wrongSound: MediaPlayer
    // private val args = FragmentQuestionsArgs.fromBundle(requireArguments())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        correctSound = MediaPlayer.create(context, R.raw.correct)
        wrongSound = MediaPlayer.create(context, R.raw.incorrect)
        //correctSound.prepare()
        //wrongSound.prepare()
        correctSound.stop()
        wrongSound.stop()
        correctSound.prepare()
        wrongSound.prepare()
        val view = inflater.inflate(R.layout.fragment_questions, container, false)
        val difficulty = FragmentQuestionsArgs.fromBundle(requireArguments()).difficulty
        val operation = FragmentQuestionsArgs.fromBundle(requireArguments()).operation
        val numOfQuestions = FragmentQuestionsArgs.fromBundle(requireArguments()).numOfQuestions
        val retNumQuestion = numOfQuestions
        ansText = view.findViewById<EditText>(R.id.editTextNumber)
        var tempNum = 1
        val done = view.findViewById<Button>(R.id.bDone)
        Log.e("difficulty level", difficulty)
        Log.e("operation", operation)
        Log.e("Number of Questions", numOfQuestions.toString())
        updateQuestion(operation, difficulty)
        Log.e("ref1", ref1.toString())
        Log.e("ref2", ref2.toString())
        done.setOnClickListener {
            val userAnswer = ansText?.text.toString().toIntOrNull()
            if (userAnswer == calculateCorrectAnswer(ref1, ref2, operation)) {
                numCorrect++
                val correctToast = Toast.makeText(context, "Correct. Good Work!", Toast.LENGTH_SHORT)
                correctToast.show()
                if (wrongSound.isPlaying){
                    wrongSound.stop()
                    wrongSound.prepare()
                }
                if(correctSound.currentPosition != -1) correctSound.start()
            }
            else{
                val incorrectToast = Toast.makeText(context, "Wrong", Toast.LENGTH_SHORT)
                incorrectToast.show()
                if (correctSound.isPlaying) {
                    correctSound.stop()
                    correctSound.prepare()
                }
                if(wrongSound.currentPosition != -1) wrongSound.start()
            }
            Log.d("Input Answer", userAnswer.toString())
            Log.d("Actual Answer", calculateCorrectAnswer(ref1, ref2, operation).toString())
            if (tempNum <= numOfQuestions) {
                tempNum++
                updateQuestion(operation, difficulty)
            }
            //if (tempNum == numOfQuestions + 1) tempNum++
            else {
                // Navigate to next fragment
                val args = Bundle()
                Log.e("Total Correct", numCorrect.toString())
                Log.e("Total Questions before Fragment switch", retNumQuestion.toString())
                args.putInt("pastNumOfQuestions", retNumQuestion)
                args.putInt("pastNumCorrect", numCorrect)
                view.findNavController().navigate(R.id.action_fragment_questions_to_fragment_welcome, args)
            }
        }
        return view
    }

    private fun updateQuestion(operator: String, difficulty: String) {
        val (operand1, operand2) = generateOperands(difficulty, operator)
        val op1 = view?.findViewById<TextView>(R.id.tvOperand1)
        val op2 = view?.findViewById<TextView>(R.id.tvOperand2)
        val oper = view?.findViewById<TextView>(R.id.tvOperator)
        op1?.text = operand1.toString()
        op2?.text = operand2.toString()
        ref1 = operand1
        ref2 = operand2
        oper?.text = operator
        ansText?.text?.clear()
    }

    private fun generateOperands(difficulty: String, operator: String): Pair<Int, Int> {
        if(difficulty == "Easy"){
            if(operator == "-"){
                val operand1 = (1 until 10).random()
                val operand2 = (1 until operand1).random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
            if(operator == "/"){
                val operand1 = generateNonPrime(1 until 10)
                val factors = getFactors(operand1)
                val operand2 = factors.random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
            else{
                val operand1 = (1 until 10).random()
                val operand2 = (1 until 10).random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
        }
        if(difficulty == "Medium"){
            if(operator == "-"){
                val operand1 = (1 until 25).random()
                val operand2 = (1 until operand1).random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
            if(operator == "/"){
                val operand1 = generateNonPrime(1 until 25)
                val factors = getFactors(operand1)
                val operand2 = factors.random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
            else{
                val operand1 = (1 until 25).random()
                val operand2 = (1 until 25).random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
        }
        if(difficulty == "Hard"){
            if(operator == "-"){
                val operand1 = (1 until 50).random()
                val operand2 = (1 until operand1).random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
            if(operator == "/"){
                val operand1 = generateNonPrime(1 until 10)
                val factors = getFactors(operand1)
                val operand2 = factors.random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
            else{
                val operand1 = (1 until 50).random()
                val operand2 = (1 until 50).random()
                Log.e("operand1", operand1.toString())
                Log.e("operand2", operand2.toString())
                return Pair(operand1, operand2)
            }
        }
        return Pair(0,0)
    }

    private fun calculateCorrectAnswer(op1: Int, op2: Int, operation: String): Int {
        if(operation  == "+"){
            return op1 + op2
        }
        if(operation  == "-"){
            return op1 - op2
        }
        if(operation  == "×"){
            return op1 * op2
        }
        else{
            return op1 / op2
        }
    }

    private fun generateNonPrime(range: IntRange): Int {
        val random = range.random()
        if (!isPrime(random)) {
            return random
        } else {
            return generateNonPrime(range)
        }
    }

    private fun isPrime(num: Int): Boolean {
        if (num <= 1) return false
        for (i in 2..sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) {
                return false
            }
        }
        return true
    }

    private fun getFactors(num: Int): List<Int> {
        val factors = mutableListOf<Int>()
        for (i in 1..num) {
            if (num % i == 0) {
                factors.add(i)
            }
        }
        return factors
    }



}


