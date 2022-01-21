
 try {
    let messageAlert = document.querySelector("#hiddenpara");
    let updatebtn = document.querySelector("#updaterbtn");

    updatebtn.addEventListener("click", ()=>{
      let musicProvider = document.querySelector("#musicProvider");

      Set("MusicProvider",  musicProvider.value )
      messageAlert.innerText ="Change complete. Refresh Twitter!";
    })

 } catch (error) {
    }

Get().then(values=>{

        if(values['MusicProvider'] == null){
            console.log("first run detected"); // Can be a background option on install.
            Set("MusicProvider", "Spotify");
            messageAlert.innerText ="Hello! First time here? Spotify is the deafult choice.. change it if you want";

        }
        else{
            // Change Selection element ui based on saved value
            $('#musicProvider').val(values['MusicProvider']).change();
        }
      })
    

