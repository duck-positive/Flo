package com.example.flo

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.databinding.ToastCustomBinding
import com.example.flo.databinding.ToastDialogBinding
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.TimeUnit

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding

    private var mediaPlayer: MediaPlayer? = null
    lateinit var timer: Timer

    private var songs = ArrayList<Song>()
    private var nowPos = 0
    private lateinit var songDB: SongDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()
        initPlayList()

        initSong()
        initClickListener()

    }
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }

    private fun initSong(){
        val getSong = getSharedPreferences("song", MODE_PRIVATE)
        val songId = getSong.getInt("songId",0)
        nowPos = getPlayingSongPosition(songId)

        startTimer()
        setPlayer(songs[nowPos])
    }
    private fun getPlayingSongPosition(songId : Int) : Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }
    private fun setPlayerStatus(isPlaying: Boolean){
        timer.isPlaying = isPlaying
        songs[nowPos].isPlaying = isPlaying

        if (isPlaying) {
            binding.songPlayerPlayIb.visibility = View.GONE
            binding.songPlayerPauseIb.visibility = View.VISIBLE

            mediaPlayer?.start()
        } else {
            binding.songPlayerPlayIb.visibility = View.VISIBLE
            binding.songPlayerPauseIb.visibility = View.GONE

            mediaPlayer?.pause()
        }
    }

    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    override fun onPause() {
        super.onPause()

        songs[nowPos].second = (songs[nowPos].playTime * binding.songPlayerTimeSb.progress) / 1000
        songs[nowPos].isPlaying = false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("songId", songs[nowPos].id)
        editor.apply()
    }
    private fun setPlayer(song: Song) {
        val music = resources.getIdentifier(song.music, "raw", this.packageName)

        binding.songPlayerTitleTv.text = song.title
        binding.songPlayerArtistTv.text = song.artist
        binding.songPlayerStartTimeTv.text =
            String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songPlayerEndTimeTv.text =
            String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songPlayerAlbumIv.setImageResource(song.coverImg!!)
        binding.songPlayerTimeSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)

        if (song.isLike) {
            binding.songPlayerLikeIb.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songPlayerLikeIb.setImageResource(R.drawable.ic_my_like_off)
        }

        mediaPlayer = MediaPlayer.create(this, music)
    }
    private fun initClickListener(){
        binding.songPlayerExitIb.setOnClickListener {
            finish()
        }
        binding.songPlayerPlayIb.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPlayerPauseIb.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPlayerPreviousIb.setOnClickListener {
            moveSong(-1)
        }
        binding.songPlayerNextIb.setOnClickListener {
            moveSong(+1)
        }
        binding.songPlayerLikeIb.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }
    private fun moveSong(direct : Int){
        if (nowPos + direct < 0){
            Toast.makeText(this,"first Song",Toast.LENGTH_SHORT).show()
            return
        }
        if (nowPos + direct >= songs.size){
            Toast.makeText(this,"Last Song",Toast.LENGTH_SHORT).show()
            return
        }
        nowPos += direct
        timer.interrupt()
        startTimer()
        mediaPlayer?.release()
        mediaPlayer = null
        setPlayer(songs[nowPos])
    }
    private fun setLike(isLike : Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)
        val context = this
        if (isLike){
            binding.songPlayerLikeIb.setImageResource(R.drawable.ic_my_like_off)
            SampleToast.createToast(context, "좋아요 한 곡이 취소되었습니다")?.show()
        } else{


            binding.songPlayerLikeIb.setImageResource(R.drawable.ic_my_like_on)
//            var v1 = layoutInflater.inflate(R.layout.toast_dialog, null)
//            var v2 = Toast(this)
//            v2.view = v1
//            v2.show()
            //SampleToast.createToast(context, "좋아요 한 곡에 담겼습니다")?.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 리소스 해제
        mediaPlayer = null // 미디어 플레이어 해제
    }

    fun animation(view : View){


    }

    inner class Timer(private val playTime:Int,var isPlaying: Boolean) : Thread(){
        private var millis: Long = 0

        @SuppressLint
        override fun run() {
            try{
                while (true) {
                    if (millis >= playTime){
                        break
                    }
                    if (isPlaying){
                        sleep(1000) // second를 millis로
                        millis++

                        runOnUiThread {
                            binding.songPlayerTimeSb.progress =
                                (millis * 1000 / playTime).toInt()
                            binding.songPlayerStartTimeTv.text = String.format(
                                "%02d:%02d",
                                TimeUnit.SECONDS.toMinutes(millis),
                                millis % 60
                            )
                        }
                    }
                }

            }catch (e : InterruptedException){

            }

        }
    }


}