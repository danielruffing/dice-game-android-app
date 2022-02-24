package com.example.cse438.cse438_assignment2.fragments

import android.content.Intent
import android.widget.Button
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cse438.cse438_assignment2.activities.MainActivity
import com.example.cse438.cse438_assignment2.R
import kotlinx.android.synthetic.main.fragment_average.*

class AverageFragment : Fragment() {
    //extras used
    public var totalRolls = IntArray(200)
    public var numberOfRolls: Int = 0

    //extras bundle
    private var extras : Bundle? = null

    //views
    public lateinit var highestButton: Button
    public lateinit var lowestButton: Button
    public lateinit var probButton: Button
    public lateinit var cancelButton: Button
    public lateinit var averageText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = activity!!.intent
        extras = intent!!.extras
        totalRolls = intent!!.getIntArrayExtra("TOTAL_ROLLS")
        numberOfRolls = intent!!.getIntExtra("NUMBER_OF_ROLLS", 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_average, container, false)
        }

    override fun onStart() {
        super.onStart()

        //set views
        highestButton = highest_button
        lowestButton = lowest_button
        probButton = prob_button
        cancelButton = cancel_button
        averageText = stat_average

        //display roll average
        averageText.text = getAverage(totalRolls).toString()

        //set listener for stat buttons
        //Stat Lowest
        lowestButton.setOnClickListener{
            //set fragment
            val fragment = LowestFragment()
            var bundle = Bundle()
            bundle!!.putAll(extras)
            fragment.arguments = bundle
            val fragManager = fragmentManager
            val transaction = fragManager!!.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
        //Stat Highest
        highestButton.setOnClickListener {
            //set fragment
            val fragment = HighestFragment()
            var bundle = Bundle()
            bundle!!.putAll(extras)
            fragment.arguments = bundle
            val fragManager = fragmentManager
            val transaction = fragManager!!.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
        //Stat Probability
        probButton.setOnClickListener {
            //set fragment
            val fragment = ProbabilityFragment()
            var bundle = Bundle()
            bundle!!.putAll(extras)
            fragment.arguments = bundle
            val fragManager = fragmentManager
            val transaction = fragManager!!.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
        //set listener for cancel button
        cancelButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtras(extras)
            startActivity(intent)
        }
    }

    //calculates the average
    fun getAverage(rolls: IntArray): Double{
        var avg : Double = 0.0
        for(i in 0..numberOfRolls){
            avg += rolls[i]
        }
        //rounds to 2 decimal places
        avg = avg/numberOfRolls.toDouble()
        avg = Math.round(avg*100.0)/100.0
        return avg
    }

}
