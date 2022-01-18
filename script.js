// Scroll handler
var didScroll = false;
var lastScrollTop = 0;
var delta = 10;


$(window).scroll(function (event) {
    didScroll = true;
  });

  setInterval(function () {
    if (didScroll) {
      hasScrolled();
      didScroll = false;
    }
  }, 250);

  function hasScrolled() {
    var st = $(this).scrollTop();

    // Return if they scroll less than 20px (delta)
    if (Math.abs(lastScrollTop - st) <= delta) return;

    // Do what you want here

    try {
        replaceSite();
    } catch (error) {}

    lastScrollTop = st;
  }


console.log("Switcher is running");

const AppleMusic = `link[href="//music.apple.com"]`;
const Spotify = `link[href="//open.spotify.com"]`;
const Anghami = `link[href="//play.anghami.com"]`;

const AppleMusicSearch = "https://music.apple.com/us/search?term=";
const SpotifySearch = "https://open.spotify.com/search/";
const AnghamiSearch = "https://play.anghami.com/search/";

const urlSpace = "%20";
let songName = "";

// Static for now..
let userPlayer = "Spotify";
let choosenPlayers = "";
let choosenSearch = "";

switch(userPlayer){
    case "Spotify": choosenPlayers = AppleMusic + "," + Anghami; choosenSearch = SpotifySearch;
    break;
    case "Apple": choosenPlayers = Spotify + "," + Anghami; choosenSearch = AppleMusicSearch;
    break;
    case "Anghami": choosenPlayers = AppleMusic + "," + Spotify; choosenSearch = AnghamiSearch;
    break;
    default : choosenPlayers =  AppleMusic;
}


function replaceSite(){
    let songs = document.querySelectorAll(choosenPlayers);

songs.forEach(song =>{
    // Will get 
    console.log(song)
    songName = getsongName(song);
    changeLink(song.parentNode, choosenSearch + songName)
    })
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

    

// TODO Perfrances 

// Detector For each card.. 

// Ignore main card

// TODO Scroll handler


function changeLink(element, linkofsong){
    element.children[2].children[0].children[0].href = linkofsong;
    element.children[2].children[1].children[0].href = linkofsong;
    console.log( element.children[2].children[1].children[0].href)
}
