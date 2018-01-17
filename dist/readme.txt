========================== WYMAGANIA PODSTAWOWE==============================================================================
1. Do uruchomienia programu wymagana jest java runtime environment (jre) w wersji 1.8 lub wy�szej.
2. W celu sprawdzenia zainstalowanej wersji javy uruchom java.version.bat i sprawd� wynik np:

java version "1.8.0_40"
Java(TM) SE Runtime Environment (build 1.8.0_40-b25)
Java HotSpot(TM) 64-Bit Server VM (build 25.40-b25, mixed mode)

Taki wynik 1.8.0_40 jest opdpowiedni� wersj�. 
W celu pobrania javy w wersji 8 (1.8) wejd� na stron� i wybierz odpowiedni� wersj� 32 lub 64 bitow� dla Twojego systemu operacyjnego:
http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

=========================== PLIK WEJ�CIOWY ====================================================================================
1. Plikiem wej�ciowym okre�laj�cym kt�ry misi�c jest planowany oraz pracownik�w wraz z ich preferencjami jest scheduleInput.csv.
2. Plik jest w formacie csv z przecinkami jako separatorami zapisany w kodowaniu UTF-8, mo�e by� uruchomiony przez ka�dy edytor tekstowy, Microsoft Excel lub Libre Office Calc.
3. Pierwszy kolumna i wiersz w pliku okre�la miesi�c planowania w formacie YYYY-MM (np 2017-11 jako listopad 2017).
4. Nast�pnie w pierwszym rz�dzie ukazane s� informacyjnie kolumny Min (minimalna liczna dy�ur�w), Max (maksymalna liczba dy�ur�w), oraz kolejne dni miesi�ca.
5. W kolejnych rz�dach prezentowani s� pracownicy dla kt�rych ustalony b�dzie grafik.
6. Dla ka�dego pracownika mo�na wskaza� minimaln� i maksymaln� preferowan� liczb� dy�ur�w, a tak�e dni w kt�rych pracownik nie mo�e by� w grafiku za pomoc� znaku "x" w odpowiednim dniu.
7. Nale�y pami�ta� aby dw�m pracownikom z dy�urem w ostatni dzie� poprzedniego miesi�ca ustawi� znak x w pierwszy dzie� aktualnego miesi�ca.

=========================== URUCHAMIANIE PROGRAMU ========================.=====================================================
1. Otw�rz i uzupe�nij plik wej�ciowy scheduleInput.csv na dany miesi�c
2. Uruchom runSchedule.bat - klikaj�c 2 razy.
3. Poczekaj na wykonanie programu, program powinien wygenerowa� plik wyj�ciowy w formacie:schedule-YYYY-DD_DATAGENERACJI.csv np. schedule-2017-11_201711121424042.csv
4. Plik wyj�ciowy ma format csv i mo�e by� otworzyony w programie Microsoft Excel lub Libre Office Calc
5. Plik zawiera dwie sekcje: pierwsz� z planem na ka�dy dzie� oraz drug� z planem dla ka�dego pracownika wraz ze statystykami wej�ciowymi (Min, Max) jak i wyj�ciowymi - liczb� przydzielonych wszytkich, dni wolnych oraz dni tygodnia.

6. W przypadku niemo�liwo�ci wygenrowania planu, b��du pliku wej�ciowego, b��du programu plik wyj�ciowy nie zostanie wygenerowany, w danym wypadku prosz� o kontakt mailowy (bartek.stasikowski@gmail.com) za��czaj�c plik wej�ciowy oraz wygenerowany plik logu (duty-scheduler.log)

