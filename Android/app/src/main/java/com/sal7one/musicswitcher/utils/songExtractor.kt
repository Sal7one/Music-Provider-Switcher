package com.sal7one.musicswitcher.utils

import androidx.core.text.HtmlCompat

class songExtractor {

    companion object{
        fun ExtractFromURL(url: String, response : String) : String{

            val begin = 7
            var end = 0

            if(url.contains("open.spotify.com")){
                end = 9
            }else if(url.contains("music.apple.com")){
                end = 14

            }else if(url.contains("play.anghami.com")){
                end = 17
            }

            var reponseString :String
            reponseString = response.substring(response.indexOf("<title>")+begin, response.lastIndexOf("</title>")-end)
            reponseString = reponseString.replace("song by", " ")

            return HtmlCompat.fromHtml(reponseString, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() // Escape speical characters in html ' == N&#039;

        }

        fun typeofLink(url: String) : String{
            if(url.contains("/playlist/"))
                return "playlist"

            else if(url.contains("/album/")){
                if(url.contains("?i="))
                    return "song"
                else
                    return "album"
            }
            else
                return "song"

        }

    }
}