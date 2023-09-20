package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
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
    //private val args = FragmentQuestionsArgs.fromBundle(requireArguments())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_questions, container, false)
        val difficulty = FragmentQuestionsArgs.fromBundle(requireArguments()).difficulty
        val operation = FragmentQuestionsArgs.fromBundle(requireArguments()).operation
        val numOfQuestions = FragmentQuestionsArgs.fromBundle(requireArguments()).numOfQuestions
        ansText = view.findViewById<EditText>(R.id.editTextNumber)
        var tempNum = 1
        val done = view.findViewById<Button>(R.id.bDone)
        Log.e("difficulty level", difficulty)
        Log.e("operation", operation)
        Log.e("Number of Questions", numOfQuestions.toString())
        updateQuestion(operation, difficulty)
        done.setOnClickListener {
            val userAnswer = ansText?.text.toString().toIntOrNull()
            if (userAnswer == calculateCorrectAnswer(ref1, ref2, operation)) numCorrect++
            Log.d("Input Answer", userAnswer.toString())
            Log.d("Actual Answer", calculateCorrectAnswer(ref1, ref2, operation).toString())
            if (tempNum <= numOfQuestions) {
                tempNum++
                updateQuestion(operation, difficulty)
            } else {
                // Navigate to next fragment
                val bundle1 = Bundle()
                Log.e("Total Correct", numCorrect.toString())
                bundle1.putInt("totalCorrect", numCorrect)
                bundle1.putInt("numOfQuestions", numOfQuestions)
                view.findNavController()
                    .navigate(R.id.action_fragement_questions_to_fragmentResult, bundle1)
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
                return Pair(operand1, operand2)
            }
            if(operator == "/"){
                val operand1 = generateNonPrime(1 until 10)
                val factors = getFactors(operand1)
                val operand2 = factors.random()
                return Pair(operand1, operand2)
            }
            else{
                val operand1 = (1 until 10).random()
                val operand2 = (1 until 10).random()
                return Pair(operand1, operand2)
            }
        }
        if(difficulty == "Medium"){
            if(operator == "-"){
                val operand1 = (1 until 25).random()
                val operand2 = (1 until operand1).random()
                return Pair(operand1, operand2)
            }
            if(operator == "/"){
                val operand1 = generateNonPrime(1 until 25)
                val factors = getFactors(operand1)
                val operand2 = factors.random()
                return Pair(operand1, operand2)
            }
            else{
                val operand1 = (1 until 25).random()
                val operand2 = (1 until 25).random()
                return Pair(operand1, operand2)
            }
        }
        if(difficulty == "Hard"){
            if(operator == "-"){
                val operand1 = (1 until 50).random()
                val operand2 = (1 until operand1).random()
                return Pair(operand1, operand2)
            }
            if(operator == "/"){
                val operand1 = generateNonPrime(1 until 10)
                val factors = getFactors(operand1)
                val operand2 = factors.random()
                return Pair(operand1, operand2)
            }
            else{
                val operand1 = (1 until 50).random()
                val operand2 = (1 until 50).random()
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


