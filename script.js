const AppleMusic = `link[href="//music.apple.com"]`;
const Spotify = `link[href="//open.spotify.com"]`;
const Anghami = `link[href="//play.anghami.com"]`;
const AppleMusicSearch = "https://music.apple.com/us/search?term=";
const SpotifySearch = "https://open.spotify.com/search/";
const AnghamiSearch = "https://play.anghami.com/search/";

let songName = "";
let choosenProvider = "";
let choosenSearch = "";
let songsList = "";

let searchforPlaylistandAlbums = false;

Get().then(savedData=>{
  let MusicProvider = savedData['MusicProvider']
  let PlayListChoice = savedData['searchforPlaylistandAlbums']

    if(MusicProvider == null){
        Set("MusicProvider", "Spotify");
        Set("searchforPlaylistandAlbums", false);
        configureApp("Spotify");
        observeTweets();
    }
    else{
          if(PlayListChoice == null)
          Set("searchforPlaylistandAlbums", false);
          else
          searchforPlaylistandAlbums = PlayListChoice;  

        configureApp(MusicProvider);
        observeTweets();
    }
  })
  
  // Based on users music provider prepare the query for querySelector (see global vars) ^ 
  function configureApp(MusicProvider){
    switch(MusicProvider){

      case "Spotify": choosenProvider = AppleMusic + "," + Anghami; 
                      choosenSearch = SpotifySearch;
      break;
      case "Apple": choosenProvider = Spotify + "," + Anghami;
                    choosenSearch = AppleMusicSearch;
      break;
      case "Anghami": choosenProvider = AppleMusic + "," + Spotify; 
                      choosenSearch = AnghamiSearch;
      break;
      default: console.log("Error in Music Switcher saved Value");
  }

  }

function getSongName(song){
    let thesong = "";
    let artist = "";
    let newsongname = "";

    if(song.href.includes("spotify")){

        thesong = song.parentNode.children[2].children[1].children[0].children[0].children[1].children[0].children[0].innerText;
        artist = song.parentNode.children[2].children[1].children[0].children[0].children[2].children[0].children[0].innerText;
        if(artist.includes("Spotify") && artist.includes("Playlist")){
        console.log("Spotify playlist detected");
          return "Playlist-->";
        }

        // Spotify sperates the artist and song names with this symbol ·
        thesong += " " + artist.substring(0, artist.indexOf("·"));
    }
    else if(song.href.includes("anghami")){

      thesong =  song.parentNode.children[2].children[1].children[0].children[0].children[1].children[0].children[0].innerText;
      newsongname = thesong.substring(0, thesong.indexOf("|"));

      // Anghami if name is too long they break the | rule (then we take the full song name)
      (newsongname.length < 1) ? thesong = thesong : thesong = newsongname;
    }
    else if(song.href.includes("apple")){

      thesong = song.parentNode.children[2].children[1].children[0].children[0].children[1].children[0].children[0].innerText;
      let songdetails = song.parentNode.children[2].children[1].children[0].children[0].children[2].children[0].children[0].innerText

      // // Apple music arabic tweet replace with space
      if(thesong.includes("لـ"))
      thesong = thesong.replaceAll("لـ", " ");

      // TODO array check of apple array of words and other music providers arrrays
      if(songdetails.includes("Playlist")){
        return "Playlist-->" + thesong;
      }else if(songdetails.includes("Album")){
        return "Album-->"+thesong
      }

    }

    // Return the song with URL ready format 
     return thesong;
    }


function changeLink(element, linkofsong){

    // Song Cover art -> then anchor element
    songCoverArt = element.children[2].children[0];
    songCoverArt.children[0].href = linkofsong;

    // Song Title part -> then anchor element
    songTitle = element.children[2].children[1];
    songTitle.children[0].href = linkofsong;
}


function findMusicTweets(){

  let isSong = true;

  // Using saved provider query select all music tweets 
  document.querySelectorAll(choosenProvider).forEach(song =>{

    // Get song name
    songName = getSongName(song);
    
    if((songName.includes("Playlist-->") || songName.includes( "Album-->"))){
      songName = songName.substring(songName.indexOf("-->")+3);

      if(!searchforPlaylistandAlbums)
      isSong = false;
    }

    // Change Tweet link of the song to search for it in our chosen provider
      if(isSong)
      changeLink(song.parentNode, choosenSearch + encodeURIComponent(songName));
    
   // Mark Song as modifed 
    song.href = "#";
    });
}


function observeTweets(){

  var arriveOptions = {
    fireOnAttributesModification: true, // Tweet ViewHolder element data changed
    onceOnly: true, // for every Tweet
    existing: true  // If Tweet is already loaded when this code ran
};

// Use Arrive js to obeserve all tweets (around 25 max are loaded and if the data changes this code will also run)
  try {
    document.arrive(`[data-testid="tweet"]`, arriveOptions, (tweet)=>{
      tweet.addEventListener('mouseover', function() {
        findMusicTweets();
      });
    })
   
  } catch (error) {
    console.log("timeline or children error")
    console.log(error)
  }
  }



