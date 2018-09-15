package me.pirrate.fintechcharity

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.pirrate.fintechcharity.api.models.Beneficiary
import android.util.TypedValue



class BeneficiaryAdapter(val dataset: List<Beneficiary>) : RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder>() {
    var onItemSelectedListener: ((Beneficiary) -> Unit)? = null
    var selected = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeneficiaryViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_beneficiary_item, parent, false) as LinearLayout
        return BeneficiaryViewHolder(linearLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: BeneficiaryViewHolder, position: Int) {
        val beneficiary = dataset.get(position)
        holder.beneficiaryName.text = beneficiary.Name
        Glide.with(holder.beneficiaryImage)
                .load(beneficiary.pictureUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.beneficiaryImage)

        if (selected == position) {
            markSelected(holder)
        } else {
            markUnselected(holder)
        }

        holder.linearLayout.setOnClickListener {
            if(selected != -1)
                notifyItemChanged(selected)

            selected = position

            notifyItemChanged(selected)

            onItemSelectedListener?.invoke(beneficiary)
        }
    }

    inner class BeneficiaryViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout) {
        val beneficiaryImage = linearLayout.findViewById<ImageView>(R.id.beneficiaryImage)
        val beneficiaryName = linearLayout.findViewById<TextView>(R.id.beneficiaryName)
    }

    fun markSelected(holder: BeneficiaryViewHolder) {
        holder.linearLayout.isSelected = true
        holder.linearLayout.setBackgroundResource(R.color.colorPrimaryDark)
        holder.beneficiaryName.setTextColor(Color.WHITE)
    }

    fun markUnselected(holder: BeneficiaryViewHolder) {
        val typedValue = TypedValue()
        holder.linearLayout.context.theme.resolveAttribute(R.attr.selectableItemBackground, typedValue, true)

        holder.linearLayout.isSelected = false
        holder.linearLayout.setBackgroundResource(typedValue.resourceId)
        holder.beneficiaryName.setTextColor(Color.BLACK)
    }
}