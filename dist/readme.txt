========================== WYMAGANIA PODSTAWOWE==============================================================================
1. Do uruchomienia programu wymagana jest java runtime environment (jre) w wersji 1.8 lub wy¿szej.
2. W celu sprawdzenia zainstalowanej wersji javy uruchom java.version.bat i sprawdŸ wynik np:

java version "1.8.0_40"
Java(TM) SE Runtime Environment (build 1.8.0_40-b25)
Java HotSpot(TM) 64-Bit Server VM (build 25.40-b25, mixed mode)

Taki wynik 1.8.0_40 jest opdpowiedni¹ wersj¹. 
W celu pobrania javy w wersji 8 (1.8) wejdŸ na stronê i wybierz odpowiedni¹ wersjê 32 lub 64 bitow¹ dla Twojego systemu operacyjnego:
http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

=========================== PLIK WEJŒCIOWY ====================================================================================
1. Plikiem wejœciowym okreœlaj¹cym który misi¹c jest planowany oraz pracowników wraz z ich preferencjami jest scheduleInput.csv.
2. Plik jest w formacie csv z przecinkami jako separatorami zapisany w kodowaniu UTF-8, mo¿e byæ uruchomiony przez ka¿dy edytor tekstowy, Microsoft Excel lub Libre Office Calc.
3. Pierwszy kolumna i wiersz w pliku okreœla miesi¹c planowania w formacie YYYY-MM (np 2017-11 jako listopad 2017).
4. Nastêpnie w pierwszym rzêdzie ukazane s¹ informacyjnie kolumny Min (minimalna liczna dy¿urów), Max (maksymalna liczba dy¿urów), oraz kolejne dni miesi¹ca.
5. W kolejnych rzêdach prezentowani s¹ pracownicy dla których ustalony bêdzie grafik.
6. Dla ka¿dego pracownika mo¿na wskazaæ minimaln¹ i maksymaln¹ preferowan¹ liczbê dy¿urów, a tak¿e dni w których pracownik nie mo¿e byæ w grafiku za pomoc¹ znaku "x" w odpowiednim dniu.
7. Nale¿y pamiêtaæ aby dwóm pracownikom z dy¿urem w ostatni dzieñ poprzedniego miesi¹ca ustawiæ znak x w pierwszy dzieñ aktualnego miesi¹ca.

=========================== URUCHAMIANIE PROGRAMU ========================.=====================================================
1. Otwórz i uzupe³nij plik wejœciowy scheduleInput.csv na dany miesi¹c
2. Uruchom runSchedule.bat - klikaj¹c 2 razy.
3. Poczekaj na wykonanie programu, program powinien wygenerowaæ plik wyjœciowy w formacie:schedule-YYYY-DD_DATAGENERACJI.csv np. schedule-2017-11_201711121424042.csv
4. Plik wyjœciowy ma format csv i mo¿e byæ otworzyony w programie Microsoft Excel lub Libre Office Calc
5. Plik zawiera dwie sekcje: pierwsz¹ z planem na ka¿dy dzieñ oraz drug¹ z planem dla ka¿dego pracownika wraz ze statystykami wejœciowymi (Min, Max) jak i wyjœciowymi - liczb¹ przydzielonych wszytkich, dni wolnych oraz dni tygodnia.

6. W przypadku niemo¿liwoœci wygenrowania planu, b³êdu pliku wejœciowego, b³êdu programu plik wyjœciowy nie zostanie wygenerowany, w danym wypadku proszê o kontakt mailowy (bartek.stasikowski@gmail.com) za³¹czaj¹c plik wejœciowy oraz wygenerowany plik logu (duty-scheduler.log)

