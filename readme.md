# Hugbúnaðarverkefni 1 - HBV501G 2023

## Hópur

- Þorvaldur Tumi Baldursson - [@ofurtumi](github.com/ofurtumi)
- Hákon Ingi Rafnsson - [@hakoningir](github.com/hakoningir)
- Svana Björg Birgisdóttir - [@svanabirgis](github.com/svanabirgis)
- Gunnar Björn Þrastarson - [@gunnarbjo](github.com/gunnarbjo)

## Uppsetning

Það þarf postgres server keyrandi á porti `5432` og verkefnið sett upp á móti honum. Stillingar eru gerðar í .env skrá í rótinni, stillingarnar útskýra sig nokkuð vel sjálfar.

```bash
HUGBO_DB_URL=jdbc:postgresql://<slóð-á-postgres>
HUGBO_DB_USER=<postgres>
HUGBO_DB_PASSWORD=<postgrespassword>
HUGBO_JWT_SECRET=======================Hugbo=Og=Hinir=Lika=Spring===========================
```

Til að geta keyrt verkefnið upp þarf maven að vera sett upp á vélinni, hægt að nota `brew install maven` á Mac eða svipaða skipun á Linux.

## Keyrsla

Til að keyra upp verkefnið þarf að keyra `mvn spring-boot:run` í rót verkefnisins. Verkefnið er aðgengilegt á `localhost:8080` eftir að það hefur keyrt upp.

### Umhverfi

Í augnablikinu hefur verkefnið verið keyrt upp á eftirfarandi umhverfum:
|Stýrikerfi|Java version|Maven version|Postgres version|
|:------------------- |:--- |:--- |:---|
|MacOs Ventura 13.2.1 |20 2023-07-18 |Apache 3.9.4|15.3|
|Windows 10 |21 2023-09-19 |Apache 3.9.4|15.1|
|Fedora Linux |21 2023-09-19 |Apache 3.8.6|15.4|

## Slóðir

Hér má sjá allar núverandi slóðir sem eru í verkefninu.

| Slóð                  | Lýsing                                                           | Þarf login |
| :-------------------- | :--------------------------------------------------------------- | :--------- |
| `/`                   | Forsíða, býður upp á að skrá sig inn eða nýskrá notanda          | Nei        |
| `/`                   | Forsíða, sýnir yfirlit árangurs spilara                          | Já         |
| `/register`           | Nýskráning notanda                                               | Nei        |
| `/login`              | Innskráning notanda                                              | Nei        |
| `/courses`            | Yfirlit yfir alla velli og hringi sem hafa verið spilaðir á þeim | Nei        |
| `/courses`            | Yfirlit yfir alla velli ásamt möguleika að skrá hring fyrir völl | Nei        |
| `/round/{id}`         | Hringskráning, 18 holur                                          | Já         |
| `/round/{id}?holes=9` | Hringskráning, 9 holur                                           | Já         |
| `/round/update/{id}`  | Uppfæra holu                                                     | Já         |
| `/round/delete/{id}`  | Uppfæra holu                                                     | Já         |
