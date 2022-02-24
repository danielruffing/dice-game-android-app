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
import kotlinx.android.synthetic.main.fragment_average.cancel_button
import kotlinx.android.synthetic.main.fragment_highest.*
import kotlinx.android.synthetic.main.fragment_lowest.average_button

class HighestFragment : Fragment() {
    //extras
    public var totalRolls = IntArray(200)
    public var numberOfRolls: Int = 0

    //extras bundle
    private var extras : Bundle? = null

    //views
    public lateinit var lowestButton: Button
    public lateinit var averageButton: Button
    public lateinit var probButton: Button
    public lateinit var cancelButton: Button
    public lateinit var highestText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = activity!!.intent
        extras = intent!!.extras

        totalRolls = intent!!.getIntArrayExtra("TOTAL_ROLLS")
        numberOfRolls = intent!!.getIntExtra("NUMBER_OF_ROLLS", 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_highest, container, false)
    }

    override fun onStart() {
        super.onStart()

        //set views
        lowestButton = low_button
        averageButton = average_button
        probButton = prob_button
        cancelButton = cancel_button
        highestText = stat_highest
        highestText.text = getHighest(totalRolls).toString()

        //set listener for stat buttons
        //Stat Average
        averageButton.setOnClickListener{
            //set fragment
            val fragment = AverageFragment()
            var bundle = Bundle()
            bundle!!.putAll(extras)
            fragment.arguments = bundle
            val fragManager = fragmentManager
            val transaction = fragManager!!.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
        //Stat Lowest
        lowestButton.setOnClickListener {
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
    fun getHighest(rolls: IntArray): Int?{
        return rolls.max()
    }
}