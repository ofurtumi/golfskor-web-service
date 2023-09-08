# Hugbúnaðarverkefni 1 - HBV501G 2023

## Hópur

- Þorvaldur Tumi Baldursson - [@ofurtumi](github.com/ofurtumi)
- Hákon Ingi Rafnsson - [@hakoningir](github.com/hakoningir)
- Svana Björg Birgisdóttir - [@svanabirgis](github.com/svanabirgis)
- Gunnar Björn Þrastarson - [@gunnarbjo](github.com/gunnarbjo)

## Uppsetning

Það þarf postgres server keyrandi á porti `5432` og verkefnið sett upp á móti honum. Stillingar eru gerðar í [þessari skrá](./src/main/resources/application.properties), stillingarnar útskýra sig nokkuð vel sjálfar.

## Keyrsla

Til að geta keyrt verkefnið upp þarf maven að vera sett upp á vélinni, hægt að nota `brew install maven` á Mac eða svipaða skipun á Linux. Til að keyra upp verkefnið þarf að keyra `mvn spring-boot:run` í rót verkefnisins. Verkefnið er aðgengilegt á `localhost:8080` eftir að það hefur keyrt upp.

## Feature

### 1. Skrá skor

- Skrá 9 eða 18 holur

### 2. Skoða gamla hringi (annara?) á velli

- Skoða par á velli

### 3. Skoða sína eigin eldri hringi

- Skoða hring á velli

### 4. Raða skori útfrá velli

- Skoða hring annara spilara útfrá völlum top 5 eða ehv

### 5. Uppfæra skori

- Fá hring í hendurnar

### 6. Eyða hringjum

- Eyðir hring

### 7. Reikna forgjöf

- Meðaltal af bestu 8 síðustu 20 hringjum

### 8. Skrá notanda

- Geta nýskráð
- Geta skráð inn
- Geta skráð út



### eh dæmi

Forritið okkar er fyrir golfara sem vilja halda utan um skorið sitt á einfaldan máta.

Golfskor er forrit til að skrá og halda utan um golfupplýsingar sem leyfir skráningu og skoðun
á skori eldri hringja.

Ólíkt núverandi lausnum (golfbox) þá mun okkar forrit vera einfalt í notkun og byrjendavænt.

Hvatningin á bakvið þetta er að golfbox (forritið sem notað er í dag) er mjög stórt forrit og getur 
verið flókið í notkun fyrir byrjendur. Einstaklingar í hópnum hafa heyrt golfara kvarta mikið undan 
þessu appi og vildu gera einfaldari útgáfu. 

Við viljum ekki hafa óþarfa eiginleika í forritinu sem að flækir einfalt verk.


# Core features

- Skrá hring
Notandi spilar 18 holu hring og á hverri holu skráir hann skor sitt á holunni.


- Breyta skori?
Notandi spilar 18 holur og skráir samstundis skor sitt á hverri holu.
Eftir hringinn áttar notandi sig á því að hann skrifaði 30 en ekki 3 á 8. holu. 
Þá opnar notandi forritið, fer inn í hringinn og breytir skori sínu yfir í 3 og vistar.

- Forgjöf
Eftir að notandi spilaði blússandi góðan hring þá opnar hann forritið og athugar hvort 
forgjöf hans hefur lækkað. Hann fer inn á prófílin sinn og sér að hún hefur lækkað um einn.
Eftir hvern leik finnur forritið 8 bestu hringi af síðustu 20 og reiknar meðaltal þessara 8 og birtir þá tölu sem forgjöf.