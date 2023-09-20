package com.example.project3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController


class FragmentResult : Fragment() {
    private lateinit var resultTv: TextView
    private lateinit var restartButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view = inflater.inflate(R.layout.fragment_result, container, false)
            Log.e("Result?", "Result View entered")
            val numOfQuestions = FragmentResultArgs.fromBundle(requireArguments()).numOfQuestions
            val numCorrect = FragmentResultArgs.fromBundle(requireArguments()).totalCorrect
            Log.e("Number of Questions", numOfQuestions.toString())
            Log.e("Number of Correct Answers", numCorrect.toString())
            resultTv = view.findViewById<TextView>(R.id.tvResult)
            restartButton = view.findViewById<Button>(R.id.bRestart)
            val returnStr = "You got " + numCorrect.toString() + " out of " + numOfQuestions.toString() + " correct"
            resultTv.text = returnStr
            restartButton.setOnClickListener {
                view.findNavController().navigate(R.id.action_fragmentResult_to_fragment_welcome)
            }
        return view
    }

}