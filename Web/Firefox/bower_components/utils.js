function SetFireFox(key, thingy) {
  browser.storage.local.set({ [key]: thingy });
  }

  function Get() {
    return new Promise(function (resolve, _reject) {
      let gettingItem = browser.storage.local.get();
gettingItem.then((gotvalues) => {
  resolve(gotvalues);
});
    });
  }
