const button = document.getElementById("theme_button");
const root = document.documentElement;

const light = {
  "--text": "#070708",
  "--background": "#edecee",
  "--primary": "#403c3b",
  "--secondary": "#cecdd0",
  "--accent": "#646d66",
};

const dark = {
  "--text": "#f7f7f8",
  "--background": "#232224",
  "--primary": "#d5d1cf",
  "--secondary": "#413f43",
  "--accent": "#a3aca5",
};

const chooseTheme = (theme) => {
  if (theme) {
    setTheme(dark);
    window.localStorage.setItem("darkTheme", 1);
  } else {
    setTheme(light);
    window.localStorage.setItem("darkTheme", 0);
  }
};

const setTheme = (theme) => {
  Object.keys(theme).forEach((key) => {
    root.style.setProperty(key, theme[key]);
  });
  button.innerHTML = theme == dark ? "☀️" : "🌙";
};

button.addEventListener("click", () => {
  let theme = Number(window.localStorage.getItem("darkTheme"));
  chooseTheme(!theme);
});

let darkTheme = Number(window.localStorage.getItem("darkTheme"));
console.log(darkTheme);
if (darkTheme === null) {
  console.log("null");
  darkTheme = window.matchMedia("(prefers-color-scheme: dark)").matches;
  window.localStorage.setItem("darkTheme", darkTheme ? 1 : 0);
}
console.log(darkTheme);

setTheme(darkTheme ? dark : light);
