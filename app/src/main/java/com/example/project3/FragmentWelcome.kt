package com.example.project3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.navigation.findNavController
import java.math.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private lateinit var btnNumbers: List<Button>
private lateinit var btnDifficulty: List<Button>
private var operation = ""
private var difficulty = ""
private var numOfQuestions = 0
private var x = 1



/**
 * A simple [Fragment] subclass.
 * Use the [FragmentWelcome.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentWelcome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        val easyButton = view.findViewById<RadioButton>(R.id.bEasy);
        easyButton.isChecked = true
        difficulty = "Easy"
        val addButton = view.findViewById<RadioButton>(R.id.bAddition)
        addButton.isChecked = true
        operation = "+"
        val startButton = view.findViewById<Button>(R.id.bStart)
        val plusButton = view.findViewById<Button>(R.id.bPlus)
        val minusButton = view.findViewById<Button>(R.id.bMinus)
        val questionNum = view.findViewById<TextView>(R.id.tvProblemNumber)
        btnNumbers = listOf(view.findViewById(R.id.bAddition), view.findViewById(R.id.bSubtraction),
            view.findViewById(R.id.bMultiplication),view.findViewById(R.id.bDivision))
        btnDifficulty = listOf(view.findViewById(R.id.bEasy), view.findViewById(R.id.bMedium),
            view.findViewById(R.id.bHard))
        btnNumbers.forEach{ btn ->
            btn.setOnClickListener{
                if(btn.id == R.id.bAddition){
                    operation = "+"
                }
                if(btn.id == R.id.bSubtraction){
                    operation = "-"
                }
                if(btn.id == R.id.bMultiplication){
                    operation = "×"
                }
                if(btn.id == R.id.bDivision){
                    operation = "/"
                }
            }
        }
        btnDifficulty.forEach{ btn ->
            btn.setOnClickListener{
                if(btn.id == R.id.bEasy){
                    difficulty = "Easy"
                }
                if(btn.id == R.id.bMedium){
                    difficulty = "Medium"
                }
                else{
                    difficulty = "Hard"
                }
            }
        }

        plusButton.setOnClickListener {
            if(questionNum.text.toString() == ""){
                questionNum.text = "1"
            }
            else{
                var temp = questionNum.text.toString().toInt()
                temp++
                questionNum.text = temp.toString()
            }
        }

        minusButton.setOnClickListener {
            if(questionNum.text.toString() == "" || questionNum.text.toString() == "0"){ }
            else{
                var temp = questionNum.text.toString().toInt()
                temp--
                questionNum.text = temp.toString()
            }
        }

        startButton.setOnClickListener {
            if(questionNum.text.toString() == "" || questionNum.text.toString() == "0"){ }
            else{
                numOfQuestions = questionNum.text.toString().toInt()
                val bundle = Bundle()
                Log.e("difficulty level", difficulty)
                Log.e("operation", operation)
                Log.e("Number of Questions", numOfQuestions.toString())
                bundle.putString("difficulty", difficulty)
                bundle.putString("operation", operation)
                bundle.putInt("numOfQuestions", numOfQuestions)
                view.findNavController().navigate(R.id.action_fragment_welcome_to_fragement_questions,bundle)
            }
        }

        return view
    }


}