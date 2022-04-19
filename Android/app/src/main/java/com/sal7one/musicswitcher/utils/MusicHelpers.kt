package com.sal7one.musicswitcher.utils

import android.util.Log
import androidx.core.text.HtmlCompat

class MusicHelpers {
    companion object {
        fun extractFromURL(url: String, response: String): String {
            var responseString: String
            val begin = 7
            var end = 0

            when {
                url.contains(StringConstants.SPOTIFY.link) -> {
                    end = 9
                }
                url.contains(StringConstants.APPLE_MUSIC.link) -> {
                    end = 14

                }
                url.contains(StringConstants.ANGHAMI.link) -> {
                    end = 17
                }
                url.contains(StringConstants.DEEZER.link) -> {
                    end = 29
                    responseString = response.substring(
                        response.indexOf("<title>") + begin,
                        response.lastIndexOf("</title>") - end
                    )
                    val songNameFirst =
                        responseString.substring(responseString.lastIndexOf("-") + 1) + " " + responseString.substring(
                            0,
                            responseString.lastIndexOf("-")
                        )

                    return HtmlCompat.fromHtml(songNameFirst, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        .toString() // Escape special characters in html ' == N&#039;
                }
                url.contains(StringConstants.YT_MUSIC.link) -> {
                    end = 16

                    if (typeofLink(url) == "playlist") {
                        var songName =
                            response.substring(response.indexOf("name=\"twitter:title\""))
                        songName = songName.substring(
                            songName.indexOf("content=") + 9,
                            songName.indexOf("\"><meta name=\"twitter:description\" ")
                        )

                        return HtmlCompat.fromHtml(
                            songName,
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString()
                    } else {

                        val songName = response.substring(
                            response.lastIndexOf("<title>") + begin,
                            response.lastIndexOf("</title>") - end
                        )

                        val artist = response.substring(
                            response.lastIndexOf("·") + 1,
                            response.lastIndexOf("℗")
                        )
                        return HtmlCompat.fromHtml(
                            "$songName $artist",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        ).toString() // Escape special characters in html ' == N&#039;
                    }
                }
                else -> {
                    Log.d("SONG_EXTRACTOR", "Link not handled value below..")
                    Log.d("SONG_EXTRACTOR", url)
                    Log.d(
                        "SONG_EXTRACTOR",
                        response.substring(
                            response.indexOf("<title>") + begin,
                            response.lastIndexOf("</title>") - end
                        )
                    )
                }
            }

            responseString = response.substring(
                response.indexOf("<title>") + begin,
                response.lastIndexOf("</title>") - end
            )
            responseString = responseString.replace("song by", " ")

            if (url.contains("music.apple.com")) {
                responseString = responseString.replace("لـ", " ")  // apple music problems
                responseString = responseString.replace("ع", " ") // apple music problems
                responseString =
                    responseString.replace("\\s\\s+".toRegex(), " ") // remove extra white space
            }
            return responseString// Escape special characters in html ' == N&#039;
        }

        fun getMusicAppPackage(currentLink: String): String {
            when {
                currentLink.contains(StringConstants.SPOTIFY.link) -> {
                    return StringConstants.SPOTIFY_PACKAGE.link
                }
                currentLink.contains(StringConstants.APPLE_MUSIC.link) -> {
                    return StringConstants.APPLE_MUSIC_PACKAGE.link
                }
                currentLink.contains(StringConstants.ANGHAMI.link) -> {
                    return StringConstants.ANGHAMI_PACKAGE.link
                }
                currentLink.contains(StringConstants.DEEZER.link) -> {
                    return StringConstants.DEEZER_PACKAGE.link
                }
                currentLink.contains(StringConstants.YT_MUSIC.link) -> {
                    return StringConstants.YT_MUSIC_PACKAGE.link
                }
                else -> return StringConstants.SPOTIFY_PACKAGE.link
            }
        }

        fun typeofLink(url: String): String {
            return when {
                url.contains("/playlist/") or url.contains("playlist?") -> "playlist"
                url.contains("/album/") -> {
                    if (url.contains("?i=")) // apple music song
                        "song"
                    else
                        "album"
                }
                else -> "song"
            }
        }
    }
}