
Funktionalit�t: Alarme
  Um Kurse nicht mehr zu verpassen und p�nktlicher zu sein
  kann der Benutzer Alarme zu rapla-Terminen setzen um
  zu diesem Zeitpunkt erinnert oder geweckt zu werden
  
  Szenario: Setze Alarmzeit
    Angenommen ich bin im Bildschirm 'Alarme'
      Und ich habe auf den Button zum Erstellen einer neuen Alarmzeit geklickt
      Und ich habe ein Datum eingestellt
      Und ich habe eine Uhrzeit eingestellt
    Wenn ich best�tige
    Dann wird dieser Alarm abgespeichert
  
  Szenario: Alarmiere
    Angenommen ein Alarm ist abgespeichert
      Und dieser Alarm ist aktiviert
    Wenn der eingestellte Zeitpunkt erreicht ist
    Dann ert�nt ein Alarmton
