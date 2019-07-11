package com.example.espl.sampleappkotlin.exoplayer

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import kotlinx.android.synthetic.main.activity_exo_player.*
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.util.Util
import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.View
import com.example.espl.sampleappkotlin.R
import com.example.espl.sampleappkotlin.objectbox.ObjectBox
import com.example.espl.sampleappkotlin.objectbox.UserLikes
import com.example.espl.sampleappkotlin.objectbox.UserLikes_
import io.objectbox.Box
import kotlinx.android.synthetic.main.custom_exo_controller_view.*

class ExoPlayerActivity : AppCompatActivity() {
    lateinit var player : ExoPlayer
    var myStringId:String = ""
    var myStringUrl:String = ""
    private lateinit var payloadBox: Box<UserLikes?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)

        val extras = intent.extras ?: return
        myStringId = extras.getString(this@ExoPlayerActivity.resources.getString(R.string.id))
        myStringUrl = extras.getString(this@ExoPlayerActivity.resources.getString(R.string.url))

        val userInit = getdataFrDB(myStringId) ?: UserLikes(0, myStringId, 0, 0)
        exo_like.text= userInit.like.toString()
        exo_dislike.text= userInit.dislike.toString()

        exo_like.setOnClickListener {
                val userl = getdataFrDB(myStringId) ?: UserLikes(
                    0,
                    myStringId,
                    0,
                    0
                )

            userl.like=(userl.like!! +1);
            exo_like.text= userl.like.toString()
            setdataToDB(userl)
        }

        exo_dislike.setOnClickListener {
            val userl = getdataFrDB(myStringId) ?: UserLikes(
                0,
                myStringId,
                0,
                0
            )
            userl.dislike=(userl.dislike!! +1);
            exo_dislike.text= userl.dislike.toString()
            setdataToDB(userl)
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        video_view.setSystemUiVisibility(
    View.SYSTEM_UI_FLAG_LOW_PROFILE
    or View.SYSTEM_UI_FLAG_FULLSCREEN
    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
)
}

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
            DefaultHttpDataSourceFactory("exoplayer-activity")
        ).createMediaSource(uri)
    }

    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        )

        video_view.setPlayer(player)
        player.setPlayWhenReady(true)
        val uri = Uri.parse(myStringUrl)
        val mediaSource = buildMediaSource(uri)
        player.prepare(mediaSource, true, false)
    }

    fun setdataToDB(u: UserLikes){
        payloadBox = ObjectBox.boxStore.boxFor(UserLikes::class.java)
        payloadBox.put(u)
    }

    fun getdataFrDB(id:String ): UserLikes?{
        val box = ObjectBox.boxStore.boxFor(UserLikes::class.java)
        val mypayload = box.query().equal(UserLikes_.userId, id).build().findFirst()
        return mypayload
    }

    private fun releasePlayer() {
        if (player != null) {
            player.release()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }
}
