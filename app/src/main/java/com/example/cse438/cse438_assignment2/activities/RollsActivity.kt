/**Author(s): Daniel Ruffing
 * Assignment 2
 * */
package com.example.cse438.cse438_assignment2.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cse438.cse438_assignment2.R
import kotlinx.android.synthetic.main.activity_rolls.*

class RollsActivity : AppCompatActivity(){
    //extras
    public var totalRolls = IntArray(200)
    public var numDice: Int = 0
    public var maxValue: Int = 0
    public var numberOfRolls: Int = 0
    public var bank: Int = 1000

    //extras bundle
    var bundle: Bundle? = null

    //views
    public lateinit var resultsButton : Button
    public lateinit var rerollButton : Button
    public lateinit var homeButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rolls)

        //set views
        resultsButton = see_results_button//enter view tag
        rerollButton = reroll_button//enter view tag
        homeButton = home_button//enter view tag

        //get the number of die and max value of die
        bundle = intent.extras
        numDice = bundle!!.getInt("NUMBER_OF_DICE")
        maxValue = bundle!!.getInt("MAX_VALUE")
        totalRolls = bundle!!.getIntArray("TOTAL_ROLLS")
        numberOfRolls = bundle!!.getInt("NUMBER_OF_ROLLS")
        bank = bundle!!.getInt("BANK")

        //display current roll
        current_roll.text = roll(numDice, maxValue).toString()
    }

    override fun onStart() {
        super.onStart()

        //opens StatsActivity
        resultsButton.setOnClickListener{
            val intent = Intent(this, StatsActivity::class.java)
            intent.putExtra("TOTAL_ROLLS", totalRolls)
            intent.putExtra("NUMBER_OF_ROLLS", numberOfRolls)
            intent.putExtra("NUMBER_OF_DICE", numDice)
            intent.putExtra("MAX_VALUE", maxValue)
            intent.putExtra("BANK", bank)

            startActivity(intent)
        }
        //reroll
        rerollButton.setOnClickListener(){
            //displays current roll
            if(bank>0){
                current_roll.text = roll(numDice, maxValue).toString()
            } else{
                //dialog to alert user
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Need $50 Balance to Play")
                builder.setMessage("Please clear values at home screen to reset bank balance")

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    Toast.makeText(applicationContext,
                        android.R.string.yes, Toast.LENGTH_SHORT).show()
                }
                builder.show()
            }

        }

        //opens MainActivity
        homeButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            //preserve rolls
            intent.putExtra("TOTAL_ROLLS", totalRolls)
            intent.putExtra("NUMBER_OF_ROLLS", numberOfRolls)
            intent.putExtra("NUMBER_OF_DICE", numDice)
            intent.putExtra("MAX_VALUE", maxValue)
            intent.putExtra("BANK", bank)

            startActivity(intent)
        }
    }

    //rolls the dice, determines quality of roll, and stores roll
    fun roll(numDice: Int, maxValue: Int): Int{
        var sum : Int = 0
        bank = bank - 50
        for(i in 1..numDice){
            sum += (Math.random() * maxValue + 1).toInt()
        }
        val highRoll : Int = (((maxValue*numDice) *3)/4)
        val lowRoll : Int = ((maxValue*numDice) /4)
        val jackpot: Int = maxValue*numDice
        if(sum==jackpot){
            bank += 100000
        }
        if(sum>=highRoll){
            bank += 200
                //text = green
            current_roll.setTextColor(Color.parseColor("#00FF00"))
            }
        else if(sum<=lowRoll){
            bank = bank - 450
            //text = red
            current_roll.setTextColor(Color.parseColor("#ff0000"))
        } else{
            current_roll.setTextColor(Color.parseColor("#000000"))
        }
        totalRolls[numberOfRolls] = sum
        numberOfRolls++
        return sum
    }
}