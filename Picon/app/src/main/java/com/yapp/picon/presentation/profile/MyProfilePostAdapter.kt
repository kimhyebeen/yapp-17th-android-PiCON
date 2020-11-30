package com.yapp.picon.presentation.profile

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.yapp.picon.R
import com.yapp.picon.databinding.MyProfilePostItemBinding
import com.yapp.picon.presentation.base.BaseRecyclerView
import com.yapp.picon.presentation.model.MyProfilePost
import kotlinx.android.synthetic.main.my_profile_post_item.view.*

class MyProfilePostAdapter(
    private val context: Context,
    private val clickEvent: (View, Int) -> Unit,
    @LayoutRes private val layoutRes: Int,
    bindingVariabledId: Int
) : BaseRecyclerView.BaseAdapter<MyProfilePost, MyProfilePostItemBinding>(
    layoutRes,
    bindingVariabledId
) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        baseViewHolder: BaseRecyclerView.BaseViewHolder<MyProfilePostItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(baseViewHolder, position)
        val size = baseViewHolder.itemView.width

        baseViewHolder.itemView.apply {
            layoutParams = LinearLayout.LayoutParams(
                size,
                size + dpToPx(context, 3f).toInt())
            setBackgroundResource(
                getColors(items[position].color)
            )
            setOnClickListener { clickEvent(it, items[position].id) }
        }

        baseViewHolder.itemView.my_profile_post_item_image.apply {
            layoutParams = LinearLayout.LayoutParams(size, size)
        }

        val multiOption = MultiTransformation(
            CenterCrop(),
            RoundedCorners(5)
        )
        Glide.with(context)
            .load(items[position].imageUrl)
            .apply(RequestOptions.bitmapTransform(multiOption))
            .into(baseViewHolder.itemView.my_profile_post_item_image)
    }

    private fun dpToPx(context: Context, dp: Float): Float {
        val displayMetrics = context.resources.displayMetrics

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)
    }

    private fun getColors(color: String): Int {
        return when(color) {
            "SOFT_BLUE" -> R.drawable.ic_custom_rounded_soft_blue
            "CORN_FLOWER" -> R.drawable.ic_custom_rounded_corn_flower
            "BLUE_GRAY" -> R.drawable.ic_custom_rounded_blue_gray
            "VERY_LIGHT_BROWN" -> R.drawable.ic_custom_rounded_very_light_brown
            "WARM_GRAY" -> R.drawable.ic_custom_rounded_warm_gray
            else -> throw Exception("MyProfilePostAdapter - getColors - color type is wrong.")
        }
    }
}