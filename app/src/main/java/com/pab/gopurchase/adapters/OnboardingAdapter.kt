package com.pab.gopurchase.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pab.gopurchase.R
import com.pab.gopurchase.models.OnboardingItem

class OnboardingAdapter(
    private val items: List<OnboardingItem>
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {
    
    inner class OnboardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageIllustration: ImageView = view.findViewById(R.id.imageIllustration)
        val textDescription: TextView = view.findViewById(R.id.textDescription)
        
        fun bind(item: OnboardingItem) {
            imageIllustration.setImageResource(item.imageRes)
            textDescription.setText(item.descriptionRes)
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding_slide, parent, false)
        return OnboardingViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        holder.bind(items[position])
    }
    
    override fun getItemCount() = items.size
}
