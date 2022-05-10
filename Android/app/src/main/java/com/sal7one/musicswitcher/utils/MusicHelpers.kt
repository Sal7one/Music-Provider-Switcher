package com.sal7one.musicswitcher.utils

import androidx.core.text.HtmlCompat

class MusicHelpers {
    companion object {
        fun extractFromURL(url: String, response: String): String {
            var responseString: String
            val begin = 7
            var end = 0

            when {
                url.contains(StringConstants.SPOTIFY.value) -> {
                    end = 9
                }
                url.contains(StringConstants.APPLE_MUSIC.value) -> {
                    end = 14

                }
                url.contains(StringConstants.ANGHAMI.value) -> {
                    end = 17
                }
                url.contains(StringConstants.DEEZER.value) -> {
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
                url.contains(StringConstants.YT_MUSIC.value) -> {
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
                }
            }

            responseString = response.substring(
                response.indexOf("<title>") + begin,
                response.lastIndexOf("</title>") - end
            )
            responseString = responseString.replace("song by", " ")

            if (url.contains(StringConstants.APPLE_MUSIC.value)) {
                responseString = responseString.replace("لـ", " ")  // apple music problems
                responseString = responseString.replace("ع", " ") // apple music problems
                responseString =
                    responseString.replace("\\s\\s+".toRegex(), " ") // remove extra white space
            }
            return responseString// Escape special characters in html ' == N&#039;
        }

        fun getMusicAppPackage(currentLink: String): String {
            when {
                currentLink.contains(StringConstants.SPOTIFY.value) -> {
                    return StringConstants.SPOTIFY_PACKAGE.value
                }
                currentLink.contains(StringConstants.APPLE_MUSIC.value) -> {
                    return StringConstants.APPLE_MUSIC_PACKAGE.value
                }
                currentLink.contains(StringConstants.ANGHAMI.value) -> {
                    return StringConstants.ANGHAMI_PACKAGE.value
                }
                currentLink.contains(StringConstants.DEEZER.value) -> {
                    return StringConstants.DEEZER_PACKAGE.value
                }
                currentLink.contains(StringConstants.YT_MUSIC.value) -> {
                    return StringConstants.YT_MUSIC_PACKAGE.value
                }
                else -> return StringConstants.SPOTIFY_PACKAGE.value
            }
        }

        fun packageNameToApp(currentLink: String): String {
            when {
                currentLink.contains(StringConstants.SPOTIFY_PACKAGE.value) -> {
                    return StringConstants.SPOTIFY_NAME.value
                }
                currentLink.contains(StringConstants.APPLE_MUSIC_PACKAGE.value) -> {
                    return StringConstants.APPLE_MUSIC_NAME.value
                }
                currentLink.contains(StringConstants.ANGHAMI_PACKAGE.value) -> {
                    return StringConstants.ANGHAMI_NAME.value
                }
                currentLink.contains(StringConstants.DEEZER_PACKAGE.value) -> {
                    return StringConstants.DEEZER_NAME.value
                }
                currentLink.contains(StringConstants.YT_MUSIC_PACKAGE.value) -> {
                    return StringConstants.YT_MUSIC_NAME.value
                }
                else -> return ""
            }
        }

        fun typeofLink(url: String): String {
            return when {
                url.contains("/playlist/") or url.contains("playlist?") -> StringConstants.LINK_TYPE_PLAYLIST.value
                url.contains("/album/") -> {
                    if (url.contains("?i=")) // apple music song
                        StringConstants.LINK_TYPE_SONG.value

                    else
                        StringConstants.LINK_TYPE_ALBUM.value
                }
                else -> StringConstants.LINK_TYPE_SONG.value

            }
        }
    }
}