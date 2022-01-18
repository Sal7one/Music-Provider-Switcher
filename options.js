 // Local extnesion handler



 try {
    let messageAlert = document.querySelector("#hiddenpara");

     
 let updatebtn = document.querySelector("#updaterbtn");

    updatebtn.addEventListener("click", ()=>{
      let musicProvider = document.querySelector("#musicProvider");

        console.log("Requested provider change!")
      SetChrome("userPlayer",  musicProvider.value )

      messageAlert.innerText ="Change complete. Refresh Twitter!";
    })

 } catch (error) {
     
    }


Get().then(values=>{

        if(values['userPlayer'] == null){
            console.log("first run detected"); // Can be a background option on install.
            SetChrome("userPlayer", "Spotify");
            messageAlert.innerText ="Hello! First time here? Spotify is the deafult choice.. change it if you want";

        }
        else{
            // Change Selection ui based on saved value
            $('#musicProvider').val(values['userPlayer']).change();
        }
      })
    

