package com.example.flo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.data.Album
import com.example.flo.databinding.ItemAlbumBinding
import android.util.Log
import androidx.core.os.persistableBundleOf
import com.example.flo.MainActivity

class AlbumRVAdapter(private val albumList: ArrayList<Album>) : RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>(){

    // 클릭 인터페이스
    interface MyItemClickListener {
        fun onItemClick(album : Album)
        fun onRemoveAlbum(position: Int)
    }

    // 리스너 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var mItemClickListener: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }
    // 뷰 홀더를 생성할 때 호출되는 함수 -> 아이템 뷰 객체를 만들어서 뷰 홀더에 넣어줌
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }
    fun addItems(albums: ArrayList<Album>) {
        albumList.clear()
        albumList.addAll(albums)
        notifyDataSetChanged()
    }

    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged()
    }
    // notify 잊지말것!!
    fun removeItems() {
        albumList.clear()
        notifyDataSetChanged()
    }
    fun removeItem(position: Int) {
        albumList.removeAt(position)
        notifyDataSetChanged()
    }

    // 생성된 뷰 홀더에 데이터를 바인딩해줘야 할 때마다 호출됨
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    // 데이터 세트 크기를 알려주는 함수, 리사이클러뷰가 마지막이 언제인지를 알 수 있음
    override fun getItemCount(): Int = albumList.size

    // 뷰홀더 : 아이템 객체들을 재활용 하기 위한 그릇
    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.itemAlbumImgIv.setOnClickListener  {
                mItemClickListener.onItemClick(albumList.get(adapterPosition))
                Log.d("AlbumRVA", "click${albumList.get(adapterPosition)}")
            }
        }
        fun bind(album : Album){
            binding.itemTitleTv.text = album.title
            binding.itemSingerTv.text = album.singer
           // binding.itemAlbumImgIv.setImageResource(album.coverImg!!)
        }

    }
}