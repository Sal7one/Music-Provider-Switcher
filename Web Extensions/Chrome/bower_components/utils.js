function SetChrome(key, thingy) {
    chrome.storage.local.set({ [key]: thingy });
  }

  function Get() {
    return new Promise(function (resolve, _reject) {
      chrome.storage.local.get(null, function (items) {
        resolve(items);
      });
    });
  }
