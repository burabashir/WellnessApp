package com.example.wellnessapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class NutritionActivity : AppCompatActivity() {

    private var rewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge to edge safe
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_nutrition)

        // Load the reward ad
        loadRewardedAd(this)

        // Find button and set listener
        val rewardAdd = findViewById<Button>(R.id.rewardAdd)
        rewardAdd.setOnClickListener {
            showRewardedAd(this)
        }

        // Apply system bar padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadRewardedAd(context: Context) {
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            context,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                    println("Ad failed to load: ${error.message}")
                }
            }
        )
    }

    private fun showRewardedAd(activity: Activity) {
        rewardedAd?.show(activity) { rewardItem ->
            val rewardAmount = rewardItem.amount
            val rewardType = rewardItem.type
            println("User earned: $rewardAmount $rewardType")
        } ?: println("Ad not ready")
    }
}