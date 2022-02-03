package com.sal7one.musicswitcher


import DataStoreProvider
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.sal7one.musicswitcher.utils.songExtractor


class DeepLinkHandler : AppCompatActivity() {

    private lateinit var viewModel: ApplicationViewModel
    private lateinit var data: Uri
    private lateinit var dataStoreProvider: DataStoreProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreProvider = DataStoreProvider.getInstance(this)

        viewModel = ViewModelProvider(this, MyViewModelFactory(dataStoreProvider)).get(ApplicationViewModel::class.java)
        data = intent?.data!!

        var action = intent?.action // Action to play music TODO analyze
        viewModel.handleDeepLink(data)

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

       viewModel.sameApp.observe(this, {
           if(it){
               val i = Intent()
               var appPackage =  sameAppPackage(data.toString())
               i.setPackage(appPackage)
               i.setAction(action)
               i.setData(data)
               startActivity(i)
               finishAndRemoveTask();
           }
        })

        viewModel.diffrentApp.observe(this, {
            if(it){
                var Songurl =data.toString()



                val stringRequest = StringRequest(Request.Method.GET, Songurl,
                    { response ->

                        var songName = songExtractor.ExtractFromURL(Songurl, response)

                        var searchURL = viewModel.searchLink.value
                        val query: String = Uri.encode(songName, "utf-8")
                        SwitchToApp(searchURL + query, action)
                    },
                    {
                        Log.d("MUSICMEE","Volley Error")

                    }
                )
                queue.add(stringRequest)

            }
        })

    }

    private fun SwitchToApp(songName: String, action: String?) {

        var uri = Uri.parse(songName)
        val i = Intent()
        i.setPackage(viewModel.Musicpackage.value)
        i.setAction(action)
        i.setData(uri)
        startActivity(i)
        finishAndRemoveTask();

    }

    private fun sameAppPackage(currentLink: String) : String {
            if(currentLink.contains( "open.spotify.com")){
                return Constants.SPOTIFY_PACKAGE.link
            }
            else if(currentLink.contains( "music.apple.com")){
                return Constants.APPLE_MUSIC_PACKAGE.link
            }
            else if(currentLink.contains( "play.anghami.com")){
                return Constants.ANGHAMI_PACKAGE.link
            }
            else if(currentLink.contains( "deezer.com")){
                return Constants.DEEZER_PACKAGE.link
            }
            else if(currentLink.contains( "music.youtube.com")){
                return Constants.YT_MUSIC_PACKAGE.link
            }
        return Constants.SPOTIFY_PACKAGE.link
    }

}