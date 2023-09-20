# Hugbúnaðarverkefni 1 - HBV501G 2023

## Hópur

- Þorvaldur Tumi Baldursson - [@ofurtumi](github.com/ofurtumi)
- Hákon Ingi Rafnsson - [@hakoningir](github.com/hakoningir)
- Svana Björg Birgisdóttir - [@svanabirgis](github.com/svanabirgis)
- Gunnar Björn Þrastarson - [@gunnarbjo](github.com/gunnarbjo)

## Uppsetning

Það þarf postgres server keyrandi á porti `5432` og verkefnið sett upp á móti honum. Stillingar eru gerðar í [þessari skrá](./src/main/resources/application.properties), stillingarnar útskýra sig nokkuð vel sjálfar.

Til að geta keyrt verkefnið upp þarf maven að vera sett upp á vélinni, hægt að nota `brew install maven` á Mac eða svipaða skipun á Linux.

## Keyrsla

Til að keyra upp verkefnið þarf að keyra `mvn spring-boot:run` í rót verkefnisins. Verkefnið er aðgengilegt á `localhost:8080` eftir að það hefur keyrt upp.

## Endapunktar

> í augnablikinu eru allir endapunktar undir `/api/players` en líklegt að það breytist á næstunni

Hægt að sjá nánari docs um endapunkta [hér á postman docs](https://documenter.getpostman.com/view/20615550/2s9YCAQAKH)
| Aðferð | Slóð | Lýsing |
|:---|:---|:---|
|`POST`|`/api/players` | býr til nýjan leikmann|
|`GET`|`/api/players` | skilar öllum leikmönnum eða leikmanni með ákveðið nafn|
|`GET`|`/api/players/active` | skilar öllum virkum/óvirkum leikmönnum|
|`GET`|`/api/players/{id}` | skilar ákveðnum leikmanni|
|`PUT`|`/api/players/{id}` | uppfærir ákveðinn leikmann|
|`PUT`|`api/players/{id}/toggle` | uppfærir virkni ákveðins leikmanns|
|`DELETE`|`/api/players/{id}` | eyðir ákveðnum leikmanni|
|`DELETE`|`/api/players` | eyðir öllum leikmönnum|
