/**Author(s): Daniel Ruffing
 * Assignment 2
 * */
package com.example.cse438.cse438_assignment2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cse438.cse438_assignment2.R


class MainActivity : AppCompatActivity() {

    //extras
    public var totalRolls = IntArray(200)
    public var numberOfRolls: Int = 0
    var dieAmount : Int = 0
    var maxValue : Int = 0
    var bank : Int = 1000

    public lateinit var numDice : EditText      //number of dice
    public lateinit var diceValue : EditText    //max dice value
    public lateinit var rollButton : Button     //roll dice button
    public lateinit var clearButton : Button    //clear rolls button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the number of die and max value of die
        var bundle : Bundle? = intent.extras
        if(bundle!=null){
            dieAmount = bundle!!.getInt("NUMBER_OF_DICE")
            maxValue = bundle!!.getInt("MAX_VALUE")
            totalRolls = bundle!!.getIntArray("TOTAL_ROLLS")
            numberOfRolls = bundle!!.getInt("NUMBER_OF_ROLLS")
            bank = bundle!!.getInt("BANK")
        }
    }

    override fun onStart(){
        super.onStart()

        //set views
        numDice = dice_number//enter view tag
        diceValue = dice_value//enter view tag
        rollButton = roll_button//enter view tag
        clearButton = clear_button//enter view tag

        //sets the text when return to main activity
        if(numberOfRolls>0){
            numDice.setText(dieAmount.toString())
            diceValue.setText(maxValue.toString())
        }

        //sends to RollsActivity
        rollButton.setOnClickListener{
            if(numDice!!.text.toString()==""|| diceValue!!.text.toString() == ""){
                //dialog to alert user
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Enter values")
                builder.setMessage(" ")

                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                    Toast.makeText(applicationContext,
                        android.R.string.yes, Toast.LENGTH_SHORT).show()
                }
                builder.show()
            }
            else{
                val intent = Intent(this, RollsActivity::class.java)
                dieAmount = numDice.text.toString().toInt()
                maxValue = diceValue.text.toString().toInt()


                intent.putExtra("TOTAL_ROLLS", totalRolls)
                intent.putExtra("NUMBER_OF_DICE", dieAmount)
                intent.putExtra("MAX_VALUE", maxValue)
                intent.putExtra("NUMBER_OF_ROLLS", numberOfRolls)
                intent.putExtra("BANK", bank)

                startActivity(intent)
            }

        }

        //reset all variable values
        clearButton.setOnClickListener{

            //dialog to alert user
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Roll History Cleared")
            builder.setMessage(" ")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.yes, Toast.LENGTH_SHORT).show()
            }
            builder.show()

            if(numberOfRolls>1){
                for(i in 0..numberOfRolls){
                    totalRolls[i] = 0
                }
            }
            numberOfRolls = 0
            dieAmount = 0
            maxValue = 0
            bank = 1000
            numDice.setText(null)
            diceValue.setText(null)
        }
    }
}

