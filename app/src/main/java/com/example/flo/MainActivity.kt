package com.example.flo

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.data.Album
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.ui.home.HomeFragment
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var gson : Gson = Gson()
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var song : Song

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().add(R.id.main_frm, HomeFragment()).commit()

        initNavigation()
        //inputDummyAlbums()
        //inputDummySongs()

        hideSystemUI()
        binding.mainPlayerLayout.setOnClickListener {
            //startActivity(Intent(this,SongActivity::class.java))
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this@MainActivity, SongActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val getSong = getSharedPreferences("song", MODE_PRIVATE)
        val songId = getSong.getInt("songId", 0)

        //val songDB = SongDatabase.getInstance(this)!!
        //song = if (songId == 0){
        //    songDB.songDao().getSong(1)
        //} else{
        //    songDB.songDao().getSong(songId)
        //}
        //setMiniPlayer(song)
    }


    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }


    private fun initNavigation() {
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

            }
            false
        }


    }
    private fun setMiniPlayer(song : Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerArtistTv.text = song.artist
        binding.mainPlayerTimeSb.progress = song.second*1000/song.playTime

        val music = resources.getIdentifier(song.music, "raw", this.packageName)

        mediaPlayer = MediaPlayer.create(this, music)
        if(song.isPlaying){
            binding.mainPauseBtn.visibility = View.VISIBLE
            binding.mainMiniplayerBtn.visibility = View.GONE
        } else{
            binding.mainPauseBtn.visibility = View.GONE
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
        }
    }
    private fun inputDummyAlbums() {
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if (albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                1,
                "IU 5th Album 'LILAC'", "아이유 (IU)", R.drawable.img_album_exp2
            )
        )

        songDB.albumDao().insert(
            Album(
                2,
                "Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp
            )
        )

        songDB.albumDao().insert(
            Album(
                3,
                "iScreaM Vol.10 : Next Level Remixes", "에스파 (AESPA)", R.drawable.gaseupgi
            )
        )

        songDB.albumDao().insert(
            Album(
                4,
                "MAP OF THE SOUL : PERSONA", "방탄소년단 (BTS)", R.drawable.onmyway
            )
        )

        songDB.albumDao().insert(
            Album(
                5,
                "GREAT!", "모모랜드 (MOMOLAND)", R.drawable.ddareungi
            )
        )

    }


    private fun inputDummySongs() {
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return
        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                1
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                1
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_lilac",
                R.drawable.img_album_exp,
                "",
                false,
                2
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter (Hotter Remix)",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_lilac",
                R.drawable.img_album_exp,
                "",
                false,
                2
            )
        )

        songDB.songDao().insert(
            Song(
                "Butter (Sweeter Remix)",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_lilac",
                R.drawable.img_album_exp,
                "",
                false,
                2
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                3
            )
        )

        songDB.songDao().insert(
            Song(
                "Next Level (IMLAY Remix)",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                3
            )
        )

        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                4
            )
        )

        songDB.songDao().insert(
            Song(
                "소우주 (Mikrokosmos)",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                4
            )
        )

        songDB.songDao().insert(
            Song(
                "Make It Right",
                "방탄소년단 (BTS)",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                4
            )
        )

        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                5
            )
        )

        songDB.songDao().insert(
            Song(
                "궁금해",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                "",
                false,
                5
            )
        )
    }
}

