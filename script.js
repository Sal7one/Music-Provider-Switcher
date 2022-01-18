console.log("Switcher is running");


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

    if(values['userPlayer'] == null){
        console.log("first run detected"); // Can be a background option on install.
        SetChrome("userPlayer", "Spotify");
    }
    else{
        console.log("user player is...");
        console.log(values['userPlayer']);

        configureApp(values['userPlayer']);
        obeserveTimeLine();
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

function obeserveTimeLine(){

    getTweets();

}

function getsongName(song){
    let thesong = "";
    let artist = "";
    if(song.href.includes("spotify")){

        thesong = song.parentNode.children[2].children[1].children[0].children[0].children[1].children[0].children[0].innerText;
        artist = song.parentNode.children[2].children[1].children[0].children[0].children[2].children[0].children[0].innerText;
        thesong += " " + artist.substring(0, artist.indexOf("·"));
    }
    else if(song.href.includes("anghami")){

        thesong =  song.parentNode.children[2].children[1].children[0].children[0].children[1].children[0].children[0].innerText;
        thesong = thesong.substring(0, thesong.indexOf("|"));
    }
    else if(song.href.includes("apple"))
    thesong =  song.parentNode.children[2].children[0].children[0].children[0].children[1].children[0].getAttribute("aria-label");

   
     return thesong.replaceAll(" ", "%20");
}



function changeLink(element, linkofsong){
    element.children[2].children[0].children[0].href = linkofsong;
    element.children[2].children[1].children[0].href = linkofsong;
}

function getTweets(){
  var arriveOptions = {
    fireOnAttributesModification: true, 
    onceOnly: true                   ,
    existing: true               
};
  try {
    document.arrive(`[data-testid="tweet"]`,arriveOptions, ()=>{
      console.log("TimeLine Changed!")
      //TODO Thread of music
      let isSingleTweet = location.href.includes("status");
      if(isSingleTweet){

      }else{
      let num = 0;

    document.querySelectorAll(choosenPlayers).forEach(song =>{
      // Will get 
      console.log(`Num of songs:  ${++num}`)
      songName = getsongName(song);
      changeLink(song.parentNode, choosenSearch + songName)
      });
    }

    

      // console.log("Arrived") Timeline arrived

      // let timeLine = document.querySelectorAll(`section[role="region"]`)[0];
      // console.log("Time Line is")
      // let tweets = timeLine.children[1].children[0].children;
      // return tweets;
    })
   
  } catch (error) {
    console.log("timeline or children error")
    console.log(error)
  }
  }



