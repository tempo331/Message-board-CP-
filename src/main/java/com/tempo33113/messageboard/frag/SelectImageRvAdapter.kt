package com.tempo33113.messageboard.frag

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tempo33113.messageboard.R
import com.tempo33113.messageboard.act.EditAdsAct
import com.tempo33113.messageboard.databinding.SelectImageFragItemBinding
import com.tempo33113.messageboard.utils.AdapterCallback
import com.tempo33113.messageboard.utils.ImageManager
import com.tempo33113.messageboard.utils.ImagePicker
import com.tempo33113.messageboard.utils.ItemTouchMoveCallback

class SelectImageRvAdapter(val  adapterCallback: AdapterCallback) : RecyclerView.Adapter<SelectImageRvAdapter.ImageHolder>(), ItemTouchMoveCallback.ItemTouchAdapter {
    val mainArray = ArrayList<Bitmap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val viewBinding = SelectImageFragItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(viewBinding, parent.context, this)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.setData(mainArray[position])
    }

    override fun getItemCount(): Int {
        return mainArray.size
    }

    override fun onMove(startPos: Int, targetPos: Int) {

        val targetItem = mainArray[targetPos]
        mainArray[targetPos] = mainArray[startPos]
        //val titleStart = mainArray[targetPos].title
        //mainArray[targetPos].title = targetItem.title
        mainArray[startPos] = targetItem
        //mainArray[startPos].title = titleStart
        notifyItemMoved(startPos,targetPos)

    }

    override fun onClear() {
        notifyDataSetChanged()
    }

    class ImageHolder(private val viewBinding: SelectImageFragItemBinding, val context: android.content.Context, val adapter : SelectImageRvAdapter) : RecyclerView.ViewHolder(viewBinding.root) {

        fun setData(bitMap : Bitmap){
            viewBinding.imEditImage.setOnClickListener {

                ImagePicker.getImages(context as EditAdsAct, 1, ImagePicker.REQUEST_CODE_GET_SINGLE_IMAGE)
                context.editImagePos = adapterPosition
            }

            viewBinding.imDelete.setOnClickListener {
                adapter.mainArray.removeAt(adapterPosition)
                adapter.notifyItemRemoved(adapterPosition)
                for (n in 0 until adapter.mainArray.size) adapter.notifyItemChanged(n)
                adapter.adapterCallback.onItemDelete()
            }

            viewBinding.tvTitle.text = context.resources.getStringArray(R.array.title_array)[adapterPosition]
            ImageManager.chooseScaleType(viewBinding.imageContent, bitMap)
            viewBinding.imageContent.setImageBitmap(bitMap)
        }
    }

    fun updateAdapter(newList : List<Bitmap>, needClear : Boolean){
        if (needClear) mainArray.clear()
        mainArray.addAll(newList)
        notifyDataSetChanged()
    }


}