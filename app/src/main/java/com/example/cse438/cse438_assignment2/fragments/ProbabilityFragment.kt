
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
import kotlinx.android.synthetic.main.fragment_probability.*

class ProbabilityFragment : Fragment() {
    //extras
    public var totalRolls = IntArray(200)
    public var numberOfRolls: Int = 0
    var numberOfDice : Int = 0
    var maxValue: Int = 0
    var bank: Int = 0

    //extras bundle
    private var extras : Bundle? = null

    //views
    public lateinit var lowestButton: Button
    public lateinit var highestButton: Button
    public lateinit var averageButton: Button
    public lateinit var cancelButton: Button
    public lateinit var probText : TextView
    public lateinit var balanceText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = activity!!.intent
        extras = intent!!.extras

        totalRolls = intent!!.getIntArrayExtra("TOTAL_ROLLS")
        numberOfRolls = intent!!.getIntExtra("NUMBER_OF_ROLLS", 1)
        numberOfDice = intent!!.getIntExtra("NUMBER_OF_DICE", 0)
        maxValue = intent!!.getIntExtra("MAX_VALUE", 0)
        bank = intent!!.getIntExtra("BANK", 100)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_probability, container, false)
    }

    override fun onStart() {
        super.onStart()

        //set views
        lowestButton = lowest_button
        averageButton = average_button
        highestButton = highest_button
        cancelButton = cancel_button
        probText = stat_chance
        balanceText = stat_balance
        probText.text = perfectRollChance(maxValue.toDouble(), numberOfDice.toDouble()).toString() + "%"
        balanceText.text = "$"+bank.toString()

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
        //set listener for cancel button
        cancelButton.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtras(extras)
            startActivity(intent)
        }
    }
    fun perfectRollChance(max:Double, nDice: Double): Double{
        var prob: Double = Math.pow((1/max), nDice)
        prob = prob *100
        prob = Math.round(prob*1000000.0)/1000000.0
        return prob
    }
}