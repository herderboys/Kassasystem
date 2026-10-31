# Kassasystem

> **Skolprojekt.** Det här är ett grupprojekt (grupp 3) i kursen INTE vid Institutionen för data- och systemvetenskap (DSV), Stockholms universitet, HT 2026. Koden är skriven för att öva på testning och är inte tänkt att användas i verkligheten.

## Vad projektet är

Ett förenklat kassasystem för en butik, skrivet i Java. Syftet med projektet är att öva på testning: testdriven utveckling, enhetstester, testdesigntekniker, mocking, kodgranskning och statisk analys. Därför finns det inget gränssnitt och ingen `main`-metod. Systemet körs bara genom testerna.

Systemet kan:

- registrera produkter med pris, moms (0, 6, 12 eller 25 %) och GTIN-streckkod
- skanna varor och lägga dem i en kundvagn
- räkna ut rabatter: fast rabatt, procentrabatt, flerköpsrabatt ("3 för 2"), medlemsrabatt och student-/seniorrabatt
- skapa ett kvitto

Belopp lagras i öre för att undvika avrundningsfel.

## Krav

- JDK 24
- Maven

## Köra testerna

```bash
mvn test
```

## Rapporter

| Rapport | Kommando | Fil |
| --- | --- | --- |
| Kodtäckning (JaCoCo) | `mvn test` | `target/site/jacoco/index.html` |
| Statisk analys (PMD) | `mvn pmd:pmd` | `target/reports/pmd.html` |

## Struktur

```
src/main/java/checkout/   systemets klasser
src/test/java/checkout/   JUnit 5-tester (vissa med Mockito)
ai-generated-tests/       tester genererade av AI-verktyg för kursens AI-moment (kompileras inte)
```

## Gruppmedlemmar

- Alexander Herder
- Albin Rosén
- Abdallah Mashally
- Hugo Lorin
