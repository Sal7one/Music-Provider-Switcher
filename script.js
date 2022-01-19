
const AppleMusic = `link[href="//music.apple.com"]`;
const Spotify = `link[href="//open.spotify.com"]`;
const Anghami = `link[href="//play.anghami.com"]`;
const AppleMusicSearch = "https://music.apple.com/us/search?term=";
const SpotifySearch = "https://open.spotify.com/search/";
const AnghamiSearch = "https://play.anghami.com/search/";

let songName = "";
let choosenPlayers = "";
let choosenSearch = "";
let songsList = "";


Get().then(values=>{
    console.log(values)
    if(values['userPlayer'] == null){
      SetFireFox("userPlayer", "Spotify");
        configureApp("Spotify");
        observeTweets();
    }
    else{
        configureApp(values['userPlayer']);
        observeTweets();
    }
  })

  function configureApp(userPlayer){
    switch(userPlayer){
      case "Spotify": choosenPlayers = AppleMusic + "," + Anghami; choosenSearch = SpotifySearch;
      break;
      case "Apple": choosenPlayers = Spotify + "," + Anghami; choosenSearch = AppleMusicSearch;
      break;
      case "Anghami": choosenPlayers = AppleMusic + "," + Spotify; choosenSearch = AnghamiSearch;
      break;
      default : choosenPlayers =  AppleMusic;
  }

  }

function getsongName(song){
    let thesong = "";
    let artist = "";
    let newsongname = "";

    if(song.href.includes("spotify")){

        thesong = song.parentNode.children[2].children[1].children[0].children[0].children[1].children[0].children[0].innerText;
        artist = song.parentNode.children[2].children[1].children[0].children[0].children[2].children[0].children[0].innerText;
        thesong += " " + artist.substring(0, artist.indexOf("·"));
    }
    else if(song.href.includes("anghami")){
      thesong =  song.parentNode.children[2].children[1].children[0].children[0].children[1].children[0].children[0].innerText;
      
      // Anghami if name is too long they break the | rule
        newsongname = thesong.substring(0, thesong.indexOf("|"));
        if(newsongname.length < 1)
        thesong = thesong;
        else
        thesong = newsongname;

    }
    else if(song.href.includes("apple")){

      
      thesong =  song.parentNode.children[2].children[0].children[0].children[0].children[1].children[0].getAttribute("aria-label");
      if(thesong.includes("لـ"))
      thesong = thesong.replaceAll("لـ", " ");
    }
     return thesong.replaceAll(" ", "%20");
}



function changeLink(element, linkofsong){
    element.children[2].children[0].children[0].href = linkofsong;
    element.children[2].children[1].children[0].href = linkofsong;
}


function changeMusic(){
        
  document.querySelectorAll(choosenPlayers).forEach(song =>{
    songName = getsongName(song);
    changeLink(song.parentNode, choosenSearch + songName)
   // Mark Song as modifed 
    song.href = "#"

    });
}


function observeTweets(){
  var arriveOptions = {
    fireOnAttributesModification: true, 
    onceOnly: true                   ,
    existing: true               
};
  try {
    document.arrive(`[data-testid="tweet"]`,arriveOptions, (tweet)=>{
      tweet.addEventListener('mouseover', function() {
        changeMusic();
      });
    })
   
  } catch (error) {
    console.log("timeline or children error")
    console.log(error)
  }
  }



