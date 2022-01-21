
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

    // Change ui based on saved data
Get().then(values=>{

        if(values['MusicProvider'] == null){
          try {
            messageAlert.innerText ="Hello! First time here? Spotify is the deafult choice.. change it if you want";
          } catch (error) {
          }
            console.log("first run detected"); // Can be a background option on install.
            Set("MusicProvider", "Spotify");

        }
        else{
            // Change Selection element ui based on saved value
            $('#musicProvider').val(values['MusicProvider']).change();
        }
      })
    

