
 try {

    let messageAlert = document.querySelector("#hiddenpara");
    let updatebtn = document.querySelector("#updaterbtn");

    updatebtn.addEventListener("click", ()=>{
      let musicProvider = document.querySelector("#musicProvider");
      let albumPlaylist = document.querySelector("#playListbutton");


      Set("MusicProvider",  musicProvider.value )
      Set("searchforPlaylistandAlbums",  albumPlaylist.checked)
      messageAlert.innerText ="Change complete. Refresh Twitter!";
    })

 } catch (error) {
    }

    // Change ui based on saved data
Get().then(values=>{



        if(values['MusicProvider'] == null){
          try {
          let messageAlert = document.querySelector("#hiddenpara");
            messageAlert.innerText ="Hello! First time here? Spotify is the deafult choice.. change it if you want";
          } catch (error) {
            console.log(error)
          }
            console.log("first run detected"); // Can be a background option on install.
            Set("MusicProvider", "Spotify");

        }
        else{
          try {
            // Change Selection element ui based on saved value
            $('#musicProvider').val(values['MusicProvider']).change();
            document.querySelector("#playListbutton").checked = values['searchforPlaylistandAlbums']
            
          } catch (error) {
            
          }

        }
      })
    

