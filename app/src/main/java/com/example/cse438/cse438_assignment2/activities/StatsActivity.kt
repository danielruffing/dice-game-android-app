/**Author(s): Daniel Ruffing
 * Assignment 2
 * */
package com.example.cse438.cse438_assignment2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cse438.cse438_assignment2.R
import com.example.cse438.cse438_assignment2.fragments.AverageFragment

class StatsActivity : AppCompatActivity(){
    //extras bundle
    var extras: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        //set inital fragment
        val fragment = AverageFragment()
        var bundle = Bundle()

        extras = intent.extras
        bundle.putAll(extras)
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()
    }
}