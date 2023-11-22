document.querySelector("#username").addEventListener("input", (event) => {
  event.target.value = event.target.value.replace(/[^a-z0-9]/gi, "");
});
