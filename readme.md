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

### 1. Skrá notanda

- Geta nýskráð
- Geta skráð inn
- Geta skráð út

### 2. Skrá stig

- Skrá skor

### 3. Skoða stig

- Skoða par fyrir völ
- Skoða hring fyrir völl
- Skoða hring annara spilara útfrá völlum top 5 eða ehv

### 4. Uppfæra stig

- Fá hring í hendurnar

### 5. Eyða stigum

- Eyðir hring

### 6. Reikna forgjöf

- Meðaltal af bestu 8 síðustu 20 leiki
