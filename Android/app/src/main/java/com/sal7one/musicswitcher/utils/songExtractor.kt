package com.sal7one.musicswitcher.utils

import android.util.Log
import androidx.core.text.HtmlCompat

class songExtractor {

    companion object{
        fun ExtractFromURL(url: String, response : String) : String{

            var reponseString :String
            var begin = 7
            var end = 0

            if(url.contains("open.spotify.com")){
                end = 9
            }else if(url.contains("music.apple.com")){
                end = 14

            }else if(url.contains("play.anghami.com")){
                end = 17
            }else if(url.contains("deezer.com")){
                end = 29

                reponseString = response.substring(response.indexOf("<title>")+begin, response.lastIndexOf("</title>")-end)

                val  songnamefirst = reponseString.substring(reponseString.lastIndexOf("-")+1) +" "+ reponseString.substring(0, reponseString.lastIndexOf("-"))


                return HtmlCompat.fromHtml(songnamefirst, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() // Escape speical characters in html ' == N&#039;

            }else if(url.contains("music.youtube.com")){
                end = 16
                val respo =  response

                val artist = respo.substring(respo.lastIndexOf("·")+1, respo.lastIndexOf("℗"))
                val songname = response.substring(response.lastIndexOf("<title>")+begin, response.lastIndexOf("</title>")-end)
                return HtmlCompat.fromHtml(songname +" " + artist, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() // Escape speical characters in html ' == N&#039;
            }else{
                Log.d("SONGEXTRACTOR","Link not handled value below..")
                Log.d("SONGEXTRACTOR", response.substring(response.indexOf("<title>")+begin, response.lastIndexOf("</title>")-end))
            }

            reponseString = response.substring(response.indexOf("<title>")+begin, response.lastIndexOf("</title>")-end)
            reponseString = reponseString.replace("song by", " ")

            return HtmlCompat.fromHtml(reponseString, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() // Escape speical characters in html ' == N&#039;

        }

        //idk  why but it's here
//        fun typeofLink(url: String) : String{
//            if(url.contains("/playlist/"))
//                return "playlist"
//
//            else if(url.contains("/album/")){
//                if(url.contains("?i="))
//                    return "song"
//                else
//                    return "album"
//            }
//            else
//                return "song"
//
//        }

    }
}