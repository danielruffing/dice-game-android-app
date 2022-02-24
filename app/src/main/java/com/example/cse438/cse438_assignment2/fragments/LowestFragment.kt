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
import kotlinx.android.synthetic.main.fragment_average.highest_button
import kotlinx.android.synthetic.main.fragment_lowest.*

class LowestFragment : Fragment() {
    public var totalRolls = IntArray(200)
    public var numberOfRolls: Int = 0
    public lateinit var highestButton: Button
    public lateinit var averageButton: Button
    public lateinit var probButton: Button
    public lateinit var cancelButton: Button
    public lateinit var lowestText : TextView
    private var extras : Bundle? = null

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
        return inflater.inflate(R.layout.fragment_lowest, container, false)
    }

    override fun onStart() {
        super.onStart()

        //set views
        highestButton = highest_button
        averageButton = average_button
        probButton = prob_button
        cancelButton = cancel_button
        lowestText = stat_lowest
        lowestText.text = getLowest(totalRolls).toString()

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
        //Stat Highest
        highestButton.setOnClickListener {
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

    fun getLowest(rolls: IntArray): Int?{
        var low : Int = 99999999
        if(rolls[0]==0){
            low = 0
        }
        for(i in 0..numberOfRolls){
            if(rolls[i]!=0) {
                if (rolls[i] < low) {
                    low = rolls[i]
                }
            }
        }
        return low
    }

}
